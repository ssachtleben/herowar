package controllers;

import models.entity.game.GeometryType;
import models.entity.game.Map;
import play.mvc.Result;
import views.html.editor;

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

}
