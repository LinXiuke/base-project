package com.zero.common.core.errors;

import com.zero.common.base.result.CommonException;
import com.zero.common.base.result.CommonResult;
import com.zero.common.base.result.ErrorCode;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Description: 全局异常处理
 */
@ControllerAdvice
public class ExceptionTranslator {

//    @ResponseBody
//    @ExceptionHandler(ConcurrencyFailureException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorVM processConcurencyError(ConcurrencyFailureException ex) {
//        return new ErrorVM("409 Conflict");
//    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult processValidationError(MethodArgumentNotValidException e) {
//        BindingResult result = e.getBindingResult();
//        List<FieldError> fieldErrors = result.getFieldErrors();

//        return processFieldErrors(fieldErrors);

        return new CommonResult(ErrorCode.REQUEST_ERROR);
    }

//    private ErrorVM processFieldErrors(List<FieldError> fieldErrors) {
//        ErrorVM errorVM = Objects.nonNull(fieldErrors.get(0)) ? new ErrorVM("error validation", fieldErrors.get(0)
//                .getDefaultMessage()) : new ErrorVM("error.validation");
//
//        for (FieldError fieldError : fieldErrors) {
//            errorVM.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
//        }
//
//        return errorVM;
//    }


    @ResponseBody
    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult processInvalidTokenException(CommonException e) {
        return new CommonResult(e.getErrorCode());
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult processAccessDeniedException(AccessDeniedException e) {
        return new CommonResult(ErrorCode.NOT_ACCESSIBLE);
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public CommonResult processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new CommonResult(ErrorCode.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResult> processRuntimeException(Exception e) {
        ResponseEntity.BodyBuilder builder;
        CommonResult commonResult;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            commonResult = new CommonResult(responseStatus.value().value() + "", responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            commonResult = new CommonResult(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return builder.body(commonResult);
    }
}
