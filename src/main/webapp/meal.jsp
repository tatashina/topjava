<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
</head>
<body>
    <form method="POST">
        Дата/время : <input type="datetime-local" name="dateTime" value="${meal.dateTime}"/> <br/>
        Описание : <input type="text" name="description" value="${meal.description}"/> <br/>
        Калории : <input type="number" name="calories" value="${meal.calories}"/> <br/>
        <br/>
        <input type="submit" value="Submit" />
    </form>
    <a href="meals">back</a>
</body>
</html>
