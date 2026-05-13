<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.radi.demo7.Product" %>
<%@ page import="com.radi.demo7.Review" %>
<!DOCTYPE html>
<html>
<head>
    <title>E-Commerce Store</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; margin: 0; padding: 20px; }
        .header { background: white; padding: 16px 24px; border-radius: 8px;
            display: flex; justify-content: space-between; align-items: center;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1); margin-bottom: 24px; }
        .header h1 { margin: 0; font-size: 22px; color: #333; }
        .user-info { font-size: 14px; color: #555; }
        .user-info span { font-weight: bold; color: #2196F3; }
        .logout-btn { background: #e53935; color: white; border: none; padding: 8px 16px;
            border-radius: 4px; cursor: pointer; font-size: 14px; text-decoration: none; }
        .products-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 16px; }
        .product-card { background: white; border-radius: 8px; padding: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
        .product-card h3 { margin: 0 0 8px 0; color: #333; }
        .product-card p { margin: 0 0 12px 0; color: #777; font-size: 14px; }
        .product-card .price { font-size: 20px; font-weight: bold; color: #2196F3; }
        .delete-btn { background: #e53935; color: white; border: none; padding: 6px 12px;
            border-radius: 4px; cursor: pointer; font-size: 13px; margin-top: 10px; }
        .admin-section { background: white; padding: 20px; border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1); margin-bottom: 24px; }
        .admin-section h2 { margin: 0 0 16px 0; font-size: 18px; color: #333; }
        .form-row { display: flex; gap: 10px; flex-wrap: wrap; }
        .form-row input { padding: 8px 12px; border: 1px solid #ddd; border-radius: 4px;
            font-size: 14px; flex: 1; min-width: 150px; }
        .add-btn { background: #4CAF50; color: white; border: none; padding: 8px 16px;
            border-radius: 4px; cursor: pointer; font-size: 14px; }
        .error { background: #ffe0e0; color: #c00; padding: 10px; border-radius: 4px; margin-bottom: 16px; }
    </style>
</head>
<body>

<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    String email = (String) session.getAttribute("email");
%>

<div class="header">
    <h1>🛒 E-Commerce Store</h1>
    <div class="user-info">
        Welcome, <span><%= username %></span> &nbsp;|&nbsp;
        Role: <span><%= role %></span> &nbsp;|&nbsp;
        <%= email %>
    </div>
    <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Sign Out</a>
    <form action="<%= request.getContextPath() %>/deleteAccount" method="post"
          onsubmit="return confirm('Are you sure you want to delete your account?')">
        <button type="submit" class="logout-btn" style="background:#555; margin-left:8px;">
            Delete Account
        </button>
    </form>
</div>

<% if ("admin".equals(role)) { %>
<div class="admin-section">
    <h2>Add New Product (Admin Only)</h2>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error">${error}</div>
    <% } %>
    <form action="<%= request.getContextPath() %>/addProduct" method="post">
        <div class="form-row">
            <input type="text" name="name" placeholder="Product name" required />
            <input type="text" name="description" placeholder="Description" required />
            <input type="number" name="price" placeholder="Price" step="0.01" required />
            <button type="submit" class="add-btn">Add Product</button>
        </div>
    </form>
</div>
<% } %>

<div class="products-grid">
    <%
        List<Product> products = (List<Product>) request.getAttribute("data");
        if (products != null) {
            for (Product p : products) {
    %>
    <div class="product-card">
        <h3><%= p.getName() %></h3>
        <p><%= p.getDescription() %></p>
        <div class="price">$<%= p.getPrice() %></div>
        <% if ("admin".equals(role)) { %>
        <form action="<%= request.getContextPath() %>/deleteProduct" method="post">
            <input type="hidden" name="id" value="<%= p.getId() %>" />
            <button type="submit" class="delete-btn">Delete</button>
        </form>
        <% } %>
    </div>
    <%
            }
        }
    %>
</div>

<!-- Reviews Section -->
<div style="margin-top: 32px;">
    <div class="admin-section">
        <h2>💬 Reviews & Feedback</h2>
        <form action="<%= request.getContextPath() %>/addReview" method="post" style="margin-bottom: 20px;">
            <div class="form-row">
                <select name="productId" style="padding: 8px 12px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px;">
                    <option value="0">General feedback</option>
                    <%
                        List<Product> productsForReview = (List<Product>) request.getAttribute("data");
                        if (productsForReview != null) {
                            for (Product p : productsForReview) {
                    %>
                    <option value="<%= p.getId() %>"><%= p.getName() %></option>
                    <% }} %>
                </select>
                <input type="text" name="content" placeholder="Write your review..." required style="flex: 2;" />
                <button type="submit" class="add-btn">Submit</button>
            </div>
        </form>

        <%
            List<Review> reviews = (List<Review>) request.getAttribute("reviews");
            if (reviews != null && !reviews.isEmpty()) {
                for (Review r : reviews) {
        %>
        <div style="background: #f9f9f9; border-radius: 6px; padding: 12px 16px; margin-bottom: 10px; border-left: 3px solid #2196F3;">
            <strong><%= r.getUsername() %></strong>
            <span style="color: #999; font-size: 12px; margin-left: 8px;"><%= r.getCreatedAt() %></span>
            <p style="margin: 6px 0 0 0; color: #444;"><%= r.getContent() %></p>
        </div>
        <%
            }
        } else {
        %>
        <p style="color: #999;">No reviews yet. Be the first!</p>
        <% } %>
    </div>
</div>

</body>
</html>