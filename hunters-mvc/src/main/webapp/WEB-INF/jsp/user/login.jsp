<%--
  Created by IntelliJ IDEA.
  User: Snurka
  Date: 12/12/2016
  Time: 2:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Login">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/user/tryLogin"
                   modelAttribute="userAuthenticate" cssClass="form-horizontal">

            <div>
                <form:label path="nickname" cssClass="control-label">Nickname</form:label>
                <div>
                    <form:input path="nickname" cssClass="form-control"/>
                    <form:errors path="nickname" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="password" cssClass="control-label">Password</form:label>
                <div>
                    <form:password path="password" cssClass="form-control"/>
                    <form:errors path="password" cssClass="help-block"/>
                </div>
            </div>
            <button type="submit">Login</button>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>
