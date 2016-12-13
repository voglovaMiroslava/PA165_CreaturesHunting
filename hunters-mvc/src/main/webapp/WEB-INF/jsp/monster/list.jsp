<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Monsters">
    <jsp:attribute name="body">

        <!-- Main content -->
        <section class="content wow fadeInDown animated">
            <div class="row">
                <div class="col-xs-12">
                    <div style="border-top:0"class="box">
                        <div class="box-header">
                            <h2 class="box-title">List of all monsters</h2>
                            <c:if test="${authenticatedUser.admin}">
                                <div class="box-tools">
                                    <div class="input-group input-group-sm right" style="width: 48px;">
                                        <a href="${pageContext.request.contextPath}/monster/new/">
                                            <button type="button" class="btn btn-info btn-flat" title='Add new monster'><span class="glyphicon glyphicon-plus"></span></button>
                                        </a>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive no-padding">
                            <table class="table table-hover">
                                <tbody>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>In Location</th>
                                        <th></th>
                                            <c:if test="${authenticatedUser.admin}">
                                            <th></th>
                                            </c:if>
                                    </tr>
                                    <c:forEach items="${monsters}" var="monster">
                                        <tr>
                                            <td>${monster.id}</td>
                                            <td><c:out value="${monster.name}"/></td>
                                            <td><c:out value="${monster.location.name}"/></td>

                                            <td class="actions">
                                                <div class="center">
                                                    <a href="${pageContext.request.contextPath}/monster/view/${monster.id}" title='View details'>
                                                        <button style="display:inline; border:0;" >VIEW</button></a>
                                                </div>
                                            </td>
                                            <c:if test="${authenticatedUser.admin}">
                                                <td class="actions">
                                                    <div class="center">
                                                        <a href="#" title='Remove monster' class="removeItem">
                                                            <form style="display: inline-block;" method="post" action="${pageContext.request.contextPath}/monster/delete/${monster.id}">
                                                                <button style="display:inline; border:0">DELETE</button>
                                                            </form>
                                                        </a>
                                                    </div>
                                                </td>
                                            </c:if>

                                        </tr>
                                    </c:forEach>
                                </tbody></table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>
            <!--<form ><input Type="button" VALUE="Go Back" onClick="history.go(-1);return true;"></form>-->
        </section><!-- /.content -->

    </jsp:attribute>
</my:pagetemplate>
