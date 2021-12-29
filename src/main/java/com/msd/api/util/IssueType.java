package com.msd.api.util;

import java.util.HashMap;
import java.util.Map;

public enum IssueType {

  BUG("BUG"), STORY("STORY"), TASK("TASK");

  private static final Map<String, IssueType> issueTypeByValue = new HashMap<String, IssueType>();

  static {
    for (IssueType type : IssueType.values()) {
      issueTypeByValue.put(type.issueType, type);
    }
  }

  final String issueType;

  IssueType(String issueType) {
    this.issueType = issueType;
  }

  public static IssueType getIssueType(String value) {
    return issueTypeByValue.get(value);
  }
}
