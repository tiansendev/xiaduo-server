package com.tiansen.ordermanager.exception;

/**
 * @author tiansen
 */
public class ParameterIllegalException extends RuntimeException {

    private static final long serialVersionUID = 8197086462208138875L;
    private String msg;

    public ParameterIllegalException() {

    }

    public ParameterIllegalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
