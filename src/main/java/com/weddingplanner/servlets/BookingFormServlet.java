// src/main/java/com/weddingplanner/servlets/BookingFormServlet.java
package com.weddingplanner.servlets;

import com.weddingplanner.models.Booking;
import com.weddingplanner.models.User;
import com.weddingplanner.models.Vendor;
import com.weddingplanner.services.BookingService;
import com.weddingplanner.services.VendorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/bookingForm")
public class BookingFormServlet extends HttpServlet {
    private BookingService bookingService;
    private VendorService vendorService;

    @Override
    public void init() {
        bookingService = new BookingService(getServletContext());
        vendorService  = new VendorService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {  // now throws IOException
        List<Vendor> vendors = vendorService.findAll();  // was throwing unhandled IOException
        req.setAttribute("vendors", vendors);
        req.getRequestDispatcher("/WEB-INF/views/bookingForm.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        String vendorName = req.getParameter("vendorName");
        LocalDate eventDate = LocalDate.parse(req.getParameter("eventDate"));
        double price = Double.parseDouble(req.getParameter("price"));

        Booking booking = new Booking(vendorName, user.getUsername(), eventDate, price);
        bookingService.save(booking);  // now public, no access error

        resp.sendRedirect(req.getContextPath() + "/bookingDashboard");
    }
}
