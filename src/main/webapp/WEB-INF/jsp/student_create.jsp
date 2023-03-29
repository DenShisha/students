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
    <title>Students Create</title>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
    <script>
        $( function() {
            $( "#datepicker" ).datepicker();
        } );
    </script>
</head>
<body>
<span>
        <h1>Система управления студентами и их успеваемостью</h1>
        <a class="Logout" href="">Logout</a>
    </span>
<div class="navP">
    <nav>
        <a href="/">На главную</a>
        <a href="/students">Назад</a>
    </nav>
    <p class="p1">Для создания студента заполните все поля и нажмите кнопку "Создать".</p>
</div>
<form action="student_create" method="post">
    <div class="input1"><label>Фамилия <input name="surname" type="text"></label></div>
    <div class="input2"><label>Имя <input name="name" type="text"></label></div>
    <div class="input3"><label>Группа <input name="group" type="text"></label></div>
    <div class="input4"><label>Дата поступления <input id="datepicker" name="date" type="text"></label></div>
    <div class="button2">
        <button>Создать</button>
    </div>
</form>
<c:if test="${error eq 1}">
    <h4 class="p4">Поля не должны быть пустыми</h4>
</c:if>
</body>
</html>