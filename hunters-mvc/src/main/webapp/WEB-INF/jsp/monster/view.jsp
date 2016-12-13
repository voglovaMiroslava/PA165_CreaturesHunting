<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Monster: ${monster.name}">
    <jsp:attribute name="body">
        <div style="height: 50px;text-align: right;">
        <c:if test="${authenticatedUser.admin}">
            <button style="display: inline-block;"><my:a href="/monster/edit/${monster.id}">Edit monster</my:a></button>
            <form style="display: inline-block;" method="post" action="${pageContext.request.contextPath}/monster/delete/${monster.id}">
                <button style="color: #028fcc;"type="submit">Delete monster</button>
            </form>
        </c:if>
        </div>
        <table width="100%">
            <thead>
            <th width="70%" />
            <th width="5%"/>
            <th/>
        </thead>
        <tr>
            <td>
                <div class="monster-view-div">
                    <span class="monster-view-attrib">Id</span>
                    <span>${monster.id}</span>
                </div>
                <div class="monster-view-div">
                    <span class="monster-view-attrib">Name</span>
                    <span>
                        <c:out value="${monster.name}"/>
                    </span>
                </div> 
                <div class="monster-view-div">
                    <span class="monster-view-attrib">Dangerousness</span>
                    <span>
                        <c:out value="${monster.power}"/>
                    </span>
                </div>
                <div class="monster-view-div">
                    <span class="monster-view-attrib">Height</span>
                    <span>
                        <c:out value="${monster.height}"/>
                    </span>
                </div>
                <div class="monster-view-div">
                    <span class="monster-view-attrib">Weight</span>
                    <span>
                        <c:out value="${monster.weight}"/>
                    </span>
                </div>
                <div>
                    <span class="monster-view-attrib">Location</span>
                    <table class="table table-hover" style="margin-left: 10px;">
                        <thead>
                        <th>Name</th>
                        <th>Description</th>
                        <th  style="width: 50px;"/>
                        </thead>
                        <tbody>
                            <tr>
                                <td><c:out value="${monster.location.name}"/></td>
                                <td><c:out value="${monster.location.description}"/></td>
                                <td><a href="${pageContext.request.contextPath}/location/view/${monster.location.id}" title='View location details'>
                                        <button class="monster-button">VIEW</button></a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

            </td>
            <td/>
            <td style="display: block;">
                <table class="table table-hover">
                    <thead class="monster-thead">
                    <th>Monster Types</th>
                    </thead>
                    <tbody>
                        <c:forEach items="${monster.types}" var="type">
                            <tr>
                                <td><c:out value="${type}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </td>
        </tr>
    </table>
    <div style="margin-top: 40px; display: block;text-align: center;">
        <button style="color: #333333;">
            <my:a href="/monster/list">Go back to list of monsters</my:a>
        </button>
    </div>
</jsp:attribute>
</my:pagetemplate>