package com.weddingplanner.servlets;

import com.weddingplanner.services.VendorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/deleteVendor")
public class VendorDeleteServlet extends HttpServlet {
    private VendorService vendorService;

    @Override
    public void init() {
        vendorService = new VendorService(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null && !id.isBlank()) {
            // Delete the vendor
            vendorService.delete(id);

            // Set a success message (optional)
            req.getSession().setAttribute("message", "Vendor deleted successfully");
        } else {
            // Set an error message (optional)
            req.getSession().setAttribute("error", "Vendor ID is required");
        }

        // Redirect back to the dashboard
        resp.sendRedirect(req.getContextPath() + "/vendorDashboard");
    }
} 