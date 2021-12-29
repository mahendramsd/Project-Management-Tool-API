package com.msd.api.util;

import java.util.HashMap;
import java.util.Map;

public enum ChangeLogType {

  CREATE("CREATE"), ASSIGN("ASSIGN"), STATE("STATE");

  private static final Map<String, ChangeLogType> issueTypeByValue = new HashMap<String, ChangeLogType>();

  static {
    for (ChangeLogType type : ChangeLogType.values()) {
      issueTypeByValue.put(type.changeLogType, type);
    }
  }

  final String changeLogType;

  ChangeLogType(String issueType) {
    this.changeLogType = issueType;
  }

  public static ChangeLogType getIssueType(String value) {
    return issueTypeByValue.get(value);
  }
}
