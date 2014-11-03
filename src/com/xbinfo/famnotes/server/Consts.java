package com.xbinfo.famnotes.server;

public class Consts {

	
	
	public final static String CFG_PATH=System.getProperty("user.dir")+"/conf/";
	public final static String DB_CFG_FILE=CFG_PATH+"c3p0.properties";
	public final static String LOG4J_FILE=CFG_PATH+"log4j_fmtposgtw.properties";
	public static final String FMT_HOST_GTW_CONF_FILE=CFG_PATH+"fmthostgtw.properties";
	public final static String LOCAL_ERR_INFO_FILE=CFG_PATH+"local_errinfo.properties";
	public final static String OtcpCore8583_INFO_FILE=CFG_PATH+"otcpCore8583.properties";
	public final static String OtcpCore8583_XML_MAP_FILE=CFG_PATH+"otcpCore8583XmlMap.properties";
	public static final String DEFAULT_CHARSET = "GBK";
	public static final int FAIL=1;
	public static final int SUCC=0;
	public final static String TX_CONF= CFG_PATH+"txconf.properties";
	public static final int NEEDCHECKPAY=1;//�Ƿ���Ҫ���˱�־λ
	
	
	public static final int MSG8583_HEAD_LEN = 4;
}
