<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="weapon.list.title"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <div class="container-fluid" style="margin-bottom: 10px">
        <c:if test="${authenticatedUser.isAdmin()}">
            <button type="button" class="btn-primary btn" style="float:right" title='Add new weapon'
                         onclick="location.href='${pageContext.request.contextPath}/weapon/new'">
                <span class="glyphicon glyphicon-plus" aria-hidden="false"></span> Add New
            </button>
        </c:if>
    </div>

    <div class="box-body table-responsive no-padding">
        <table class="table table-hover">
            <thead class="monster-thead">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Ammo</th>
                <th>Gun-reach</th>
                <th>Damage</th>
                <th>Detail</th>
                <c:if test="${authenticatedUser.isAdmin()}">
                <th>Delete</th>
            </c:if>
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
                    <button class="glyphicon glyphicon-eye-open" title='Details about weapon'
                            onclick="location.href='${pageContext.request.contextPath}/weapon/view/${weapon.id}'">
                    </button>
                </td>
                <td>
                        <%--<form method="post" action="${pageContext.request.contextPath}/weapon/delete/${weapon.id}">--%>
                        <%--<button class="glyphicon glyphicon-trash btn" type="submit"></button>--%>
                        <%--</form>--%>
                    <c:if test="${authenticatedUser.isAdmin()}">
                        <button class="glyphicon glyphicon-trash" onclick=" openModal(${weapon.id})" title='Delete weapon'>
                        </button>
                    </c:if>

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
                </td>
            </tr>
        </c:forEach>
            </tbody>
        </table>
    </div>

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


</jsp:attribute>
</my:pagetemplate>
