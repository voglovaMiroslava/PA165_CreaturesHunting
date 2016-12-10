<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--<fmt:message var="title" key="location.list.title"/>--%>
<my:pagetemplate title="List of locations">
    <jsp:attribute name="body">

        <!-- Main content -->
        <section class="content wow fadeInDown animated">
            <div class="row">
                <div class="col-xs-12">
                    <div style="border-top:0"class="box">
                        <div class="box-header">
                            <h2 class="box-title">Location List</h2>

                            <div class="box-tools">
                                <div class="input-group input-group-sm right" style="width: 48px;">
                                    <a href="${pageContext.request.contextPath}/location/new/">
                                        <button type="button" class="btn btn-info btn-flat" title='Add new location'><span class="glyphicon glyphicon-plus"></span></button>
                                    </a>
                                </div>
                                <!--                                <div class="input-group input-group-sm right" style="width: 48px;">
                                                                    <a href="${pageContext.request.contextPath}/">
                                                                        <button type="button" class="btn btn-info btn-flat" title='Go back'><span class="glyphicon glyphicon-backward"></span></button>
                                                                    </a>
                                                                </div>-->
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive no-padding">
                            <table class="table table-hover">
                                <tbody><tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Description</th>
                                        <th>Edit</th>
                                        <th>Delete</th>
                                        <c:if test="${authenticatedUser.isAdmin==true}"><th><fmt:message key="example.actions"/></th></c:if>
                                        </tr>
                                    <c:forEach items="${locations}" var="location">
                                        <tr>
                                            <td>${location.id}</td>
                                            <td><c:out value="${location.name}"/></td>
                                            <td><c:out value="${location.description}"/></td>

                                            <td class="actions">
                                                <div class="center">
                                                    <a href="${pageContext.request.contextPath}/location/view/${location.id}" title='View details'>
                                                        <button style="display:inline; border:0;" class="glyphicon glyphicon-eye-open"></button></a>
                                                </div>
                                            </td>
                                            <td class="actions">
                                                <div class="center">
                                                    <a href="#" title='Remove location' class="removeItem">
                                                        <form style="display: inline-block;" method="post" action="${pageContext.request.contextPath}/location/delete/${location.id}">
                                                            <button style="display:inline; border:0" class="glyphicon glyphicon-trash"></button>
                                                        </form>
                                                    </a>
                                                </div>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </tbody></table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>
            <form ><input Type="button" VALUE="Go Back" onClick="history.go(-1);return true;"></form>
        </section><!-- /.content -->

    </jsp:attribute>
</my:pagetemplate>
