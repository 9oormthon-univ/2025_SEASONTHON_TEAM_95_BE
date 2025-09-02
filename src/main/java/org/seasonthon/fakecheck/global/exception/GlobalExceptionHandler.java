package org.seasonthon.fakecheck.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.seasonthon.fakecheck.global.exception.dto.ErrorStatus;
import org.seasonthon.fakecheck.global.exception.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RootException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(RootException e) {
        ErrorStatus errorStatus = e.getErrorStatus();
        log.error("커스텀 예외: 상태코드 - {}, 메세지 - {}", errorStatus.getStatusCode(), errorStatus.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .statusCode(errorStatus.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(errorStatus.getStatusCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("예외 발생: {}", e.getMessage());

        int statusCode = INTERNAL_SERVER_ERROR.value();
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(statusCode)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        int statusCode = e.getStatusCode().value();
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(statusCode)
                .message("유효하지 않은 파라미터입니다.")
                .build();

        e.getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            log.error("유효성 검사 실패 - 필드: {}, 메시지: {}", field, errorMessage);
            response.addValidation(field, errorMessage);
        });

        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException e) {
        int statusCode = BAD_REQUEST.value();
        String paramName = e.getParameter().getParameterName();
        String paramType = e.getParameter().getParameterType().getSimpleName();
        String detailMessage = e.getMessage();
        String message = "[" + paramName + "] 파라미터는 " + paramType + " 타입이어야 합니다. 상세: " + detailMessage;

        ErrorResponse response = ErrorResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .build();

        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        int statusCode = BAD_REQUEST.value();
        String paramName = e.getParameterName();
        String paramType = e.getParameterType();
        String message = paramType + " 타입의" + " [ " + paramName + " ] " + "파라미터가 누락되었습니다.";

        ErrorResponse response = ErrorResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .build();

        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeader(MissingRequestHeaderException ex) {
        int statusCode = BAD_REQUEST.value();
        String headerName = ex.getHeaderName();
        String message = headerName + " 헤더가 요청에 포함되어야 합니다.";

        ErrorResponse error = ErrorResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .build();

        return ResponseEntity.status(statusCode).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleRequestBodyMissing(HttpMessageNotReadableException ex) {
        int statusCode = BAD_REQUEST.value();
        String message = "요청 바디가 올바르지 않거나 누락되었습니다: " + ex.getMessage();

        ErrorResponse error = ErrorResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .build();

        return ResponseEntity.status(statusCode).body(error);
    }

}