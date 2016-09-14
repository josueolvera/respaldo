<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Roles Grupo de Convenios">

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
                    this.obtainRoles();
                },
                data: {
                    rolesGroupsAgreements: [],
                    roleSelected: {},
                    roles: [],
                    allCheck: false
                },
                methods: {
                    obtainRoles: function () {
                        this.roles = [];
                        this.$http.get(ROOT_URL + "/calculation-roles").success(function (data) {
                            this.roles = data;
                        });
                    },
                    changedGroup: function (role) {
                        this.allCheck = false;
                        this.$http.get(ROOT_URL + "/roles-group-agreements/role/"+role.idCalculationRole).success(function (data) {
                            this.rolesGroupsAgreements = data;
                        });
                    },
                    assignGroupToRole: function (group) {
                        this.$http.post(ROOT_URL + "/roles-group-agreements/change-status", JSON.stringify(group)).success(function (data) {
                        }).error(function () {
                            showAlert("Hubo un error en la solicitud", {type: 3});
                        });
                    },
                    all: function () {
                        var self = this;
                        if (this.allCheck == true){
                            this.rolesGroupsAgreements.forEach(function (group) {
                                group.hasGroup = true;
                                self.assignGroupToRole(group);
                            })
                        }else{
                            this.rolesGroupsAgreements.forEach(function (group) {
                                group.hasGroup = false;
                                self.assignGroupToRole(group);
                            })
                        }
                    }
                },
                filters: {

                }
            });


        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }

            .table-body .table-row:nth-child(2n+1) {
                background: white;
            }

            .table-row {
                padding: 1rem;
            }

            .flex-content {
                overflow-x: hidden;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos" class="flex-box container-fluid">
            <br>
            <div>
                <div class="row">
                    <div class="col-xs-6 text-header">
                        <h2>Asignación de grupos por rol</h2>
                    </div>
                    <div class="col-xs-4">

                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-xs-6">
                    <div style="background: #ddd" class="panel panel-default">
                        <!-- Default panel contents -->
                        <!-- Table -->
                        <div class="flex-box container-fluid">
                            <div class="row table-header active">
                                <div class="col-xs-12" style="font-size: 18px"><b>Selecciona un rol</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row" v-for="role in roles">
                                    <div class="col-xs-10">
                                        <input type="radio" :value="role" v-model="roleSelected" @change="changedGroup(roleSelected)">
                                        <label>{{role.description}}</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6" v-if="rolesGroupsAgreements.length > 0">
                    <div style="background: #ddd" class="panel panel-default">
                        <!-- Default panel contents -->
                        <!-- Table -->
                        <div class="flex-box container-fluid">
                            <div class="row table-header active">
                                <div class="col-xs-12" style="font-size: 18px"><b>Selecciona los grupos para asignarlos al rol</b></div>
                                <div class="col-xs-12" style="font-size: 14px">
                                    <input type="checkbox" v-model="allCheck" @change="all()">
                                    <label>Seleccionar todos</label>
                                </div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row" v-for="group in rolesGroupsAgreements">
                                    <div class="col-xs-12">
                                        <input type="checkbox" :value="group" v-model="group.hasGroup" @change="assignGroupToRole(group)">
                                        <label>{{group.aG.agreementGroupName}}</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
