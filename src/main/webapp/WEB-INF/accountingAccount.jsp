<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Cuentas contables">

    <jsp:attribute name="styles">
        <style>
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
            function isNumberKeyAndLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 13 ||
                        (charCode > 64 && charCode < 91) ||
                        (charCode > 47 && charCode < 58) ||
                        (charCode > 96 && charCode < 123) ||
                        charCode === 8
                ) {
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
                    this.getAccountingAccounts();
                    this.getAccountigCategorys();
                    this.getAccountigNatures();
                    this.getAccountingTypes();
                },
                data: {
                    accountingAccounts: [],
                    accounting: {
                        firstLevel: '',
                        secondLevel: '',
                        thirdLevel: '',
                        description: '',
                        accountingAccountType: {},
                        accountingAccountNature: {},
                        accountingAccountCategory: {}
                    },
                    accountingCategorys: [],
                    accountingNatures: [],
                    accountingTypes: [],
                    accountingAccount: {}
                },
                methods: {
                    getAccountingAccounts: function () {
                        this.$http.get(ROOT_URL + '/accounting-accounts')
                                .success(function (data) {
                                    this.accountingAccounts = data;
                                });
                    },
                    getAccountigCategorys: function () {
                        this.$http.get(ROOT_URL + '/accounting-category')
                                .success(function (data) {
                                    this.accountingCategorys = data;
                                });
                    },
                    getAccountigNatures: function () {
                        this.$http.get(ROOT_URL + '/accounting-nature')
                                .success(function (data) {
                                    this.accountingNatures = data;
                                });
                    },
                    getAccountingTypes: function () {
                        this.$http.get(ROOT_URL + '/accounting-type')
                                .success(function (data) {
                                    this.accountingTypes = data;
                                });
                    },
                    saveAccountingAccount: function () {
                        this.$http.post(ROOT_URL + '/accounting-accounts', JSON.stringify(this.accounting))
                                .success(function (data) {
                                    showAlert('Registro guardado con éxito');
                                    $('#modalAlta').modal('hide');
                                    this.getAccountingAccounts();
                                }).error(function (data) {
                            showAlert(data.error.message, {type: 3});
                        });
                    },
                    modifyAccounting: function (accounting) {
                        $('#modalModificar').modal('show');
                        this.accountingAccount = (JSON.parse(JSON.stringify(accounting)));
                    },
                    updateAccountingAccount: function (accountingAccount) {
                        this.$http.post(ROOT_URL + '/accounting-accounts/' + accountingAccount.idAccountingAccount, accountingAccount)
                                .success(function (data) {
                                    showAlert('Cuenta contable actualizada');
                                    $('#modalModificar').modal('hide');
                                    this.getAccountingAccounts();
                                }).error(function (data) {
                            showAlert('Error en la solicitud', {type: 3});
                        });
                    },
                    cancelar: function () {
                        this.accounting.description = '';
                        this.accounting.firstLevel = '';
                        this.accounting.secondLevel = '';
                        this.accounting.thirdLevel = '';
                        this.accounting.accountingAccountType = {};
                        this.accounting.accountingAccountCategory = {};
                        this.accounting.accountingAccountNature = {};
                        $('#modalAlta').modal('hide');
                        $('#modalModificar').modal('hide');
                    }
                },
                filters: {
                    numbersPadding: function (value) {
                        var result = "";
                        var padding = 4;
                        if (typeof value != 'undefined') {
                            result = '00000' + value;
                            result = result.substr(result.length - padding);
                        }
                        return result;
                    },
                    numbersSecondLevelPadding: function (value) {
                        var result = "";
                        var padding = 3;
                        if (typeof value != 'undefined') {
                            result = '0000' + value;
                            result = result.substr(result.length - padding);
                        }
                        return result;
                    }
                }
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos" class="flex-box container-fluid">
        <br>
        <div>
            <div class="row">
                <div class="col-xs-7 text-header">
                    <h2>Cuentas contables</h2>
                </div>

                <div class="col-xs-5 text-right" style="padding: 0px">
                    <div class="col-xs-7 text-left" style="padding: 0px">
                        <label>Buscar por Descripción</label>
                        <input class="form-control" v-model="search">
                    </div>


                    <button type="button" class="btn btn-default" name="button"
                            style="margin-top: 25px" data-toggle="modal" data-target="#modalAlta">
                        Nueva cuenta contable
                    </button>
                </div>
            </div>


            <div>
                <div class="row table-header">
                    <div class="col-xs-1"><b>Primer nivel</b></div>
                    <div class="col-xs-1"><b>Segundo nivel</b></div>
                    <div class="col-xs-1"><b>Tercer nivel</b></div>
                    <div class="col-xs-4"><b>Descripción</b></div>
                    <div class="col-xs-2"><b>Tipo</b></div>
                    <div class="col-xs-1"><b>Naturaleza</b></div>
                    <div class="col-xs-1"><b>Clasificación</b></div>
                    <div class="col-xs-1"><b>Modificar</b></div>
                </div>
            </div>
        </div>
        <br>
        <div class="table-body flex-row flex-content">
            <div class="row table-row" v-for="accounting in accountingAccounts | filterBy search in 'description'">
                <div class="col-xs-1">{{accounting.firstLevel}}</div>
                <div class="col-xs-1">{{accounting.secondLevel | numbersSecondLevelPadding}}</div>
                <div class="col-xs-1">{{accounting.thirdLevel | numbersPadding}}</div>
                <div class="col-xs-4">{{accounting.description}}</div>
                <div class="col-xs-2">{{accounting.cAccountingAccountType.name}}</div>
                <div class="col-xs-1">{{accounting.cAccountingAccountNature.nature}}</div>
                <div class="col-xs-1">{{accounting.cAccountingAccountCategory.classification}}</div>
                <div class="col-xs-1">
                    <button type="button" class="btn btn-default" name="button" data-toggle="tooltip"
                            data-placement="bottom" title="Modificar"
                            @click="modifyAccounting(accounting)"><span class="glyphicon glyphicon-pencil"></span>
                    </button>
                </div>
            </div>
        </div>
        <!-- container-fluid -->
        <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <form v-on:submit.prevent="saveAccountingAccount">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Registro de cuenta contable</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>Descripción</label>
                                    <input class="form-control"  v-model="accounting.description" required>
                                </div>
                                <div class="col-xs-3">
                                    <label>Tipo</label>
                                    <select class="form-control" v-model="accounting.accountingAccountType" required>
                                        <option v-for="type in accountingTypes"
                                                value="{{type}}">
                                            {{type.name}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-3">
                                    <label>Naturaleza</label>
                                    <select class="form-control" v-model="accounting.accountingAccountNature" required>
                                        <option v-for="nature in accountingNatures"
                                                value="{{nature}}">
                                            {{nature.nature}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-2">
                                    <label>Clasificación</label>
                                    <select class="form-control" v-model="accounting.accountingAccountCategory" required>
                                        <option v-for="category in accountingCategorys"
                                                value="{{category}}">
                                            {{category.classification}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div>
                                <div class="row">
                                    <div class="col-xs-4">
                                        <label>Cuenta contable</label>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-2">
                                        <label>Primer nivel</label>
                                        <input maxlength="4" class="form-control text-center" v-model="accounting.firstLevel"
                                               onkeypress="return isNumberKey(event)"  required>
                                    </div>
                                    <div class="col-xs-2">
                                        <label>Segundo nivel</label>
                                        <input maxlength="3" class="form-control text-center" v-model="accounting.secondLevel"
                                               onkeypress="return isNumberKey(event)" required>
                                    </div>
                                    <div class="col-xs-2">
                                        <label>Tercer nivel</label>
                                        <input maxlength="4" class="form-control text-center" v-model="accounting.thirdLevel"
                                               onkeypress="return isNumberKey(event)" required>
                                    </div>
                                </div>
                                <br>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-success">Guardar</button>
                            <button type="button" class="btn btn-default" @click="cancelar">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="modalModificar" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <form v-on:submit.prevent="updateAccountingAccount(accountingAccount)">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Modificación de cuenta contable</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>Descripción</label>
                                    <input class="form-control"  v-model="accountingAccount.description" required>
                                </div>
                                <div class="col-xs-3">
                                    <label>Tipo</label>
                                    <select class="form-control" v-model="accountingAccount.accountingAccountType" required>
                                        <option v-for="type in accountingTypes"
                                                value="{{type}}">
                                            {{type.name}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-3">
                                    <label>Naturaleza</label>
                                    <select class="form-control" v-model="accountingAccount.accountingAccountNature" required>
                                        <option v-for="nature in accountingNatures"
                                                value="{{nature}}">
                                            {{nature.nature}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-2">
                                    <label>Clasificación</label>
                                    <select class="form-control" v-model="accountingAccount.accountingAccountCategory" required>
                                        <option v-for="category in accountingCategorys"
                                                value="{{category}}">
                                            {{category.classification}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="modal-footer">
                                <button class="btn btn-success">Guardar</button>
                                <button type="button" class="btn btn-default" @click="cancelar">Cancelar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
