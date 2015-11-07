package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.EnvironmentDAO;
import dao.GeometryDAO;
import dao.TowerDAO;
import dao.UnitDAO;
import models.entity.game.*;
import play.Play;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Result;
import utils.MaterialComparator;

import java.util.Collections;

import static play.libs.Json.toJson;

/**
 * The Geometries controller handle api requests for the Geometry model.
 *
 * @author Sebastian Sachtleben
 */
public class Geometries extends BaseAPI<Long, Geometry> {

    public Geometries() {
        super(Long.class, Geometry.class);
    }

    @Transactional
    public Result show(Long id) {
        final GeometryDAO geometryDAO = Play.application().injector().instanceOf(GeometryDAO.class);
        final Geometry geo = geometryDAO.findUnique(id);
        handleGeo(geo);
        return ok(toJson(geo));
    }

    @Transactional
    public Result showByEnv(Long id) {
        // response().setHeader(EXPIRES, "Thu, 16 Feb 2023 20:00:00 GMT");
        final EnvironmentDAO environmentDAO = Play.application().injector().instanceOf(EnvironmentDAO.class);
        Environment env = environmentDAO.getById(id);
        if (env == null || env.getGeometry() == null) {
            return badRequest("No result found");
        }
        Geometry geo = env.getGeometry();
        handleGeo(geo);
        JsonNode node = toJson(geo);
        return ok(node);
    }

    @Transactional
    public Result showByUnit(Long id) {
        // response().setHeader(EXPIRES, "Thu, 16 Feb 2023 20:00:00 GMT");
        final UnitDAO unitDAO = Play.application().injector().instanceOf(UnitDAO.class);
        Unit unit = unitDAO.getById(id);
        if (unit == null || unit.getGeometry() == null) {
            return badRequest("No result found");
        }
        Geometry geo = unit.getGeometry();
        handleGeo(geo);
        JsonNode node = toJson(geo);
        return ok(node);
    }

    @Transactional
    public Result showByTower(Long id) {
        // response().setHeader(EXPIRES, "Thu, 16 Feb 2023 20:00:00 GMT");
        final TowerDAO towerDAO = Play.application().injector().instanceOf(TowerDAO.class);
        Tower tower = towerDAO.getById(id);
        if (tower == null || tower.getGeometry() == null) {
            return badRequest("No result found");
        }
        Geometry geo = tower.getGeometry();
        handleGeo(geo);
        JsonNode node = toJson(geo);
        return ok(node);
    }

    private void handleGeo(Geometry geo) {
        final GeometryDAO geometryDAO = Play.application().injector().instanceOf(GeometryDAO.class);
        geometryDAO.mapMaterials(geo); // For global binding ... TODO
        geo.getMaterials().clear();
        for (GeoMaterial geoMap : geo.getGeoMaterials()) {
            geo.getMaterials().add(geoMap.getId().getMaterial());
        }
        Collections.sort(geo.getMaterials(), new MaterialComparator());
    }

    @Transactional
    public Result update(Long id) {
        Geometry geometry = findUnique(id);
        geometry = JPA.em().merge(geometry);
        return ok(toJson(geometry));
    }

}

