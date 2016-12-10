<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit weapon">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/weapon/update/${weaponUpdate.id}"
                   modelAttribute="weaponUpdate" cssClass="form-horizontal">

            <div>
                <form:label path="name" cssClass="control-label">Name</form:label>
                <div>
                    <form:input path="name" cssClass="form-control" value="${weaponUpdate.name}"/>
                    <form:errors path="name" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="ammo" cssClass="control-label">Ammo</form:label>
                <div>
                    <form:input path="ammo" cssClass="form-control" value="${weaponUpdate.ammo}"/>
                    <form:errors path="ammo" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="gunReach" cssClass="control-label">Gun reach</form:label>
                <div>
                    <form:input path="gunReach" cssClass="form-control" value="${weaponUpdate.gunReach}"/>
                    <form:errors path="gunReach" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="damage" cssClass="control-label">Damage</form:label>
                <div>
                    <form:input path="damage" cssClass="form-control" value="${weaponUpdate.damage}"/>
                    <form:errors path="damage" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="effectiveAgainst" cssClass="control-label">Effective against</form:label>
                <div>
                    <form:checkboxes path="effectiveAgainst" items="${monsterTypes}" />
                    <form:errors path="effectiveAgainst" cssClass="help-block"/>
                        <%--<c:forEach items="${monsterTypes}" var="type">--%>
                          <%--<form:checkbox path="effectiveAgainst" name="${type}" value="${type}" />--%>
                        <%--</c:forEach>--%>
                    <%--<form:checkboxes path="effectiveAgainst" items="${monsterTypes}" >--%>
                    <%--<c:if test="${item} = ${weaponUpdate.effectiveAgainst}"></c:if>--%>
                    <%--<c:forEach items="${weaponUpdate.effectiveAgainst}" var="type">--%>
                        <%--<form:checkbox path="effectiveAgainst" value="${type}" />--%>
	                <%--</c:forEach>--%>
                    <%--<form:errors path="effectiveAgainst" cssClass="help-block"/>--%>
                    <%--<c:forEach items="${weaponUpdate.effectiveAgainst}" var="current">--%>
                        <%--[<c:out value="${current}"/>]--%>
	                <%--</c:forEach>--%>
                </div>
            </div>
            <button type="submit">Update weapon</button>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>