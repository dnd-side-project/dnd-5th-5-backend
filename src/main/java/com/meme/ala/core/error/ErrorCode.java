package com.meme.ala.core.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "Method Not Allowed"),
    ENTITY_NOT_FOUND(400, "C003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // JWT
    EXPIRED_JWT(403, "J001", "Expired Jwt"),
    UNSUPPORTED_JWT(403, "J002", "Unsupported Jwt"),
    SIGNATURE_INVALID_JWT(403, "JOO3", "Signature Invalid Jwt"),
    JWT_NOT_FOUND(403, "J004", "Jwt Not Found"),
    AUTHENTICATION_FAILED(403, "J005", "Authentication Failed"),

    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),

    // Friend
    ALREADY_FRIEND(400, "F001", "Already Friend"),
    NOT_FRIEND(400, "F002", "Not Friend"),
    NOT_FOLLOWING(400, "F003", "Not Following"),
    NOT_FOLLOWER(400, "F004", "Not Follower"),
    NOT_DEFAULT(400, "F005", "Not Default"),

    // AlaCard
    INVALID_COOKIE(400, "A001", "Invalid Cookie"),
    BACKGROUND_NOT_FOUND(400, "A002", "Background Not Found"),
    ALACARD_NOT_FOUND(400, "A003", "AlaCard Not Found");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }


}