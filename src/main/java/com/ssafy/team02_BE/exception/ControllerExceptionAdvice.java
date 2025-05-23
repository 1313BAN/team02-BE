package com.ssafy.team02_BE.exception;

import com.ssafy.team02_BE.common.dto.ApiResponse;
import com.ssafy.team02_BE.exception.model.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 컨트롤러 레벨에서 발생한 예외를 처리
 */
@RestControllerAdvice
//@Slf4j
public class ControllerExceptionAdvice {

    /**
     * 400 Bad Request
     */

    /**
     * 요청 바디(JSON, XML 등 메시지 포맷이 서버가 기대하는 형식과 맞지 않을 때
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(
                ApiResponse.error(ErrorCode.INVALID_REQUEST_BODY),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * 요청 시 필수 쿼리 파라미터 또는 폼 파라미터를 안 보냈을 때
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameterException(final MissingServletRequestParameterException exception) {
        return new ResponseEntity<>(
                ApiResponse.error(ErrorCode.NOT_EXIST_PARAMETER),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * DTO에 대한 validation 실패 (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(" ");
        }

        return new ResponseEntity<>(
                ApiResponse.error(ErrorCode.BAD_REQUEST_EXCEPTION, sb.toString()),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * GET, POST, PUT 등 잘못된 메서드로 요청
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException exception) {
        return new ResponseEntity<>(
                ApiResponse.error(ErrorCode.NOT_SUPPORTED_METHOD),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    /**
     * 자격 증명 실패 시
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(
                ApiResponse.error(ErrorCode.BAD_CREDENTIALS),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Custom Error - 직접 발생시킨 예외에 대해 응답 형식을 맞추기 위함
     */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponse<Void>> handleCustomException(final CustomException exception) {
        return new ResponseEntity<>(
                ApiResponse.error(exception.getErrorCode()),
                exception.getErrorCode().getCode()
        );
    }
}