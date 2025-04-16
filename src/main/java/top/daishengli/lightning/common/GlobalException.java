package top.daishengli.lightning.common;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author daishengli
 **/
@RestControllerAdvice
public class GlobalException {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 全局异常拦截（拦截项目中的所有异常）
     *
     * @param e 错误
     * @return 错误响应
     */
    @ExceptionHandler
    public CommonResponse<String> handlerException(Exception e) {
        logger.error("发生错误", e);
        // 不同异常返回不同状态码
        String errorMessage;
        // 如果是未登录异常
        if (e instanceof NotLoginException ee) {
            errorMessage = ee.getMessage();
            return CommonResponse.unauthorized(errorMessage);
        }
        // 如果是角色异常
        else if (e instanceof NotRoleException ee) {
            errorMessage = "无此角色：" + ee.getRole();
            return CommonResponse.forbidden(errorMessage);
        }
        // 如果是权限异常
        else if (e instanceof NotPermissionException ee) {
            errorMessage = "无此权限：" + ee.getPermission();
            return CommonResponse.forbidden(errorMessage);
        }
        // 如果是被封禁异常
        else if (e instanceof DisableServiceException) {
            errorMessage = "账号被封禁：" + ((DisableServiceException) e).getDisableTime() + "秒后解封";
            return CommonResponse.unauthorized(errorMessage);
        }
        // 普通异常, 输出：500 + 异常信息
        else {
            errorMessage = e.getMessage();
        }
        return CommonResponse.failed(errorMessage);
    }

    /**
     * 拦截项目中的NotLoginException异常
     *
     * @param nle 未登录异常
     * @return 未登录异常信息
     */
    @ExceptionHandler(NotLoginException.class)
    public CommonResponse<String> handlerNotLoginException(NotLoginException nle) {
        String message = switch (nle.getType()) {
            case NotLoginException.NOT_TOKEN -> "未提供token";
            case NotLoginException.INVALID_TOKEN -> "token无效";
            case NotLoginException.TOKEN_TIMEOUT -> "token已过期";
            case NotLoginException.BE_REPLACED -> "token已被顶下线";
            case NotLoginException.KICK_OUT -> "token已被踢下线";
            default -> "当前会话未登录";
        };
        return CommonResponse.unauthorized(message);
    }
}
