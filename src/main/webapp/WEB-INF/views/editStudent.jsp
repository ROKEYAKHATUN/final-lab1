<%@ page contentType="text/html" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head></head>
<body>
    <h3>Edit Student</h3>

    <form:form method="post" action="${pageContext.request.contextPath}/students/${id}/edit" modelAttribute="student">

        <label>Id</label>
        <form:input path="id" id="id" readonly="true"/>

        <label>Full Name</label>
        <form:input path="name" id="name"/>
        <form:errors path="name"/>

        <br><br>

        <label>Date Of Birth</label>
        <form:input type="date" path="dateOfBirth" id="dateOfBirth"/>
        <form:errors path="dateOfBirth"/>

        <br><br>

        <label>Email</label>
        <form:input path="email" id="email"/>
        <form:errors path="email"/>

        <br><br>

        <label>Gender</label>
        <form:radiobutton path="gender" value="MALE" label="Male"/>
        <form:radiobutton path="gender" value="FEMALE" label="Female"/>
        <form:errors path="gender"/>

        <br><br>

        <label>Quota</label>
        <form:input path="quota" id="quota"/>
        <form:errors path="quota"/>

        <br><br>

        <label>Country</label>
        <form:input path="country" id="country"/>
        <form:errors path="country"/>

        <br><br>

        <input type="submit" value="Update" />

    </form:form>

    <a href="${pageContext.request.contextPath}">Home</a>

</body>
</html>
