<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            width: 350px;
        }
        h2 {
            text-align: center;
            margin-bottom: 24px;
            color: #333;
        }
        input {
            width: 100%;
            padding: 10px;
            margin: 8px 0 16px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }
        button {
            width: 100%;
            padding: 10px;
            background: #2196F3;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        button:hover { background: #1976D2; }
        .error {
            background: #ffe0e0;
            color: #c00;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 16px;
            font-size: 14px;
        }
        .link {
            text-align: center;
            margin-top: 16px;
            font-size: 14px;
        }
        a { color: #2196F3; text-decoration: none; }
    </style>
</head>
<body>
<div class="container">
    <h2>Login</h2>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error">${error}</div>
    <% } %>

    <form action="<%= request.getContextPath() %>/login" method="post">
        <label>Username</label>
        <input type="text" name="username" required />

        <label>Password</label>
        <input type="password" name="password" required />

        <button type="submit">Login</button>
    </form>

    <div class="link">
        Don't have an account? <a href="<%= request.getContextPath() %>/signup">Sign Up</a>
    </div>
</div>
</body>
</html>