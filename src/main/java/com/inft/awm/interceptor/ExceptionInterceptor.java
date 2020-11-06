package com.inft.awm.interceptor;


import com.inft.awm.response.Result;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
/**
 * Handle to Unified return format if there is a exception
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
@RestControllerAdvice
public class ExceptionInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionInterceptor.class);


    @ExceptionHandler
    public Result handleException(HttpServletRequest request, HttpServletResponse response, final Exception e) {
//        LOG.error(e.getMessage(), e);
//        if (e instanceof AlertException) {//可以在前端Alert的异常
//            if (((AlertException) e).getRetCode() != null) {//预定义异常
//                return new Result(((AlertException) e).getRetCode());
//            } else {
//                return new Result(1, e.getMessage() != null ? e.getMessage() : "");
//            }
//        } else {//其它异常
//            if (Util.isProduct()) {//如果是正式环境，统一提示
//                return new Result(RetCode.ERROR);
//            } else {//测试环境，alert异常信息
//                return new Result(1, StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.toString());
//            }
//        }
        if (e instanceof ConstraintViolationException) {//ConstraintViolationException
            //cast exception
            ConstraintViolationException exception = (ConstraintViolationException) e;
            //Get constraintViolations
            final Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> str : constraintViolations) {
                System.out.println(str.getMessageTemplate());
                sb.append(str.getMessageTemplate());
                sb.append("/n");
            }
            //Unified return format
            return new Result(1 ,sb.toString());
        }
        return new Result(1,e.getMessage());
    }
}
