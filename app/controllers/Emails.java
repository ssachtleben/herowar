package controllers;

import models.entity.Email;

/**
 * @author Alexander Wilhelmer
 */
public class Emails extends BaseAPI<Long, Email> {

   public Emails() {
      super(Long.class, Email.class);
   }

}
