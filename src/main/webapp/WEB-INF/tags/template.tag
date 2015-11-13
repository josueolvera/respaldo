<%--
    Document   : Template base para todas las paginas
    Author     : rafael
--%>

<%@tag description="User Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
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
    <jsp:invoke fragment="styles" />
</head>
    <body>
        <jsp:doBody />
        <script src="BIDGroup/assets/js/jquery-2.1.4.min.js"></script>
        <script src="BIDGroup/assets/js/bootstrap.min.js"></script>
        <script src="BIDGroup/assets/js/vue-1.0.7.js"></script>
        <script src="BIDGroup/assets/js/vue-resource-0.1.17.min.js"></script>
        <script type="text/javascript">
            var ROOT_URL = "http://localhost:8080/BIDGroup";
        </script>
        <jsp:invoke fragment="scripts" />
    </body>
</html>
