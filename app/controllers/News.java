package controllers;

import play.data.Form;
import play.db.jpa.JPA;
import play.mvc.Result;

import static play.libs.Json.toJson;

public class News extends BaseAPI<Long, models.News> {

    public News() {
        super(Long.class, models.News.class);
    }

    public Result list() {
        return listAll();
    }

    public Result show(final Long id) {
        return showEntry(id);
    }

    public Result update(final Long id) {
        // models.entity.News news = instance.findUnique(id);
        models.News news = JPA.em().merge(Form.form(models.News.class).bindFromRequest().get());
        return ok(toJson(news));
    }

    public Result delete(final Long id) {
        return deleteEntry(id);
    }

    public Result add() {
        return addEntry();
    }
}