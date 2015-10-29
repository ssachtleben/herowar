package models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.UserPermission;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * @author Alexander Wilhelmer
 */
@Entity
@Table(name = "users")
public class User extends BaseModel implements Serializable {


   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotNull
   private String username;

   private String lastName;

   private String firstName;

   @NotNull
   private String password;

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


   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   private Set<Email> emails;

   @OneToMany(mappedBy = "user")
   private Set<LinkedService> linkedServices;

   @ManyToMany(cascade = CascadeType.ALL)
   @JsonIgnore
   private Set<UserPermission> permissions;

   @ManyToMany(cascade = CascadeType.ALL)
   @JsonIgnore
   private Set<SecurityRole> roles;

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

   public Set<Email> getEmails() {
      if (emails == null)
         emails = new HashSet<Email>();
      return emails;
   }

   public void setEmails(Set<Email> emails) {
      this.emails = emails;
   }

   public Set<LinkedService> getLinkedServices() {
      return linkedServices;
   }

   public void setLinkedServices(Set<LinkedService> linkedServices) {
      this.linkedServices = linkedServices;
   }


   public Set<SecurityRole> getRoles() {
      if (roles == null)
         roles = new HashSet<SecurityRole>();
      return roles;
   }

   public void setRoles(Set<SecurityRole> roles) {
      this.roles = roles;
   }

   public Set<UserPermission> getPermissions() {
      if (permissions == null)
         permissions = new HashSet<UserPermission>();
      return permissions;
   }

   public void setPermissions(Set<UserPermission> permissions) {
      //TODO
      this.permissions = permissions;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   @Override
   public String toString() {
      return "User [id=" + id + ", username=" + username + "]";
   }


}