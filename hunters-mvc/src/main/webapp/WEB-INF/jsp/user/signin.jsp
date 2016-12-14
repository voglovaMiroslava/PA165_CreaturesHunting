<%--
  Created by IntelliJ IDEA.
  User: Snurka
  Date: 12/13/2016
  Time: 11:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="SignIn">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/user/trySignin"
                   modelAttribute="userCreate" cssClass="form-horizontal">

            <div>
                <form:label path="nickname" cssClass="control-label">Nickname</form:label>
                <div>
                    <form:input path="nickname" cssClass="form-control"/>
                    <form:errors path="nickname" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="email" cssClass="control-label">Email</form:label>
                <div>
                    <form:input path="email" cssClass="form-control"/>
                    <form:errors path="email" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="plainPassword" cssClass="control-label">Password</form:label>
                <div>
                    <form:password path="plainPassword" cssClass="form-control"/>
                    <form:errors path="plainPassword" cssClass="help-block"/>
                </div>
            </div>
            <button type="submit">Signin</button>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>
