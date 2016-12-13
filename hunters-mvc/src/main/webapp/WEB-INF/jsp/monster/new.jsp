<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New monster">
    <jsp:attribute name="body">
        <section class="content">
            <div class="row">
                <div class="col-md-8">
                    <div class="box box-primary">
                        <form:form method="post" action="${pageContext.request.contextPath}/monster/create" modelAttribute="monsterToCreate">
                            <div class="form-group">
                                <form:label path="name" cssClass="control-label">Name</form:label>
                                    <div>
                                    <form:input path="name" cssClass="form-control"/>
                                    <form:errors path="name" cssClass="help-block"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <form:label path="height" cssClass="control-label">height</form:label>
                                    <div>
                                    <form:input path="height" cssClass="form-control"/>
                                    <form:errors path="height" cssClass="help-block"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <form:label path="weight" cssClass="control-label">weight</form:label>
                                    <div>
                                    <form:input path="weight" cssClass="form-control"/>
                                    <form:errors path="weight" cssClass="help-block"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <form:label path="power" cssClass="control-label">power</form:label>
                                    <div>
                                    <form:input path="power" cssClass="form-control"/>
                                    <form:errors path="power" cssClass="help-block"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <form:label path="locationId" cssClass="control-label">Location</form:label>
                                <form:select path="locationId">
                                    <form:options items="${locationList}" itemLabel="name" itemValue="id"/>
                                </form:select>
                            </div>
                            <div class="form-group">
                                <form:label path="types" cssClass="control-label">monsterTypes</form:label>
                                    <div>
                                    <form:checkboxes path="types" items="${monsterTypes}" />
                                    <form:errors path="types" cssClass="help-block"/>
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="submit" class="btn btn-primary" >Create Monster</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </section><!-- /.content -->
    </jsp:attribute>
</my:pagetemplate>