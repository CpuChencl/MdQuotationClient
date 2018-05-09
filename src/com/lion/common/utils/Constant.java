package com.lion.common.utils;

import java.util.ArrayList;

public class Constant {
	
	/*redis key prefix */
	/* 行情缓存 */
	public static String PRE_KEY_MARKETDATA = "marketdata:";
	
	public static String PRE_KEY_ERRCODE = "errcode:";
	
	public static String PRE_KEY_STATICVALUE = "staticvalue:";
	
	public static String PRE_KEY_STOCKCODE_NAME = "stockcodeName:";
	
	public static String PRE_KEY_SORTZDF = "sortzdf:";
	
	public static String PRE_KEY_SORTDPTURN = "sortdpturn:";
	
	public static String PRE_KEY_MINDATA = "mindata:"; 
	
	public static String PRE_KEY_STOCKINFO = "stockinfo:";
	
	public static String PRE_KEY_INDUSTRYINFO = "industryinfo:";
	
	public static String PRE_KEY_INDUSTRYMEMLIST = "industrymemlist:";
	
	public static String REDIS_KEY_STOCKLIST = "stocklist";
	
	public static String REDIS_KEY_INDUSTRYLIST = "industrylist";
	
	public static String INDUSTRYINFO_MEM_KEY_INDUSTRYNAME = "industryname";

	public static String SUF_KEY_LOADFLAG = "loadflag";

	public static String STOCKINFO_MEM_KEY_STOCKNAME = "stockname";
	
	public static String STOCKINFO_MEM_KEY_PB = "pb";
	
	public static String STOCKINFO_MEM_KEY_PE = "pe";
	
	public static String STOCKINFO_MEM_KEY_MVTOTAL = "mvtotal";
	
	public static String STOCKINFO_MEM_KEY_MVFLOAT = "mvfloat";
	
	public static String STOCKINFO_MEM_KEY_ASSETSTODAY = "assetstoday";
	
	public static String STOCKINFO_MEM_KEY_SHRTODAY = "shrtoday";
 	
	public static String STOCKINFO_MEM_KEY_LASTPRICE = "lastprice";
	
	public static String STOCKINFO_MEM_KEY_PRECLOSEP = "preclosep";
	
	public static String STOCKINFO_MEM_KEY_OP = "op";
	
	public static String STOCKINFO_MEM_KEY_HP = "hp";
	
	public static String STOCKINFO_MEM_KEY_LP = "lp";
	
	public static String STOCKINFO_MEM_KEY_VOL = "vol";
	
	public static String STOCKINFO_MEM_KEY_TURNOVER = "turnover";
	
	public static String STOCKINFO_MEM_KEY_UPPERLIMITP = "upperlimitp";
	
	public static String STOCKINFO_MEM_KEY_LOWERLIMITP = "lowerlimitp";
	
	public static String STOCKINFO_MEM_KEY_UPDATETIME = "updatetime";
	
	public static String STOCKINFO_MEM_KEY_B1 = "b1";
	
	public static String STOCKINFO_MEM_KEY_BV1 = "bv1";
	
	public static String STOCKINFO_MEM_KEY_B2 = "b2";
	
	public static String STOCKINFO_MEM_KEY_BV2 = "bv2";
	
	public static String STOCKINFO_MEM_KEY_B3 = "b3";
	
	public static String STOCKINFO_MEM_KEY_BV3 = "bv3";
	
	public static String STOCKINFO_MEM_KEY_B4 = "b4";

	public static String STOCKINFO_MEM_KEY_BV4 = "bv4";
	
	public static String STOCKINFO_MEM_KEY_B5 = "b5";
	
	public static String STOCKINFO_MEM_KEY_BV5 = "bv5";
	
	public static String STOCKINFO_MEM_KEY_A1 = "a1";
	
	public static String STOCKINFO_MEM_KEY_AV1 = "av1";
	
	public static String STOCKINFO_MEM_KEY_A2 = "a2";
	
	public static String STOCKINFO_MEM_KEY_AV2 = "av2";
	
	public static String STOCKINFO_MEM_KEY_A3 = "a3";
	
