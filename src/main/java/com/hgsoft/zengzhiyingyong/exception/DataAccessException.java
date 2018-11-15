package com.hgsoft.zengzhiyingyong.exception;

/**
 * 定义系统访问级别异常
 * Created by hegc on 2016-04-04.
 */
public class DataAccessException extends ApplicationException
{
    public DataAccessException()
    {
    }

    public DataAccessException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DataAccessException(Enum clazz, Throwable cause, Object[] args) {
        super(clazz, cause, args);
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Enum clazz, Object[] args) {
        super(clazz, args);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
