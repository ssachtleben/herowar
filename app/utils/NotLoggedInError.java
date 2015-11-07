package utils;

import models.ApiError;

public class NotLoggedInError extends ApiError {

   public NotLoggedInError() {
      super(80025, "Not logged in", "Please login to do this action.");
   }

}