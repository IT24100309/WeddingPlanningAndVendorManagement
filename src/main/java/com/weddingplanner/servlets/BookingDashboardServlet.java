package com.weddingplanner.servlets;

import com.weddingplanner.models.Booking;
import com.weddingplanner.services.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/bookingDashboard")
public class BookingDashboardServlet extends HttpServlet {
    private BookingService svc;

    @Override
    public void init() throws ServletException {
        // Must pass ServletContext, not a String
        svc = new BookingService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Optional vendor-name filter param
        String vendorFilter = req.getParameter("searchVendor");
        List<Booking> bookings;

        if (vendorFilter != null && !vendorFilter.isBlank()) {
            bookings = svc.findByVendorName(vendorFilter);
        } else {
            bookings = svc.findAll();
        }

        req.setAttribute("bookings", bookings);
        req.getRequestDispatcher("/WEB-INF/views/bookingDashboard.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        switch (action) {
            case "add":
                // Create new booking from form inputs
                Booking newBooking = new Booking(
                        req.getParameter("vendorName"),
                        req.getParameter("customerName"),
                        LocalDate.parse(req.getParameter("eventDate")),
                        Double.parseDouble(req.getParameter("price"))
                );
                svc.save(newBooking);
                break;

            case "updateInline":
                // Inline price update
                Booking existing = svc.findById(req.getParameter("id"));
                existing.setPrice(Double.parseDouble(req.getParameter("price")));
                svc.update(existing);
                break;

            case "delete":
                svc.delete(req.getParameter("id"));
                break;

            default:
                // unknown action
                break;
        }

        resp.sendRedirect(req.getContextPath() + "/bookingDashboard");
    }
}
