<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

<t:template pageTitle="BID Group: Bandeja de entrada solicitante">
      <jsp:attribute name="scripts">
        <script type="text/javascript">
            function validateFloatKeyPress(el, evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                var number = el.value.split('.');
                if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                //just one dot
                if (number.length > 1 && charCode == 46) {
                    return false;
                }
                //get the carat position
                var caratPos = getSelectionStart(el);
                var dotPos = el.value.indexOf(".");
                if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
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
        </script>
          <script>
              var search = document.getElementById("search"),
                  food = document.getElementsByTagName("span"),
                  forEach = Array.prototype.forEach;

              search.addEventListener("keyup", function(e){
                  var choice = this.value;

                  forEach.call(food, function(f){
                      if (f.innerHTML.toLowerCase().search(choice.toLowerCase()) == -1)
                          f.parentNode.style.display = "none";
                      else
                          f.parentNode.style.display = "block";
                  });
              }, false);
          </script>
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                created: function () {

                },
                ready: function () {
                    this.getUserInSession();
                    this.activateDateTimePickerStart();
                    this.getBussinessLines();
                    this.obtainCurrencies();
                    this.getDistributors();
                },
                data: {
                    startDate: '',
                    endDate: '',
                    total: 0.00,
                    roleCostCenterList: [],
                    costCenterList: [],
                    budgetCategories: [],
                    budgetSubcategories: [],
                    products: [],
                    providers: [],
                    providerAccounts: [],
                    currencies: [],
                    requestProducts: [],
                    user: {},
                    estimation: {
                        amount: '',
                        provider: '',
                        account: '',
                        currency: '',
                        rate: '',
                        file: ''
                    },
                    updateAmount: {
                        accountNumber: '',
                        amount: ''
                    },
                    bussinessLines: [],
                    bussinessLine: {
                        idDistributor: '',
                        idBank: '',
                        accountclabe: '',
                        accountnumber: '',
                        idCurrency: '',
//                        idaccountbanktype: '',
                        amount: ''
                    },
                    requestBody: {
                        request: {
                            description: '',
                            purpose: '',
                            userResponsible: '',
                            idCostCenter: '',
                            idBudgetCategory: '',
                            idBudgetSubcategory: '',
                            idRequestCategory: ''
                        },
                        products: []
                    },
                    estimations: [],
                    selectProducts: {},
                    selected: {
                        costCenter: null,
                        budgetCategory: null,
                        budgetSubcategory: null,
                        product: null
                    },
                    newEstimationFormActive: false,
                    distributors: []
                },
                methods: {
                    updateAmount: function () {
                        this.$http.update(ROOT_URL + "/distributors-detail-banks/account-number" , JSON.stringify(this.updateAmount)).success(function (data) {
                            this.updateAmount = [];
                            showAlert("Registro guardado con exito");
                        }).error(function () {
                            showAlert("Error en la solicitud", {type: 3});
                        });
                    },
                    getDistributors: function () {
                        this.$http.get(ROOT_URL + "/distributors").success(function (data) {
                            this.distributors = data;
                        })
                    },
                    obtainCurrencies: function () {
                        this.$http.get(ROOT_URL + "/currencies").success(function (data) {
                            this.currencies = data;
                        });
                    },
                    arrayObjectIndexOf: function (myArray, idBankTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === idBankTerm) return i;
                        }
                        return -1;
                    },
                    getUserInSession: function () {
                        this.$http.get(ROOT_URL + "/user")
                            .success(function (data) {
                                this.user = data;
                            })
                            .error(function (data) {
                                showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                            });
                    },
                    getBussinessLines: function () {
                        var self = this;
                        this.$http.get(ROOT_URL + "/distributors-detail-banks").success(function (data) {
                            this.bussinessLines = data;
                            this.bussinessLines.forEach(function (element) {
                               self.total += element.amount;
                            });
                        })
                    },

                    showModalAlta: function () {
                        this.bussinessLine.idDistributor = '';
                        this.bussinessLine.idBank = '';
                        this.bussinessLine.accountclabe = '';
                        this.bussinessLine.accountnumber = '';
                        this.bussinessLine.idCurrency = '';
//                        this.bussinessLine.idaccountbanktype = '';
                        this.bussinessLine.amount = '';
                        $("#agregarcuentabancaria").modal("show");
                    },
                    saveBussinessLine: function () {
//                        if(this.bussinessLine.idBank.length  && this.bussinessLine.idCurrency.length  &&
//                            this.bussinessLine.idDistributor.length  &&  this.bussinessLine.idaccountbanktype.length  &&
//                            this.bussinessLine.accountnumber.length && this.bussinessLine.accountclabe.length
//                            && this.bussinessLine.amount.length ){
                        this.$http.post(ROOT_URL + "/distributors-detail-banks/save", JSON.stringify(this.bussinessLine)).success(function (data) {
                            this.bussinessLines = [];
                            this.bussinessLines = data;
                            $("#modalAlta").modal("hide");
                            showAlert("Registro guardado con exito");
                        }).error(function () {
                            showAlert("Error en la solicitud", {type: 3});
                        });
//                        }else{
//                            showAlert("Es necesario que se llenen los campos: Empresa, Banco, Cuenta Bancaria, Cuenta Clabe, Clabe Interbancaria, Tipo de moneda, Monto", {type: 3});
//                        }
                    },
                    getProviderAccounts: function () {
                        this.$http.get(ROOT_URL + '/providers-accounts/provider/' + this.estimation.provider.idProvider)
                            .success(function (data) {
                                this.providerAccounts = data;
                            })
                            .error(function (data) {

                            });
                    },
                    onDateChanged: function () {
                        var startDate;
                        var endDate;

                        var startDateISOString = this.dateTimePickerStart.DateTimePicker.date().toISOString();
                        var endDateISOString = this.dateTimePickerEnd.DateTimePicker.date().toISOString();

                        startDate = new Date(startDateISOString);
                        endDate = new Date(endDateISOString);

                        if (startDate instanceof Date && endDate instanceof Date) {
                            var timeDiff = Math.abs(startDate.getTime() - endDate.getTime());
                            this.dateDifference = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;
                        }
                    },
                    activateDateTimePickerStart: function () {

                        var currentDate = new Date();

                        this.dateTimePickerStart = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false

                        }).data();


                    },
                    activateDateTimePickerEnd: function (startDate) {


                        this.dateTimePickerEnd = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false

                        }).data();
                    },
                    filters: {}
                }

            });

        </script>

        <script type="text/javascript">
            function format(input) {
                var num = input.value.replace(/\,/g, '');
                if (!isNaN(num)) {
                    num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g, '$1,');
                    num = num.split('').reverse().join('').replace(/^[\,]/, '');
                    num = num.split('').join('').replace(/^[\,]/, '');
                    var inicio = num.substring(0, 1);
                    if (inicio == '0') {
                        showAlert("Corregir cantidad", {type: 3});
                        input.value = '';
                    } else {
                        input.value = num.split('').join('').replace(/^[\,]/, '');
                    }
                }

                else {
                    showAlert("Solo se permiten números", {type: 3});
                    input.value = '';
                }
            }

            function cleanField(obj) {
                var inicial = obj.value;
                if (obj.value == '0' || obj.value == '0.00') {
                    obj.value = '';
                } else {
                    return false;
                }
            }
            function onlyNumbers(e) {
                var key = window.Event ? e.which : e.keyCode
                return (key >= 48 && key <= 57)
            }

        </script>

      <script type="text/javascript">
          function startTime() {
              today = new Date();
              h = today.getHours();
              m = today.getMinutes();
              s = today.getSeconds();
              d = today.getDate();
              u = today.getMonth() + 1;
              y = today.getFullYear();
              h = checkTime(h);
              m = checkTime(m);
              s = checkTime(s);
              d = checkTime(d);
              u = checkTime(u);
              y = checkTime(y);
              document.getElementById('reloj').innerHTML = d + "/" + u + "/" + y + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + h + ":" + m + ":" + s;
              t = setTimeout('startTime()', 500);
          }
          function checkTime(i) {
              if (i < 10) {
                  i = "0" + i;
              }
              return i;
          }
          window.onload = function () {
              startTime();
          }
      </script>

          <script>
              function valida(e){
                  tecla = (document.all) ? e.keyCode : e.which;

                  //Tecla de retroceso para borrar, siempre la permite
                  if (tecla==8){
                      return true;
                  }

                  // Patron de entrada, en este caso solo acepta numeros
                  patron =/[0-9]/;
                  tecla_final = String.fromCharCode(tecla);
                  return patron.test(tecla_final);
              }
          </script>
    </jsp:attribute>
    <jsp:attribute name="styles">


    </jsp:attribute>

    <jsp:body>
        <div id="content">
                <%--CREACION DE TITULO CON USUARIO GRUPO ZARCO--%>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-3">
                        <h2>TESORERO/BANCOS</h2>
                    </div>
                    <div class="col-md-2 text-right">
                    </div>
                    <div class="col-md-4">
                    </div>
                    <div class="col-md-3 text-right" style="margin-top: 10px">
                        <label>Nombre de usuario</label>
                        <p>
                            <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                        </p>
                    </div>
                </div>
            </div>
            <br><br>
                <%--TERMINACION DE TITULO CON USUARIO GRUPO ZARCO--%>

                <%-- EMPIEZA TOTAL DE FLUJO DE EFECTIVO--%>
            <div class="panel panel-default" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #aaaaaa">
                            <h5 style="text-align: center"><b style="color: black">TOTAL DE FLUJO DE EFECTIVO</b></h5>
                        </div>
                        <table class="table table-striped">
                            <thead>
                            <th class="col-md-12" style="text-align: center" id="reloj">
                            </th>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="col-md-12" style="text-align: center"><b>TOTAL: &nbsp &nbsp{{total | currency}}</b></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!--TERMINA EL TOTAL DE FLUJO DE EFECTIVO-->

            <!--EMPIEZA REPORTE DE FLUJO-->
            <div class="panel panel-default" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #aaaaaa">
                            <h5><b style="color: black">REPORTE DE FLUJO </b></h5>
                        </div>
                        <br><br>
                        <div class="panel-body">
                            <div class="col-xs-12">
                                <div class="col-xs-5" style="padding-left: 0">
                                    <div class="col-xs-2" style="padding-left: 0; margin-top: 5px">
                                        <span><b>De</b></span>
                                    </div>
                                    <div class="col-xs-10" style="padding-left: 0; padding-right: 0">
                                        <div class="form-group">
                                            <div class="input-group date" id="startDate">
                                                <input type="text" class="form-control"
                                                       v-model="requestBody.travelExpense.startDate" required>
                                                <span class="input-group-addon" @click="destroyDateTimePickerStart">
                                           <span class="glyphicon glyphicon-calendar"></span>
                                       </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-5">
                                    <div class="col-xs-2" style="padding-left: 0; margin-top: 5px">
                                        <p><b>A</b></p>
                                    </div>
                                    <div class="col-xs-10" style="padding-left: 0">
                                        <div class="form-group">
                                            <div class="input-group date" id="endDate">
                                                <input type="text" class="form-control"
                                                       v-model="requestBody.travelExpense.endDate" required>
                                                <span class="input-group-addon"
                                                      @click="activateDateTimePickerEnd(requestBody.travelExpense.startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <button type="button" class="btn btn-success">Generar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--TERMINA REPORTE DE FLUJO-->



            <div class="panel panel-default" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12" style="margin-left: 1%">
                        <div class="col-md-5">
                            <div>
                                <label for="search"><h5><b style="color: black">BUSCAR POR NOMBRE DE LA EMPRESA</b></h5></label>
                                <input class="form-control" type="text" id="search" placeholder = "Buscar por empresa" autofocus/>
                            </div>
                        </div>
                        <br><br>

                        <!--AQUI EMPIEZA BOTON CON MODAL AGREGAR ESTADO DE CUENTA-->

                        <div class="col-md-4" style="text-align: right">
                            <button type="button" class="btn btn-warning" data-toggle="modal"
                                    data-target="#agregarestado">
                                Agregar estado de cuenta
                            </button>
                        </div>
                        <div class="modal fade" id="agregarestado" role="dialog">
                            <div class="modal-dialog modal-ms">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title">Selecionar</h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-6">
                                                    <label><h6><b style="color: black">Empresa</b></h6></label>
                                                    <select v-model="bussinessLine.idDistributor" class="form-control">
                                                        <option></option>
                                                        <option v-for="distributor in distributors" :value="distributor.idDistributor">
                                                            {{distributor.acronyms}}
                                                        </option>
                                                    </select><br>
                                                </div>
                                                <div class="col-md-6">
                                                    <label><h6><b style="color: black">Banco</b></h6></label>
                                                    <select v-model="updateAmount.accountNumber" class="form-control">
                                                        <option></option>
                                                        <option  v-for="bank in bussinessLines"
                                                                 v-if="bank.idDistributor == bussinessLine.idDistributor"
                                                                 :value="bank.accountNumber">
                                                            {{bank.banks.acronyms}} : Cuenta: {{bank.accountNumber}}
                                                        </option>
                                                    </select><br>
                                                </div>
                                                <div class="col-md-12">
                                                    <div class="col-md-2">
                                                        <label><h6><b style="color: black">Cantidad</b></h6></label>
                                                    </div>
                                                    <div class="col-md-10">
                                                        <div class="input-group">
                                                            <span class="input-group-addon">$</span>
                                                            <input type="text" name="numpiso"
                                                                   onkeypress="return valida(event)"
                                                                   class="form-control"
                                                                   onpaste="alert('Acceso Denegado');return false"
                                                                   v-model="updateAmount.amount">
                                                            <br>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-success" data-dismiss="modal" @click="updateAmount()">
                                            Guardar
                                        </button>
                                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                            <%--TERMINA BOTON CON MODAL AGREGAR ESTADO DE CUENTA--%>

                        <!--AQUI EMPIEZA BOTON CON MODAL AGREGAR CUENTA BANCARIA-->
                        <div class="col-md-3">
                            <button type="button" class="btn btn-info" data-toggle="modal"
                                    @click="showModalAlta()">
                                Agregar cuenta bancaria
                            </button>
                        </div>
                        <br><br><br>
                        <div class="modal fade" id="agregarcuentabancaria" role="dialog">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title">Selecionar</h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-4">
                                                    <label><h6><b style="color: black">Empresa</b></h6></label>
                                                    <select v-model="bussinessLine.idDistributor" class="form-control">
                                                        <option></option>
                                                        <option v-for="distributor in bussinessLines" :value="distributor.idDistributor">
                                                            {{distributor.distributors.acronyms}}
                                                        </option>
                                                    </select><br>
                                                </div>
                                                <div class="col-md-4">
                                                    <label><h6><b style="color: black">Banco</b></h6></label>
                                                    <select v-model="bussinessLine.idBank" class="form-control">
                                                        <option></option>
                                                        <option  v-for="bank in bussinessLines" :value="bank.banks.idBank">
                                                            {{bank.banks.acronyms}}
                                                        </option>
                                                    </select><br>
                                                </div>
                                                <div class="col-md-4">
                                                    <form>
                                                        <label><h6><b style="color: black">CLABE</b></h6>
                                                        </label>
                                                        <input type="text" name="numpiso" onkeypress="return valida(event)" class="form-control"
                                                               v-model="bussinessLine.accountclabe"
                                                               onpaste="alert('Acceso Denegado');return false">
                                                    </form>
                                                    <br>
                                                </div>
                                                <br>
                                                <br>
                                                <div class="col-md-4">
                                                    <form>
                                                        <label><h6><b style="color: black">Numero de cuenta</b></h6>
                                                        </label>
                                                        <input type="text" name="numpiso" onkeypress="return valida(event)"
                                                               class="form-control" v-model="bussinessLine.accountnumber"
                                                               onpaste="alert('Acceso Denegado');return false">
                                                    </form>
                                                    <br><br><br>
                                                </div>
                                                <div class="col-md-4">
                                                    <label><h6><b style="color: black">Tipo de moneda</b></h6></label>
                                                    <select v-model="bussinessLine.idCurrency" class="form-control">
                                                        <option></option>
                                                        <option  v-for="currency in currencies" :value="currency.idCurrency">
                                                            {{currency.currency}}
                                                        </option>
                                                    </select><br>
                                                </div>
                                                <div class="col-md-4">
                                                    <form>
                                                        <label><h6><b style="color: black">Monto</b></h6></label>
                                                        <input type="text" name="numpiso" onkeypress="return valida(event)"
                                                               class="form-control" v-model="bussinessLine.amount"
                                                               onpaste="alert('Acceso Denegado');return false">
                                                    </form>
                                                    <br><br><br>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-success" data-dismiss="modal" @click="saveBussinessLine()">Guardar</button>
                                        </button>
                                        <button type="button" class="btn btn-danger" data-dismiss="modal">Salir</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <!--TERMINA BOTON CON MODAL AGREGAR CUENTA BANCARIA-->

            <!--EMPIEZA COLPASO DE EMPRESAS-->
            <section>
                <div v-for="(index, distributor) in distributors">
                    <div id="accordion-{{index}}" role="tablist" aria-multiselectable="true">
                        <div class="panel panel-default">
                            <div class="card">
                                <div class="card-header" role="tab" id="headingONE-{{index}}">
                                    <div class="panel-heading" style="background-color: #aaaaaa">
                                        <a class="collapsed" data-toggle="collapse" data-parent="#accordion-{{index}}"
                                           href="#collapseONE-{{index}}"
                                           aria-expanded="false" aria-controls="collapseONE-{{index}}">
                                            <div class="col-md-10">
                                                <span><b style="color: black">{{distributor.distributorName}}</b></span>
                                            </div>
                                        </a>
                                        <div class="col-md-2 text-right">
                                            <label style="color: black">{{distributor.amount &nbsp  |&nbsp currency}}</label>
                                        </div>
                                        <br>
                                    </div>
                                </div>
                                <div id="collapseONE-{{index}}" class="collapse" role="tabpanel"
                                     aria-labelledby="headingONE-{{index}}">
                                    <div class="card-block">
                                        <div class="panel-body">
                                            <div class="col-md-12">
                                                <div class="row">
                                                    <table class="table table-striped">
                                                        <thead>
                                                        <tr>
                                                            <th class="col-md-4" style="text-align: center"><b>NOMBRE DEL BANCO</b></th>
                                                            <th class="col-md-4" style="text-align: center"><b>NUMERO DE CUENTA</b></th>
                                                            <th class="col-md-4" style="text-align: center"><b>CANTIDAD DEL BANCO</b></th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <tr v-for="(index, bussinessLine) in bussinessLines" v-if="bussinessLine.idDistributor == distributor.idDistributor" >
                                                            <td class="col-md-4" style="text-align: center">{{bussinessLine.banks.bankName}}</td>
                                                            <td class="col-md-4" style="text-align: center">{{bussinessLine.accountNumber}}</td>
                                                            <td class="col-md-4" style="text-align: center">{{bussinessLine.amount | currency}}</td>
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
                    </div>
                </div></section>

            <!--TERMINA COLAPSO DE EMPRESAS-->

        </div>
    </jsp:body>
</t:template>