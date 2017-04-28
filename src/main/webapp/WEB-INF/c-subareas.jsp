<%--
  User: leonardo
  Date: 28/04/17
  Time: 10:40 AM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Catalogo de areas">

    <jsp:attribute name="styles">
        <style>
            .table-striped th {
                background: #204d74;
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
                    this.getsubareases();
                },
                data: {
                    subareases: [],
                    subarease: {
                        name: '',
                        acronym: ''
                    }
                },
                methods: {
                    getsubareases: function () {
                        this.$http.get(ROOT_URL + "/subareas").success(function (data) {
                            this.subareases = data;
                        })
                    },
                    showModalAlta: function () {
                        this.subarease.name = '';
                        this.subarease.acronym = '';
                        $("#modalAlta").modal("show");
                    },
                    savesubarease: function () {
                        if(this.subarease.name.length > 0 && this.subarease.acronym.length > 0){
                            this.$http.post(ROOT_URL + "/subareas/save-subareas", JSON.stringify(this.subarease)).success(function (data) {
                                this.subareases = [];
                                this.subareases = data;
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
                        <h2>Catalogo de subarea</h2>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-primary" style="margin-top: 15px" @click="showModalAlta()">Agregar subarea
                        </button>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped">
                        <thead>
                        <th class="col-md-4">Id de subarea</th>
                        <th class="col-md-4">Nombre de subarea</th>
                        <th class="col-md-4">Acronimo de subarea</th>
                        </thead>
                        <tbody>
                        <tr v-for="subarease in subareases">
                            <td class="col-md-4">{{subarease.idSubarea}}</td>
                            <td class="col-md-4">{{subarease.subareaName}}</td>
                            <td class="col-md-4">{{subarease.acronym}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal fade"  id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-ms">
                    <div class="modal-content modal-ms">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div class="alert alert-info">
                                <h4 class="modal-title" id="" style="text-align: center"><label>Catalogo de Subarea</label></h4>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-6">
                                        <label>Nombre</label>
                                        <input class="form-control" type="text" name="Nombre" onpaste="alert('Acceso Denegado');return false" v-model="subarease.name" onkeypress="return isLetterKey(event)">
                                    </div>
                                    <div class="col-md-6">
                                        <label>Acronimo</label>
                                        <input class="form-control" type="text" name="Acronimo" onpaste="alert('Acceso Denegado');return false" v-model="subarease.acronym" onkeypress="return isLetterKey(event)">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="savesubarease()">Guardar</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
