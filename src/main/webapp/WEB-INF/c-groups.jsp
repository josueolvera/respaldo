<%--
  User: Leonardo
  Date: 28/04/17
  Time: 08:43 PM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Catalogo de Grupos">

    <jsp:attribute name="styles">
        <style>
            .table-striped th {
                background: saddlebrown;
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
                    this.getgruposos();
                },
                data: {
                    gruposos: [],
                    gruposo: {
                        name: '',
                        acronym: ''
                    }
                },
                methods: {
                    getgruposos: function () {
                        this.$http.get(ROOT_URL + "/groups").success(function (data) {
                            this.gruposos = data;
                        })
                    },
                    showModalAlta: function () {
                        this.gruposo.name = '';
                        this.gruposo.acronym = '';
                        $("#modalAlta").modal("show");
                    },
                    savegruposo: function () {
                        if(this.gruposo.name.length > 0 && this.gruposo.acronym.length > 0){
                            this.$http.post(ROOT_URL + "/groups/save-groups", JSON.stringify(this.gruposo)).success(function (data) {
                                this.gruposos = [];
                                this.gruposos = data;
                                $("#modalAlta").modal("hide");
                                showAlert("Registro guardado con exito");
                            }).error(function () {
                                showAlert("Error en la solicitud", {type: 3});
                            })
                        }else{
                            showAlert("Es necesario que se llenen los campos: Nombre, Acronimo", {type: 3});
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
                        <h2>Catalogo de Grupos</h2>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-info" style="margin-top: 15px" @click="showModalAlta()">Agregar Grupos
                        </button>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped">
                        <thead>
                        <th class="col-md-4">Id de grupo</th>
                        <th class="col-md-4">Nombre de grupo</th>
                        <th class="col-md-4">Acronimo de grupo</th>
                        </thead>
                        <tbody>
                        <tr v-for="gruposo in gruposos">
                            <td class="col-md-4">{{gruposo.idGroup}}</td>
                            <td class="col-md-4">{{gruposo.groupName}}</td>
                            <td class="col-md-4">{{gruposo.acronyms}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content modal-lg">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div class="alert alert-success">
                            <h4 class="modal-title" style="text-align: center" id=""><label>Catalogo de grupos</label></h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-6">
                                        <label>Nombre de grupo</label>
                                        <input class="form-control" type="text" name="Nombre" onpaste="alert('Acceso Denegado');return false" v-model="gruposo.name" onkeypress="return isLetterKey(event)">
                                    </div>
                                    <div class="col-md-6">
                                        <label>Acronimo de grupo</label>
                                        <input class="form-control" type="text" name="Acronimo" onpaste="alert('Acceso Denegado');return false"  v-model="gruposo.acronym" onkeypress="return isLetterKey(event)">
                                    </div>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-info" @click="savegruposo()">Guardar</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
