package com.zero.common.core.errors;

import com.zero.common.base.result.CommonException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Objects;

/**
 * @Description: 全局异常处理
 */
@ControllerAdvice
public class ExceptionTranslator {

    @ResponseBody
    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorVM processConcurencyError(ConcurrencyFailureException ex) {
        return new ErrorVM("409 Conflict");
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorVM processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private ErrorVM processFieldErrors(List<FieldError> fieldErrors) {
        ErrorVM dto = Objects.nonNull(fieldErrors.get(0)) ? new ErrorVM("error validation", fieldErrors.get(0)
                .getDefaultMessage()) : new ErrorVM("error.validation");

        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }

        return dto;
    }


    @ResponseBody
    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorVM processInvalidTokenException(CommonException e) {
        return new ErrorVM(e.getErrorCode().getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorVM processAccessDeniedException(AccessDeniedException e) {
        return new ErrorVM("403 Forbidden");
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorVM processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new ErrorVM("405 Method Not Allowed");
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorVM> processRuntimeException(Exception ex) {
        ResponseEntity.BodyBuilder builder;
        ErrorVM errorVM;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorVM = new ErrorVM("error." + responseStatus.value().value(), responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorVM = new ErrorVM("500 Internal Server Error");
        }
        return builder.body(errorVM);
    }
}
