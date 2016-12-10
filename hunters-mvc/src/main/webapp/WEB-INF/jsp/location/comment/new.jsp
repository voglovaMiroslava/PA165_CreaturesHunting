<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New location">
    <jsp:attribute name="body">
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-8">
                    <div class="box box-primary">
                        <!--                        <div class="box-header with-border">
                                                    <h3 class="box-title">Create new location</h3>
                                                </div>-->
                        <!-- /.box-header -->
                        <!-- form start -->
                        <form:form method="post" action="${pageContext.request.contextPath}/location/comment/create" modelAttribute="commentCreate">
                            <div class="form-group">
                                <form:label path="content" cssClass="control-label">Content</form:label>
                                    <div>
                                    <form:input path="content" cssClass="form-control"/>
                                    <form:errors path="content" cssClass="help-block"/>
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="submit" class="btn btn-primary" >Create Comment</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </section><!-- /.content -->
    </jsp:attribute>
</my:pagetemplate>