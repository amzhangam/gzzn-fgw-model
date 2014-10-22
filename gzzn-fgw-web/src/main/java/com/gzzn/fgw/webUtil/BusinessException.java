package com.gzzn.fgw.webUtil;

/**
 * 
 * @author ChengZhi 2013-4-28下午4:27:24
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 0xc1a865c45ffdc5f9L;

	public BusinessException(String frdMessage){
		super(frdMessage);
	}

	public BusinessException(Throwable throwable){
		super(throwable);
	}

	public BusinessException(Throwable throwable, String frdMessage) {
		super(throwable);
	}

	private static String createFriendlyErrMsg(String msgBody) {
		StringBuffer friendlyErrMsg = new StringBuffer("");
		friendlyErrMsg.append(msgBody);
		return msgBody;
	}

}
