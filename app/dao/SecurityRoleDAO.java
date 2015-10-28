package dao;

import models.SecurityRole;


public class SecurityRoleDAO extends BaseDAO<Long, SecurityRole> {

   public SecurityRoleDAO() {
      super(Long.class, SecurityRole.class);
   }

   public SecurityRole findByRoleName(String roleName) {
      return this.getSingleByPropertyValue("roleName", roleName);
   }

}