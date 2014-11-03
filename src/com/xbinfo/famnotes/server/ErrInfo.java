package com.xbinfo.famnotes.server;

public class ErrInfo {
	public static int ERR_UNKNOW=-9999;//δ֪����

	private String errNo;
	private String errMsg;

	public ErrInfo()
	{
		errNo=""+ERR_UNKNOW;
		errMsg="";
	}
	public ErrInfo(String inErrNo,String inErrMsg)
	{
		errNo=inErrNo;
		errMsg=inErrMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrNo() {
		return errNo;
	}

	public void setErrNo(String errNo) {
		this.errNo = errNo;
	}



}
