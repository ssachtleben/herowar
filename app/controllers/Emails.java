package controllers;

import models.Email;

/**
 * @author Alexander Wilhelmer
 */
public class Emails extends BaseAPI<Long, Email> {
   public Emails() {
      super(Long.class, models.Email.class);
   }


}
