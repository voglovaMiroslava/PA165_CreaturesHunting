<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title><c:out value="${title}"/></title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/dist/css/AdminLTE.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/dist/css/skins/skin-blue.min.css">


        <link href="${pageContext.request.contextPath}/static/dist/css/animate.min.css" rel="stylesheet">
         <!--<link href="${pageContext.request.contextPath}/static/dist/css/lightbox.css" rel="stylesheet">-->
        <link href="${pageContext.request.contextPath}/static/dist/css/main.css" rel="stylesheet">
        <link id="css-preset" href="${pageContext.request.contextPath}/static/dist/css/presets/preset1.css" rel="stylesheet">
         <!--<link href="${pageContext.request.contextPath}/static/dist/css/responsive.css" rel="stylesheet">-->


        <style>
            body {
                /*background-image: url("${pageContext.request.contextPath}/static/dist/img/66617.jpg");*/
                /*background-color: #CECB26;*/
                padding: 20px;
                padding-top: 70px;
                /* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
            }
            .center {
                margin: auto;
                width: 90%;
                padding: 1px;
            }

            .actions {
                width: 70px;
            }

            .actions a {
                margin-right: 5px;
            }

            .alertCallout {
                padding-top: 20px;
                margin-left: 14px;
                margin-bottom: -10px;
            }
        </style>
        <!-- jQuery 2.1.4 -->
        <script src="${pageContext.request.contextPath}/static/plugins/jQuery/jQuery-2.1.4.min.js"></script>

        <jsp:invoke fragment="head"/>
    </head>
    <body>
        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}">MAIN MENU</a>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-left">
                        <li>
                            <a href="${pageContext.request.contextPath}/location/list">Location</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/weapon/list"">Weapon</a>
                        </li>
                        <li>
                            <a href="#">Monster</a>
                        </li>
                        <li>
                            <a href="#">Comment</a>
                        </li>
                        <li>
                            <a href="#">Contact</a>
                        </li>

                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li >
                            <a href="${pageContext.request.contextPath}/user/login">Login</a>
                        </li>
                        <li >
                            <a href="#">Log out</a>
                        </li>
                    </ul>
                </div>
                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container -->
        </nav>

        <!-- page title -->
        <c:if test="${not empty title}">
            <div class="page-header">
                <h1><c:out value="${title}"/></h1>
            </div>
        </c:if>

        <!-- page body -->
        <jsp:invoke fragment="body"/>

        <!--   Footer -->
<!--    <center>
        <footer class="footer bottom">
            <div class="pull-right hidden-xs">
                <a href="https://www.fi.muni.cz" target="_blank"><fmt:message key="menu.fi"/></a>
            </div>
            <strong>Copyright &copy; 2016 <a href="https://github.com/voglovaMiroslava/PA165_CreaturesHunting">PA165 Hunters Team No. 1</a>.</strong> <fmt:message key="menu.rights"/>

        </footer>
    </center>-->
    <!--REQUIRED JS SCRIPTS -->

    <!-- Bootstrap 3.3.5 -->
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <!-- AdminLTE App -->
    <script src="${pageContext.request.contextPath}/static/dist/js/app.min.js"></script>

    <script>

        function bindRemoveItem() {
            if ($(".removeItem").length > 0) {
                $("body").delegate(".removeItem", "click", function () {
                    if (confirm("Do you want to delete this item?"))
                        $(this).find("form").submit();

                    return false;
                });
            }

            if ($(".rentItem").length > 0) {
                $("body").delegate(".rentItem", "click", function () {
                    if (confirm("Do you want to rent this item?"))
                        $(this).find("form").submit();

                    return false;
                });
            }
        }

        $(document).ready(function () {
            setActiveMenu();

            if ($(".callout").length > 0) {
                setTimeout(function () {
                    $(".callout").fadeOut(400);
                }, 5000);
            }

            bindRemoveItem();
        });
    </script>
</body>
</html>

