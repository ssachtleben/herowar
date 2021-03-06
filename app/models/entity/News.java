package models.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sebastian Sachtleben
 */
@Entity
@SuppressWarnings("serial")
public class News extends BaseModel implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String headline;

   @Column(length = 2000)
   private String text;

   @ManyToOne(cascade = CascadeType.REFRESH)
   private User author;

   // GETTER & SETTER //

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getHeadline() {
      return headline;
   }

   public void setHeadline(String headline) {
      this.headline = headline;
   }

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public User getAuthor() {
      return author;
   }

   public void setAuthor(User author) {
      this.author = author;
   }

}