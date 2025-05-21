// src/main/java/com/weddingplanner/servlets/PaymentFormServlet.java
package com.weddingplanner.servlets;

import com.weddingplanner.models.Booking;
import com.weddingplanner.models.Payment;
import com.weddingplanner.models.User;
import com.weddingplanner.services.BookingService;
import com.weddingplanner.services.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/paymentForm")
public class PaymentFormServlet extends HttpServlet {
    private PaymentService paymentService;
    private BookingService bookingService;

    @Override
    public void init() {
        // pass ServletContext
        paymentService = new PaymentService(getServletContext());
        bookingService = new BookingService(getServletContext());
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<Booking> myBookings = bookingService.findByCustomerName(user.getUsername());

        req.setAttribute("bookings", myBookings);
        req.getRequestDispatcher("/WEB-INF/views/paymentForm.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String bookingId = req.getParameter("bookingId");
        double amount    = Double.parseDouble(req.getParameter("amount"));
        String method    = req.getParameter("method");

        Payment payment = new Payment(bookingId, LocalDate.now(), amount, method);
        paymentService.save(payment);

        resp.sendRedirect(req.getContextPath() + "/paymentDashboard");
    }
}
