<%--
  Created by IntelliJ IDEA.
  User: Kevin Salvador
  Date: 14/12/2016
  Time: 04:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>
<t:template pageTitle="BID Group: Busqueda de empleados">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function validateFloatKeyPress(content, evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                var number = content.value.split('.');
                if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                if(number.length>1 && charCode == 46){
                    return false;
                }
                var caratPos = getSelectionStart(content);
                var dotPos = content.value.indexOf(".");
                if( caratPos > dotPos && dotPos>-1 && (number[1].length > 1)){
                    return false;
                }
                return true;
            }
            function getSelectionStart(o) {
                if (o.createTextRange) {
                    var r = document.selection.createRange().duplicate();
                    r.moveEnd('character', o.value.length);
                    if (r.text == '') return o.value.length;
                    return o.value.lastIndexOf(r.text)
                } else return o.selectionStart
            }
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }

        </script>
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.getCostAllocating();
                    this.getUser();
                },
                data: {
                    customers:{},
                    percentage:'',
                    selectCostAllocation:{},
                    regresarBusqueda: ROOT_URL + '/siad/cost-allocation',
                    selected : {
                        customer: null,
                        percentage: ''
                    },
                    cargado: '',
                    acumulado: 0,
                    cargado: '',
                    costAllocation:{
                        employeeClients:[],
                        idDwEmployee: ${idDwEmployee}
                    },
                    user: {}
                      },
                methods: {
                    getCostAllocating:function () {
                        this.$http.get(ROOT_URL+'/customers').success(function (data) {
                            this.customers = data;
                        })
                    },
                    getUser: function () {
                        this.$http.get(ROOT_URL + "/user").success(function (data) {
                            this.user = data;
                        })
                    },
                    removeProduct: function (eC) {
                        var self = this;
                         var aux = this.customers.filter(function (element){
                            if(eC.customer.idCustomer == element.idCustomer) {
                                return element;
                            }
                        });
                        console.log(this.costAllocation.employeeClients);
                         if (aux == 0){
                             this.customers.push(eC.customer);
                         }
                         this.acumulado = parseFloat(this.acumulado - eC.percentage);
                        if (this.acumulado < 0){
                            this.acumulado = 0;
                        }
                        this.costAllocation.employeeClients.$remove(eC);
                        this.selected = {
                            customer: null,
                            percentage: ''
                        }
                    },
                    exit: function () {
                        window.location = this.regresarBusqueda;
                    },
                    addClientCost : function() {
                        var object = {};
                        var self = this;
                        this.cargado = parseFloat(this.selected.percentage);
                        this.acumulado =parseFloat(this.cargado + this.acumulado);
                        if(this.selected.percentage == 0){
                            showAlert("No se puede asignar 0%",{type: 3});
                        }else if(this.acumulado <= 100) {
                            object = JSON.parse(JSON.stringify(this.selected));
                            var aux = this.costAllocation.employeeClients.filter(function (element) {
                                if (element.customer.idCustomer == self.selected.idCustomer) {
                                    return element;
                                }
                            });
                            if (aux == 0) {
                                this.costAllocation.employeeClients.push(object);
                                this.customers.$remove(this.selected.customer);
                                this.selected = {
                                    customer: null,
                                    percentage: ''
                                }
                            }
                        }else {
                            showAlert("No se puede exceder el 100%",{type: 3});
                            this.acumulado = parseFloat(this.acumulado - this.cargado);
                        }
                    },
//                    addClientCost: function () {
//                        var object = {};
//                        object = JSON.parse(JSON.stringify(this.selected));
//                        if (this.validationClientCost().length == 0) {
//                            this.costAllocation.employeeClients.push(object);
//                        }
//                        console.log(object);
//                    },
                    filterList: function () {
                      this.customers.$remove(eC)

                      },
                    validationClientCost: function () {
                        var self = this;

                        return this.costAllocation.employeeClients.filter(function (element){
                            if(self.selected.customer.idCustomer == element.customer.idCustomer) {
                                return element;
                            }
                        });
                    },
                    saveCostAllocation: function () {
                        if(this.acumulado ==100) {
                            this.$http.post(ROOT_URL + "/cost-allocation/save", JSON.stringify(this.costAllocation)).success(function (data) {
                                showAlert("Transacción realizada con exito");
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            })
                        }else{
                            showAlert("El porcentaje debe alcanzar el 100%",{type: 3});
                        }
                    }
                    }
                })
        </script>
     </jsp:attribute>
    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                    <div class="row">
                        <div class="col-xs-6">
                            <h1>Asignaciones de porcentaje</h1>
                        </div>
                        <div class="col-xs-3 col-xs-offset-3">
                            <label>
                                Asigna
                            </label>
                            <input class="form-control" type="text" name="name" value="" disabled="true" v-model="user.dwEmployee.employee.fullName">
                        </div>
                    </div>
                    <br>
                    <div class="col-md-12">
                        <form v-on:submit.prevent="addClientCost">
                            <div class="col-md-3">
                            <label>Empresa</label>
                            <select class="form-control" v-model="selected.customer">
                                <option></option>
                                <option v-for="customer in customers" value="{{customer}}" >
                                    {{customer.customerName}}
                                </option>
                            </select>
                            </div>
                            <div class="col-md-2">
                            <label>
                                Porcentaje
                            </label>
                            <input class="form-control" type="text" v-model="selected.percentage" max="100" min="1" onkeypress="return validateFloatKeyPress(this,event)">
                            </div>
                            <div class="col-md-1">
                                <button style="margin-top: 25px" class="btn btn-default">Agregar</button>
                            </div>
                            <div class="col-md-6 text-right">
                                    <button style="margin-top: 25px" class="btn btn-default" @click="exit">Regresar</button>
                            </div>
                        </form>
                    </div>
                <br>
                <form v-on:submit.prevent="saveCostAllocation">
                    <div v-if="costAllocation.employeeClients.length > 0">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <h5><b>Porcentajes asignados</b></h5>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="table-responsive">
                                                    <table class="table">
                                                        <thead>
                                                        <tr>
                                                            <th>#</th>
                                                            <th>EMPRESA</th>
                                                            <th>PORCENTAJE</th>
                                                            <th>Eliminar</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <tr v-for="eC in costAllocation.employeeClients">
                                                            <td><b>{{$index + 1}}</b></td>
                                                            <td>{{eC.customer.customerName}}</td>
                                                            <td>{{eC.percentage}}%</td>
                                                            <td>
                                                                <button class="btn btn-danger" type="button" @click="removeProduct(eC)"
                                                                        data-toggle="tooltip" data-placement="top" title="Eliminar">
                                                                    <span class="glyphicon glyphicon-trash"></span>
                                                                </button>
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <label>El porcentaje acumulado es de: {{acumulado}}%</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-10"></div>
                            <div class="col-md-2">
                                <button class="btn btn-success form-control">Enviar</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div><!--Container-fluid-->
        </div><!--content-->
    </jsp:body>
</t:template>