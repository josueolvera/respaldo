<%--
  User: lEONARDO
  Date: 28/04/17
  Time: 08:40 AM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Catalogo de roles">

    <jsp:attribute name="styles">
        <style>
            .table-striped th {
                background: black;
                color: white;
            }
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }
            .table-body .table-row:nth-child(2n+1) {
                background: #ddd;
            }
            .table-row {
                padding: 1rem;
            }
            .flex-content {
                overflow-x: hidden;
            }
        </style>
    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>

        <script>
            function isLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 32 ||
                    charCode === 13 ||
                    (charCode > 64 && charCode < 91) ||
                    (charCode > 96 && charCode < 123) ||
                    charCode === 8
                ) {
                    return true;
                }
                else {
                    return false;
                }
            }
            function isNumberKeyAndLetterKey(evt){
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 13 ||
                    (charCode > 64 && charCode < 91) ||
                    (charCode > 47 && charCode < 58) ||
                    (charCode > 96 && charCode < 123) ||
                    charCode === 8
                )  {
                    return true;
                }
                else {
                    return false;
                }

            }
        </script>

        <script type="text/javascript">
            var vm = new Vue({
                el: '#contenidos',
                created: function () {
                },
                ready: function () {
                    this.getrolesas();
                },
                data: {
                    rolesas: [],
                    rolesa: {
                        name: '',
                    }
                },
                methods: {
                    getrolesas: function () {
                        this.$http.get(ROOT_URL + "/roles").success(function (data) {
                            this.rolesas = data;
                        })
                    },
                    showModalAlta: function () {
                        this.rolesa.name = '';
                        $("#modalAlta").modal("show");
                    },
                    saverolesa: function () {
                        if(this.rolesa.name.length > 0){
                            this.$http.post(ROOT_URL + "/roles/save-roles", JSON.stringify(this.rolesa)).success(function (data) {
                                this.rolesas = [];
                                this.rolesas = data;
                                $("#modalAlta").modal("hide");
                                showAlert("Registro guardado con exito");
                            }).error(function () {
                                showAlert("Error en la solicitud", {type: 3});
                            })
                        }else{
                            showAlert("Es necesario que se llenen los campos: Nombre,", {type: 3});
                        }
                    }
                },
                filters: {}
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-10">
                        <h2>Catalogo de Roles</h2>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-info" style="margin-top: 15px" @click="showModalAlta()">Agregar Roles
                        </button>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped">
                        <thead>
                        <th class="col-md-6">Id de roles</th>
                        <th class="col-md-6">Nombre de Roles</th>
                        </thead>
                        <tbody>
                        <tr v-for="rolesa in rolesas">
                            <td class="col-md-6">{{rolesa.idRole}}</td>
                            <td class="col-md-6">{{rolesa.roleName}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content modal-sm">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div class="alert alert-success">
                            <h4 class="modal-title" style="text-align: center" id=""><label>Catalogo de Roles</label></h4>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-12">
                                        <label>Nombre de Roles</label>
                                        <input class="form-control" type="text" name="nombre" onpaste="alert('Acceso Denegado');return false" v-model="rolesa.name" onkeypress="return isLetterKey(event)">
                                    </div>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-info" @click="saverolesa()">Guardar</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>