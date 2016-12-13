<%--
  Created by IntelliJ IDEA.
  User: Snurka
  Date: 12/12/2016
  Time: 2:15 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="User">
<jsp:attribute name="body">

    <table class="table">
        <caption>Users</caption>
        <thead>
        <tr>
            <th>nickname</th>
            <th>email</th>
            <th>isAdmin</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td><c:out value="${user.nickname}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.admin}"/></td>
            </tr>
        </tbody>
    </table>
    <button><my:a href="/user/changePassword" >
        <span aria-hidden="true"></span>
        Change Password
    </my:a></button>


</jsp:attribute>
</my:pagetemplate>

