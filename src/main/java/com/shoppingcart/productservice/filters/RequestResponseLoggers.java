package com.shoppingcart.productservice.filters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

@Slf4j
@Component
@Order(1)
public class RequestResponseLoggers implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        MyCustomHttpRequestWrapper requestWrapper = new MyCustomHttpRequestWrapper((HttpServletRequest) servletRequest);
        log.info("Request URI : {}" , requestWrapper.getRequestURI());
        log.info("Request Method : {}" , requestWrapper.getMethod());
        log.info("Request Body : {}" ,new String(requestWrapper.getByteArray()));


        filterChain.doFilter(requestWrapper, servletResponse);

        MyCustomHttpResponseWrapper responseWrapper = new MyCustomHttpResponseWrapper((HttpServletResponse) servletResponse);

        log.info("Response status - {} " , responseWrapper.getStatus());
        log.info("Response Body - {} " , new String(responseWrapper.getBaos().toByteArray()));


    }

    private class MyCustomHttpRequestWrapper extends HttpServletRequestWrapper {

        byte[] byteArray;
        public MyCustomHttpRequestWrapper(HttpServletRequest request) {
            super(request);
            try {
                byteArray = IOUtils.toByteArray(request.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("Issue While reading request Stream");
            }
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new MyCustomDelegatingServletInputStream(new ByteArrayInputStream(byteArray));
        }

        public byte[] getByteArray() {
            return byteArray;
        }
    }

    private class MyCustomHttpResponseWrapper extends HttpServletResponseWrapper {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PrintStream printStream = new PrintStream(baos);

        public ByteArrayOutputStream getBaos() {
            return baos;
        }
        public MyCustomHttpResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new MyCustomDelegatingServletOutputStream(new TeeOutputStream(super.getOutputStream(), printStream));
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter(new TeeOutputStream(super.getOutputStream(), printStream));
        }
    }
}
