<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="weapon.view.title"><fmt:param value="${weapon.name}"/></fmt:message>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <div class="row">
            <%--<form style="float:right; margin: 10px" method="post" action="${pageContext.request.contextPath}/weapon/delete/${weapon.id}">--%>
            <%--<button class="glyphicon glyphicon-trash btn" type="submit" ></button>--%>
            <%--</form>--%>
        <button style="float:right; margin: 10px" class="glyphicon glyphicon-trash btn"
                onclick=" openModal(${weapon.id}) ">
        </button>
        <button style="float:right; margin: 10px" class="glyphicon glyphicon-edit btn"
                onclick="location.href='${pageContext.request.contextPath}/weapon/edit/${weapon.id}'">
        </button>

        <button style="float:right; margin: 10px" class="glyphicon glyphicon-list btn"
                onclick="location.href='${pageContext.request.contextPath}/weapon/list'">
        </button>
    </div>
    <%--Table with weapon stats--%>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
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
            <td>
                <table><c:forEach items="${weapon.effectiveAgainst}" var="effective">
                <tr>
                    <td>
                        <c:out value="${effective}"/>
                    </td>
                </tr>
                 </c:forEach>
                </table>

            </td>
        </tr>
        </tbody>
    </table>
    <%--Table of killable monsters--%>
    <table class="table">
        <caption id="killableCaption">
            <button id="showKillable" class="btn btn-primary" onclick="toggleTable()">
                Show monsters that can be killed by "<c:out value="${weapon.name}"/>"
            </button>
        </caption>
        <tbody id="killableBody" hidden>
        <c:forEach items="${killable}" var="monster">
            <tr>
                <td>${monster.name}</td>
            </tr>
        </c:forEach>
        <c:if test="${fn:length(killable) eq 0}">
            <tr>
                <td>This weapon cannot kill any monsters or there are no monsters(Maybe all monsters died)</td>
            </tr>
        </c:if>
        </tbody>

    </table>

    <br>

    <%--Add comment--%>
    <button type="button" class="btn btn-default btn" style="float:right"
            onclick="location.href='${pageContext.request.contextPath}/weapon/${weapon.id}/comment/new'">
        <span class="glyphicon glyphicon-plus" aria-hidden="false"></span> Add Comment
    </button>
    <%--Table of comments--%>
    <table class="table">
        <caption>Comments</caption>
        <thead>
        <tr>
            <th>User</th>
            <th>Content</th>
            <th style="float:right">Delete comment</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${weapon.comments}" var="comment">
            <tr>
                <td><c:out value="${comment.user.nickname}"/></td>
                <td><c:out value="${comment.content}"/></td>
                <td>
                    <button style="float:right; margin: 10px" class="glyphicon glyphicon-trash btn"
                            onclick="openModal('comment_${comment.id}')">
                    </button>
                        <%--Modal for deleting comment--%>
                        <my:modal suffix="comment_${comment.id}" title="Delete comment">
                            <jsp:attribute name="body">
                                <strong>Do you really want to delete comment with id=<c:out value="${comment.id}"/>
                                    written by user "<c:out value="${comment.user.nickname}"></c:out>"</strong>
                            </jsp:attribute>
                            <jsp:attribute name="footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                        onclick="closeModal('comment_${comment.id}')">Close
                                </button>
                                <form style="float: right; margin-left: 10px" method="post"
                                      action="${pageContext.request.contextPath}/weapon/${weapon.id}/comment/delete/${comment.id}">
                                    <input type="submit" class="btn btn-primary" value="Delete"/>
                                </form>
                            </jsp:attribute>
                        </my:modal>
                </td>
            </tr>
           </c:forEach>
        </tbody>
    </table>

    <%--Modal for deleting weapon--%>
    <my:modal suffix="${weapon.id}" title="Delete weapon">
        <jsp:attribute name="body">
            <strong>Do you really want to delete weapon <c:out value="${weapon.name}"/></strong>
        </jsp:attribute>
        <jsp:attribute name="footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal"
                    onclick="closeModal(${weapon.id})">Close
            </button>
            <form style="float: right; margin-left: 10px" method="post"
                  action="${pageContext.request.contextPath}/weapon/delete/${weapon.id}">
                <input type="submit" class="btn btn-primary" value="Delete"/>
            </form>
        </jsp:attribute>
    </my:modal>

    <script>
        function openModal(suffix) {
            var modal = $("#modal_" + suffix);
            if (modal)
                modal.modal('show');
        }
        function closeModal(suffix) {
            var modal = $("#modal_" + suffix);
            if (modal)
                modal.modal('hide');
        }
    </script>

    <script>function toggleTable() {
//        var tButton = document.getElementById("showKillable");
//        var tCaption = document.getElementById("killableCaption");
        var tBody = document.getElementById("killableBody");
        $(tBody).toggle();
    }</script>

</jsp:attribute>
</my:pagetemplate>