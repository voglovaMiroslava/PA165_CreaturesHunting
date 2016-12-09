<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">

    <div >
        <h1>Welcome to Creatures Hunting page</h1>

        <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/weapon/list"
              role="button">Weapons</a></p>
    </div>


    <div class="row">
        <div class="col-xs-12 col-sm-6 col-md-2 col-lg-1">
            <p><button class="btn btn-default">Responsive Button</button></p>
        </div>

    </div>

</jsp:attribute>
</my:pagetemplate>