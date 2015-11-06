package controllers;

import models.entity.game.Object3D;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Result;

import static play.libs.Json.toJson;

/**
 * The Objects controller handle api requests for the Object3D model.
 *
 * @author Sebastian Sachtleben
 */
public class Objects extends BaseAPI<Long, Object3D> {

    public Objects() {
        super(Long.class, Object3D.class);
    }

    @Transactional
    public Result update(Long id) {
        // Object3D object = instance.findUnique(id);
        final Object3D object = JPA.em().merge(Form.form(Object3D.class).bindFromRequest().get());
        return ok(toJson(object));
    }

}

