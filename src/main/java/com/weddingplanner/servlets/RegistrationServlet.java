package com.weddingplanner.servlets;

import com.weddingplanner.models.User;
import com.weddingplanner.services.UserService;
import com.weddingplanner.utils.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email    = req.getParameter("email");

        // Validate input
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            req.setAttribute("error", "All fields are required");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        // Hash the password before creating the user
        String hashedPassword = PasswordUtil.hash(password);
        User newUser = new User(username, hashedPassword, email);
        
        if (userService.register(newUser)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", newUser);
            resp.sendRedirect(req.getContextPath() + "/bookingDashboard");
        } else {
            req.setAttribute("error", "Registration failed. Username might already be taken.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}
