<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vendor Dashboard - Wedding Planner</title>
    <jsp:include page="/WEB-INF/includes/head.jsp" />
    <style>
        .portfolio-thumb { max-width: 60px; max-height: 60px; object-fit: cover; border-radius: 6px; }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    
    <div class="content-wrapper">
        <div class="container py-4">
            <h1 class="mb-4">Vendors</h1>

            <!-- Filter form -->
            <div class="card mb-4">
                <div class="card-body">
                    <form method="get" action="vendorDashboard" class="row g-3 align-items-center">
                        <div class="col-auto">
                            <label class="form-label">Search by Service Type:</label>
                            <input type="text" name="searchType" class="form-control"
                                value="${param.searchType}" placeholder="e.g. Catering"/>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary">Search</button>
                            <a href="vendorDashboard" class="btn btn-outline-secondary">Clear</a>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Vendor List -->
            <div class="card">
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty vendors}">
                            <div class="alert alert-info">
                                No vendors found.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Service</th>
                                            <th>Description</th>
                                            <th>Contact</th>
                                            <th>Phone</th>
                                            <th>Email</th>
                                            <th>Price Range</th>
                                            <th>Status</th>
                                            <th>Portfolio</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="v" items="${vendors}">
                                            <tr>
                                                <td>${v.name}</td>
                                                <td>${v.serviceType}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${fn:length(v.description) > 40}">
                                                            ${fn:substring(v.description, 0, 40)}...
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${v.description}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${v.contactPerson}</td>
                                                <td>${v.phoneNumber}</td>
                                                <td>
                                                    <a href="mailto:${v.email}">${v.email}</a>
                                                </td>
                                                <td>${v.priceRange}</td>
                                                <td>
                                                    <span class="badge ${v.status == 'Active' ? 'bg-success' : 'bg-secondary'}">
                                                        ${v.status}
                                                    </span>
                                                </td>
                                                <td>
                                                    <c:if test="${not empty v.portfolio}">
                                                        <div class="d-flex flex-wrap gap-1">
                                                            <c:forEach var="img" items="${v.portfolio}" varStatus="loop">
                                                                <a href="${img}" target="_blank">
                                                                    <img src="${img}" class="portfolio-thumb" alt="Portfolio ${loop.index + 1}">
                                                                </a>
                                                            </c:forEach>
                                                        </div>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <a href="editVendor?id=${v.id}" class="btn btn-sm btn-warning mb-1">Edit</a>
                                                    <form action="deleteVendor" method="post" style="display:inline;">
                                                        <input type="hidden" name="id" value="${v.id}">
                                                        <button type="submit" class="btn btn-sm btn-danger mb-1" onclick="return confirm('Delete this vendor?');">Delete</button>
                                                    </form>
                                                    <a href="bookingForm?vendorId=${v.id}" class="btn btn-sm btn-primary mb-1">Book</a>
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
                <a href="addVendor" class="btn btn-success me-2">
                    Add Vendor
                </a>
                <a href="bookingForm" class="btn btn-info">
                    Make a Booking
                </a>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>
