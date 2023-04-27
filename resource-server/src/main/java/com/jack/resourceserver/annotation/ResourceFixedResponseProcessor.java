package com.jack.resourceserver.annotation;

import com.jack.clientauthority.utils.FixedResponse;
import com.jack.utils.web.R;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * resource server提供的接口，需要增加固定的格式给resource server前端页面，以及client调用
 */
@Slf4j
@Aspect
@Component
public class ResourceFixedResponseProcessor {

    @Pointcut("execution(public com.jack.utils.web.R com.jack.resourceserver.controller..*.*(..))")
    public void pointcut() {}

    /**
     * AfterReturning只切入方法成功执行，如果方法抛出异常（包括业务异常）则不管。
     */
    @AfterReturning(value = "pointcut()", returning = "resp")
    public void afterReturning(R resp) {
        if (resp.getCode() == R.getCodeOk()) {
            resp.put(FixedResponse.FIXED_CODE, FixedResponse.CODE_OK);
        } else {
            resp.put(FixedResponse.FIXED_CODE, FixedResponse.CODE_ERROR);
        }

        resp.put(FixedResponse.FIXED_MSG, resp.getMsg());
        resp.put(FixedResponse.FIXED_DATA, resp.getData());
    }
}
