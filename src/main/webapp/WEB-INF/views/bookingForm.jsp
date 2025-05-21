<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Make a Booking - Wedding Planner</title>
    <jsp:include page="/WEB-INF/includes/head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    
    <div class="content-wrapper">
        <div class="container py-5">
            <h1 class="mb-4">Book a Vendor</h1>

            <!-- Booking Form -->
            <div class="card">
                <div class="card-body">
                    <form action="bookingForm" method="post" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label class="form-label">Vendor</label>
                            <select name="vendorName" class="form-select" required>
                                <option value="">Select a vendor...</option>
                                <c:forEach var="v" items="${vendors}">
                                    <option value="${v.name}">
                                        ${v.serviceType} by ${v.name} @ Rs.${v.priceRange}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="form-text">Choose a vendor from the list</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Event Date</label>
                            <input type="date" name="eventDate" class="form-control" required
                                min="${java.time.LocalDate.now()}">
                            <div class="form-text">Select your event date</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Price (Rs.)</label>
                            <input type="number" step="0.01" name="price" class="form-control" required
                                min="0">
                            <div class="form-text">Enter the booking amount</div>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">Book Now</button>
                            <a href="bookingDashboard" class="btn btn-outline-secondary">
                                Cancel
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
    
    <script>
        // Form validation
        (function () {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.prototype.slice.call(forms).forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
        })()
    </script>
</body>
</html>
