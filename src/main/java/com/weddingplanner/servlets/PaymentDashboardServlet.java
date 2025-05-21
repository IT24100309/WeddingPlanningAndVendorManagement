// src/main/java/com/weddingplanner/servlets/PaymentDashboardServlet.java
package com.weddingplanner.servlets;

import com.weddingplanner.models.Payment;
import com.weddingplanner.services.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/paymentDashboard")
public class PaymentDashboardServlet extends HttpServlet {
    private PaymentService svc;

    @Override
    public void init() throws ServletException {
        // Must pass ServletContext, not a String
        svc = new PaymentService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Optional filter by booking ID
        String bookingFilter = req.getParameter("searchBookingId");
        List<Payment> payments;
        if (bookingFilter != null && !bookingFilter.isBlank()) {
            payments = svc.findByBookingId(bookingFilter);
        } else {
            payments = svc.findAll();
        }

        req.setAttribute("payments", payments);
        req.getRequestDispatcher("/WEB-INF/views/paymentDashboard.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        switch (action) {
            case "add":
                // Create and save a new Payment
                Payment newPayment = new Payment(
                        req.getParameter("bookingId"),
                        LocalDate.parse(req.getParameter("paymentDate")),
                        Double.parseDouble(req.getParameter("amount")),
                        req.getParameter("method")
                );
                svc.save(newPayment);
                break;

            case "updateInline":
                // Inline amount update for an existing payment
                Payment existing = svc.findById(req.getParameter("id"));
                existing.setAmount(Double.parseDouble(req.getParameter("amount")));
                svc.update(existing);
                break;

            case "delete":
                svc.delete(req.getParameter("id"));
                break;

            default:
                // no-op
        }

        resp.sendRedirect(req.getContextPath() + "/paymentDashboard");
    }
}
