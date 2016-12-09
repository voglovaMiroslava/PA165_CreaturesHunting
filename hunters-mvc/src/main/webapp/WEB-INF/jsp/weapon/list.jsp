<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="weapon.list.title"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">

     <button><my:a href="/weapon/new" >
        <span aria-hidden="true"></span>
        New weapon
    </my:a></button>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Ammo</th>
            <th>Gun-reach</th>
            <th>Damage</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${weapons}" var="weapon">
            <tr>
                <td>${weapon.id}</td>
                <td><c:out value="${weapon.name}"/></td>
                <td><c:out value="${weapon.ammo}"/></td>
                <td><c:out value="${weapon.gunReach}"/></td>
                <td><c:out value="${weapon.damage}"/></td>
                <td>
                    <button> <my:a href="/weapon/view/${weapon.id}" >View</my:a> </button>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/weapon/delete/${weapon.id}">
                        <button type="submit" >Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>
