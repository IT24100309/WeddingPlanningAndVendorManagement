<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // Redirect to vendor dashboard for this specific part of the project
    response.sendRedirect(request.getContextPath() + "/vendorDashboard");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Wedding Planner - Vendor Management</title>
    <jsp:include page="/WEB-INF/includes/head.jsp" />
    <style>
        .hero {
            background: url('https://source.unsplash.com/1200x400/?wedding') center/cover no-repeat;
            height: 400px; position: relative; color: white;
        }
        .hero-overlay {
            position:absolute; top:0; left:0; width:100%; height:100%;
            background:rgba(0,0,0,0.5);
            display:flex; align-items:center; justify-content:center;
        }
        .hero h1 { font-size:3rem; font-family:'Playfair Display', serif; }
        .feature-card {
            transition: transform 0.3s ease;
        }
        .feature-card:hover {
            transform: translateY(-5px);
        }
        .vendor-section {
            background-color: #f8f9fa;
            padding: 2rem 0;
            margin: 2rem 0;
            border-radius: 8px;
        }
        .vendor-link {
            text-decoration: none;
            color: inherit;
        }
        .vendor-link:hover {
            color: #0d6efd;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    
    <div class="content-wrapper">
        <div class="hero">
            <div class="hero-overlay">
                <h1>Vendor Management System</h1>
            </div>
        </div>
        
        <!-- Vendor Management Section -->
        <div class="container">
            <div class="vendor-section">
                <h2 class="text-center mb-4">Vendor Management Links</h2>
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="list-group">
                            <a href="${pageContext.request.contextPath}/vendorDashboard" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                <div>
                                    <h5 class="mb-1">Vendor Dashboard</h5>
                                    <p class="mb-1 text-muted">View and manage all vendors</p>
                                </div>
                                <i class="bi bi-chevron-right"></i>
                            </a>
                            
                            <a href="${pageContext.request.contextPath}/addVendor" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                <div>
                                    <h5 class="mb-1">Add New Vendor</h5>
                                    <p class="mb-1 text-muted">Register a new vendor in the system</p>
                                </div>
                                <i class="bi bi-plus-circle"></i>
                            </a>
                            
                            <a href="${pageContext.request.contextPath}/sortVendorsByPrice" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                <div>
                                    <h5 class="mb-1">Sort Vendors by Price</h5>
                                    <p class="mb-1 text-muted">View vendors sorted by price range</p>
                                </div>
                                <i class="bi bi-sort-numeric-down"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Quick Actions -->
            <div class="row justify-content-center mb-4">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header bg-primary text-white">
                            <h5 class="card-title mb-0">Quick Actions</h5>
                        </div>
                        <div class="card-body">
                            <div class="d-grid gap-2">
                                <a href="${pageContext.request.contextPath}/vendorDashboard?searchType=Catering" class="btn btn-outline-primary">
                                    <i class="bi bi-cup-hot"></i> View Catering Vendors
                                </a>
                                <a href="${pageContext.request.contextPath}/vendorDashboard?searchType=Venue" class="btn btn-outline-primary">
                                    <i class="bi bi-building"></i> View Venue Vendors
                                </a>
                                <a href="${pageContext.request.contextPath}/vendorDashboard?searchType=Photography" class="btn btn-outline-primary">
                                    <i class="bi bi-camera"></i> View Photography Vendors
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>
