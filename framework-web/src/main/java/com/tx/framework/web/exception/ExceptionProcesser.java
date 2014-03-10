package com.tx.framework.web.exception;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.tx.framework.common.mapper.JsonMapper;
import com.tx.framework.web.entity.UUIDEntity;
import com.tx.framework.web.manage.errlog.ServiceExceptionLogger;
import com.tx.framework.web.webservice.errlog.RestExceptionLogger;

/**
 * 只处理方法未处理的异常，如果方法try catch了所有异常则不会进入此切面
 * 1、若业务/rest服务抛出unchecked exception则在这里转换为自定义的ServiceException或RestException(二者都继承runtimeException)
 * 2、若业务/rest服务遇到checked exception应try catch为对应的ServiceException或RestException抛出，不要传递到controller
 * @author tangx
 *
 */
@Aspect
@Component
public class ExceptionProcesser {
	
    /**
     * rest服务切入点
     */
    @Pointcut("execution(* com.tx.framework.web.webservice..*.*(..))")
	private void restMethod() {
	}
    
    /**
     * 业务服务切入点
     */
    @Pointcut("execution(* com.tx.framework.web.manage..*.*(..))")
	private void serviceMethod() {
	}
    
    /**
     * 记录日志并转换为rest异常
     * @param ex
     */
    @AfterThrowing(throwing="ex",pointcut="restMethod()")  
    public void toRestException(JoinPoint jp, Throwable ex){  
    	RestExceptionLogger.error(jp.toShortString(), parseParameters(jp), ex);
        throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }  
    
    /**
     * 记录日志并转换为业务异常
     * @param ex
     */
    @AfterThrowing(throwing="ex",pointcut="serviceMethod()")  
    public void toServiceException(JoinPoint jp, Throwable ex){  
    	ServiceExceptionLogger.error(jp.toShortString(), parseParameters(jp), ex);
        throw new ServiceException(ex.getMessage());
    }  
    
    private String parseParameters(JoinPoint jp) {
		MethodSignature signature = (MethodSignature) jp.getSignature();
		String[] parameterNames = signature.getParameterNames();
		Object[] parameterValues = jp.getArgs();
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < parameterValues.length; i++) {
			Object value = parameterValues[i];
			String name = parameterNames[i];
			if (value != null && value instanceof UUIDEntity) {
				sb.append(name + ":" + JsonMapper.nonEmptyMapper().toJson(value) + ";");
			}
			if (value != null) {
				if(value instanceof Long 
						|| value instanceof Integer 
						|| value instanceof String
						|| value instanceof List){
					sb.append(name + ":" + value + ";");
				}
			}
		}
		return sb.toString();
	}
  
}
