package com.polar_moviechart.movieservice.exception;

public class MovieBusinessException extends RuntimeException {
    private static final String msg = "예기치 못한 오류가 발생했습니다." + "\n" + "불편을 드려 죄송합니다.";
    private ErrorCode errorCode;

    public MovieBusinessException() {
        super(msg);
    }

    public MovieBusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public MovieBusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MovieBusinessException(Throwable cause) {
        super(msg, cause);
    }

    public String getCode() {
        return errorCode.getCode();
    }
}
