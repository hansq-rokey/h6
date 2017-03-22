package com.papabear.dis.exception;

/**
 * 补贴卷相关信息不能为空异常类
 * @author 赵磊
 * @date 2016年4月7日
 *
 */
public class ClaimException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2303052077054185744L;

	public ClaimException() {
		super();
	}

	public ClaimException(String message) {
		super(message);
	}

	public ClaimException(Throwable cause) {
		super(cause);
	}

	public ClaimException(String message, Throwable cause) {
		super(message, cause);
	}

}
