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
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="/BIDGroupLines/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/BIDGroupLines/assets/css/styles.css"/>
    <link rel="stylesheet" href="/BIDGroupLines/assets/css/messenger.css"/>
    <link rel="stylesheet" href="/BIDGroupLines/assets/css/messenger-theme-flat.css"/>
    <link rel="stylesheet" href="/BIDGroupLines/assets/css/selectize.css"/>
    <link rel="stylesheet" href="/BIDGroupLines/assets/css/selectize.bootstrap3.css"/>
    <link rel="stylesheet" href="/BIDGroupLines/assets/css/bootstrap-toggle.min.css"/>
    <link rel="stylesheet" href="/BIDGroupLines/assets/css/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" href="/BIDGroupLines/assets/css/fullcalendar.min.css"/>
    <link rel="stylesheet" href="/BIDGroupLines/assets/css/fullcalendar.print.css" media="print"/>
    <jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />
    <jsp:invoke fragment="styles" />
</head>
    <body>
        <div id="global-loader" class="loading hidden">Loading&#8230;</div>
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
                    <nav @mouseenter="toggleSidebar" @mouseleave="toggleSidebar"
                         :class="{ 'expanded': sidebar.expanded }" class="main-menu flex-box">
                        <ul class="flex-header flex-row">
                            <li>
                                <a href="/BIDGroupLines">
                                    <span class="fa fa-home fa-2x glyphicon glyphicon-bidg"></span>
                                    <span class="nav-text"><img height="40" src="/BIDGroupLines/assets/img/BID_Blanco_nombre.png" /></span>
                                </a>
                            </li>
                        </ul>
                        <ul class="top-menu flex-content flex-row">
                            <li v-for="menuSystem in menu" class="has-sub-nav">
                                <a @click="toggleSystemItem(menuSystem)" href="#" class="menu-toggle">
                                    <span class="fa fa-list fa-2x glyphicon {{ menuSystem.iconClass }}"></span>
                                    <span class="nav-text">{{ menuSystem.systemName }}</span>
                                </a>
                                <ul :class="{ 'expanded': menuSystem.expanded && sidebar.expanded }" class="menu-sub-nav">
                                    <li v-for="moduleSystem in menuSystem.modules" class="has-sub-nav">
                                        <a @click="toggleModuleItem(moduleSystem)" href="#" class="menu-toggle">
                                            <span class="nav-text">{{ moduleSystem.moduleName }}</span>
                                        </a>
                                        <ul :class="{ 'expanded': moduleSystem.expanded && sidebar.expanded }"
                                            class="menu-sub-nav">
                                            <li v-for="viewModule in moduleSystem.views">
                                                <a href="/BIDGroupLines/{{ viewModule.cTasks.taskName }}">
                                                    <span class="nav-text">{{ viewModule.viewName }}</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <ul class="logout flex-footer flex-row">
                            <li :class="{ 'vertical': sidebar.expanded }" class="sidebar-date">
                                <span class="date-text">
                                    {{ systemDate.dateElements.dayNameLong | capitalize }}
                                    {{ systemDate.dateElements.day }},
                                    {{ systemDate.dateElements.monthNameLong | capitalize }}
                                    {{ systemDate.dateElements.year }}
                                </span>
                            </li>
                            <li class="inbox-item">
                                <a :href="sidebar.inboxURL">
                                    <span class="fa fa-list fa-2x glyphicon glyphicon-envelope"></span>
                                    <span class="nav-text">
                                        Bandeja de entrada
                                        <span class="badge">{{ notifications }}</span>
                                    </span>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <span class="fa fa-list fa-2x glyphicon glyphicon-user"></span>
                                    <span class="nav-text">${user.username}</span>
                                </a>
                            </li>
                            <li>
                                <a @click.prevent="closeSession" href="#">
                                    <span class="fa fa-list fa-2x glyphicon glyphicon-off"></span>
                                    <span class="nav-text">Salir</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
                <div class="col-xs-10 col-md-11 col-lg-11 body"><jsp:doBody /></div>
            </c:if>
        </div>
        <script src="/BIDGroupLines/assets/js/jquery-2.1.4.min.js"></script>
        <script src="/BIDGroupLines/assets/js/bootstrap.min.js"></script>
        <script src="/BIDGroupLines/assets/js/moment.min.js"></script>
        <script src="/BIDGroupLines/assets/js/moment-locale-es-mx.js"></script>
        <script src="/BIDGroupLines/assets/js/bootstrap-datetimepicker.min.js"></script>
        <script src="/BIDGroupLines/assets/js/jquery.onscreen.min.js"></script>
        <script src="/BIDGroupLines/assets/js/vue-1.0.7.js"></script>
        <script src="/BIDGroupLines/assets/js/vue-resource-0.7.2-.min.js"></script>
        <script src="/BIDGroupLines/assets/js/selectize.standalone.min.js"></script>
        <script src="/BIDGroupLines/assets/js/bootstrap-toggle.min.js"></script>
        <script src="/BIDGroupLines/assets/js/messenger.min.js"></script>
        <script src="/BIDGroupLines/assets/js/messenger-theme-flat.js"></script>
        <script src="/BIDGroupLines/assets/js/alerts.js"></script>
        <script src="/BIDGroupLines/assets/js/accounting.js"></script>
        <script src="/BIDGroupLines/assets/js/fullcalendar.min.js"></script>
        <script src="/BIDGroupLines/assets/js/fullcalendar-locale-es.js"></script>
        <script src="/BIDGroupLines/assets/js/gcal.js"></script>
        <script type="text/javascript">
            Vue.http.interceptors.push({
                request: function (request) {
                    request.headers['Content-Type'] = "application/json; charset=UTF-8"
                    switch (request.method) {
                        case 'POST':
                        case 'DELETE':
                        case 'PUT':
                        case 'post':
                        case 'delete':
                        case 'put':
                            var loader = document.getElementById('global-loader');
                            if (loader == null) return request;
                            loader.classList.remove('hidden');
                            break;
                    }
                    return request;
                },

                response: function (response) {
                    var loader = document.getElementById('global-loader');
                    if (loader == null) return response;
                    loader.classList.add('hidden');
                    return response;
                }
            });
            var ROOT_URL = "/BIDGroupLines";
            var USER_VM = new Vue({
                el: '#main-sidebar',
                ready: function () {
                    this.fetchApp();
                },
                data: {
                    sidebar: {
                        inboxURL: ROOT_URL + "/user/inbox-page",
                        expanded: false,
                        itemsExpanded: {
                            system: {
                                expanded: false
                            },
                            module: {
                                expanded: false
                            }
                        }
                    },
                    menu: null,
                    user: null,
                    notifications: 0,
                    systemDate: null
                },
                methods: {
                    fetchApp: function () {
                        this.$http.get(ROOT_URL + '/app-menu').success(function (data) {
                            this.menu = data.menu;
                            this.user = data.user;
                            this.notifications = data.notifications;
                            this.systemDate = data.systemDate;
                        });
                    },
                    closeSession: function () {
                        this.$http.post(ROOT_URL + '/logout').success(function () {
                            location.replace(ROOT_URL);
                        }).error(function () {
                            location.replace(ROOT_URL);
                        });
                    },
                    toggleSidebar: function () {
                        this.sidebar.expanded = ! this.sidebar.expanded;
                    },
                    toggleSystemItem: function (item) {
                        if (item.expanded) {
                            item.expanded = false;
                        } else {
                            this.sidebar.itemsExpanded.system.expanded = false;
                            Vue.set(item, "expanded", true);
                        }
                        this.sidebar.itemsExpanded.system = item;
                    },
                    toggleModuleItem: function (item) {
                        if (item.expanded) {
                            item.expanded = false;
                        } else {
                            this.sidebar.itemsExpanded.module.expanded = false;
                            Vue.set(item, "expanded", true);
                        }
                        this.sidebar.itemsExpanded.module = item;
                    },
                }
            });
        </script>

        <script>
            Number.prototype.formatMoney = function(places, symbol, thousand, decimal) {
                places = !isNaN(places = Math.abs(places)) ? places : 2;
                symbol = symbol !== undefined ? symbol : "$";
                thousand = thousand || ",";
                decimal = decimal || ".";
                var number = this,
                        negative = number < 0 ? "-" : "",
                        i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
                        j = (j = i.length) > 3 ? j % 3 : 0;
                return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
            };
        </script>
        <jsp:invoke fragment="scripts" />
    </body>
</html>
