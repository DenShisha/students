<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../resources/css/style.css">
    <title>Students Progress Page</title>
</head>
<body>
<div>
    <h1>Система управления студентами и их успеваемостью</h1>
    <c:choose>
        <c:when test="${isAuthorised eq true}">
            <p>Привет, ${login}</p>
            <a class="Logout" href="/logout">Logout</a>
        </c:when>
        <c:otherwise>
            <a class="Login" href="/login">Login</a>
        </c:otherwise>
    </c:choose>
</div>

<div class="navP">
    <nav>
        <a href="/">На главную</a>
        <a href="/students">Назад</a>
    </nav>
    <p class="p1">Отображена успеваемость для следующего студента:</p>
</div>

<div>
    <table class="table1" border="1">
        <tr>
            <th class="th1">Фамилия</th>
            <th class="th2">Имя</th>
            <th class="th3">Группа</th>
            <th>Дата поступления</th>
        </tr>
        <tr>
            <td>${student.surname}</td>
            <td>${student.name}</td>
            <td>${student.group.name}</td>
            <td><fmt:formatDate value="${student.date}" pattern="d/MM/yyyy"/></td>
        </tr>
    </table>
</div>


<div class="tableSelect">
    <div class="qqq">
        <table class="table2" border="1">
            <tr>
                <th class="th11">Дисциплина</th>
                <th class="th12">Оценка</th>
            </tr>
            <c:forEach items="${grades}" var="g">
                <tr>
                    <td>${g.discipline.name}</td>
                    <c:if test="${g.value ne -1}">
                        <td>${g.value}</td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="Select">
        <form action="/student_progress" method="get">
            <input type="hidden" name="idForProgress" value="${student.id}">
            <label >
                Выбрать семестр
                <select name="selectedTermId">
                    <c:forEach items="${terms}" var="t">
                        <c:choose>
                            <c:when test="${t.id == selectedTerm.id}">
                                <option selected value="${t.id}">${t.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${t.id}">${t.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </label>
            <button class="button1">Выбрать</button> <br>
        </form>
        <p>Средняя оценка за семестр: <fmt:formatNumber value="${averageGrade}" maxFractionDigits="2"/></p>
    </div>
</div>
</body>
</html>