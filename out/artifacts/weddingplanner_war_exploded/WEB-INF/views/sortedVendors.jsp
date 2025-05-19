<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vendors Sorted by Price - Wedding Planner</title>
    <jsp:include page="/WEB-INF/includes/head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    
    <div class="content-wrapper">
        <div class="container py-5">
            <h1 class="mb-4">Vendors (Sorted by Price)</h1>

            <!-- Back to Vendor Dashboard -->
            <div class="mb-4">
                <a href="vendorDashboard" class="btn btn-outline-secondary">
                    ← Back to Vendor Dashboard
                </a>
            </div>

            <!-- Vendor List -->
            <div class="card">
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty vendors}">
                            <div class="alert alert-info">
                                No vendors available.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Service Type</th>
                                            <th>Price Range</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="v" items="${vendors}">
                                            <tr>
                                                <td>${v.name}</td>
                                                <td>${v.serviceType}</td>
                                                <td>Rs. ${v.priceRange}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>
