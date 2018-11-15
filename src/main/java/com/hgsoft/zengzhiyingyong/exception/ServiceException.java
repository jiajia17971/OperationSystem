package com.hgsoft.zengzhiyingyong.exception;

/**
 * 定义Service层异常
 * Created by hegc on 2016-04-04.
 */
public class ServiceException extends ApplicationException
{

    public ServiceException() {
    }

    public ServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ServiceException(Enum clazz, Throwable cause, Object[] args) {
        super(clazz, cause, args);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Enum clazz, Object[] args) {
        super(clazz, args);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
