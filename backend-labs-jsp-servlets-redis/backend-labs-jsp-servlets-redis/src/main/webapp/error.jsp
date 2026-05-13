<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Something went wrong</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5;
            display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .box { background: white; padding: 40px; border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1); text-align: center; max-width: 400px; }
        h2 { color: #e53935; margin-bottom: 12px; }
        p { color: #666; margin-bottom: 24px; }
        a { background: #2196F3; color: white; padding: 10px 20px;
            border-radius: 4px; text-decoration: none; font-size: 14px; }
    </style>
</head>
<body>
<div class="box">
    <h2>⚠️ Something went wrong</h2>
    <p>An error occurred while processing your request. Please try again.</p>
    <a href="javascript:history.back()">Go Back</a>
</div>
</body>
</html>