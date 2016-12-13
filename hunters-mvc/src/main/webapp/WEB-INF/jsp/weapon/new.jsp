<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New weapon">
    <jsp:attribute name="body">
        <div class="container">
        <form:form method="post" action="${pageContext.request.contextPath}/weapon/create"
                   modelAttribute="weaponCreate" cssClass="form-horizontal" >

            <div class="form-group ${name_error?'has-error':''}">
                <form:label path="name" cssClass="control-label">Name</form:label>
                <div>
                    <form:input path="name" cssClass="form-control"/>
                    <form:errors path="name" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${ammo_error?'has-error':''}">
                <form:label path="ammo" cssClass="control-label">Ammo</form:label>
                <div>
                    <form:input path="ammo" cssClass="form-control"/>
                    <form:errors path="ammo" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${gunReach_error?'has-error':''}">
                <form:label path="gunReach" cssClass="control-label">Gun reach</form:label>
                <div>
                    <form:input path="gunReach" cssClass="form-control"/>
                    <form:errors path="gunReach" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${damage_error?'has-error':''}">
                <form:label path="damage" cssClass="control-label">Damage</form:label>
                <div>
                    <form:input path="damage" cssClass="form-control"/>
                    <form:errors path="damage" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="effectiveAgainst" cssClass="control-label">Effective against</form:label>
                <div>
                    <table class="table table-bordered" style="width: 180px">
                        <c:forEach items="${monsterTypes}" var="type">
                            <tr>
                                <td>${type}</td>
                                <td class="input-group-addon"><form:checkbox path="effectiveAgainst" name="${type}"
                                                                             value="${type}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <form:errors path="effectiveAgainst" cssClass="help-block"/>
                </div>
            </div>
            <div class="row">
                <button style="float:left; margin: 10px;" title="Save new weapon"
                        class="btn-primary btn" type="submit">
                    <span class="glyphicon glyphicon-floppy-disk" aria-hidden="false"></span> Save weapon
                </button>
            </div>

        </form:form>
        </div>
    </jsp:attribute>
</my:pagetemplate>