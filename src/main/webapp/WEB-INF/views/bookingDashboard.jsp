<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Dashboard - Wedding Planner</title>
    <jsp:include page="/WEB-INF/includes/head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    <div class="content-wrapper">
        <div class="container py-5">
            <h1 class="mb-4">Your Bookings</h1>

            <!-- Search form -->
            <div class="card mb-4">
                <div class="card-body">
                    <form method="get" action="bookingDashboard" class="row g-3 align-items-center">
                        <div class="col-auto">
                            <label class="form-label">Filter by Vendor:</label>
                            <input type="text" name="searchVendor" class="form-control"
                                   value="${param.searchVendor}" placeholder="Vendor name"/>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary">Search</button>
                            <a href="bookingDashboard" class="btn btn-outline-secondary">Clear</a>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Booking List -->
            <div class="card">
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty bookings}">
                            <div class="alert alert-info">
                                No bookings found.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Vendor</th>
                                            <th>Customer</th>
                                            <th>Date</th>
                                            <th>Price</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="b" items="${bookings}">
                                            <tr>
                                                <td>${b.id}</td>
                                                <td>${b.vendorName}</td>
                                                <td>${b.customerName}</td>
                                                <td>${b.eventDate}</td>
                                                <td>Rs. ${b.price}</td>
                                                <td>
                                                    <a href="bookingDetails?id=${b.id}" 
                                                       class="btn btn-sm btn-info me-2">View</a>
                                                    <a href="bookingDelete?id=${b.id}"
                                                       onclick="return confirm('Delete this booking?');"
                                                       class="btn btn-sm btn-danger">Delete</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Navigation Links -->
            <div class="mt-4">
                <a href="bookingForm" class="btn btn-success">
                    Make a New Booking
                </a>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>
