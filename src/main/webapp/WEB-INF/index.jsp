<%-- 
    Document   : index
    Created on : 5/11/2015, 11:20:14 AM
    Author     : sistemask
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Login">

    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                data: {
                    username: '',
                    password: '',
                    mayusActive: false
                },
                methods: {
                    submitLogin: function () {
                        this.$http.post(ROOT_URL + '/login', {
                            username: this.username,
                            password: this.password
                        }).success(function (data, status, request) {
                            location.reload();
                        });
                    },
                    submitLogout: function () {
                        this.$http.post(ROOT_URL + '/logout').success(function (data, status, request) {
                            location.reload();
                        });
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content" class="container-fluid">

            <div class="row">
                <div class='container-header'>
                    <div class="header">

                    </div>
                </div>
            </div>
            ${user}

            <c:if test="${user.username == null}">
                <div class="row">
                    <div class="login-form-wrapper col-md-6 col-xs-8 col-sm-6 col-lg-4">
                        <div class="login-form form-signin">
                            <h4 class="form-signin-heading"><strong>Bienvenido</strong></h4>

                            <p class="text-center"><label>Usuario:</label></p>
                            <input v-model="username" type="text" name="username"  class="form-control" placeholder="usuario o email" required autofocus>
                            <p class="text-center"><label>Password:</label></p>
                            <input v-model="password" type="password" v-on:keydown.20="mayusActive = ! mayusActive" name="password" class="form-control" required>
                            <div v-if="mayusActive">
                                <div class="alert alert-danger" role="alert">Mayusculas Activadas</div>
                            </div>
                            <br/>
                            <p class="text-center"><button v-on:click="submitLogin" class="login-button btn btn-default" type="submit">Iniciar Sesión</button></p>

                            <p class="login-form-links text-center"><span><a href="#">¿No puedes acceder a tu cuenta?</a></span></p>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${user.username != null}">
                <p>Bienbenido ${user.username}</p>
                <p><button v-on:click="submitLogout">Logout</button></p>
            </c:if>
        </div>
    </jsp:body>
</t:template>
