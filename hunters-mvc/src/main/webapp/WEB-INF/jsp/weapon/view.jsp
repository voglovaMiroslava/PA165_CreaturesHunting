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
                <div class="col-xs-12">
                    <div style="border-top:0"class="box">
                        <div class="row">
                            <c:if test="${authenticatedUser.isAdmin()}">
                                <button style="float:right; margin: 10px" class="glyphicon glyphicon-trash " title='Delete weapon'
                                        onclick=" openModal(${weapon.id}) ">
                                </button>
                                <button style="float:right; margin: 10px" class="glyphicon glyphicon-edit" title='Edit weapon'
                                        onclick="location.href='${pageContext.request.contextPath}/weapon/edit/${weapon.id}'">
                                </button>
                            </c:if>
                            <button style="float:right; margin: 10px" class="glyphicon glyphicon-list" title='List of weapons'
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
                        <table class="table-bordered table-hover" style="width:300px">
                            <caption id="killableCaption">
                                <button id="showKillable" class="btn btn-primary"
                                        onclick="toggleTable()">Hide monsters that can be killed</button>
                            </caption>
                            <tbody id="killableBody">
                            <c:forEach items="${killable}" var="monster">
                                <tr>
                                    <td><a href="${pageContext.request.contextPath}/monster/view/${monster.id}">
                                    <c:out value="${monster.name}"/></a>
                                    </td>
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
                        <c:if test="${not empty authenticatedUser}">
                        <button type="button" class="btn btn-default" style="float:right" title='Add comment for this weapon'
                                onclick="location.href='${pageContext.request.contextPath}/weapon/${weapon.id}/comment/new'">
                            <span class="glyphicon glyphicon-plus" aria-hidden="false"></span> Add Comment
                        </button>
                        </c:if>
                        <c:if test="${empty authenticatedUser}">
                             <button type="button" class="btn btn-primary" style="float:right"
                                     onclick="location.href='${pageContext.request.contextPath}/user/login'">
                                 LOGIN to add comment
                             </button>
                        </c:if>

                            <%--Table of comments--%>
                            <table class="table table-hover">
                                <caption><strong>Comments</strong></caption>
                                <thead>
                                <tr>
                                    <th>User</th>
                                    <th>Content</th>
                                    <c:if test="${authenticatedUser.isAdmin() == true}">
                                    <th style="float:right">Delete comment</th>
                                    </c:if>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${weapon.comments}" var="comment">
                                    <tr>
                                        <td><c:out value="${comment.user.nickname}"/></td>
                                        <td><c:out value="${comment.content}"/></td>
                                        <td>
                                            <c:if test="${authenticatedUser.isAdmin() or (authenticatedUser.id eq comment.user.id)}">
                                                <button style="float:right; margin: 10px" class="glyphicon glyphicon-trash btn-ls" title='Delete comment'
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
                                            </c:if>
                                        </td>
                                    </tr>
                                   </c:forEach>
                                </tbody>
                            </table>

                    </div>
                </div>
 </div>

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
        var tButton = document.getElementById("showKillable");
//        var tCaption = document.getElementById("killableCaption");
        var tBody = document.getElementById("killableBody");
        var buttonText = document.getElementById("showKillable").innerHTML;
        if (buttonText=="Show monsters that can be killed"){
            document.getElementById("showKillable").innerHTML="Hide monsters that can be killed";
            location.reload();
        }
        if (buttonText=="Hide monsters that can be killed"){
            document.getElementById("showKillable").innerHTML="Show monsters that can be killed";
        }
        $(tBody).toggle();
    }</script>

</jsp:attribute>
</my:pagetemplate>