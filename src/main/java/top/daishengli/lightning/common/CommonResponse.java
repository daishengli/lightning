package top.daishengli.lightning.common;

import cn.hutool.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 通用返回
 *
 * @author daishengli
 */
public class CommonResponse<T> {

    /**
     * 状态码
     */
    @Schema(description = "状态码")
    private Integer code;

    /**
     * 消息
     */
    @Schema(description = "提示消息")
    private String message;

    /**
     * 数据
     */
    @Schema(description = "数据")
    private T data;

    /**
     * 获取 状态码
     *
     * @return code 状态码
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * 设置 状态码
     *
     * @param code 状态码
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取 消息
     *
     * @return message 消息
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 设置 消息
     *
     * @param message 消息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取 数据
     *
     * @return data 数据
     */
    public T getData() {
        return this.data;
    }

    /**
     * 设置 数据
     *
     * @param data 数据
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 通用构造器
     */
    protected CommonResponse() {
    }

    /**
     * 构造器
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     */
    protected CommonResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<T>(HttpStatus.HTTP_OK, "SUCCESS", data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<T>(HttpStatus.HTTP_OK, message, data);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResponse<T> failed(String message) {
        return new CommonResponse<T>(HttpStatus.HTTP_INTERNAL_ERROR, message, null);
    }

    /**
     * 未授权返回结果
     *
     * @param message 未授权消息提示
     */
    public static <T> CommonResponse<T> unauthorized(String message) {
        return new CommonResponse<T>(HttpStatus.HTTP_UNAUTHORIZED, message, null);
    }

    /**
     * 无权限返回结果
     *
     * @param message 无权限消息提示
     */
    public static <T> CommonResponse<T> forbidden(String message) {
        return new CommonResponse<T>(HttpStatus.HTTP_UNAUTHORIZED, message, null);
    }

}
