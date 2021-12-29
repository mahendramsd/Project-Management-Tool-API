package com.msd.api.util;

import java.util.HashMap;
import java.util.Map;

public enum State {

  CREATED("CREATED"), OPEN("OPEN"), IN_PROGRESS("IN_PROGRESS"),TESTING("TESTING"), DEPLOY("DEPLOY");

  private static final Map<String, State> stateByValue = new HashMap<>();

  static {
    for (State type : State.values()) {
      stateByValue.put(type.state, type);
    }
  }

  final String state;

  State(String state) {
    this.state = state;
  }

  public static State getState(String value) {
    return stateByValue.get(value);
  }


}
