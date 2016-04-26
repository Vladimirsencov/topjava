${meal.dateTime.toLocalDate()}  ${meal.dateTime.toLocalTime()}
<%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.9/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="webjars/datetimepicker/2.3.4/jquery.datetimepicker.css">
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <%--<section>
                &lt;%&ndash;http://stackoverflow.com/questions/10327390/how-should-i-get-root-folder-path-in-jsp-page&ndash;%&gt;
                <h3><a href="${pageContext.request.contextPath}">Home</a></h3>
                <h3><fmt:message key="meals.title"/></h3>
                <form method="post" action="meals/filter">
                    <dl>
                        <dt>From Date:</dt>
                        <dd><input type="date" name="startDate" value="${startDate}"></dd>
                    </dl>
                    <dl>
                        <dt>To Date:</dt>
                        <dd><input type="date" name="endDate" value="${endDate}"></dd>
                    </dl>
                    <dl>
                        <dt>From Time:</dt>
                        <dd><input type="time" name="startTime" value="${startTime}"></dd>
                    </dl>
                    <dl>
                        <dt>To Time:</dt>
                        <dd><input type="time" name="endTime" value="${endTime}"></dd>
                    </dl>
                    <button type="submit">Filter</button>
                </form>
                <hr>
                <a href="meals/create">Add Meal</a>
                <hr>
                <table border="1" cellpadding="8" cellspacing="0">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Calories</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <c:forEach items="${mealList}" var="meal">
                        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
                        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                            <td>
                                    &lt;%&ndash;${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}&ndash;%&gt;
                                <%=TimeUtil.toString(meal.getDateTime())%>
                            </td>
                            <td>${meal.description}</td>
                            <td>${meal.calories}</td>
                            <td><a href="meals/update?id=${meal.id}">Update</a></td>
                            <td><a href="meals/delete?id=${meal.id}">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </section>--%>
            <h3><fmt:message key="meals.title"/></h3>
            <div class="view-box">
                <form method="post" class="form-horizontal" role="form" id="filter">
                    <div class="form-group">


                        <label class="control-label col-sm-2" for="startDate">From Date</label>

                        <div class="col-sm-2">
                            <input type="date" name="startDate" id="startDate">
                        </div>


                        <label class="control-label col-sm-2" for="endDate">To Date</label>

                        <div class="col-sm-2">
                            <input type="date" name="endDate" id="endDate">
                        </div>


                        <label class="control-label col-sm-2" for="startTime">From Time</label>

                        <div class="col-sm-2">
                            <input type="time" name="startTime" id="startTime">
                        </div>


                        <label class="control-label col-sm-2" for="endTime">To Time</label>

                        <div class="col-sm-2">
                            <input type="time" name="endTime" id="endTime">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2">
                            <button type="submit" class="btn btn-primary pull-right">Filter</button>
                        </div>
                    </div>
                </form>
                <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.add"/></a>

                <jsp:include page="fragments/footer.jsp"/>
</body>
</html>
