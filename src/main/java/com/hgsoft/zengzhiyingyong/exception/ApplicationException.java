package com.hgsoft.zengzhiyingyong.exception;

import java.text.MessageFormat;

/**
 * 定义系统级别异常
 * Created by hegc on 2016-04-04.
 */
public class ApplicationException extends RuntimeException
{
    public ApplicationException()
    {
    }

    public ApplicationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ApplicationException(Enum clazz, Throwable cause, Object[] args) {
        this(MessageFormat.format(clazz.toString(), args), cause);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Enum clazz, Object[] args) {
        super(MessageFormat.format(clazz.toString(), args));
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}

