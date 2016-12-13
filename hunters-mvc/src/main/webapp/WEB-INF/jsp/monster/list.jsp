<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Monsters">
    <jsp:attribute name="body">

        <section class="content wow fadeInDown animated">
            <img alt="monster" src="/pa165/static/dist/img/creepy-monster.png" style="width: 220px;">
            <c:if test="${authenticatedUser.admin}">
                    <div class="input-group input-group-sm monster-add-button">
                        <a href="/pa165/monster/new/">
                            <button type="button" class="btn btn-info btn-flat" title="Add new monster"><span class="glyphicon glyphicon-plus"></span></button>
                        </a>
                    </div>
            </c:if>
            <div class="row">
                <div class="col-xs-12">
                    <div style="border-top:0"class="box">
                        <div class="box-body table-responsive no-padding">
                            <table class="table table-hover">
                                <thead class="monster-thead">
                                <th>#</th>
                                <th>Name</th>
                                <th>Location</th>
                                <th>Dangerousness</th>
                                <th></th>
                                    <c:if test="${authenticatedUser.admin}">
                                    <th></th>
                                    </c:if>
                                </thead>
                                <tbody>
                                    <c:forEach items="${monsters}" var="monster">
                                        <tr>
                                            <td>${monster.id}</td>
                                            <td><c:out value="${monster.name}"/></td>
                                            <td><c:out value="${monster.location.name}"/></td>
                                            <td><c:out value="${monster.power}"/></td>

                                            <td class="actions">
                                                <div class="center">
                                                    <a href="${pageContext.request.contextPath}/monster/view/${monster.id}" title='View details'>
                                                        <button class="monster-button">VIEW</button></a>
                                                </div>
                                            </td>
                                            <c:if test="${authenticatedUser.admin}">
                                                <td class="actions">
                                                    <div class="center">
                                                        <a href="#" title='Remove monster' class="removeItem">
                                                            <form style="display: inline-block;" method="post" action="${pageContext.request.contextPath}/monster/delete/${monster.id}">
                                                                <button class="monster-button">DELETE</button>
                                                            </form>
                                                        </a>
                                                    </div>
                                                </td>
                                            </c:if>

                                        </tr>
                                    </c:forEach>
                                </tbody></table>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </jsp:attribute>
</my:pagetemplate>
