<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New comment">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/weapon/${weaponId}/comment/create"
                   modelAttribute="commentCreate" cssClass="form-horizontal">

            <div>
                <form:label path="content" cssClass="control-label">Content</form:label>
                <div>
                    <form:input path="content" cssClass="form-control"/>
                    <form:errors path="content" cssClass="help-block"/>
                </div>
                <input type="hidden" id="userId" name="userId" value="${userId}">

            </div>
            <div class="row">
                <button style="float:right; margin: 10px;" class="glyphicon glyphicon-floppy-disk btn btn-primary" type="submit"></button>
            </div>

        </form:form>
    </jsp:attribute>
</my:pagetemplate>