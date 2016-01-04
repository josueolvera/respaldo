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
    <link rel="stylesheet" href="/BIDGroup/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="/BIDGroup/assets/css/styles.css">
    <link rel="stylesheet" href="/BIDGroup/assets/css/messenger.css">
    <link rel="stylesheet" href="/BIDGroup/assets/css/messenger-theme-air.css">
    <link rel="stylesheet" href="/BIDGroup/assets/css/messenger-spinner.css">
    <jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />
    <jsp:invoke fragment="styles" />
</head>
    <body>
        <div class="main-container">
            <c:if test="${user.username == null}">
                <div class="row">
                    <div class='header-wrapper'>
                        <div class="header"></div>
                    </div>
                </div>
                <jsp:doBody />
            </c:if>

            <c:if test="${user.username != null}">
                <div id="main-sidebar" class="sidebar">
                    <nav class="main-menu">
                        <ul>
                            <li>
                                <a href="/BIDGroup">
                                    <span class="fa fa-home fa-2x glyphicon glyphicon-bidg"></span>
                                    <span class="nav-text"><img height="40" src="/BIDGroup/assets/img/BID_Blanco_nombre.png" /></span>
                                </a>
                            </li>
                        </ul>
                        <ul class="top-menu">
                            <li v-for="menuSystem in menu" class="has-sub-nav">
                                <a href="#">
                                    <span class="fa fa-list fa-2x glyphicon {{ menuSystem.iconClass }}"></span>
                                    <span class="nav-text">{{ menuSystem.systemName }}</span>
                                </a>
                                <ul class="menu-sub-nav">
                                    <li v-for="moduleSystem in menuSystem.modules" class="has-sub-nav">
                                        <a href="#"><span class="nav-text">{{ moduleSystem.moduleName }}</span></a>
                                        <ul class="menu-sub-nav">
                                            <li v-for="viewModule in moduleSystem.views">
                                                <a href="/BIDGroup/{{ viewModule.cTasks.taskName }}">
                                                    <span class="nav-text">{{ viewModule.viewName }}</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <ul class="logout">
                            <li>
                                <a href="#">
                                    <span class="fa fa-list fa-2x glyphicon glyphicon-user"></span>
                                    <span class="nav-text">${user.username}</span>
                                </a>
                            </li><li>
                                <a @click.prevent="closeSession" href="#">
                                    <span class="fa fa-list fa-2x glyphicon glyphicon-off"></span>
                                    <span class="nav-text">Salir</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
                <div class="col-xs-push-2 col-xs-10 col-md-push-1 col-md-11 col-lg-push-1 col-lg-11"><jsp:doBody /></div>
            </c:if>
        </div>
        <script src="/BIDGroup/assets/js/jquery-2.1.4.min.js"></script>
        <script src="/BIDGroup/assets/js/bootstrap.min.js"></script>
        <script src="/BIDGroup/assets/js/vue-1.0.7.js"></script>
        <script src="/BIDGroup/assets/js/vue-resource-0.1.17.min.js"></script>
        <script src="/BIDGroup/assets/js/messenger.min.js"></script>
        <script src="/BIDGroup/assets/js/alerts.js"></script>
        <script src="/BIDGroup/assets/js/accounting.js"></script>
        <script type="text/javascript">
            var ROOT_URL = "/BIDGroup";

            var USER_VM = new Vue({
                el: '#main-sidebar',
                ready: function () {
                    this.$http.get(ROOT_URL + '/app-menu').success(function (data) {
                        this.menu = data;
                    });
                },
                data: {
                    menu: {}
                },
                methods: {
                    closeSession: function () {
                        this.$http.post(ROOT_URL + '/logout').success(function () {
                            location.replace(ROOT_URL);
                        }).error(function () {
                            location.replace(ROOT_URL);
                        });
                    }
                }
            });
        </script>
        <jsp:invoke fragment="scripts" />
    </body>
</html>
