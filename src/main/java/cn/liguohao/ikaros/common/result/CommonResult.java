package cn.liguohao.ikaros.common.result;

import cn.liguohao.ikaros.common.Assert;
import cn.liguohao.ikaros.common.JacksonConverter;
import cn.liguohao.ikaros.common.Strings;
import cn.liguohao.ikaros.common.kit.TimeKit;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author guohao
 * @date 2022/09/07
 */
public class CommonResult<T> extends BaseResult<T> implements Serializable {

    private static final String DEFAULT_SUCCESS_MSG = "SUCCESS";
    private static final String DEFAULT_FAIL_MSG = "FAIL";

    private CommonResult() {
    }

    private CommonResult(boolean success, String code,
                         String message, String timestamp,
                         T data, Throwable throwable) {
        this.setSuccess(success);
        this.setCode(code);
        this.setMessage(message);
        this.setTimestamp(timestamp);
        this.setData(data);
        this.setThrowable(throwable);
    }

    private static <T> CommonResult<T> baseCreate(String code, String msg, T data,
                                                  Throwable throwable) {
        Assert.isNotBlank(code);
        CommonResult<T> result = new CommonResult<T>();
        result.setCode(code);
        result.setSuccess(Objects.equals(code, ResultCode.SUCCESS));
        result.setMessage(msg);
        result.setData(data);
        result.setTimestamp(TimeKit.nowTimestamp());
        result.setThrowable(throwable);
        return result;
    }

    public static <T> CommonResult<T> ok(String code, String msg, T data) {
        if (code == null) {
            code = ResultCode.OTHER_EXCEPTION;
        }
        if (Strings.isBlank(msg)) {
            msg = DEFAULT_SUCCESS_MSG;
        }
        return baseCreate(code, msg, data, null);
    }

    public static <T> CommonResult<T> ok(String code, T date) {
        return ok(code, DEFAULT_SUCCESS_MSG, date);
    }

    public static <T> CommonResult<T> ok(T date) {
        return ok(ResultCode.SUCCESS, date);
    }

    public static <T> CommonResult<T> ok() {
        return ok(null);
    }

    public static <T> CommonResult<T> ok(String message) {
        return ok(ResultCode.SUCCESS, message, null);
    }


    public static <T> CommonResult<T> fail(String code, String msg, T data, Throwable throwable) {
        if (code == null) {
            code = ResultCode.OTHER_EXCEPTION;
        }
        if (Strings.isBlank(msg)) {
            msg = DEFAULT_FAIL_MSG;
        }
        return baseCreate(code, msg, data, throwable);
    }

    public static <T> CommonResult<T> fail(String code, String msg, T date) {
        return fail(code, msg, date, null);
    }

    public static <T> CommonResult<T> fail(String code, Throwable throwable) {
        return fail(code, DEFAULT_FAIL_MSG, null, throwable);
    }

    public static <T> CommonResult<T> fail(String code, T data) {
        return fail(code, DEFAULT_FAIL_MSG, data);
    }

    public static <T> CommonResult<T> fail(T date) {
        return fail(ResultCode.SUCCESS, date);
    }

    public static <T> CommonResult<T> fail(String msg) {
        return ok(ResultCode.OTHER_EXCEPTION, msg, null);
    }

    @Override
    public String toString() {
        return JacksonConverter.obj2Json(this);
    }
}