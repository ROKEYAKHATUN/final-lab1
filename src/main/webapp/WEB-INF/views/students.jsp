<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" %>

<html>
<head>
    <title>All Students</title>
</head>
<body>

<h2>All Students</h2>

<c:if test="${not empty students}">
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Date of Birth</th>
                <th>Gender</th>
                <th>Quota</th>
                <th>Country</th>
                <th>....</th>
                <th>....</th>

            </tr>
        </thead>
        <tbody>
            <c:forEach var="student" items="${students}">

                <tr>
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student.email}</td>
                    <td>${student.dateOfBirth}</td>
                    <td>${student.gender}</td>
                    <td>${student.quota}</td>
                    <td>${student.country}</td>
                    <td><a href="${pageContext.request.contextPath}/students/${student.id}/edit">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/students/${student.id}/delete">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty students}">
    <p>No students found.</p>
</c:if>

</body>
</html>
