package com.mtnz.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 
public class CORSFilter implements Filter {

    private boolean isCross = false;

    @Override
    public void destroy() {
        isCross = false;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String isCrossStr = filterConfig.getInitParameter("IsCross");
        isCross = isCrossStr.equals("true") ? true : false;
        //System.out.println("跨域开启状态：" + isCrossStr);
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	  /*if(servletRequest!=null&&servletResponse!=null){
    		  HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
    		  httpResponse.addHeader("Access-Control-Allow-Origin", "*");
    		  filterChain.doFilter(servletRequest, servletResponse);
    	  }*/
        if (isCross) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            //System.out.println("拦截请求: " + httpServletRequest.getServletPath());
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            // httpServletResponse.setHeader("Access-Control-Allow-Methods", "*"); // 表示所有请求都有效
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE,PUT");
            httpServletResponse.setHeader("Access-Control-Max-Age", "0");
            httpServletResponse.setHeader("Access-Control-Allow-Headers",
                    "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("XDomainRequestAllowed", "1");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }	
 
   /* @Override
    public void destroy() {
 
    }*/
}
