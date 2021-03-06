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
                <input type="hidden" id="userId" name="userId" value="${authenticatedUser.id}">

            </div>
            <div class="row">
                <button style="float:left; margin: 10px;" title="Save new weapon"
                        class="btn-primary btn" type="submit">
                    <span class="glyphicon glyphicon-floppy-disk" aria-hidden="false"></span> Save comment
                </button>
            </div>

        </form:form>
    </jsp:attribute>
</my:pagetemplate>