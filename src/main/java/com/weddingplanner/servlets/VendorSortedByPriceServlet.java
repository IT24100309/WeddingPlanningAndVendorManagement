package com.weddingplanner.servlets;

import com.weddingplanner.models.Vendor;
import com.weddingplanner.services.VendorService;
import com.weddingplanner.utils.CustomLinkedList;
import com.weddingplanner.utils.SortingUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/sortVendorsByPrice")
public class VendorSortedByPriceServlet extends HttpServlet {
    private VendorService vendorService;

    @Override
    public void init() throws ServletException {
        vendorService = new VendorService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // Get all vendors
        List<Vendor> allVendors = vendorService.findAll();
        
        // Convert to our custom linked list
        CustomLinkedList<Vendor> vendorList = new CustomLinkedList<>();
        for (Vendor vendor : allVendors) {
            vendorList.add(vendor);
        }
        
        // Sort using our custom bubble sort
        SortingUtil.bubbleSortByPriceRange(vendorList, Vendor::getPriceRange);
        
        // Convert back to ArrayList for JSP
        List<Vendor> sortedVendors = new ArrayList<>();
        for (Vendor vendor : vendorList) {
            sortedVendors.add(vendor);
        }
        
        // Set the sorted vendors as an attribute and forward to the view
        req.setAttribute("vendors", sortedVendors);
        req.getRequestDispatcher("/WEB-INF/views/sortedVendors.jsp").forward(req, resp);
    }
} 