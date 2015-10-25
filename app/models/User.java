package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sebastian Sachtleben
 */
@Entity
@Table(name = "users")
public class User extends BaseModel implements Serializable {


   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotNull
   private String username;
   @NotNull
   private String lastName;
   @NotNull
   private String firstName;

   private boolean newsletter = false;

   private boolean active = true;

   private boolean emailValidated = false;

   private String avatar;

   @JsonIgnore
   private String lastIp;

   @NotNull
   private Long posts = 0L;

   @JsonIgnore
   private Date lastLogin = null;

   @OneToMany(mappedBy = "user")
   private List<Email> emails;

   @OneToMany(mappedBy = "user")
   private List<LinkedService> linkedServices;

   @ManyToMany(cascade = CascadeType.ALL)
   @JsonIgnore
   private List<UserPermission> permissions;
   @ManyToMany(cascade = CascadeType.ALL)

   @JsonIgnore
   private List<SecurityRole> roles;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public boolean isNewsletter() {
      return newsletter;
   }

   public void setNewsletter(boolean newsletter) {
      this.newsletter = newsletter;
   }

   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public boolean isEmailValidated() {
      return emailValidated;
   }

   public void setEmailValidated(boolean emailValidated) {
      this.emailValidated = emailValidated;
   }

   public String getAvatar() {
      return avatar;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   public String getLastIp() {
      return lastIp;
   }

   public void setLastIp(String lastIp) {
      this.lastIp = lastIp;
   }

   public Long getPosts() {
      return posts;
   }

   public void setPosts(Long posts) {
      this.posts = posts;
   }

   public Date getLastLogin() {
      return lastLogin;
   }

   public void setLastLogin(Date lastLogin) {
      this.lastLogin = lastLogin;
   }

   public List<Email> getEmails() {
      return emails;
   }

   public void setEmails(List<Email> emails) {
      this.emails = emails;
   }

   public List<LinkedService> getLinkedServices() {
      return linkedServices;
   }

   public void setLinkedServices(List<LinkedService> linkedServices) {
      this.linkedServices = linkedServices;
   }


   public List<SecurityRole> getRoles() {
      return roles;
   }

   public void setRoles(List<SecurityRole> roles) {
      this.roles = roles;
   }

   public List<UserPermission> getPermissions() {
      if (permissions == null)
         permissions = new ArrayList<UserPermission>();
      return permissions;
   }

   public void setPermissions(List<UserPermission> permissions) {
      //TODO
      this.permissions = permissions;
   }


   @Override
   public String toString() {
      return "User [id=" + id + ", username=" + username + "]";
   }


}