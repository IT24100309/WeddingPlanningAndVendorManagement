package com.weddingplanner.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebFilter({"/bookingForm", "/bookingDashboard", "/paymentForm", "/paymentDashboard"})
public class AuthFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  req  = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String requestURI = req.getRequestURI();
        logger.log(Level.INFO, "AuthFilter processing request: {0}", requestURI);

        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        if (!loggedIn) {
            logger.log(Level.WARNING, "Unauthorized access attempt to {0}", requestURI);
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        logger.log(Level.INFO, "Authorized access to {0}", requestURI);
        chain.doFilter(request, response);
    }
}
