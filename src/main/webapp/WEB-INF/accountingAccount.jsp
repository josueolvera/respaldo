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
                background: black;
                color: white;

            }

            .table-body .table-row:nth-child(2n+1) {
                background: white;
                overflow: auto;
                border: solid 1px;
            }

            .table-row {
                padding: 1rem;
            }

            .flex-content {
                overflow-x: hidden;
            }

            .table-hover {
                background-color: #2ba6cb;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>

        <script>
            function isLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
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
                var charCode = (evt.which) ? evt.which : event.keyCode;
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
                        descriptionFirst: '',
                        descriptionSecond: '',
                        descriptionThird: '',
                        acronyms: '',
                        accountingAccountType: {},
                        accountingAccountNature: {},
                        accountingAccountCategory: {}
                    },
                    acronyms:'',
                    accountingCategorys: [],
                    accountingNatures: [],
                    accountingTypes: [],
                    accountingAccount: {
                        description:'',
                        accountingAccountType: {},
                        accountingAccountNature: {},
                        accountingAccountCategory: {}
                    },
                    field1: false,
                    field2: false,
                    field3: false,
                    secondLevel: false,
                    thirdLevel: false,
                    existAccount: []
                },
                methods: {
                    findAccountingAccount: function () {
                        this.accountingAccounts = null;
                        this.$http.get(ROOT_URL + "/accounting-accounts/accounting?acronyms=" + this.acronyms).success(function (data) {
                            this.accountingAccounts = data;
                        })
                    },
                    validateFirstLevel: function () {
                        if (this.accounting.firstLevel.length == 4) {
                            var fisrtLevel = this.accounting.firstLevel + "-000-0000";
                            this.$http.get(ROOT_URL + "/accounting-accounts/validate?acronym=" + fisrtLevel).success(function (data) {
                                if (data == null) {
                                    showAlert("No existe");
                                    this.field1 = true;
                                    this.secondLevel = false;
                                    this.accounting.secondLevel = "";
                                    this.accounting.thirdLevel = "";
                                } else {
                                    this.field1 = false;
                                    this.secondLevel = true;
                                    this.field3 = false;
                                    showAlert("Ya existe la cuenta contable");
                                }
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });
                        }
                    },

                    validateSecondLevel: function () {
                        if (this.accounting.secondLevel.length == 3) {
                            var secondLevel = this.accounting.firstLevel + "-" + this.accounting.secondLevel + "-0000";
                            var self = this;
                            this.$http.get(ROOT_URL + "/accounting-accounts/validate?acronym=" + secondLevel).success(function (data) {
                                if (data == null) {
                                    showAlert("No existe");
                                    this.field2 = true;
                                    this.thirdLevel = false;
                                    this.accounting.thirdLevel = "";
                                } else {
                                    var firstLevel = this.accounting.firstLevel + "-000-0000";
                                    if (firstLevel == secondLevel) {
                                        this.accounting = {
                                            firstLevel: '',
                                            secondLevel: '',
                                            thirdLevel: '',
                                            descriptionFirst: '',
                                            descriptionSecond: '',
                                            descriptionThird: '',
                                            acronyms: '',
                                            accountingAccountType: {},
                                            accountingAccountNature: {},
                                            accountingAccountCategory: {}
                                        };
                                        this.field1 = false;
                                        this.secondLevel = false;
                                        this.field2 = false;
                                        this.thirdLevel = false;
                                        showAlert("Error al crear una cuenta contable que es primer nivel", {type: 3});
                                    } else {
                                        this.field2 = false;
                                        this.thirdLevel = true;
                                        this.accounting.descriptionSecond = "";
                                    }
                                }
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });

                        }

                    },

                    validateThirdLevel: function () {
                        if (this.accounting.thirdLevel.length == 4) {
                            var thirdLevel = this.accounting.firstLevel + "-" + this.accounting.secondLevel + "-" + this.accounting.thirdLevel;
                            this.$http.get(ROOT_URL + "/accounting-accounts/validate?acronym=" + thirdLevel).success(function (data) {
                                if (data == null) {
                                    showAlert("No existe");
                                    this.field3 = true;

                                } else {
                                    this.field3 = false;
                                    this.accountingAccount.descriptionThird = "";
                                    showAlert("Ya existe la cuenta contable");
                                }

                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3})
                            });

                        }
                    },

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
                                this.getAccountingAccounts();
                                showAlert('Registro guardado con éxito');
                                $('#modalAlta').modal('hide');
                                this.accounting = {
                                    firstLevel: '',
                                    secondLevel: '',
                                    thirdLevel: '',
                                    descriptionFirst: '',
                                    descriptionSecond: '',
                                    descriptionThird: '',
                                    acronyms: '',
                                    accountingAccountType: {},
                                    accountingAccountNature: {},
                                    accountingAccountCategory: {}
                                };
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
                                this.getAccountingAccounts();
                                showAlert('Cuenta contable actualizada');
                                $('#modalModificar').modal('hide');
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
        <div>
            <div class="row">
                <div class="col-xs-7 text-header">
                    <h2>Cuentas contables</h2>
                </div>
            </div>
            <br>
            <div class="col-md-12">
                <div class="col-md-3">
                    <label>Buscar por cuenta contable</label>
                    <input class="form-control" v-model="acronyms">
                </div>
                <div class="col-md-2">
                    <button style="margin-top: 25px" class="btn btn-info" @click="findAccountingAccount()">
                        Buscar
                    </button>
                </div>
                <div class="col-lg-6 pull-right" style="text-align: right;">
                    <button type="button" class="btn btn-default" name="button"
                            style="margin-top: 25px" data-toggle="modal" data-target="#modalAlta">
                        Nueva cuenta contable
                    </button>
                </div>
            </div>
            <br><br><br>
            <div v-if="accountingAccounts!=null">
                <div class="row table-header">
                    <div class="col-xs-2"><b>Cuenta contable</b></div>
                    <div class="col-xs-4"><b>Nombre de la cuenta</b></div>
                    <div class="col-xs-2"><b>Tipo</b></div>
                    <div class="col-xs-1"><b>Naturaleza</b></div>
                    <div class="col-xs-1"><b>Clasificación</b></div>
                    <div class="col-xs-1"><b>Modificar</b></div>
                </div>
            </div>
        </div>
        <br>
        <div class="table-body flex-row flex-content" style="color: black">
            <div class="row table-row" v-for="accounting in accountingAccounts | filterBy search in 'description'">
                <div class="col-xs-2">{{accounting.acronyms}}</div>
                <div class="col-xs-4">{{accounting.budgetCategory.budgetCategory}}</div>
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
                                    <select class="form-control" v-model="accounting.accountingAccountCategory"
                                            required>
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
                                        <input maxlength="4" class="form-control text-center"
                                               @input="validateFirstLevel()" v-model="accounting.firstLevel"
                                               onkeypress="return isNumberKey(event)" required>
                                    </div>
                                    <div class="col-xs-5" v-if="field1">
                                        <label>Descripción</label>
                                        <input class="form-control text-center" v-model="accounting.descriptionFirst"
                                               required>
                                    </div>
                                </div>
                                <div class="row" v-if="secondLevel">
                                    <div class="col-xs-2">
                                        <label>Segundo nivel</label>
                                        <input maxlength="3" class="form-control text-center"
                                               @input="validateSecondLevel()" v-model="accounting.secondLevel"
                                               onkeypress="return isNumberKey(event)" required>
                                    </div>
                                    <div class="col-xs-5" v-if="field2">
                                        <label>Descripción</label>
                                        <input class="form-control text-center" v-model="accounting.descriptionSecond"
                                               required>
                                    </div>
                                </div>
                                <div class="row" v-if="thirdLevel">
                                    <div class="col-xs-2">
                                        <label>Tercer nivel</label>
                                        <input maxlength="4" class="form-control text-center"
                                               @input="validateThirdLevel()" v-model="accounting.thirdLevel"
                                               onkeypress="return isNumberKey(event)" required>
                                    </div>
                                    <div class="col-xs-5" v-if="field3">
                                        <label>Descripción</label>
                                        <input class="form-control text-center" v-model="accounting.descriptionThird"
                                               required>
                                    </div>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-success" v-if="accounting.descriptionFirst.length > 0">Guardar
                            </button>
                            <button class="btn btn-success" v-if="accounting.descriptionSecond.length > 0">Guardar
                            </button>
                            <button class="btn btn-success" v-if="accounting.descriptionThird.length > 0">Guardar
                            </button>
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
                                    <input class="form-control" v-model="accountingAccount.description" required>
                                </div>
                                <div class="col-xs-3">
                                    <label>Tipo</label>
                                    <select class="form-control" v-model="accountingAccount.accountingAccountType"
                                            required>
                                        <option v-for="type in accountingTypes"
                                                value="{{type}}">
                                            {{type.name}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-3">
                                    <label>Naturaleza</label>
                                    <select class="form-control" v-model="accountingAccount.accountingAccountNature"
                                            required>
                                        <option v-for="nature in accountingNatures"
                                                value="{{nature}}">
                                            {{nature.nature}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-2">
                                    <label>Clasificación</label>
                                    <select class="form-control" v-model="accountingAccount.accountingAccountCategory"
                                            required>
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
