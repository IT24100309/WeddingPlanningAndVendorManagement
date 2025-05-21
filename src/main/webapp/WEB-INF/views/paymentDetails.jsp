<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Details - Wedding Planner</title>
    <jsp:include page="/WEB-INF/includes/head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    
    <div class="content-wrapper">
        <div class="container py-5">
            <h1 class="mb-4">Payment Details</h1>
            
            <div class="card">
                <div class="card-body">
                    <!-- If no payment was found, show a message -->
                    <c:if test="${empty payment}">
                        <div class="alert alert-warning">
                            <p><em>No payment details available.</em></p>
                        </div>
                    </c:if>

                    <!-- Otherwise display the payment fields -->
                    <c:if test="${not empty payment}">
                        <dl class="row">
                            <dt class="col-sm-3">Payment ID</dt>
                            <dd class="col-sm-9">${payment.id}</dd>
                            
                            <dt class="col-sm-3">Booking ID</dt>
                            <dd class="col-sm-9">${payment.bookingId}</dd>
                            
                            <dt class="col-sm-3">Date</dt>
                            <dd class="col-sm-9">${payment.paymentDate}</dd>
                            
                            <dt class="col-sm-3">Amount</dt>
                            <dd class="col-sm-9">Rs. ${payment.amount}</dd>
                            
                            <dt class="col-sm-3">Method</dt>
                            <dd class="col-sm-9">${payment.method}</dd>
                        </dl>
                    </c:if>
                </div>
            </div>
            
            <div class="mt-4">
                <a href="${pageContext.request.contextPath}/paymentDashboard" class="btn btn-outline-secondary">
                    ← Back to Payment Dashboard
                </a>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>
