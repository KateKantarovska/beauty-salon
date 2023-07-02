package com.project.beautysalon.filters;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("requestEncoding");
        if (encoding == null) {
            encoding = "UTF-8";
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getCharacterEncoding() == null) {
            servletRequest.setCharacterEncoding(encoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
