//package com.juaracoding.security;
//
//import com.juaracoding.core.MyHttpServletRequestWrapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
//@Component
//public class FirstLayer extends HttpFilter {
//
//
//
//    @Override
//    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String strContentType = request.getContentType()==null?"":request.getContentType();
//        if(!strContentType.startsWith("multipart/form-data") || "".equals(strContentType)){
//            request = new MyHttpServletRequestWrapper(request);
//        }
//        chain.doFilter(request,response);
//    }
//}