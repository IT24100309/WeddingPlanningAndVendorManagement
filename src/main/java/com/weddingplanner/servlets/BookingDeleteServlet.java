package com.weddingplanner.servlets;

import com.weddingplanner.services.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/bookingDelete")
public class BookingDeleteServlet extends HttpServlet {
    private BookingService svc;

    @Override
    public void init() throws ServletException {
        // pass the ServletContext, not a String
        svc = new BookingService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        svc.delete(id);
        resp.sendRedirect(req.getContextPath() + "/bookingDashboard");
    }
}
