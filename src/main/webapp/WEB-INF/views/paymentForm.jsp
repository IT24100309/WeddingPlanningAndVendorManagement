<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Make a Payment - Wedding Planner</title>
    <jsp:include page="/WEB-INF/includes/head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navbar.jsp" />
    
    <div class="content-wrapper">
        <div class="container py-5">
            <h1 class="mb-4">Make a Payment</h1>

            <!-- Payment Form -->
            <div class="card">
                <div class="card-body">
                    <form action="paymentForm" method="post" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label class="form-label">Booking</label>
                            <select name="bookingId" class="form-select" required>
                                <option value="">Select a booking...</option>
                                <c:forEach var="b" items="${bookings}">
                                    <option value="${b.id}">
                                        ${b.vendorName} - ${b.eventDate} (Rs. ${b.price})
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="form-text">Choose the booking to pay for</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Amount (Rs.)</label>
                            <input type="number" step="0.01" name="amount" class="form-control" required
                                min="0">
                            <div class="form-text">Enter the payment amount</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Payment Method</label>
                            <select name="method" class="form-select" required>
                                <option value="">Select payment method...</option>
                                <option value="Credit Card">Credit Card</option>
                                <option value="Debit Card">Debit Card</option>
                                <option value="Net Banking">Net Banking</option>
                                <option value="UPI">UPI</option>
                            </select>
                            <div class="form-text">Choose your preferred payment method</div>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">Make Payment</button>
                            <a href="paymentDashboard" class="btn btn-outline-secondary">
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
