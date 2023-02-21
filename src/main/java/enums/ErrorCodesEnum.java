package enums;

public enum ErrorCodesEnum {
    // 999 - General error codes
    GENERAL_ERROR_ILLEGAL_ARGUMENT(999001, "Illegal argument."), GENERAL_ERROR_BAD_REQUEST(999002, "Bad request."),
    GENERAL_ERROR_METHOD_NOT_SUPPORTED(999003, "Method not supported."),
    GENERAL_ERROR_SERVICE_UNAVAILABLE(999004, "External service unavailable."),
    GENERAL_ERROR_MEDIA_TYPE_NOT_SUPPORTED(999005, "Media type not supported."),
    GENERAL_ERROR_MEDIA_TYPE_NOT_ACCEPTABLE(999006, "Media type not acceptable."),
    GENERAL_ERROR_MISSING_SERVLET_PARAMETER(999007, "Missing servlet parameter."),
    GENERAL_ERROR_MISSING_PATH_VARIABLE(999008, "Missing path variable."),
    GENERAL_ERROR_TYPE_MISMATCH(999009, "Type mismatch."),
    GENERAL_ERROR_HTTP_MESSAGE_NOT_READABLE(999010, "Http message not readable."),
    GENERAL_ERROR_HTTP_MESSAGE_NOT_WRITABLE(999011, "Http message not writable."),
    GENERAL_ERROR_BIND_EXCEPTION(999012, "Bind exception."),
    GENERAL_ERROR_SERVLET_REQUEST_BIND_EXCEPTION(999013, "Servlet request bind exception"),
    GENERAL_ERROR_NO_HANDLER_FOUND(9990014, "No handler found."),
    GENERAL_ERROR_VALIDATION_FAILED(999015, "Validation failed."),
    GENERAL_UNEXPECTED_ERROR(999999, "Unexpected error.");

    private int code;
    private String message;

    ErrorCodesEnum(int c, String m) {
        code = c;
        message = m;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}