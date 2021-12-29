package com.msd.api.util;

import org.springframework.http.HttpStatus;

public enum CustomErrorCodes {
	USER_DISABLED(1000, "USER_DISABLED", HttpStatus.BAD_REQUEST),
	INVALID_CREDENTIALS(1001, "INVALID_CREDENTIALS", HttpStatus.BAD_REQUEST),
	USER_NOT_FOUND(1002, "User Not Found", HttpStatus.NOT_FOUND),
	PROJECT_NOT_FOUND(1003, "Project Id Not found", HttpStatus.NOT_FOUND),
	ISSUE_NOT_FOUND(1004, "Issue Id Not Found", HttpStatus.NOT_FOUND),
	USER_ALREADY_EXIST(1005, "username or email already exist", HttpStatus.CONFLICT),
	USER_TYPE_NOT_FOUND(1006, "user type not found ", HttpStatus.NOT_FOUND);

	private final int id;
	private final String msg;
	private final HttpStatus httpCode;

	CustomErrorCodes(int id, String msg, HttpStatus httpCode) {
		this.id = id;
		this.msg = msg;
		this.httpCode = httpCode;
	}

	public int getId() {
		return this.id;
	}

	public String getMsg() {
		return this.msg;
	}

	public HttpStatus getHttpCode() {
		return this.httpCode;
	}

}
