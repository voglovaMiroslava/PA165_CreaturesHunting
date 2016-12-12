<%--
  Created by IntelliJ IDEA.
  User: Snurka
  Date: 12/12/2016
  Time: 6:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
    <jsp:attribute name="body">

    <!--section class="content-header">
        <h1>
            <fmt:message key="example.forbidden"/>
        </h1>
    </section-->

        <!-- Main content -->
        <section class="content">
            <div class="error-page">
                <h2 class="headline text-yellow"> 403</h2>
                <div class="error-content">
                    <h3><i class="fa fa-warning text-yellow"></i> Forbidden</h3>
                    <p>
                        Forbidden content. You may <a href="${pageContext.request.contextPath}/">return to dashboard</a>.
                    </p>
                </div><!-- /.error-content -->
            </div><!-- /.error-page -->
        </section><!-- /.content -->

    </jsp:attribute>
</my:pagetemplate>
