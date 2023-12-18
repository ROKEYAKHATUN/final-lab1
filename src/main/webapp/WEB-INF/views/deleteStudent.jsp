<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Student</title>
</head>
<body>

<h2>Delete Student</h2>

<p>Are you sure you want to delete this student?</p>

<!-- Display student details -->

<form method="post" action="/students/${student.id}/delete">
    <input type="submit" value="Delete"/>
</form>

<a href="/students">Cancel</a>

</body>
</html>
