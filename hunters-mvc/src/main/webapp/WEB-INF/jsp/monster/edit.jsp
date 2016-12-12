<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit monster">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/monster/update/${monsterToUpdate.id}"
                   modelAttribute="monsterToUpdate" cssClass="form-horizontal">

            <div>
                <form:label path="name" cssClass="control-label">Name</form:label>
                    <div>
                    <form:input path="name" cssClass="form-control" value="${monsterToUpdate.name}"/>
                    <form:errors path="name" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="height" cssClass="control-label">height</form:label>
                    <div>
                    <form:input path="height" cssClass="form-control" value="${monsterToUpdate.height}"/>
                    <form:errors path="height" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="weight" cssClass="control-label">weight</form:label>
                    <div>
                    <form:input path="weight" cssClass="form-control" value="${monsterToUpdate.weight}"/>
                    <form:errors path="weight" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="power" cssClass="control-label">power</form:label>
                    <div>
                    <form:input path="power" cssClass="form-control" value="${monsterToUpdate.power}"/>
                    <form:errors path="power" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="location" cssClass="control-label">location</form:label>
                    <div>
                    <form:select path="location" items="${locationList}" title="${monsterToUpdate.location.name}"/>
                    <form:errors path="location" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="types" cssClass="control-label">types</form:label>
                    <div>
                    <form:checkboxes path="types" items="${monsterTypes}" />
                    <form:errors path="types" cssClass="help-block"/>
                </div>
            </div>
            <button type="submit">Update monster</button>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>