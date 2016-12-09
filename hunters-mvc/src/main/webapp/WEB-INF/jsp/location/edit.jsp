<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit location">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/location/update/${locationUpdate.id}"
                   modelAttribute="locationUpdate" cssClass="form-horizontal">

            <div>
                <form:label path="name" cssClass="control-label">Name</form:label>
                <div>
                    <form:input path="name" cssClass="form-control" value="${locationUpdate.name}"/>
                    <form:errors path="name" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="description" cssClass="control-label">Description</form:label>
                <div>
                    <form:input path="description" cssClass="form-control" value="${locationUpdate.description}"/>
                    <form:errors path="description" cssClass="help-block"/>
                </div>
            </div>
                
            <button type="submit">Update Location</button>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>