<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile - Wedding Planner</title>
    <jsp:include page="/WEB-INF/includes/head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    
    <div class="content-wrapper">
        <div class="container py-5">
            <c:choose>
                <c:when test="${empty sessionScope.user}">
                    <c:redirect url="login" />
                </c:when>
                <c:otherwise>
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="card shadow">
                                <div class="card-body">
                                    <h1 class="card-title text-center mb-4">Welcome, ${sessionScope.user.username}!</h1>
                                    
                                    <div class="mb-4">
                                        <dl class="row">
                                            <dt class="col-sm-3">Username</dt>
                                            <dd class="col-sm-9">${sessionScope.user.username}</dd>

                                            <dt class="col-sm-3">Email</dt>
                                            <dd class="col-sm-9">${sessionScope.user.email}</dd>
                                        </dl>
                                    </div>

                                    <div class="d-grid gap-2">
                                        <a href="bookingDashboard" class="btn btn-primary">
                                            My Bookings
                                        </a>
                                        <a href="paymentDashboard" class="btn btn-info">
                                            My Payments
                                        </a>
                                        <a href="logout" class="btn btn-danger">
                                            Log Out
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>