	public static String STOCKINFO_MEM_KEY_AV3 = "av3";
	
	public static String STOCKINFO_MEM_KEY_A4 = "a4";

	public static String STOCKINFO_MEM_KEY_AV4 = "av4";
	
	public static String STOCKINFO_MEM_KEY_A5 = "a5";
	
	public static String STOCKINFO_MEM_KEY_AV5 = "av5";
	
	public static String STOCKINFO_MEM_KEY_ACTIONDAY = "actionday";
	
	public static String STOCKINFO_MEM_KEY_ZDE = "zde";
	
	public static String STOCKINFO_MEM_KEY_ZDF = "zdf";
	
	public static String STOCKINFO_MEM_KEY_ZF = "zf";
	
	public static String STOCKINFO_MEM_KEY_DPTURN = "dpturn";
	
	public static String SORT_ZDF = "sortzdfset";
	
	public static String SORT_DPTURN = "sortdpturnset";
	
	public static String STOCKINFO_MEM_KEY_LOCALTIME = "localtime";
	
	public static String STOCKINFO_MEM_KEY_FENSHIALL = "fenshiall";
	
	/*HUNDSUN FRC KEY */
	public static String PRE_HSFRC_KEY_LOGIN = "hsfrctrade_userLogin:";
	
	public static String HSFRC_KEY_SET_OPE_NO = "hsfrctrade_operator_set";
	
	public static String HSFRC_KEY_OPERATOR_NO = "operator_no";
	
	public static String HSFRC_KEY_PASSWORD = "password";
	
	public static String HSFRC_KEY_TOKEN = "token";
	
	public static String HSFRC_KEY_ACCOUNT_CODE = "account_code";
	
	public static String HSFRC_KEY_ASSET_NO = "asset_no";
	
	/*HUNDSUN KEY */
	public static String PRE_HS_KEY_LOGIN = "hstrade_userLogin:";
	
	public static String HS_KEY_ACCOUNT_CONTENT = "account_content";
	
	public static String HS_KEY_BRANCH_NO = "branch_no";
	
	public static String HS_KEY_FUND_ACCOUNT = "fund_account";
	
	public static String HS_KEY_PASSWORD = "password";
	
	
	public static final ArrayList GET_USERLOGININFO_KEYS = new ArrayList(){{
		add("session");
		add("uid");
	}
	};
	
	public static final ArrayList GET_TRADERLOGININFO_KEYS = new ArrayList(){{
		add("session");
		add("uid");
		add("password");
		add("account_id");
	}
	};
	
	public static final ArrayList GET_HSFRCACCOUNT_KEYS = new ArrayList(){{
		add(HSFRC_KEY_TOKEN);
		add(HSFRC_KEY_ACCOUNT_CODE);
		add(HSFRC_KEY_ASSET_NO);
	}
	};
	
	public static final ArrayList GET_HSACCOUNT_KEYS = new ArrayList(){{
		add(HS_KEY_BRANCH_NO);
		add(HS_KEY_FUND_ACCOUNT);
		add(HS_KEY_PASSWORD);
	}
	};
	
	/* SESSION KEY */
	
	/** 验证码手�? */
	public static final String VALIDATE_PHONE = "validate_phone";
	
	/** 验证码手�? */
	public static final String VALIDATE_PHONE_CODE = "validate_phone_code";
	
	/** 验证码手�? */
	public static final String VALIDATE_PHONE_TIME = "validate_phone_time";
	
	/** user session */
	public static final String USER_SESSION = "user_session";
	
	public static final String KEY_SOURCE = "source";
	
	public static final String KEY_UID = "uid";
	
	public static final String KEY_UNAME = "uname";
	
	public static final String KEY_NICKNAME = "nickname";
	
	public static final String KEY_EMAIL = "email";
	
	public static final String KEY_PHONE = "phone";
	
	public static final String KEY_USERNAME = "username";
	
	/* key */
	public static final String KEY_STOCKCODE = "stockcode";
	
	public static final String KEY_RETCODE = "retcode";
	
	public static final String KEY_ERR_CODE = "ERR_CODE";
	
	public static final String KEY_ERR_NAME = "ERR_NAME";
}