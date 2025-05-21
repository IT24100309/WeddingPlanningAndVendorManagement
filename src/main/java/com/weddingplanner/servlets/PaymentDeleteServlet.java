package com.weddingplanner.servlets;

import com.weddingplanner.services.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/paymentDelete")
public class PaymentDeleteServlet extends HttpServlet {
    private PaymentService paymentService;
    
    @Override
    public void init() throws ServletException {
        this.paymentService = new PaymentService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle GET requests from the dashboard JSP
        handleDelete(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle POST requests 
        handleDelete(request, response);
    }
    
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentId = request.getParameter("id");
        
        if (paymentId == null || paymentId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Payment ID is required");
            return;
        }

        try {
            paymentService.delete(paymentId);
            response.sendRedirect(request.getContextPath() + "/paymentDashboard");
        } catch (IOException e) {
            throw new ServletException("Failed to delete payment", e);
        }
    }
}
