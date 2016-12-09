<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="weapon.view.title"><fmt:param value="${weapon.name}"/></fmt:message>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <button><my:a href="/weapon/list">List of Weapons</my:a></button>
    <button><my:a href="/weapon/edit/${weapon.id}">Edit Weapon</my:a></button>
  <form method="post" action="${pageContext.request.contextPath}/weapon/delete/${weapon.id}">
      <button type="submit">Delete</button>
  </form>


    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Ammo</th>
            <th>Gun reach</th>
            <th>Damage</th>
            <th>Effective against</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${weapon.id}</td>
            <td><c:out value="${weapon.name}"/></td>
            <td><c:out value="${weapon.ammo}"/></td>
            <td><c:out value="${weapon.gunReach}"/></td>
            <td><c:out value="${weapon.damage}"/></td>
            <td><c:forEach items="${weapon.effectiveAgainst}" var="effective">
                <c:out value="${effective} /"/>
            </c:forEach>
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
        <c:forEach items="${weapon.comments}" var="comment">
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