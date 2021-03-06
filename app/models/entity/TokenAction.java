package models.entity;

import play.data.format.Formats;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@SuppressWarnings("serial")
public class TokenAction implements Serializable {

   /**
    * Verification time frame (until the user clicks on the link in the email) in seconds Defaults to one week
    */
   public final static long VERIFICATION_TIME = 7 * 24 * 3600;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true)
   private String token;

   @ManyToOne(cascade = CascadeType.PERSIST)
   private User targetUser;

   @Enumerated(EnumType.STRING)
   private Type type;

   @Temporal(TemporalType.TIMESTAMP)
   @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date created;

   @Temporal(TemporalType.TIMESTAMP)
   @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date expires;

   @javax.persistence.Transient
   public boolean isValid() {
      return this.expires.after(new Date());
   }

   public Long getId() {
      return id;
   }

   // GETTER & SETTER //

   public void setId(Long id) {
      this.id = id;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public User getTargetUser() {
      return targetUser;
   }

   public void setTargetUser(User targetUser) {
      this.targetUser = targetUser;
   }

   public Type getType() {
      return type;
   }

   public void setType(Type type) {
      this.type = type;
   }

   public Date getCreated() {
      return created;
   }

   public void setCreated(Date created) {
      this.created = created;
   }

   public Date getExpires() {
      return expires;
   }

   public void setExpires(Date expires) {
      this.expires = expires;
   }

   public enum Type {
      EMAIL_VERIFICATION, PASSWORD_RESET
   }
}
