package top.daishengli.lightning.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST API 通用控制器
 *
 * @author daishengli
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求成功
     *
     * @param data 数据内容
     * @param <T>  对象泛型
     * @return ignore
     */
    protected <T> CommonResponse<T> success(T data) {
        return CommonResponse.success(data);
    }

    /**
     * 请求成功
     *
     * @param data 数据内容
     * @param <T>  对象泛型
     * @return ignore
     */
    protected <T> CommonResponse<T> success(T data, String message) {
        return CommonResponse.success(data, message);
    }

    /**
     * 请求失败
     *
     * @param msg 提示内容
     * @return ignore
     */
    protected <T> CommonResponse<T> failed(String msg) {
        return CommonResponse.failed(msg);
    }

    /**
     * 自定义返回内容
     *
     * @param code    HTTP状态码{@link cn.hutool.http.HttpStatus}
     * @param message 提示内容
     * @param data    数据内容
     * @param <T>     对象泛型
     * @return ignore
     */
    protected <T> CommonResponse<T> custom(Integer code, String message, T data) {
        return new CommonResponse<T>(code, message, data);
    }
}
