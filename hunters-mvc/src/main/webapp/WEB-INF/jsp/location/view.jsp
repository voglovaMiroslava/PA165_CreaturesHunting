<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="location.view.title"><fmt:param value="${location.name}"/></fmt:message>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${location.id}</td>
                                <td><c:out value="${location.name}"/></td>
                                <td><c:out value="${location.description}"/></td>
                                <td class="actions">
                                    <div class="center">
                                        <a href="${pageContext.request.contextPath}/location/edit/${location.id}" title='Edit location'><button style="display:inline; border:0;float: left" class="glyphicon glyphicon-edit"></button></a>
                                    </div>
                                </td>
                                <td class="actions">
                                    <div class="center">
                                        <a href="#" title='Remove location' class="removeItem">
                                            <form method="post" action="${pageContext.request.contextPath}/location/delete/${location.id}">
                                                <button style="display:inline; border:0;float: left" class="glyphicon glyphicon-trash"></button>
                                            </form>
                                        </a>
                                    </div>
                                </td>
                            </tr>

                        </tbody>
                    </table>

                    <br>

                    <table class="table table-hover">
                        <caption>Comments</caption>
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Content</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${location.comments}" var="comment">
                                <tr>
                                    <td><c:out value="${comment.user.nickname}"/></td>
                                    <td><c:out value="${comment.content}"/></td>
                                    <td class="actions">
                                        <div class="center">
                                            <form method="post" action="${pageContext.request.contextPath}/comment/delete/${comment.id}">
                                                <button style="display:inline; border:0;float: left" class="glyphicon glyphicon-trash"></button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <p><my:a href="/location/list">Back</my:a></p>
                        <!--</div>-->
                        <!-- /.box -->
                    </div>
                </div>
            </section><!-- /.content -->

    </jsp:attribute>
</my:pagetemplate>