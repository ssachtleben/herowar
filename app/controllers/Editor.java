package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.EnvironmentDAO;
import dao.GeometryDAO;
import dao.MapDAO;
import dao.MaterialDAO;
import json.excludes.MeshExcludeGeometryMixin;
import models.entity.game.*;
import play.Play;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Result;
import utils.EnvironmentComparator;
import views.html.editor;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static play.libs.Json.toJson;

public class Editor extends BaseController {

    public Result index() {
        return ok(editor.render());
    }

    public Result mapDefault() {
        Map map = new Map();
        map.getTerrain().getGeometry().setType(GeometryType.TERRAIN);
        map.getTerrain().getGeometry().getMetadata().setGeneratedBy("WorldEditor");
        return ok(toJson(map));
    }

    @Transactional
    public Result mapShow(Long id) {
        final MapDAO mapDAO = Play.application().injector().instanceOf(MapDAO.class);
        Map map = mapDAO.getMapById(id);
        if (map != null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.addMixInAnnotations(Mesh.class, MeshExcludeGeometryMixin.class);
            mapDAO.mapAll(map);
            try {
                return ok(mapper.writeValueAsString(map));
            } catch (IOException e) {
                log().error("Failed to serialize root environment:", e);
            }

            return badRequest("Unexpected error occurred");
        }
        return notFound();
    }

    @Transactional
    public Result envShow(Long id) {
        final EnvironmentDAO environmentDAO = Play.application().injector().instanceOf(EnvironmentDAO.class);
        List<Environment> envs = environmentDAO.getEnvWithGeometries(id);
        Collections.sort(envs, new EnvironmentComparator());
        return ok(toJson(envs));
    }

    @BodyParser.Of(value = BodyParser.Json.class, maxLength = 52428800)
    @Transactional
    public Result addMap() {
        log().info("Saving MAP");
        if (request().body().isMaxSizeExceeded()) {
            return badRequest("Too much data!");
        }
        JsonNode mapNode = request().body().asJson();
        if (mapNode == null) {
            return badRequest("Failed to parse json request");
        }
        ObjectMapper mapper = new ObjectMapper();
        Map map = null;
        try {
            map = mapper.readValue(mapper.writeValueAsString(mapNode), Map.class);
        } catch (IOException e) {
            String errorMessage = "Failed to parse request data to entity";
            log().error(errorMessage, e);
            return badRequest(errorMessage);
        }
        if (map == null || !isValid(map)) {
            String errorMessage = "Failed to parse request data to entity";
            return badRequest(errorMessage);
        }
        saveMap(map);
        // Flushing for new Id
        JPA.em().flush();
        log().info(String.format("Map Id <%s> successfully saved!", map.getId()));
        return ok(toJson(map.getId()));
    }

    private boolean isValid(Map map) {
        if (map == null || map.getTerrain() == null || map.getTerrain().getGeometry() == null
                || map.getTerrain().getGeometry().getMetadata() == null) {
            return false;
        }
        return true;
    }

    private void saveMap(Map map) {
        final MapDAO mapDAO = Play.application().injector().instanceOf(MapDAO.class);

        if (map.getTerrain().getGeometry().getMetadata().getGeometry() == null) {
            map.getTerrain().getGeometry().getMetadata().setGeometry(map.getTerrain().getGeometry());
        }
        if (map.getTerrain().getGeometry().getType() == null) {
            map.getTerrain().getGeometry().setType(GeometryType.TERRAIN);
        }
        if (map.getTerrain().getMap() == null) {
            map.getTerrain().setMap(map);
        }
        if (map.getObjects() != null) {
            mapDAO.createMeshes(map);
        }

        java.util.Map<Long, Path> newPaths = null;
        if (map.getPaths() != null) {
            newPaths = mapDAO.createPaths(map);
        }
        if (map.getWaves() != null) {
            mapDAO.createWaves(map, newPaths);
        }

        // For saving MatGeoId.materialId is the index of map.getMaterials()!
        java.util.Map<Integer, Material> matMap = saveMaterials(map);
        saveGeometryMaterials(map.getTerrain().getGeometry(), matMap);
        if (map.getId() == null) {
            JPA.em().persist(map);
        } else {
            map = JPA.em().merge(map);
        }

    }

    // Mapping Indexes
    private void saveGeometryMaterials(Geometry geometry, java.util.Map<Integer, Material> mapping) {
        if (geometry.getMatIdMapper() != null) {
            if (geometry.getGeoMaterials() == null) {
                geometry.setGeoMaterials(new HashSet<GeoMaterial>());
            }
            final GeometryDAO geometryDAO = Play.application().injector().instanceOf(GeometryDAO.class);
            geometryDAO.createGeoMaterials(geometry, mapping);
        }
    }

    private java.util.Map<Integer, Material> saveMaterials(Map map) {
        if (map.getAllMaterials() == null) {
            map.setAllMaterials(new HashSet<Material>());
        }
        final MaterialDAO materialDAO = Play.application().injector().instanceOf(MaterialDAO.class);
        java.util.Map<Integer, Material> result = materialDAO.mapAndSave(map.getMaterials());
        for (Material mat : result.values()) {
            map.getAllMaterials().add(mat);
        }
        return result;
    }

}
