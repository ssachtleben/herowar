package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin;

/**
 * Handles all routes from the /admin area.
 *
 * @author Sebastian Sachtleben
 */
public class Admin extends Controller {

    public Result index() {
        return ok(admin.render());
    }

    public Result login() {
        return index();
    }

    public Result userAll() {
        return index();
    }

    public Result userShow(Long id) {
        return index();
    }

    public Result mapAll() {
        return index();
    }

    public Result mapNew() {
        return index();
    }

    public Result mapShow(Long id) {
        return index();
    }

    public Result objectAll() {
        return index();
    }

    public Result objectNew() {
        return index();
    }

    public Result objectShow(Long id) {
        return index();
    }

    public Result newsAll() {
        return index();
    }

    public Result newsNew() {
        return index();
    }

    public Result newsShow(Long id) {
        return index();
    }

}
