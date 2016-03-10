<%-- 
    Document   : index
    Created on : 5/11/2015, 11:20:14 AM
    Author     : sistemask
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Login">

    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                data: {
                    loginInProgress: false,
                    username: '',
                    password: '',
                    mayusActive: false
                },
                methods: {
                    submitLogin: function (event) {
                        event.preventDefault();
                        this.$http.post(ROOT_URL + '/login', {
                            username: this.username,
                            password: this.password
                        }).success(function (data, status, request) {
                            location.reload();
                        }).error(function (data, status, request) {
                            if (status == 412) {
                                $('#active-session-modal').modal('show');
                            } else {
                                showAlert("Tu usuario o contraseña son incorrectos, por favor verifícalos");
                            }
                        });
                    },
                    closeActiveSession: function () {
                        this.$http.post(ROOT_URL + '/close-active-session', {
                            username: this.username
                        }).success(function (data,  status, request) {
                            showAlert("Se ha cerrado tu sesión anterior. Inicia nuevamente.");
                            location.reload();
                        }).error(function () {
                            showAlert("Se ha cerrado tu sesión anterior. Inicia nuevamente.");
                            $('#active-session-modal').modal('close');
                        })
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">

            <c:if test="${user.username == null}">
                <div class="row">
                    <div class="login-form-wrapper col-md-6 col-xs-8 col-sm-6 col-lg-4">
                        <form v-on:submit="submitLogin" class="login-form form-signin" action="/login" method="post">
                            <h4 class="form-signin-heading"><strong>Bienvenido</strong></h4>
                            <p class="text-center"><label>Usuario:</label></p>
                            <input v-model="username" type="text" name="username"  class="form-control text-center" placeholder="usuario o email" required autofocus>
                            <p class="text-center"><label>Password:</label></p>
                            <input v-model="password" type="password" name="password" class="form-control text-center" required>
                            <div v-if="mayusActive">
                                <div class="alert alert-danger" role="alert">Mayusculas Activadas</div>
                            </div>
                            <br/>
                            <p class="text-center"><input class="login-button btn btn-default" type="submit" value="Iniciar Sesión" /></p>

                            <p class="login-form-links text-center"><span><a href="#">¿No puedes acceder a tu cuenta?</a></span></p>
                        </form>
                    </div>
                </div>

                <div id="active-session-modal" class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4>Ya tiene una sesión activa</h4>
                            </div>
                            <div class="modal-body">
                                <p>¿Desea cerrar su sesión anterior?</p>
                                <p>
                                    <button v-on:click="closeActiveSession" class="btn btn-success">Cerrar sesión</button>
                                    <button class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                </p>
                            </div>
                            <div class="modal-footer"></div>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${user.username != null}">
                <p>Bienvenido ${user.username}</p>
            </c:if>
        </div>
    </jsp:body>
</t:template>
