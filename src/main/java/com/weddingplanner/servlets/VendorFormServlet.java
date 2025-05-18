package com.weddingplanner.servlets;

import com.weddingplanner.models.Vendor;
import com.weddingplanner.services.VendorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet({"/addVendor", "/editVendor"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5,   // 5MB per file
        maxRequestSize = 1024 * 1024 * 20 // 20MB total
)
public class VendorFormServlet extends HttpServlet {
    private VendorService vendorService;

    @Override
    public void init() {
        vendorService = new VendorService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        Vendor vendor = null;
        if (id != null && !id.isBlank()) {
            vendor = vendorService.findById(id);
        }
        req.setAttribute("vendor", vendor);
        req.getRequestDispatcher("/WEB-INF/views/vendorForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        boolean isEdit = (id != null && !id.isBlank());

        // Handle file uploads
        List<String> portfolioPaths = new ArrayList<>();
        for (Part part : req.getParts()) {
            if (part.getName().equals("portfolio") && part.getSize() > 0) {
                String fileName = UUID.randomUUID() + "_" + part.getSubmittedFileName();
                String uploadDir = getServletContext().getRealPath("/uploads/portfolio");
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();
                String filePath = uploadDir + File.separator + fileName;
                part.write(filePath);
                // Store relative path for web access
                portfolioPaths.add("uploads/portfolio/" + fileName);
            }
        }

        // Collect form fields
        String name = req.getParameter("name");
        String serviceType = req.getParameter("serviceType");
        String description = req.getParameter("description");
        String contactPerson = req.getParameter("contactPerson");
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email");
        String website = req.getParameter("website");
        String address = req.getParameter("address");
        String priceRange = req.getParameter("priceRange");
        String availability = req.getParameter("availability");
        String terms = req.getParameter("terms");
        String status = req.getParameter("status");

        LocalDateTime now = LocalDateTime.now();

        Vendor vendor;
        if (isEdit) {
            vendor = vendorService.findById(id);
            if (vendor == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vendor not found");
                return;
            }
            // Update fields
            vendor.setName(name);
            vendor.setServiceType(serviceType);
            vendor.setDescription(description);
            vendor.setContactPerson(contactPerson);
            vendor.setPhoneNumber(phoneNumber);
            vendor.setEmail(email);
            vendor.setWebsite(website);
            vendor.setAddress(address);
            vendor.setPriceRange(priceRange);
            vendor.setAvailability(availability);
            vendor.setTerms(terms);
            vendor.setStatus(status);
            vendor.setUpdatedAt(now);
            if (!portfolioPaths.isEmpty()) {
                List<String> currentPortfolio = vendor.getPortfolio();
                if (currentPortfolio == null) currentPortfolio = new ArrayList<>();
                currentPortfolio.addAll(portfolioPaths);
                vendor.setPortfolio(currentPortfolio);
            }
            vendorService.save(vendor); // update
        } else {
            vendor = new Vendor();
            vendor.setId(UUID.randomUUID().toString());
            vendor.setName(name);
            vendor.setServiceType(serviceType);
            vendor.setDescription(description);
            vendor.setContactPerson(contactPerson);
            vendor.setPhoneNumber(phoneNumber);
            vendor.setEmail(email);
            vendor.setWebsite(website);
            vendor.setAddress(address);
            vendor.setPriceRange(priceRange);
            vendor.setAvailability(availability);
            vendor.setTerms(terms);
            vendor.setStatus(status);
            vendor.setCreatedAt(now);
            vendor.setUpdatedAt(now);
            vendor.setPortfolio(portfolioPaths);
            vendor.setRating(0.0);
            vendor.setReviews(new ArrayList<>());
            vendorService.save(vendor); // add new
        }

        resp.sendRedirect(req.getContextPath() + "/vendorDashboard");
    }
}