package com.tiansen.ordermanager.exception;

/** 这里继承RuntimeException异常 **/
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 2332608236621015980L;
	private String stateCode;

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public BusinessException() {
	}
	public BusinessException(String stateCode) {
		this.stateCode=stateCode;
	}
	public BusinessException(String stateCode,String message) {
		super(message);
		this.stateCode=stateCode;
	}
	public BusinessException(Throwable cause) {
	    super(cause);
	}

	public BusinessException(String message, Throwable cause) {
	    super(message, cause);
	}
}
