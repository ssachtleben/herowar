package models.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sebastian Sachtleben
 */
@Entity
public class SecurityRole implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String roleName;

   @ManyToOne(cascade = CascadeType.REFRESH)
   private User user;

   public String getName() {
      return roleName;
   }

   // GETTER & SETTER //

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getRoleName() {
      return roleName;
   }

   public void setRoleName(String roleName) {
      this.roleName = roleName;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }
}
