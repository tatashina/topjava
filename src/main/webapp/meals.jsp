<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <table>
        <tr>
            <th>Дата/время</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>
        <tr>
            <c:forEach items="${mealList}" var="meal">
                <tr style="color:${meal.excess ? 'red' : 'green'}">
                    <td><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                </tr>
            </c:forEach>
    </table>
</body>
</html>
