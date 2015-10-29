package dao;

import models.entity.Email;

/**
 * Handles all database queries for the {@link Email} entity.
 *
 * @author Sebastian Sachtleben
 */
public class EmailDAO extends BaseDAO<Long, Email> {

   public EmailDAO() {
      super(Long.class, Email.class);
   }

   public Email findByAddress(String address) {
      return getSingleByPropertyValue("address", address);
   }

}