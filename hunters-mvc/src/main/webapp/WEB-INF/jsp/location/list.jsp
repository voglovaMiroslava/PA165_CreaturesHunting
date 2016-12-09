<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="location.list.title"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">

     <button><my:a href="/location/new" >
        <span aria-hidden="true"></span>
        New location
    </my:a></button>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${locations}" var="location">
            <tr>
                <td>${location.id}</td>
                <td><c:out value="${location.name}"/></td>
                <td><c:out value="${location.description}"/></td>
                <td>
                    <button> <my:a href="/location/view/${location.id}" >View</my:a> </button>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/location/delete/${location.id}">
                        <button type="submit" >Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>
