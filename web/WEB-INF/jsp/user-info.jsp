<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <%@include file="styles.jsp"%>
</head>
<body>
<label>balance: ${requestScope.user.balance}</label><br>
<label>rate: ${requestScope.user.rate}</label><br>
</body>
</html>