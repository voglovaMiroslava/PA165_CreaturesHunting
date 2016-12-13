<%--
  Created by IntelliJ IDEA.
  User: Snurka
  Date: 12/12/2016
  Time: 6:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Change Password">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/user/tryChangePassword"
                   modelAttribute="changePass" cssClass="form-horizontal">

            <div>
                <form:label path="nickname" cssClass="control-label">Nickname</form:label>
                <div>
                    <form:input path="nickname" cssClass="form-control"/>
                    <form:errors path="nickname" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="oldPassword" cssClass="control-label">Old password</form:label>
                <div>
                    <form:password path="oldPassword" cssClass="form-control"/>
                    <form:errors path="oldPassword" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="newPassword" cssClass="control-label">New password</form:label>
                <div>
                    <form:password path="newPassword" cssClass="form-control"/>
                    <form:errors path="newPassword" cssClass="help-block"/>
                </div>
            </div>
            <button type="submit">Change password</button>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>
