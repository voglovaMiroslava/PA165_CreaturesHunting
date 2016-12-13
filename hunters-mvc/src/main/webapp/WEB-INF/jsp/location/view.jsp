<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="location.view.title"><fmt:param value="${location.name}"/></fmt:message>
<my:pagetemplate title="Location: ${location.name}">
    <jsp:attribute name="body">
        <section class="content wow fadeInDown animated">
            <div class="row">
                <div class="col-xs-12">
                    <div style="border-top:0"class="box">
                        <div class="box-header">
                            <!--<h2 class="box-title"></h2>-->
                        </div>
                        <table class="table table-hover">
                            <caption>Details</caption>
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Description</th>
                                    <c:if test="${not empty bestWeapon}">
                                        <th>Best Weapon</th>
                                    </c:if>
                                    <c:if test="${not empty authenticatedUser}">
                                        <th>Add Comment</th>
                                    </c:if>
                                    <c:if test="${authenticatedUser.isAdmin() == true}">
                                        <th>Edit</th>
                                        <th>Delete</th>
                                    </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${location.id}</td>
                                    <td><c:out value="${location.name}"/></td>
                                    <td><c:out value="${location.description}"/></td>
                                    <c:if test="${not empty bestWeapon}">
                                        <td class="actions">
                                            <div class="center">

                                                <a href="${pageContext.request.contextPath}/weapon/view/${bestWeapon.id}" title='View best Weapon'>
                                                    <button style="display:inline; border:0;" class="fa fa-shield"></button></a>

                                            </div>
                                        </td>
                                    </c:if>
                                    <c:if test="${not empty authenticatedUser}">
                                        <td class="actions">
                                            <div class="center">
                                                <a href="${pageContext.request.contextPath}/location/${location.id}/comment/new/" title='Add comment'>
                                                    <button style="display:inline; border:0;" class="fa fa-pencil"></button></a>
                                            </div>
                                        </td>
                                    </c:if>
                                    <c:if test="${authenticatedUser.isAdmin() == true}">
                                        <td class="actions">
                                            <div class="center">
                                                <a href="${pageContext.request.contextPath}/location/edit/${location.id}" title='Edit location'>
                                                    <button style="display:inline; border:0;float: left" class="glyphicon glyphicon-edit"></button></a>
                                            </div>
                                        </td>
                                        <td class="actions">
                                            <div class="center">
                                                <a href="#" title='Remove location' class="removeItem">
                                                    <form style="display: inline-block;" method="post" action="${pageContext.request.contextPath}/location/delete/${location.id}">
                                                        <button style="border:0" class="glyphicon glyphicon-trash"></button>
                                                    </form>
                                                </a>
                                            </div>
                                        </td>
                                    </c:if>
                                </tr>

                            </tbody>
                        </table>
                        <c:choose>
                            <c:when test="${monsters.size() > 0}"> 
                                <table class="table table-hover">
                                    <caption>Monsters</caption>
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Name</th>
                                            <th>Detail</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${monsters}" var="monster">
                                            <tr>
                                                <td><c:out value="${monster.id}"/></td>
                                                <td><c:out value="${monster.name}"/></td>
                                                <td class="actions">
                                                <td class="actions">
                                                    <div class="center">
                                                        <a href="${pageContext.request.contextPath}/monster/view/${monster.id}" title='View details'>
                                                            <button style="display:inline; border:0;" class="glyphicon glyphicon-eye-open"></button></a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when> 
                            <c:otherwise>
                                <h3>There are no monsters!</h3>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${comments.size() > 0}">                        
                                <table class="table table-hover">
                                    <caption>Comments</caption>
                                    <thead>
                                        <tr>
                                            <th>User</th>
                                            <th>Content</th>
                                                <c:if test="${authenticatedUser.isAdmin() == true}">
                                                <th>Delete</th>
                                                </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${comments}" var="comment">
                                            <tr>
                                                <td><c:out value="${comment.user.nickname}"/></td>
                                                <td><c:out value="${comment.content}"/></td>
                                                <c:if test="${authenticatedUser.isAdmin() == true}">
                                                    <td class="actions">
                                                        <div class="center">
                                                            <a href="#" title='Remove comment' class="removeItem">
                                                                <form method="post" action="${pageContext.request.contextPath}/location/${location.id}/comment/delete/${comment.id}">
                                                                    <button style="display:inline; border:0;float: left" class="glyphicon glyphicon-trash"></button>
                                                                </form>
                                                            </a>
                                                        </div>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when> 
                            <c:otherwise>
                                <h3>There are no comments!</h3>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <!--                <form ><input Type="button" VALUE="Go Back" onClick="history.go(-1);return true;"></form>-->
                <center>
                    <a href="${pageContext.request.contextPath}/location/list" title='Go to list of locations'>
                        <button style="display:inline; border:0;" class="glyphicon glyphicon-backward"> Back to Locations</button></a>
                </center>
        </section>

    </jsp:attribute>
</my:pagetemplate>