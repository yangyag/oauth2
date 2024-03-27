package com.yangyag.yangyagoauth.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends RuntimeException {

    private final ErrorCode errorCode;

    public TokenException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    // 여기서 ErrorCode는 HTTP 상태 코드와 함께 추가적인 정보를 제공하는 열거 타입입니다.
    // 필요에 따라 다음과 같이 구현할 수 있습니다.
    public enum ErrorCode {
        INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid Token"),
        EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Expired Token");

        private final HttpStatus httpStatus;
        private final String errorMessage;

        ErrorCode(HttpStatus httpStatus, String errorMessage) {
            this.httpStatus = httpStatus;
            this.errorMessage = errorMessage;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
