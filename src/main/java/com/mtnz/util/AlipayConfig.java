package com.mtnz.util;

public class AlipayConfig {
	public static String partner = "2088311398603457";
	//public static String partner = "";
	public static String private_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANL3xfhLMlo+ULRYKJLwRog9YWfgYODC7PuxJMtyreIVbuwIyHHHxa52N1TR9iHfzE3/4mUEderlvMjfHSPE7gpMsft1Q157bWhY2TJx06Ojn9apnFwMNrK/b1wTXwDHMRefbMTPLvFLEECFuRX6+tfRHUGplcwx28OGMcMyg1v9AgMBAAECgYEAhY5ILPzl3o2Op+0bmpI0BLHfBVTd38xnHJuzcHykKflppFGXG7jdo6nBM5hMGnvEqihxGYRuZguUrRHaL7fO/V2Tl/pQYfyV9k8Pv5qTZMdS+V/b6OubB+LMsI1eMuKOyHeK31NF51ELz+KJzRyRNaSci6NdVbx6SegJonSHjCECQQD06cFDwqng7lxz+O3WFodokmCV+ZdGP4RYKw5MnLzpJormZJaZuGcCFilVl9oMCqjRAfmBkr79ndaaZrxolcipAkEA3ISiYsOoXJ7niEJj7UXw3hLeSCybywgcDDn8IWiNrAOulvBblZt/J3WA3WAkgJ4EuPlctDhU4r6JfAh3qUfpNQJAaNDh9a4KJkndJ4URxN4/dXGwBH+dTmNAsLs8k21BLEdiitfh3EgfTbVOW2Nx3lVxioHd1qaHNuOmRM5TNC9mgQJBALTFFckY03951BtjMT9kwluJ5CYOS3hYoYV0Uzef2eNU8V5SkwFFgyr5rDXNhp7Y6s4nvNMQCqYFw/alilJ+3LECQQCXBr6IjI/F1P2NoQZ8vfoAgF+r7maZI/XE1Yuu17iRK6qDsxDSNcJMGOj5wmVswRmmE5ECp2FQOsn0bPHOnRoX";
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	public static String sign_type = "RSA";
	public static String input_charset = "utf-8";
	public static String service = "http://m.nongjike.cn/NJK/app/notify_url";
	public static String app_id="2016071901639691";
	public static String log_path = "D:\\";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088311398603457";
	// 商户的私钥
	public static String key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIw"
			+ "ggJeAgEAAoGBANL3xfhLMlo+ULRYKJLwRog9YWfgYODC7PuxJMtyreIVbuwI"
			+ "yHHHxa52N1TR9iHfzE3/4mUEderlvMjfHSPE7gpMsft1Q157bWhY2TJx06Oj"
			+ "n9apnFwMNrK/b1wTXwDHMRefbMTPLvFLEECFuRX6+tfRHUGplcwx28OGMcMy"
			+ "g1v9AgMBAAECgYEAhY5ILPzl3o2Op+0bmpI0BLHfBVTd38xnHJuzcHykKflp"
			+ "pFGXG7jdo6nBM5hMGnvEqihxGYRuZguUrRHaL7fO/V2Tl/pQYfyV9k8Pv5q"
			+ "TZMdS+V/b6OubB+LMsI1eMuKOyHeK31NF51ELz+KJzRyRNaSci6NdVbx6Seg"
			+ "JonSHjCECQQD06cFDwqng7lxz+O3WFodokmCV+ZdGP4RYKw5MnLzpJormZJa"
			+ "ZuGcCFilVl9oMCqjRAfmBkr79ndaaZrxolcipAkEA3ISiYsOoXJ7niEJj7UX"
			+ "w3hLeSCybywgcDDn8IWiNrAOulvBblZt/J3WA3WAkgJ4EuPlctDhU4r6JfAh"
			+ "3qUfpNQJAaNDh9a4KJkndJ4URxN4/dXGwBH+dTmNAsLs8k21BLEdiitfh3Eg"
			+ "fTbVOW2Nx3lVxioHd1qaHNuOmRM5TNC9mgQJBALTFFckY03951BtjMT9kwlu"
			+ "J5CYOS3hYoYV0Uzef2eNU8V5SkwFFgyr5rDXNhp7Y6s4nvNMQCqYFw/alilJ"
			+ "+3LECQQCXBr6IjI/F1P2NoQZ8vfoAgF+r7maZI/XE1Yuu17iRK6qDsxDSNcJ"
			+ "MGOj5wmVswRmmE5ECp2FQOsn0bPHOnRoX";;

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "E:\\pay\\";//"/data/tomcat-Mall/logs/pay/";

	// 字符编码格式 目前支持 gbk 或 utf-8在线客服
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "MD5";
	// 字符编码格式 目前支持 gbk 或 utf-8在线客服
	public static String seller_email = "350853798@qq.com";
	
	// 签名方式 不需修改
	public static String subject = "农极客";
	
	// 签名方式 不需修改
	public static String agentid = "11532464a1";
	// 字符编码格式 目前支持 gbk 或 utf-8在线客服
	public static String call_back_url = "http://www.xiaoerzuche.com/pay/alipayweb/callbackAlipay.ihtml";
	
	// 签名方式 不需修改
	public static String notify_url = "http://101.200.130.60:8080/NJK/app/notify_url";
	public static String alipay_gateway = "http://wappaygw.alipay.com/service/rest.htm?";
	
	//返回格式
	public static String format = "json";
	//必填，不需要修改
	
	//返回格式
	public static String version = "1.0";*/
	
}
