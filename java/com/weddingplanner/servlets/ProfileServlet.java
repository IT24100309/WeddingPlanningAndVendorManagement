package com.weddingplanner.servlets;

import com.weddingplanner.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        if (u == null) {
            resp.sendRedirect("login");
            return;
        }
        req.setAttribute("user", u);
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp")
                .forward(req, resp);
    }
}
