<%--
    Document   : Template base para todas las paginas
    Author     : rafael
--%>

<%@tag description="User Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle" required="true"%>
<%@attribute name="scripts" fragment="true" %>
<%@attribute name="styles" fragment="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="BIDGroup/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="BIDGroup/assets/css/styles.css">
    <link rel="stylesheet" href="BIDGroup/assets/css/messenger.css">
    <link rel="stylesheet" href="BIDGroup/assets/css/messenger-theme-air.css">
    <link rel="stylesheet" href="BIDGroup/assets/css/messenger-spinner.css">
    <jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />
    <jsp:invoke fragment="styles" />
</head>
    <body>
        <div class="container-fluid">
            <c:if test="${user.username == null}">
                <div class="row">
                    <div class='header-wrapper'>
                        <div class="header"></div>
                    </div>
                </div>
            </c:if>

            <c:if test="${user.username != null}">
                <div class="row">
                    <div class='header-wrapper'>
                        <div class="header">
                            <form action="/BIDGroup/logout" method="post">
                                <input type="submit" value="Cerrar sesión">
                            </form>
                        </div>
                    </div>
                </div>
            </c:if>

            <jsp:doBody />
        </div>
        <script src="BIDGroup/assets/js/jquery-2.1.4.min.js"></script>
        <script src="BIDGroup/assets/js/bootstrap.min.js"></script>
        <script src="BIDGroup/assets/js/vue-1.0.7.js"></script>
        <script src="BIDGroup/assets/js/vue-resource-0.1.17.min.js"></script>
        <script src="BIDGroup/assets/js/messenger.min.js"></script>
        <script src="BIDGroup/assets/js/alerts.js"></script>
        <script type="text/javascript">
            var ROOT_URL = "http://localhost:8080/BIDGroup";
        </script>
        <jsp:invoke fragment="scripts" />
    </body>
</html>
