<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Details - Wedding Planner</title>
    <jsp:include page="/WEB-INF/includes/head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    
    <div class="content-wrapper">
        <div class="container py-5">
            <h1 class="mb-4">Booking Details</h1>

            <!-- Booking Information -->
            <div class="card">
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty booking}">
                            <div class="alert alert-warning">
                                Booking not found.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <dl class="row">
                                <dt class="col-sm-3">Vendor</dt>
                                <dd class="col-sm-9">${booking.vendorName}</dd>

                                <dt class="col-sm-3">Customer</dt>
                                <dd class="col-sm-9">${booking.customerName}</dd>

                                <dt class="col-sm-3">Event Date</dt>
                                <dd class="col-sm-9">${booking.eventDate}</dd>

                                <dt class="col-sm-3">Price</dt>
                                <dd class="col-sm-9">Rs. ${booking.price}</dd>
                            </dl>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Navigation -->
            <div class="mt-4">
                <a href="bookingDashboard" class="btn btn-outline-secondary">
                    ← Back to Booking Dashboard
                </a>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>
