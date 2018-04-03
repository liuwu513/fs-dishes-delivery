package com.fs.dishes.base.exceptions;

/**
 * 自定义异常
 * Created by liuwu on 2018/2/28 0028.
 */
public class FDDException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    
    public FDDException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public FDDException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public FDDException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public FDDException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
