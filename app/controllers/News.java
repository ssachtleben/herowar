package controllers;

import play.data.Form;
import play.db.jpa.JPA;
import play.mvc.Result;

import static play.libs.Json.toJson;

public class News extends BaseAPI<Long, models.entity.News> {

   public News() {
      super(Long.class, models.entity.News.class);
   }

   public Result update(final Long id) {
      // models.entity.News news = instance.findUnique(id);
      models.entity.News news = JPA.em().merge(Form.form(models.entity.News.class).bindFromRequest().get());
      return ok(toJson(news));
   }

}