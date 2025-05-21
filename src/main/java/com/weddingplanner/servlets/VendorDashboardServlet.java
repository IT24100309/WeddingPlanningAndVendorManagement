package com.weddingplanner.servlets;

import com.weddingplanner.models.Vendor;
import com.weddingplanner.services.VendorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/vendorDashboard")
public class VendorDashboardServlet extends HttpServlet {
    private VendorService svc;

    @Override
    public void init() throws ServletException {
        // Pass the ServletContext
        svc = new VendorService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Get session messages if any
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        String error = (String) session.getAttribute("error");

        // Clear session messages after retrieving
        session.removeAttribute("message");
        session.removeAttribute("error");

        /*Optional search by service type**/
        String filter = req.getParameter("searchType");
        List<Vendor> vendors = new ArrayList<>();

        try {
            if (filter != null && !filter.isBlank()) {
                vendors = svc.findByServiceType(filter);
            } else {
                vendors = svc.findAll();
            }
        } catch (IOException e) {
            error = "Failed to load vendors: " + e.getMessage();
        }

        // Attach messages and vendors to request
        if (message != null) req.setAttribute("message", message);
        if (error != null) req.setAttribute("error", error);
        req.setAttribute("vendors", vendors);

        // Forward to the JSP
        req.getRequestDispatcher("/WEB-INF/views/vendorDashboard.jsp")
                .forward(req, resp);
    }
}
