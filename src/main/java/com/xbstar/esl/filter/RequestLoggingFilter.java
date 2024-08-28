package com.xbstar.esl.filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
 
/*设置过滤器，再将过滤器绑定到springboot的配置类
*/


public class RequestLoggingFilter implements Filter {
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
 
        // 打印请求URL和HTTP方法
        //System.out.println(request.getMethod() + " " + request.getRequestURL());
              
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        String writeValueAsString = objectMapper.writeValueAsString(request.getParameterMap());
        String sec = request.getParameter("section");
        if("dialplan".equals(sec)) {
            System.out.println("【DIALPLAN请求参数】:"+writeValueAsString);

        }else if("directory".equals(sec)) {
            //System.out.println("【DIRECTORY请求参数】:"+writeValueAsString);

        }else if("configuration".equals(sec)) {
        	System.out.println("【CONFIGURATION请求参数】:"+writeValueAsString);
        }
  
 
        
		/*
		 * // 打印请求头 String headers = request.getHeaderNames().hasMoreElements() ?
		 * "\nHeaders: " : ""; while (request.getHeaderNames().hasMoreElements()) {
		 * String headerName = request.getHeaderNames().nextElement(); headers +=
		 * headerName + ": " + request.getHeader(headerName) + "; "; }
		 * System.out.println(headers);
		 * 
		 * // 打印请求参数 String queryString = request.getQueryString(); // 只适用于GET方法 if
		 * (queryString != null) { System.out.println("Query String: " + queryString); }
		 */
 
        // 传递给下一个过滤器或servlet
        chain.doFilter(servletRequest, servletResponse);
    }
}