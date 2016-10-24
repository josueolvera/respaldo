<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Tabuladores por grupo de convenios">

    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function isNumberKey(evt, obj) {
                /*var charCode = (evt.which) ? evt.which : event.keyCode
                 if (charCode > 31 && (charCode < 48 || charCode > 57))
                 return false;
                 return true;*/
                var charCode = (evt.which) ? evt.which : event.keyCode
                var value = obj.value;
                var dotcontains = value.indexOf(".") != -1;
                if (dotcontains)
                    if (charCode == 46) return false;
                if (charCode == 46) return true;
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
                    this.getAgreementGroups();
                    this.getRulesType();
                },
                data: {
                    agreementGroups: {},
                    idAgreementGroup: 0,
                    idDateCalculation: 0,
                    tabsOfGroup: {},
                    montoMinimo: '',
                    montoMaximo: '',
                    tabulator: '',
                    ruleType: 0,
                    rulesTypes: {},
                    dateTypes: []
                },
                methods: {
                    getAgreementGroups: function () {
                        this.$http.get(ROOT_URL + "/c-agreements-groups/actives")
                                .success(function (data) {
                                    this.agreementGroups = data;
                                });
                    },
                    getTabsOfGroup: function () {
                        this.tabsOfGroup = {};
                        this.$http.get(ROOT_URL + "/agreement-condition/" + this.idAgreementGroup)
                                .success(function (data) {
                                    var jsonObjectIndex = {};

                                    data.forEach(function (aGC) {
                                        if (isNaN(aGC.cDateCalculation)) {
                                            jsonObjectIndex[aGC.cDateCalculation._id] = aGC.cDateCalculation;
                                        } else {
                                            aGC.cDateCalculation = jsonObjectIndex[aGC.cDateCalculation];
                                        }
                                    });

                                    this.tabsOfGroup = data;
                                });
                    },
                    updateTab: function (tab) {
                        this.$http.post(ROOT_URL + "/agreement-condition", JSON.stringify(tab))
                                .success(function (data) {
                                    showAlert("Actualización exitosa");
                                });
                    },
                    saveTab: function (ruleType) {

                        var newTab = {
                            idGroupCondition: 0,
                            idAg: 0,
                            idDateCalculation: 0,
                            order: 0,
                            tabulator: 0,
                            amountMin: 0,
                            amountMax: 0,
                            status: 0,
                            typeOperation: 0
                        };

                        newTab.idAg = this.idAgreementGroup;
                        newTab.idDateCalculation = this.idDateCalculation;

                        if (ruleType == 1 || ruleType == 3 || ruleType == 4) {
                            newTab.amountMin = this.montoMinimo;
                            newTab.amountMax = this.montoMaximo;

                            if (this.montoMaximo < this.montoMinimo) {
                                showAlert("El monto maximo no puede ser menor al minimo");
                                newTab.amountMax = '';
                                this.montoMaximo = '';
                            }
                        }
                        if (ruleType == 2) {
                            newTab.amountMin = this.montoMaximo;
                            newTab.amountMax = this.montoMaximo;
                        }

                        newTab.tabulator = this.tabulator;
                        newTab.typeOperation = this.ruleType;

                        if (this.montoMaximo !== '' && this.tabulator !== '') {
                            this.$http.post(ROOT_URL + "/agreement-condition/save", JSON.stringify(newTab))
                                    .success(function (data) {
                                        showAlert("Registro Exitoso");
                                        this.getTabsOfGroup();
                                        this.clearFields();
                                    });
                        }
                        else {
                            showAlert("Favor de llenar todos los campos");
                        }
                    },
                    getRulesType: function () {

                        this.$http.get(ROOT_URL + "/c-type-operation")
                                .success(function (data) {
                                    this.rulesTypes = data;
                                });
                    },
                    clearFields: function () {
                        this.montoMinimo = '';
                        this.montoMaximo = '';
                        this.tabulator = '';
                        this.idDateCalculation = 0;
                        this.dateTypes = [];
                        this.obtainDateType();
                    },
                    obtainDateType: function () {

                        this.$http.get(ROOT_URL + "/date-calculation").success(function (data) {
                            this.dateTypes = data;
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        })
                    }
                },
                filters: {}
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">

            <div class="row">
                <div class="col-xs-12 text-center">
                    <h1>Gestión de tabuladores de grupos</h1>
                </div>
            </div>

            <br>
            <div class="row">
                <div class="col-xs-3 text-left">
                    <label>
                        Grupo de Convenio
                    </label>
                    <select class="form-control" v-model="idAgreementGroup" @change="getTabsOfGroup">
                        <option value="0"></option>
                        <option v-for="agreementGroup in agreementGroups" value="{{agreementGroup.idAg}}">
                            {{agreementGroup.agreementGroupName}}
                        </option>
                    </select>
                </div>
                <div class="col-xs-3 text-left" v-show="idAgreementGroup> 0">
                    <label>
                        Tipo de regla
                    </label>
                    <select class="form-control" v-model="ruleType" @change="clearFields">
                        <option value=""></option>
                        <option v-for="rules in rulesTypes" value="{{rules.typeOperation}}">
                            {{rules.description}}
                        </option>
                    </select>
                </div>
            </div>
            <br>

            <div class="row" v-if="ruleType == 1">
                <div class="col-xs-3">
                    <label>
                        Monto mínimo
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">$</span>
                        <input number type="text" class="form-control" v-model="montoMinimo"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-3">
                    <label>
                        Monto máximo
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">$</span>
                        <input number type="text" class="form-control" v-model="montoMaximo"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-3">
                    <label>
                        Tabulador
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">%</span>
                        <input number type="text" class="form-control" v-model="tabulator"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>
                <div class="col-xs-2">
                    <label>
                        Tipo de calculo
                    </label>
                    <select class="form-control" v-model="idDateCalculation">
                        <option v-for="type in dateTypes" value="{{type.idDateCalculation}}">
                            {{type.nameDate}}
                        </option>
                    </select>
                </div>

                <div class="col-xs-1 text-left" style="margin-top: 25px">
                    <button class="btn btn-default" @click="saveTab(ruleType)" title="Almacenar regla">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </div>
            </div>

            <div class="row" v-if="ruleType == 2">

                <div class="col-xs-3">
                    <label>
                        Nùmero de solicitudes
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">#</span>
                        <input number type="text" class="form-control" v-model="montoMaximo"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-3">
                    <label>
                        Tabulador
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">$</span>
                        <input number type="text" class="form-control" v-model="tabulator"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-3">
                    <label>
                        Tipo de calculo
                    </label>
                    <select class="form-control" v-model="idDateCalculation">
                        <option v-for="type in dateTypes" value="{{type.idDateCalculation}}">
                            {{type.nameDate}}
                        </option>
                    </select>
                </div>

                <div class="col-xs-3 text-left" style="margin-top: 25px">
                    <button class="btn btn-default" @click="saveTab(ruleType)" title="Almacenar regla">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </div>
            </div>

            <div class="row" v-if="ruleType == 3">
                <div class="col-xs-3">
                    <label>
                        Alcance mínimo
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">%</span>
                        <input number type="text" class="form-control" v-model="montoMinimo"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-3">
                    <label>
                        Alcance máximo
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">%</span>
                        <input number type="text" class="form-control" v-model="montoMaximo"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-3">
                    <label>
                        Tabulador
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">%</span>
                        <input number type="text" class="form-control" v-model="tabulator"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-2">
                    <label>
                        Tipo de calculo
                    </label>
                    <select class="form-control" v-model="idDateCalculation">
                        <option v-for="type in dateTypes" value="{{type.idDateCalculation}}">
                            {{type.nameDate}}
                        </option>
                    </select>
                </div>

                <div class="col-xs-1 text-left" style="margin-top: 25px">
                    <button class="btn btn-default" @click="saveTab(ruleType)" title="Almacenar regla">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </div>
            </div>

            <div class="row" v-if="ruleType == 4">
                <div class="col-xs-3">
                    <label>
                        Indice de reproceso mínimo
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">%</span>
                        <input number type="text" class="form-control" v-model="montoMinimo"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-3">
                    <label>
                        Indice de reproceso máximo
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">%</span>
                        <input number type="text" class="form-control" v-model="montoMaximo"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-3">
                    <label>
                        Tabulador
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">%</span>
                        <input number type="text" class="form-control" v-model="tabulator"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-2">
                    <label>
                        Tipo de calculo
                    </label>
                    <select class="form-control" v-model="idDateCalculation">
                        <option v-for="type in dateTypes" value="{{type.idDateCalculation}}">
                            {{type.nameDate}}
                        </option>
                    </select>
                </div>

                <div class="col-xs-1 text-left" style="margin-top: 25px">
                    <button class="btn btn-default" @click="saveTab(ruleType)" title="Almacenar regla">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </div>
            </div>

            <div class="row" v-if="ruleType == 5">
                <div class="col-xs-3">
                    <label>
                        Monto mínimo
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">$</span>
                        <input number type="text" class="form-control" v-model="montoMinimo"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-3">
                    <label>
                        Monto máximo
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">$</span>
                        <input number type="text" class="form-control" v-model="montoMaximo"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>

                <div class="col-xs-3">
                    <label>
                        Bono
                    </label>
                    <div class="input-group">
                        <span class="input-group-addon">%</span>
                        <input number type="text" class="form-control" v-model="tabulator"
                               onkeypress="return isNumberKey(event,this)">
                    </div>
                </div>
                <div class="col-xs-2">
                    <label>
                        Tipo de calculo
                    </label>
                    <select class="form-control" v-model="idDateCalculation">
                        <option v-for="type in dateTypes" value="{{type.idDateCalculation}}">
                            {{type.nameDate}}
                        </option>
                    </select>
                </div>

                <div class="col-xs-1 text-left" style="margin-top: 25px">
                    <button class="btn btn-default" @click="saveTab(ruleType)" title="Almacenar regla">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <h3>Tabuladores Existentes</h3>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <table class="table table-striped">
                        <thead>
                        <th>
                            #
                        </th>
                        <th>
                            Monto minimo
                        </th>
                        <th>
                            Monto maximo
                        </th>
                        <th>
                            Tabulador
                        </th>
                        <th>
                            Tipo de calculo
                        </th>
                        <th>
                            Estatus
                        </th>
                        <th>
                            Tipo de Regla
                        </th>
                        </thead>
                        <tbody>
                        <tr v-for="tab in tabsOfGroup">
                            <td>
                                {{$index + 1}}
                            </td>
                            <td>
                                {{tab.amountMin}}
                            </td>
                            <td>
                                {{tab.amountMax}}
                            </td>
                            <td>
                                {{tab.tabulator}}
                            </td>
                            <td>
                                {{tab.cDateCalculation.nameDate}}
                            </td>
                            <td>
                                <input type="checkbox" :value="tab" v-model="tab.statusBoolean"
                                       @change="updateTab(tab)">
                            </td>
                            <td>
                                <label v-if="tab.typeOperation == 1">
                                    Monto comisionable
                                </label>
                                <label v-if="tab.typeOperation == 2">
                                    Bono por solicitudes
                                </label>
                                <label v-if="tab.typeOperation == 3">
                                    Alcance de meta
                                </label>
                                <label v-if="tab.typeOperation == 4">
                                    Indice de reproceso
                                </label>
                                <label v-if="tab.typeOperation == 5">
                                    Bono por acumulado mensual
                                </label>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

            </div>


        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
