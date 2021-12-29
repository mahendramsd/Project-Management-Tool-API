package com.msd.api.exception;


import com.msd.api.util.CustomErrorCodes;

public class CustomException extends RuntimeException {

  private CustomErrorCodes customErrorCodes;

  public CustomException(String message) {
    super(message);
  }


  public CustomException(String message, Throwable cause) {
    super(message, cause);
  }

  public CustomException(CustomErrorCodes customErrorCodes) {
    this.customErrorCodes = customErrorCodes;
  }

  public CustomErrorCodes getCustomErrorCodes() {
    return customErrorCodes;
  }

}
