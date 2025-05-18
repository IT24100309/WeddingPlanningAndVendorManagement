package com.weddingplanner.servlets;

import com.weddingplanner.models.Vendor;
import com.weddingplanner.services.VendorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/vendorDashboard")
public class VendorDashboard extends HttpServlet {
    private VendorService svc;

    @Override
    public void init() throws ServletException {
        // Pass the ServletContext so your service can find its file-based repo
        svc = new VendorService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Optional search by service type
        String filter = req.getParameter("searchType");
        List<Vendor> vendors;
        if (filter != null && !filter.isBlank()) {
            vendors = svc.findByServiceType(filter);
        } else {
            vendors = svc.findAll();
        }

        // Attach to request and forward into /WEB-INF/views
        req.setAttribute("vendors", vendors);
        req.getRequestDispatcher("/WEB-INF/views/vendorDashboard.jsp")
                .forward(req, resp);
    }
}
