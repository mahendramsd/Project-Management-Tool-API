package com.msd.api.exception;

import com.msd.api.util.DynamicErrorCodes;

public class DynamicException extends RuntimeException {

  private DynamicErrorCodes dynamicErrorCodes;

  public DynamicException(DynamicErrorCodes dynamicErrorCodes) {
    this.dynamicErrorCodes = dynamicErrorCodes;
  }

  public DynamicErrorCodes getDynamicErrorCodes() {
    return dynamicErrorCodes;
  }
}
