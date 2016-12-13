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
                <div class="col-lg-12 wow fadeInDown animated">
                    <h1 style="font-size: 45px; padding-top: 70px;">Welcome to Creatures Hunting page</h1>
                </div>
            </center>
        </header>
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center fadeInDown animated">
                    <h2><span class="glyphicon glyphicon-random"></span></h2>
                    <hr class="star-primary">
                </div>
            </div>
            <div class="text-center our-services">
                <div class="row">
                    <div class="col-sm-4 wow fadeInDown animated" data-wow-duration="1000ms" data-wow-delay="300ms" style="visibility: visible; animation-duration: 1000ms; animation-delay: 300ms; animation-name: fadeInDown;">
                        <div class="service-icon">
                            <a href="${pageContext.request.contextPath}/location/list">
                                <i style="color: white" class="glyphicon glyphicon-map-marker"></i>
                            </a>
                        </div>
                        <div class="service-info">
                            <h3>Location</h3>
                            <p>Information on all the locations that were examined after the nuclear disaster. You can find users comments, monsters from the locations and the most effective weapon for each location.</p>
                        </div>
                    </div>
                    <div class="col-sm-4 wow fadeInDown animated" data-wow-duration="1000ms" data-wow-delay="300ms" style="visibility: visible; animation-duration: 1000ms; animation-delay: 300ms; animation-name: fadeInDown;">
                        <div class="service-icon">
                            <a href="${pageContext.request.contextPath}/monster/list">
                                <i style="color: white" class="fa fa-optin-monster" aria-hidden="true"></i>
                            </a>
                        </div>
                        <div class="service-info">
                            <h3>Monster</h3>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore</p>
                        </div>
                    </div>
                    <div class="col-sm-4 wow fadeInDown animated" data-wow-duration="1000ms" data-wow-delay="300ms" style="visibility: visible; animation-duration: 1000ms; animation-delay: 300ms; animation-name: fadeInDown;">
                        <div class="service-icon">
                            <a href="${pageContext.request.contextPath}/weapon/list">
                                <i style="color: white" class="fa fa-shield"></i>
                            </a>
                        </div>
                        <div class="service-info">
                            <h3>Weapon</h3>
                            <p>Find out which weapon is best for you.</p>
                        </div>
                    </div>

                </div> 
            </div>
        </div>

    </div>

</jsp:attribute>
</my:pagetemplate>