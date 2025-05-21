// src/main/java/com/weddingplanner/servlets/BookingDetailsServlet.java
package com.weddingplanner.servlets;

import com.weddingplanner.models.Booking;
import com.weddingplanner.services.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/bookingDetails")
public class BookingDetailsServlet extends HttpServlet {
    private BookingService svc;

    @Override
    public void init() throws ServletException {
        // Pass the ServletContext, not a String
        svc = new BookingService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Read the booking ID from the query param
        String id = req.getParameter("id");

        // Use findById, not find()
        Booking booking = svc.findById(id);

        req.setAttribute("booking", booking);
        req.getRequestDispatcher("/WEB-INF/views/bookingDetails.jsp")
                .forward(req, resp);
    }
}
