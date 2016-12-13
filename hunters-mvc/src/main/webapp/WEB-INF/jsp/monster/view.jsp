<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Monster: ${monster.name}">
    <jsp:attribute name="body">
        <button><my:a href="/monster/list">List of monsters</my:a></button>
        <c:if test="${authenticatedUser.admin}">
            <button><my:a href="/monster/edit/${monster.id}">Edit monster</my:a></button>
            <form method="post" action="${pageContext.request.contextPath}/monster/delete/${monster.id}">
                <button type="submit">Delete</button>
            </form>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>height</th>
                    <th>weight</th>
                    <th>power</th>
                    <th>location</th>
                    <th>types</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${monster.id}</td>
                    <td><c:out value="${monster.name}"/></td>
                    <td><c:out value="${monster.height}"/></td>
                    <td><c:out value="${monster.weight}"/></td>
                    <td><c:out value="${monster.power}"/></td>
                    <td><c:out value="${monster.location.name}"/></td>
                    <td><c:forEach items="${monster.types}" var="type">
                            <c:out value="${type} /"/>
                        </c:forEach>
                    </td>
                </tr>

            </tbody>
        </table>

        <br>

    </jsp:attribute>
</my:pagetemplate>