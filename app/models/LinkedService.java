package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sebastian Sachtleben
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"identifier", "type"}))
public class LinkedService extends BaseModel implements Serializable {


   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @Enumerated(value = EnumType.STRING)
   private ServiceType type;

   private String identifier;

   private String link;

   private String name;

   @JsonIgnore
   private String token;

   @JsonIgnore
   private Long expires;

   @JsonIgnore
   @Column(length = 2000)
   private String data;


   @JsonIgnore
   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "user_id", referencedColumnName = "id")
   private User user;

   public String typeName() {
      return type.name();
   }

   public JsonNode data() {
      return Json.parse(data);
   }


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public ServiceType getType() {
      return type;
   }

   public void setType(ServiceType type) {
      this.type = type;
   }

   public String getIdentifier() {
      return identifier;
   }

   public void setIdentifier(String identifier) {
      this.identifier = identifier;
   }

   public String getLink() {
      return link;
   }

   public void setLink(String link) {
      this.link = link;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public Long getExpires() {
      return expires;
   }

   public void setExpires(Long expires) {
      this.expires = expires;
   }

   public String getData() {
      return data;
   }

   public void setData(String data) {
      this.data = data;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   @Override
   public String toString() {
      return "LinkedService [id=" + id + ", type=" + type + ", identifier=" + identifier + ", token=" + token + ", expires=" + expires
              + "]";
   }

}
