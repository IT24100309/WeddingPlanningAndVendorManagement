<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Wedding Planner</title>
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
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    
    <div class="content-wrapper">
        <div class="hero">
            <div class="hero-overlay">
                <h1>Welcome to Wedding Planner</h1>
            </div>
        </div>
        
        <div class="container py-5">
            <div class="row gy-4">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <div class="col-md-3">
                            <a href="vendorDashboard" class="text-decoration-none text-dark">
                                <div class="card feature-card shadow-sm p-3 text-center h-100">
                                    <div class="card-body">
                                        <i class="bi bi-shop fs-1 mb-3 text-primary"></i>
                                        <h5>Vendors</h5>
                                        <p>Manage Services</p>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <div class="col-md-3">
                            <a href="bookingDashboard" class="text-decoration-none text-dark">
                                <div class="card feature-card shadow-sm p-3 text-center h-100">
                                    <div class="card-body">
                                        <i class="bi bi-calendar-check fs-1 mb-3 text-success"></i>
                                        <h5>Bookings</h5>
                                        <p>View & Edit</p>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <div class="col-md-3">
                            <a href="paymentDashboard" class="text-decoration-none text-dark">
                                <div class="card feature-card shadow-sm p-3 text-center h-100">
                                    <div class="card-body">
                                        <i class="bi bi-credit-card fs-1 mb-3 text-danger"></i>
                                        <h5>Payments</h5>
                                        <p>Track Records</p>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <div class="col-md-3">
                            <a href="profile" class="text-decoration-none text-dark">
                                <div class="card feature-card shadow-sm p-3 text-center h-100">
                                    <div class="card-body">
                                        <i class="bi bi-person-circle fs-1 mb-3 text-info"></i>
                                        <h5>My Profile</h5>
                                        <p>Account Settings</p>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-12 text-center py-5">
                            <h2 class="mb-4">Plan Your Perfect Wedding Day</h2>
                            <p class="lead mb-5">Register or login to start planning your special day with our comprehensive wedding planning tools.</p>
                            <a href="login" class="btn btn-primary btn-lg me-3">Login</a>
                            <a href="register" class="btn btn-outline-primary btn-lg">Register</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>
