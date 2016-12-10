<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
    <jsp:attribute name="body">

        <header>
            <center>
                <div class="col-lg-12">
                    <h1 style="font-size: 45px">Welcome to Creatures Hunting page</h1>
                </div>
            </center>
        </header>
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2><span class="glyphicon glyphicon-random"></span></h2>
                    <hr class="star-primary">
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 text-center">
                    <p><a style="font-size: 40px" class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/weapon/list"
                          role="button">Weapons</a></p>
                </div>
                <div class="col-sm-4 text-center">
                    <p><a style="font-size: 40px" class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/location/list"
                          role="button">Locations</a></p>
                </div>
                <div class="col-sm-4 text-center">
                    <p><a style="font-size: 40px" class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/monster/list"
                          role="button">Monsters</a>
                    </p>
                </div>
            </div>  
        </div>

    </div>

</jsp:attribute>
</my:pagetemplate>