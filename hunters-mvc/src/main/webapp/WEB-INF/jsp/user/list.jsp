<%--
  Created by IntelliJ IDEA.
  User: Snurka
  Date: 12/12/2016
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Users">
    <jsp:attribute name="body">

        <table class="table">
            <caption>Users</caption>
            <thead>
                <tr>
                    <th>id</th>
                    <th>nickname</th>
                    <th>email</th>
                    <th>isAdmin</th>
                        <c:if test="${authenticatedUser.isAdmin()==true}">
                        <th>Delete</th> 
                        </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td><c:out value="${user.nickname}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.admin}"/></td>
                        <c:if test="${authenticatedUser.isAdmin()==true && user.isAdmin() == false}">
                            <td class="actions">
                                <div class="center">
                                    <a href="#" title='Remove user' class="removeItem">
                                        <form style="display: inline-block;" method="post" action="${pageContext.request.contextPath}/user/delete/${user.id}">
                                            <button style="display:inline; border: 0; background-color: transparent" class="glyphicon glyphicon-trash"></button>
                                        </form>
                                    </a>
                                </div>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
