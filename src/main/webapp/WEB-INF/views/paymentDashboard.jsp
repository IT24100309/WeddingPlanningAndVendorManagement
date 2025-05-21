<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Dashboard - Wedding Planner</title>
    <jsp:include page="/WEB-INF/includes/head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    
    <div class="content-wrapper">
        <div class="container py-5">
            <h1 class="mb-4">Your Payments</h1>

            <!-- Filter form -->
            <div class="card mb-4">
                <div class="card-body">
                    <form method="get" action="paymentDashboard" class="row g-3 align-items-center">
                        <div class="col-auto">
                            <label class="form-label">Filter by Booking ID:</label>
                            <input type="text" name="searchBookingId" class="form-control"
                                value="${param.searchBookingId}" placeholder="Booking ID"/>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary">Search</button>
                            <a href="paymentDashboard" class="btn btn-outline-secondary">Clear</a>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Payment List -->
            <div class="card">
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty payments}">
                            <div class="alert alert-info">
                                No payments found.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Booking ID</th>
                                            <th>Date</th>
                                            <th>Amount</th>
                                            <th>Method</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="p" items="${payments}">
                                            <tr>
                                                <td>${p.id}</td>
                                                <td>${p.bookingId}</td>
                                                <td>${p.paymentDate}</td>
                                                <td>Rs. ${p.amount}</td>
                                                <td>${p.method}</td>
                                                <td>
                                                    <a href="paymentDetails?id=${p.id}" 
                                                    class="btn btn-sm btn-info me-2">View</a>
                                                    <a href="paymentDelete?id=${p.id}"
                                                    onclick="return confirm('Delete this payment?');"
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
                <a href="paymentForm" class="btn btn-success">
                    Make a New Payment
                </a>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>
