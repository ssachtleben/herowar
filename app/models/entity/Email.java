package models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Sebastian Sachtleben
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "idx_mail", columnNames = { "address" }))
public class Email extends BaseModel implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Required(groups = { All.class, NoUser.class })
   @play.data.validation.Constraints.Email
   @Column(unique = true)
   private String address;

   @Required(groups = { All.class, NoUser.class })
   private Boolean main = false;

   @Required(groups = { All.class, NoUser.class })
   private Boolean confirmed = false;

   @Column(unique = true)
   private String confirmCode;

   @Required(groups = { All.class })
   @JsonIgnore
   @ManyToOne
   @JoinColumn(name = "user_id", referencedColumnName = "id")
   private User user;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public Boolean getMain() {
      return main;
   }

   public void setMain(Boolean main) {
      this.main = main;
   }

   public Boolean getConfirmed() {
      return confirmed;
   }

   public void setConfirmed(Boolean confirmed) {
      if (confirmed) {
         setConfirmCode(null);
      }
      this.confirmed = confirmed;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public String getConfirmCode() {
      return confirmCode;
   }

   public void setConfirmCode(String confirmCode) {
      this.confirmCode = confirmCode;
   }

   @Override
   public String toString() {
      return "Email [id=" + id + ", address=" + address + ", main=" + main + ", confirmed=" + confirmed + "]";
   }

   public interface All {
   }

   public interface NoUser {
   }

}
