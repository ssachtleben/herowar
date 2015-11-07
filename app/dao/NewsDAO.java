package dao;


import controllers.Application;
import models.entity.News;
import models.entity.User;
import play.db.jpa.JPA;

public class NewsDAO extends BaseDAO<Long, News> {

   public NewsDAO() {
      super(Long.class, News.class);
   }


   public void merge(News news, News news2) {
      news.setHeadline(news2.getHeadline());
      news.setText(news2.getText());
   }

   public News create(String headline, String text) {
      return create(headline, text, Application.getLocalUser());
   }

   public News create(String headline, String text, User author) {
      final News news = new News();
      news.setHeadline(headline);
      news.setText(text);
      news.setAuthor(author);
      JPA.em().persist(news);
      return news;
   }

   public long getNewsCount() {
      return this.getBaseCount();
   }

}
