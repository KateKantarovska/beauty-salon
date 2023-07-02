package com.project.beautysalon.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@WebFilter(filterName = "LanguageFilter", urlPatterns = "/*")
public class LanguageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var session = ((HttpServletRequest) servletRequest).getSession();
        String lang = (String) session.getAttribute("lang");
        if (lang == null) {
            List<String> supportedLangs = List.of("uk", "en");
            List<String> acceptedLangs = Collections.list(servletRequest.getLocales()).stream().map(Locale::toLanguageTag).toList();
            for (var language : supportedLangs) {
                if (acceptedLangs.contains(language)) {
                    session.setAttribute("lang", language);
                    break;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
