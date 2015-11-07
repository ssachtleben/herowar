package dao;

import com.google.inject.Inject;
import models.entity.game.*;
import models.entity.game.Map;
import play.Logger;
import play.db.jpa.JPA;

import java.util.*;

public class MapDAO extends BaseDAO<Long, Map> {
   private static final Logger.ALogger log = Logger.of(MapDAO.class);

   @Inject
   private PathDAO pathDAO;

   @Inject
   private GeometryDAO geometryDAO;

   @Inject
   private UnitDAO unitDAO;

   @Inject
   private MeshDAO meshDAO;

   public MapDAO() {
      super(Long.class, Map.class);
   }


   public Map create(String name, String description, int teamSize) {
      final Map map = new Map();
      map.setName(name);
      map.setDescription(description);
      map.setTeamSize(teamSize);
      return map;
   }

   public void merge(final Map map, final Map map2) {
      map.setName(map2.getName());
      map.setDescription(map2.getDescription());
      map.setTeamSize(map2.getTeamSize());
      JPA.em().persist(map);
   }

   public Map getMapByName(String name) {
      return this.getSingleByPropertyValue("name", name);
   }

   public Map getMapById(Long id) {
      return this.findUnique(id);
   }

   /**
    * For loading the MatGeoId.materialId is the real DB id
    *
    * @param map
    */
   public void mapMaterials(Map map) {
      Geometry geo = map.getTerrain().getGeometry();
      map.setMaterials(new ArrayList<Material>());
      if (map.getAllMaterials() != null) {
         for (Material mat : map.getAllMaterials()) {
            mat.setMaterialId(mat.getId());
            map.getMaterials().add(mat);
         }

         Collections.sort(map.getMaterials(), new Comparator<Material>() {
            @Override
            public int compare(Material o1, Material o2) {
               return o1.getSortIndex().compareTo(o2.getSortIndex());
            }

         });
         geometryDAO.mapMaterials(geo);
      }
   }

   public void mapStaticGeometries(Map map) {
      map.setStaticGeometries(new ArrayList<Geometry>());
      for (Mesh mesh : map.getObjects()) {
         mesh.setGeoId(mesh.getGeometry().getId());
         if (!map.getStaticGeometries().contains(mesh.getGeometry()))
            mesh.getGeometry().setMaterials(new ArrayList<Material>());
         for (GeoMaterial geoMap : mesh.getGeometry().getGeoMaterials()) {
            mesh.getGeometry().getMaterials().add(geoMap.getId().getMaterial());
         }
         geometryDAO.mapMaterials(mesh.getGeometry());
         map.getStaticGeometries().add(mesh.getGeometry());
      }
   }

   public void mapWaves(Map map) {
      for (Wave wave : map.getWaves()) {
         if (wave.getPath() != null)
            wave.setPathId(wave.getPath().getId());
         wave.setUnitIds(new ArrayList<Long>());
         for (Unit unit : wave.getUnits()) {
            wave.getUnitIds().add(unit.getId());
         }
      }

   }

   public void createWaves(Map map, java.util.Map<Long, Path> newPaths) {
      Set<Wave> waves = new HashSet<Wave>();
      for (Wave wave : map.getWaves()) {
         wave.setMap(map);
         Path path = null;
         if (wave.getPathId() != null && wave.getPathId().longValue() > -1) {
            path = pathDAO.findUnique(wave.getPathId());
         } else if (wave.getPathId() != null && newPaths != null) {
            path = newPaths.get(wave.getPathId());
         }
         log.info("saving wave with pathId " + wave.getPathId());
         wave.setPath(path);
         wave.setUnits(new HashSet<Unit>());
         if (wave.getUnitIds() != null) {
            for (Long unitId : wave.getUnitIds()) {
               Unit unit = unitDAO.findUnique(unitId);
               if (unit != null)
                  wave.getUnits().add(unit);
            }
         }
         if (wave.getId() != null && wave.getId().longValue() > -1) {
            wave = JPA.em().merge(wave);
         } else {
            wave.setId(null);
         }
         waves.add(wave);
      }

      map.setWaves(waves);

   }

   public java.util.Map<Long, Path> createPaths(Map map) {
      java.util.Map<Long, Path> result = new HashMap<Long, Path>();
      Set<Path> paths = new HashSet<Path>();
      for (Path path : map.getPaths()) {
         path.setMap(map);
         Set<Waypoint> waypoints = new HashSet<Waypoint>();
         for (int i = 0; i < path.getWaypoints().size(); i++) {
            Waypoint waypoint = path.getWaypoints().get(i);
            waypoint.setSortOder(i);
            waypoint.setPath(path);
            if (waypoint.getId() != null && waypoint.getId().longValue() > -1) {
               waypoint = JPA.em().merge(waypoint);
            } else {
               waypoint.setId(null);
            }
            waypoints.add(waypoint);
         }

         path.setDbWaypoints(waypoints);
         if (path.getId() != null && path.getId().longValue() > -1) {
            path = JPA.em().merge(path);
         } else {
            result.put(path.getId(), path);
            path.setId(null);

         }
         paths.add(path);
      }

      map.setPaths(paths);

      return result;
   }

   public void createMeshes(Map map) {
      Set<Mesh> meshes = new HashSet<Mesh>();
      for (Mesh mesh : map.getObjects()) {
         mesh.setMap(map);
         if (mesh.getGeometry().getId() != null) {
            mesh.setGeometry(geometryDAO.findUnique(mesh.getGeometry().getId()));
         }
         if (mesh.getId() == null || mesh.getId().intValue() < 0) {
            mesh.setId(null);
            JPA.em().persist(mesh);
         } else {
            mesh = meshDAO.merge(mesh);
         }
         meshes.add(mesh);
      }

      map.setObjects(meshes);

   }

   public void mapPaths(Map map) {
      for (Path path : map.getPaths()) {
         pathDAO.mapWaypoints(path);
      }

   }

   public void mapAll(Map map) {
      mapMaterials(map);
      mapStaticGeometries(map);
      mapWaves(map);
      mapPaths(map);

   }

}
