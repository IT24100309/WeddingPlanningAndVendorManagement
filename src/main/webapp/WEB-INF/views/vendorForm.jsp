<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${vendor != null ? 'Edit' : 'Add'} Vendor - Wedding Planner</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow">
                <div class="card-body">
                    <h2 class="card-title mb-4 text-center">${vendor != null ? 'Edit' : 'Add'} Vendor</h2>
                    <form action="${vendor != null ? 'editVendor' : 'addVendor'}" method="post" enctype="multipart/form-data" class="needs-validation" novalidate>
                        <c:if test="${vendor != null}">
                            <input type="hidden" name="id" value="${vendor.id}">
                        </c:if>
                        <div class="mb-3">
                            <label class="form-label">Vendor Name</label>
                            <input type="text" name="name" class="form-control" required maxlength="100" value="${vendor.name}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Service Type</label>
                            <select name="serviceType" class="form-select" required>
                                <option value="">Select...</option>
                                <option value="Catering" ${vendor.serviceType == 'Catering' ? 'selected' : ''}>Catering</option>
                                <option value="Photography" ${vendor.serviceType == 'Photography' ? 'selected' : ''}>Photography</option>
                                <option value="Venue" ${vendor.serviceType == 'Venue' ? 'selected' : ''}>Venue</option>
                                <option value="Decor" ${vendor.serviceType == 'Decor' ? 'selected' : ''}>Decor</option>
                                <option value="Music" ${vendor.serviceType == 'Music' ? 'selected' : ''}>Music</option>
                                <option value="Makeup" ${vendor.serviceType == 'Makeup' ? 'selected' : ''}>Makeup</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <textarea name="description" class="form-control" rows="3" maxlength="500">${vendor.description}</textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Contact Person</label>
                            <input type="text" name="contactPerson" class="form-control" maxlength="100" value="${vendor.contactPerson}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Phone Number</label>
                            <input type="tel" name="phoneNumber" class="form-control" maxlength="20" value="${vendor.phoneNumber}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" maxlength="100" value="${vendor.email}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Website / Social Media</label>
                            <input type="url" name="website" class="form-control" maxlength="200" value="${vendor.website}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Address</label>
                            <input type="text" name="address" class="form-control" maxlength="200" value="${vendor.address}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Price Range</label>
                            <input type="text" name="priceRange" class="form-control" maxlength="50" value="${vendor.priceRange}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Availability</label>
                            <input type="text" name="availability" class="form-control" maxlength="100" value="${vendor.availability}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Portfolio (Images/Links)</label>
                            <input type="file" name="portfolio" class="form-control" multiple>
                            <c:if test="${vendor.portfolio != null}">
                                <div class="mt-2">
                                    <strong>Current Portfolio:</strong>
                                    <ul>
                                        <c:forEach var="img" items="${vendor.portfolio}">
                                            <li><a href="${img}" target="_blank">${img}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Terms & Conditions</label>
                            <textarea name="terms" class="form-control" rows="2" maxlength="500">${vendor.terms}</textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Status</label>
                            <select name="status" class="form-select">
                                <option value="Active" ${vendor.status == 'Active' ? 'selected' : ''}>Active</option>
                                <option value="Inactive" ${vendor.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success">${vendor != null ? 'Update' : 'Add'} Vendor</button>
                        <a href="vendorDashboard" class="btn btn-outline-secondary ms-2">Cancel</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Bootstrap form validation
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