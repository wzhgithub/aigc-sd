package com.yiyun.ai.controller;

import com.yinyun.ai.common.constance.ServiceConstance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonResponse> AllExceptionHandle(Exception exception) {
        String message = exception.getMessage();
        log.error("catch err:{}", message);
        CommonResponse body = new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
        return ResponseEntity.internalServerError().body(body);
    }

    @Data
    @AllArgsConstructor
    public static class CommonResponse {
        int code;
        String message;
        long responseTs;
        String requestId;

        public CommonResponse(int code, String message) {
            this(code, message, System.currentTimeMillis(), MDC.get(ServiceConstance.RequestId.name()));
        }
    }
}
