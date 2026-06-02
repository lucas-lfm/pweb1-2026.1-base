package com.proflucasmendes.exemplo_mvc.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")
public class GlobalFilter implements Filter {
  
  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws IOException, ServletException {
    resp.setCharacterEncoding("UTF-8");

    chain.doFilter(req, resp);
  }

}
