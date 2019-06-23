package com.crm.common.exception;

import com.crm.common.bo.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaohaifeng
 * 2018-06-05
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 全局处理Exception 错误的情况下返回500
     *
     * @param ex                 ex
     * @param request            request
     * @param httpServletRequest httpServletRequest
     * @return com.crm.common.bo.JsonResult
     * @author huhong
     * @date 2019-06-22 16:27
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public JsonResult handleOtherExceptions(final Exception ex, final WebRequest request, final
    HttpServletRequest httpServletRequest) {
        if (ex instanceof BusinessException) {
            LOGGER.error(ex.getMessage());
        }else {
            LOGGER.error(ex.getMessage(), ex);
        }
        if (ex instanceof BusinessException) {
            BusinessException exception = (BusinessException) ex;
            JsonResult result = new JsonResult(exception.getCode(), exception.getMsg());
            return result;
        } else if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            List<ObjectError> list = bindingResult.getAllErrors();
            List<String> errorMsg = new ArrayList<>();
            for (ObjectError objectError : list) {
                FieldError fieldError = (FieldError) objectError;
                StringBuffer message = new StringBuffer();
                message.append("字段：");
                message.append(fieldError.getField());
                message.append(" 异常：");
                message.append(fieldError.getDefaultMessage());
                errorMsg.add(message.toString());

            }
            JsonResult result = new JsonResult();
            return result.setMsg(StringUtils.join(errorMsg, "|")).setCode(500);
        } else if (ex instanceof ConstraintViolationException) {
            //对验证异常的处理javax.validation.ConstraintViolationException
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;
            return new JsonResult(500).setMsg(constraintViolationException.getMessage());
        } else if (ex instanceof NoHandlerFoundException) {
            return new JsonResult(404).setMsg(ex.getMessage());
        } else {
            return new JsonResult(500).setMsg("服务端异常");
        }
    }

}
