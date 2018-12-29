package com.tiansen.ordermanager.exception;

import com.alibaba.fastjson.JSON;
import com.tiansen.ordermanager.common.code.ServiceResultCode;
import com.tiansen.ordermanager.common.model.ServiceResult;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tiansen
 */
@CommonsLog
@RestControllerAdvice
class ExceptionHandlerControllerAdvice {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 捕捉所有Shiro异常
     * @param e
     * @return
     */
    @ExceptionHandler(ShiroException.class)
    public ServiceResult handle401(HttpServletRequest request, ShiroException e) {
        logError(request, e);
        return ServiceResult.failure(ServiceResultCode.PERMISSION_NO_ENOUGH.getCode(), e.getMessage());
    }

    /**
     * 单独捕捉Shiro(UnauthorizedException)异常
     * 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ServiceResult handle401(HttpServletRequest request, UnauthorizedException e) {
        logError(request, e);
        return ServiceResult.failure(ServiceResultCode.PERMISSION_NO_ENOUGH.getCode(), e.getMessage());
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     * @param e
     * @return
     */

    @ExceptionHandler(UnauthenticatedException.class)
    public ServiceResult handle401(HttpServletRequest request, UnauthenticatedException e) {
        logError(request, e);
        return ServiceResult.failure(ServiceResultCode.PERMISSION_NO_ENOUGH.getCode(), e.getMessage());
    }

    /**
     * 404
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ServiceResult<?> resourceNotFoundExceptionHandler(HttpServletRequest request, ResourceNotFoundException e) {
        logError(request, e);
        return ServiceResult.failure(ServiceResultCode.RESOURCE_UNFIND.getCode(), e.getMessage());
    }

    /**
     * 参数异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ParameterIllegalException.class)
    public ServiceResult<?> parameterIllegalExceptionHandler(HttpServletRequest request, ParameterIllegalException e) {
        logError(request, e);
        return ServiceResult.failure(ServiceResultCode.PARAM_EXCEPTION.getCode(), e.getMessage());
    }

    /**
     * 捕捉校验异常(BindException)
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ServiceResult validException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return ServiceResult.failure(ServiceResultCode.PARAM_EXCEPTION.getCode(), JSON.toJSONString(result));
    }

    /**
     * 捕捉校验异常(MethodArgumentNotValidException)
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ServiceResult validException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return ServiceResult.failure(ServiceResultCode.PARAM_EXCEPTION.getCode(), JSON.toJSONString(result));
    }

    /**
     * 500
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ServerInternalErrorException.class)
    public ServiceResult<?> serverInternalErrorExceptionHandler(HttpServletRequest request, ServerInternalErrorException e) {
        logError(request, e);
        return ServiceResult.failure(ServiceResultCode.SYSTEM_EXCEPTION.getCode(), e.getMessage());
    }

    /**
     * 未知错误
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ServiceResult<?> exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        e.printStackTrace();
        logError(request, e);
        return ServiceResult.failure(ServiceResultCode.SYSTEM_EXCEPTION.getCode(), e.getMessage());
    }

    /**
     * 业务异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ServiceResult<?> businessExceptionHandler(HttpServletRequest request, BusinessException e) {
        logError(request, e);
        if (StringUtils.isBlank(e.getMessage()))
            return ServiceResult.failure(e.getStateCode());
        else
            return ServiceResult.failure(e.getStateCode(), e.getMessage());
    }

    /********************************** HELPER METHOD **********************************/
    /**
     * 获取校验错误信息
     * @param fieldErrors
     * @return
     */
    private Map<String, Object> getValidError(List<FieldError> fieldErrors){
        Map<String, Object> result = new HashMap<String, Object>(16);
        List<String> errorList = new ArrayList<String>();
        StringBuffer errorMsg = new StringBuffer("校验异常(ValidException):");
        for (FieldError error : fieldErrors){
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField() + "-" + error.getDefaultMessage() + ".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }

    private void logError(HttpServletRequest request, Exception e) {
        logger.error("[URI: " + request.getRequestURI() + "]"
                + "[error msg: " + e.getMessage() + "]");
    }

}
