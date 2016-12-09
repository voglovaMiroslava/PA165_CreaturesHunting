<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="location.view.title"><fmt:param value="${location.name}"/></fmt:message>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <button><my:a href="/location/list">List of Locations</my:a></button>
        <button><my:a href="/location/edit/${location.id}">Edit Location</my:a></button>
        <form method="post" action="${pageContext.request.contextPath}/location/delete/${location.id}">
            <button type="submit">Delete</button>
        </form>

        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Description</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${location.id}</td>
                    <td><c:out value="${location.name}"/></td>
                    <td><c:out value="${location.description}"/></td>
                    </td>
                </tr>

            </tbody>
        </table>

        <br>

        <table>
            <caption>Comments</caption>
            <thead>
                <tr>
                    <th>User</th>
                    <th>Content</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${location.comments}" var="comment">
                    <tr>
                        <td><c:out value="${comment.user.nickname}"/></td>
                        <td><c:out value="${comment.content}"/></td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/comment/delete/${comment.id}">
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


    </jsp:attribute>
</my:pagetemplate>