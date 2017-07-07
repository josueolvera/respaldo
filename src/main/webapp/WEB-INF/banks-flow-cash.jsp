<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>
<t:template pageTitle="BID Group: Tesorero bancos">
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
          <script type="text/javascript">
              var vm = new Vue({
                  el: '#content',
                  created: function () {

                  },
                  ready: function () {
                      this.getUserInSession();
                      //this.activateDateTimePickerStart();
                      this.obtainCurrencies();
                      this.getDistributors();
                      this.obtainBanks();
                      this.obtainTotal();
                      this.obtainDistributorsTotal();
                  },
                  data: {
                      startDate: '',
                      endDate: '',
                      dateTimePickerStart: null,
                      dateTimePickerEnd: null,
                      total: 0.00,
                      totalParcial: 0,
                      totalisimo: 0.00,
                      currencies: [],
                      distributorSelected: {},
                      optionSelected: {},
                      user: {},
                      prueba: [],
                      updateAmount: {
                          accountNumber: '',
                          amount: ''
                      },
                      detailsBanks: [],
                      detailsBank: {
                          idDistributor: '',
                          idBank: '',
                          accountclabe: '',
                          accountnumber: '',
                          idCurrency: '',
                          amount: ''
                      },
                      amountdistributor: [],
                      requestBody: {
                          request: {},
                          products: [],
                          travelExpense: {
                              startDate: '',
                              endDate: ''
                          }
                      },
                      distributors: [],
                      banks: []
                  },
                  methods: {
                      GenerarReporteXlm: function () {
                          if (this.startDate == '' || this.endDate == '') {
                              showAlert("Selecione las dos fechas", {type: 3});
                          } else {
                              this.startDate = this.dateTimePickerStart.DateTimePicker.date().toISOString().slice(0, -1);
                              this.endDate = this.dateTimePickerStart.DateTimePicker.date().toISOString().slice(0, -1);
                              window.location = ROOT_URL + "/distributors-detail-banks/report-distributors-detail-banks?startDate=" +
                                  this.startDate + "&endDate=" + this.endDate;
                              this.clearReportData();
                          }
                      },
                      clearReportData: function () {
                          this.startDate = '';
                          this.endDate = '';
                      },
                      showModalAltaAgrega: function () {
                          this.distributorSelected = {};
                          this.detailsBanks = [];
                          this.distributors = [];
                          this.getDistributors();
                          this.detailsBank.idDistributor = '';
                          this.updateAmount.accountNumber = '';
                          this.updateAmount.amount = '';
                          $("#modalAltaAgrega").modal("show");
                      },
                      updateAmountByAccountNumber: function () {
                          if (this.updateAmount.accountNumber != '' &&
                              this.updateAmount.amount != "") {
                              this.$http.post(ROOT_URL + "/distributors-detail-banks/account-number",
                                  JSON.stringify(this.updateAmount)).success(function (data) {
                                  this.total = 0;
                                  this.obtainDistributorsTotal();
                                  this.totalisimo = 0;
                                  this.obtainBanks();
                                  this.totalParcial = 0;
                                  this.detailsBanks = [];
                                  this.updateAmount = {};
                                  this.obtainTotal();
                                  $("#modalAltaAgrega").modal("hide");
                                  showAlert("El nuevo monto ingresado fue actualizado con éxito");
                                  this.distributorSelected = '';
                                  this.updateAmount = '';

                              })
                          } else {
                              showAlert("Debes ingresar los datos requeridos: Empresa, Banco, Cantidad ", {type: 3})

                          }
                      },
                      validateAccountEmployee: function (account, clabe) {
                          if (account.length > 0 && clabe.length > 0) {
                              if (clabe.indexOf(account) == -1) {
                                  if (!showAlert("La clabe es incorrecta", {type: 3})) {
                                      this.detailsBank.accountclabe = '';
                                      this.detailsBank.accountnumber = '';
                                      this.updateAmount = '';
                                  }
                              }
                          } else {
                              if (account.length > 0 && (account.length > 11 || account.length < 5)) {
                                  showAlert("Debes ingresar entre 5 y 11 caracteres en el numero de cuenta", {type: 3});
                                  this.detailsBank.accountnumber = '';
                                  this.updateAmount = '';
                              }


                              if (clabe.length > 0 && clabe.length < 18) {
                                  showAlert("Debes ingresar 18 caracteres en la CLABE", {type: 3});
                              }
                              if (account.length < 5 && clabe.length < 18) {
                                  showAlert("Debes ingresar un Numero de Cuenta o CLABE", {type: 3});
                              }
                          }
                      },
                      getDistributors: function () {
                          this.$http.get(ROOT_URL + "/distributors").success(function (data) {
                              this.distributors = data;
                          })
                      },
                      obtainBanks: function () {
                          this.$http.get(ROOT_URL + "/banks").success(function (data) {
                              this.banks = data;
                          })
                      },
                      obtainCurrencies: function () {
                          this.$http.get(ROOT_URL + "/currencies").success(function (data) {
                              this.currencies = data;
                          });
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
                      getdetailsBanks: function () {
                          var self = this;
                          this.$http.get(ROOT_URL + "/distributors-detail-banks/distributor/" +
                              this.distributorSelected.idDistributor).success(function (data) {
                              this.detailsBanks = data;
                          });
                      },
                      obtainTotal: function () {
                          var self = this;
                          this.$http.get(ROOT_URL + "/distributors-detail-banks").success(function (data) {
                              data.forEach(function (element) {
                                  element.amount = element.amount * element.currencies.rate;
                                  self.total += element.amount;
                              });
                          });
                      },
                      obtainDistributorsTotal: function () {
                          var self = this;
                          this.$http.get(ROOT_URL + "/distributors-detail-banks").success(function (data) {
                              data.forEach(function (element) {
                                  element.amount = element.amount + element.idDistributor;
                                  self.totalisimo += element.amount;
                              });
                          });
                      },
                      clearSelection: function () {
                          this.detailsBank.idDistributor = '';
                          this.detailsBank.idBank = '';
                          this.detailsBank.accountclabe = '';
                          this.detailsBank.accountnumber = '';
                          this.detailsBank.idCurrency = '';
                          this.detailsBank.amount = '';
                      },
                      showModalAltaAgregaBan: function () {
                          this.distributorSelected = {};
                          this.detailsBanks = [];
                          this.distributors = [];
                          this.getDistributors();
                          this.detailsBank.idDistributor = '';
                          this.detailsBank.idBank = '';
                          this.detailsBank.accountclabe = '';
                          this.detailsBank.accountnumber = '';
                          this.detailsBank.idCurrency = '';
                          this.detailsBank.amount = '';
                          $("#agregarcuentabancaria").modal("show");
                      },
                      savedetailsBank: function () {
                          this.$http.get(ROOT_URL + "/distributors-detail-banks/find-account-number?accountNumber=" +
                              this.detailsBank.accountnumber).success(function (info) {
                              this.prueba = info;
                              if (info == null) {
                                  if (this.detailsBank.idDistributor != "" && this.detailsBank.idBank != '' &&
                                      this.detailsBank.accountclabe != ""
                                      && this.detailsBank.idCurrency != "" && this.detailsBank.amount != "" &&
                                      this.detailsBank.accountnumber != "") {
                                      this.$http.post(ROOT_URL + "/distributors-detail-banks/save",
                                          JSON.stringify(this.detailsBank)).success(function (data) {
                                          $("#agregarcuentabancaria").modal("hide");
                                          this.total = 0;
                                          this.obtainDistributorsTotal();
                                          this.totalisimo = 0;
                                          this.obtainBanks();
                                          this.totalParcial = 0;
                                          this.detailsBanks = [];
                                          this.updateAmount = {};
                                          this.obtainTotal();
                                          showAlert("La cuenta bancaria ingresada fue registrada exitosamente");
                                          this.distributorSelected = '';
                                          this.updateAmount = '';
                                      })
                                  } else {
                                      showAlert("Debes ingresar los datos requeridos: Empresa, Banco, CLABE, Numero de" +
                                          " cuenta, Tipo de moneda y Monto.",
                                          {type: 3})
                                  }
                              } else {
                                  showAlert("Esa cuenta ya existe", {type: 3});
                                  this.detailsBank.idDistributor = '';
                                  this.detailsBank.idBank = '';
                                  this.detailsBank.accountclabe = '';
                                  this.detailsBank.accountnumber = '';
                                  this.detailsBank.idCurrency = '';
                                  this.detailsBank.amount = '';
                              }
                          }).error(function () {
                              showAlert("Debes ingresar los datos requeridos: Empresa, Banco, CLABE, Numero de" +
                                  " cuenta, Tipo de moneda y Monto.",
                                  {type: 3});
                          });
                      },
                      showMoreAmount: function (detailBank) {
                          this.updateAmount.amount = '';
                          this.updateAmount.accountNumber = detailBank.accountNumber;
                          $("#moreAmount").modal("show");
                      },
                      saveMoreAmount: function () {
                          if (this.updateAmount.accountNumber != '' &&
                              this.updateAmount.amount != '') {
                              this.$http.post(ROOT_URL + "/distributors-detail-banks/account-number",
                                  JSON.stringify(this.updateAmount)).success(function (data) {
                                  this.total = 0;
                                  this.obtainDistributorsTotal();
                                  this.totalisimo = 0;
                                  this.obtainBanks();
                                  this.totalParcial = 0;
                                  this.detailsBanks = [];
                                  this.updateAmount = {};
                                  this.obtainTotal();
                                  $("#moreAmount").modal("hide");
                                  showAlert("El dinero se agrego correctamente");
                                  this.distributorSelected = '';
                                  this.updateAmount = '';
                                  setTimeout(function () {
                                      location.reload();
                                  }, 3000)
                              }).error(function () {
                                  showAlert("Error al generar la solicitud", {type: 3});
                              });
                          } else {
                              showAlert("Es necesario llenar todos los campos", {type: 3})
                          }
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
                      findLikeAccountNumber: function () {
                          this.distributors = [];
                          this.$http.get(ROOT_URL + "distributors-detail-banks/find-account-number?find-account-number=" +
                              this.accountNumber).success(function (data) {
                              this.requestsPD = data;
                          })
                      },
                      activateDateTimePickerEnd: function (startDate) {
                          var date = moment(startDate, 'DD-MM-YYYY').format('YYYY-MM-DD');
                          this.dateTimePickerEnd = $('#endDate').datetimepicker({
                              locale: 'es',
                              format: 'DD-MM-YYYY',
                              useCurrent: false,
                              minDate: date

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
                          showAlert("INGRESAR UNA CANTIDAD REAL", {type: 3});
                          input.value = '';
                      } else {
                          input.value = num.split('').join('').replace(/^[\,]/, '');
                      }
                  }

                  else {
                      showAlert("Solo se permiten numeros", {type: 3});
                      input.value = '';
                  }
              }
              //no permite letras ni numero
              function keyCaracteres(obj) {
                  var inicial = obj.value;
                  if (obj.value.length > 0) {
                      obj.value = '';
                      showAlert("Debes seleccionar una fecha del calendario.", {type: 3});
                  } else {
                      showAlert("Debes seleccionar una fecha del calendario.", {type: 3});
                      return false;
                  }
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
              document.getElementById('reloj').innerHTML = d + "/" + u + "/" + y + "&nbsp;" + "&nbsp;"
                  + "&nbsp;" + "&nbsp;" + "&nbsp;" + h + ":" + m + ":" + s;
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
                   function justNumbers(e) {
                       var keynum = window.event ? window.event.keyCode : e.which;
                       if ((keynum == 8) || (keynum == 46))
                           return true;

                       return /\d/.test(String.fromCharCode(keynum));
                   }
               </script>
    </jsp:attribute>
    <jsp:attribute name="styles">


    </jsp:attribute>
    <jsp:body>
        <%--CREACION DE ENCABEZADO--%>
        <div id="content">
        <div class="row">
            <div class="col-md-12">
                <div class="col-md-3">
                    <h2>TESORERO/BANCOS</h2>
                </div>
                <div class="col-md-2 text-right">
                </div>
                <div class="col-md-4">
                </div>
                <div class="col-md-3" style="margin-top: 10px">
                    <label>NOMBRE DE USUARIO</label>
                    <p>
                        <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
        </div>
        <br><br>
        <%-- EMPIEZA TOTAL DE FLUJO DE EFECTIVO--%>
        <div class="panel panel-default" style="background-color: #F2F2F2">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-12" style="background-color: #949494">
                        <h5 style="text-align: center"><b style="color: black">TOTAL DE FLUJO DE EFECTIVO</b></h5>
                    </div>
                    <div class="panel-body">
                        <b></b>
                        <div class="form-group"><br><br>
                            <b>
                                <div class="col-md-12 text-center" id="reloj" style="color: #000000" title="">
                                </div>
                            </b><br><br>
                            <div class="col-md-12 text-center" style="color: #000000" title=""><b>TOTAL: &nbsp
                                &nbsp{{total |
                                currency}}</b>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--EMPIEZA REPORTE DE FLUJO-->
        <div class="panel panel-default" style="background-color: #F2F2F2">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-12 text-center" style="background-color: #949494">
                        <h5><b style="color: black">REPORTE FLUJO DE EFECTIVO </b></h5>
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
                                            <input class="form-control"
                                                   onkeypress="return keyCaracteres(this)"
                                                   onpaste=";return false"
                                                   v-model="startDate"
                                                   required>
                                            <span class="input-group-addon" @click="activateDateTimePickerStart">
                                           <span class="glyphicon glyphicon-calendar"></span>
                                       </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5">
                                <div class="col-xs-2" style="padding-left: 0; margin-top: 5px">
                                    <p><b>a</b></p>
                                </div>
                                <div class="col-xs-10" style="padding-left: 0">
                                    <div class="form-group">
                                        <div class="input-group date" id="endDate">
                                            <input class="form-control"
                                                   onkeypress="return keyCaracteres(this)"
                                                   onpaste=";return false"
                                                   v-model="endDate"
                                                   required>
                                            <span class="input-group-addon"
                                                  @click="activateDateTimePickerEnd(startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-success" @click="GenerarReporteXlm">Generar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--EMPIEZA PANEL QUE INCLUYE SELECCION DE EMPRESA, BOTONES AGREGAR ESTADO DE CUENTA, AGREGAR CEUNTA
        BANCARIA-->
        <!--CREACION DE SELECT DE EMPRESA-->
        <div class="panel panel-default" style="background-color: #F2F2F2">
            <div class="row">
                <div class="col-md-12" style="margin-left: 1%"><br>
                    <div class="col-md-4 text-center">
                        <label><b>SELECCIONE UNA EMPRESA</b></label>
                        <select class="form-control" v-model="distributorSelected" @change="getdetailsBanks()">
                            <option></option>
                            <option v-for="distributor in distributors" :value="distributor">
                                {{distributor.idDistributor}} - {{distributor.distributorName}}
                            </option>
                        </select>
                    </div>
                    <br>
                    <!--AQUI EMPIEZA BOTON CON MODAL AGREGAR ESTADO DE CUENTA-->
                    <div class="col-md-5 text-center">
                        <button type="button" class="btn btn-warning"
                                @click="showModalAltaAgrega" ()>
                            Agregar estado de cuenta
                        </button>
                    </div>
                    <!--AQUI EMPIEZA BOTON CON MODAL AGREGAR CUENTA BANCARIA-->
                    <div class="col-md-3">
                        <button type="button" class="btn btn-info"
                                @click="showModalAltaAgregaBan()">
                            Agregar cuenta bancaria
                        </button>
                    </div>
                    <br><br><br>
                </div>
            </div>
        </div>
        <br>
        <!--EMPIEZA MODAL AGREGAR ESTADO DE CUENTA-->
        <div class="modal fade" id="modalAltaAgrega" tabindex="-1" role="dialog"
             aria-labelledby=""
             aria-hidden="true">
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
                                    <select class="form-control"
                                            v-model="distributorSelected"
                                            @change="getdetailsBanks()"
                                    >
                                        <option v-for="distributor in distributors"
                                                :value="distributor">
                                            {{distributor.idDistributor}} - {{distributor.acronyms}}
                                        </option>
                                    </select><br>
                                </div>
                                <div class="col-md-6">
                                    <label><h6><b style="color: black">Banco</b></h6></label>
                                    <select class="form-control"
                                            v-model="updateAmount.accountNumber"
                                            :disabled="detailsBanks.length == 0"
                                    >
                                        <option></option>
                                        <option v-for="bank in detailsBanks"
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
                                            <input
                                                    number
                                                    placeholder="0"
                                                    onInput="format(this)"
                                                    class="form-control"
                                                    onpaste=";return false"
                                                    v-model="updateAmount.amount"
                                                    :disabled="!updateAmount.accountNumber > 0">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success"
                                @click="updateAmountByAccountNumber()">
                            Guardar
                        </button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!--EMPIEZA MODAL AGREGAR CUENTA BANCARIA-->
        <div class="modal fade" id="agregarcuentabancaria" tabindex="-1" role="dialog"
             aria-labelledby="" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Selecionar</h4></div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-4">
                                    <label><h6><b style="color: black">Empresa</b></h6></label>
                                    <select v-model="detailsBank.idDistributor" class="form-control">
                                        <option v-for="distributor in distributors"
                                                :value="distributor.idDistributor">
                                            {{distributor.idDistributor}} - {{distributor.acronyms}}
                                        </option>
                                    </select><br>
                                </div>
                                <div class="col-md-4">
                                    <label><h6><b style="color: black">Banco</b></h6></label>
                                    <select v-model="detailsBank.idBank" class="form-control"
                                            :disabled="detailsBank.idDistributor == 0">
                                        <option></option>
                                        <option v-for="bank in banks" :value="bank.idBank">
                                            {{bank.acronyms}}
                                        </option>
                                    </select><br>
                                </div>
                                <div class="col-md-4">
                                    <form>
                                        <label><h6><b style="color: black">Numero de cuenta</b></h6>
                                        </label>
                                        <div class="input-group">
                                            <span class="input-group-addon">#</span>
                                            <input :disabled="detailsBank.idBank == 0"
                                                   class="form-control"
                                                   id="ejemplo" onkeypress="return justNumbers(event);"
                                                   value="1"
                                                   v-model="detailsBank.accountnumber"
                                                   onpaste=";return false" maxlength="11"
                                                   @change="validateAccountEmployee(detailsBank.accountnumber,
                                                       detailsBank.accountclabe)"
                                                   required>
                                        </div>
                                    </form>
                                    <br>
                                </div>
                                <br><br>
                                <div class="col-md-4">
                                    <form>
                                        <label><h6><b style="color: black">CLABE</b></h6>
                                        </label>
                                        <div class="input-group">
                                            <span class="input-group-addon">#</span>
                                            <input :disabled="detailsBank.accountnumber == 0"
                                                   id="ejemplo1" onkeypress="return justNumbers(event);"
                                                   value="1"
                                                   class="form-control"
                                                   v-model="detailsBank.accountclabe"
                                                   onpaste=";return false" maxlength="18"
                                                   @change="validateAccountEmployee
                                                                   (detailsBank.accountnumber,detailsBank.
                                                                   accountclabe)">
                                        </div>
                                    </form>
                                </div>
                                <div class="col-md-4">
                                    <label><h6><b style="color: black">Tipo de moneda</b></h6></label>
                                    <select v-model="detailsBank.idCurrency" class="form-control"
                                            :disabled="detailsBank.accountclabe == 0">
                                        <option v-for="currency in currencies"
                                                :value="currency.idCurrency">
                                            {{currency.currency}}
                                        </option>
                                    </select><br>
                                </div>
                                <div class="col-md-4">
                                    <form>
                                        <label><h6><b style="color: black">Monto</b></h6></label>
                                        <div class="input-group">
                                            <span class="input-group-addon">$</span>
                                            <input class="form-control"
                                                   number
                                                   onpaste=";return false"
                                                   v-model="detailsBank.amount"
                                                   onInput="format(this)"
                                                   :disabled="detailsBank.idCurrency == 0">
                                        </div>
                                    </form>
                                    <br>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success"
                                @click="savedetailsBank()">Guardar
                        </button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Salir</button>
                    </div>
                </div>
            </div>
        </div>
        <!--SELECT Y PANEL DEFAULT DE EMPRESAS-->
        <div class="row">
            <div class="col-md-12" v-if="detailsBanks.length > 0">
                <div class="panel panel-default">
                    <div class="card">
                        <div class="card-header" role="tab" id="headingThree">
                            <div class="panel-heading" style="background-color: #949494">
                                <a class="collapsed" data-toggle="collapse" data-parent="#collapseThree"
                                   href="#collapseThree"
                                   aria-expanded="false" aria-controls="collapseThree">
                                    <div class="col-md-12 text-center">
                                        <p><b style="color: black">{{distributorSelected.distributorName}}</b></p>
                                    </div>
                                </a><br>
                            </div>
                        </div>
                        <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="card-block">
                                <div class="panel-body">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div>
                                                <div class="row table-header">
                                                    <div class="col-xs-2 text-center"><b>Nombre de la empresa</b>
                                                    </div>
                                                    <div class="col-xs-2 text-center"><b>Nombre del Banco</b></div>
                                                    <div class="col-xs-2 text-center"><b>CLABE</b></div>
                                                    <div class="col-xs-2 text-center"><b>Numero de cuenta</b></div>
                                                    <div class="col-xs-2 text-center"><b>Monto</b></div>
                                                    <div class="col-xs-2 text-center"><b>Agregar mas dinero</b></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="table-body flex-row flex-content-{{index}}">
                                            <div class="row table-row" v-for="detailsBank in detailsBanks"><br>
                                                <div class="col-xs-2 text-center">
                                                    {{detailsBank.distributors.acronyms}}
                                                </div>
                                                <div class="col-xs-2 text-center">{{detailsBank.banks.acronyms}}
                                                </div>
                                                <div class="col-xs-2 text-center">{{detailsBank.accountClabe}}
                                                </div>
                                                <div class="col-xs-2 text-center">{{detailsBank.accountNumber}}
                                                </div>
                                                <div class="col-xs-2 text-center">{{detailsBank.amount |currency}}
                                                </div>
                                                <div class="col-xs-2 text-center">
                                                    <button type="button" class="btn btn-success"
                                                            @click="showMoreAmount(detailsBank)">
                                                        <span class="glyphicon glyphicon-plus"
                                                              style="margin-left: 7px"></span>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--//MODAL AGREGAR DINERO--%>
        <div class="modal fade" id="moreAmount" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="">
            <div class="modal-dialog modal-xs">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">x</button>
                        <h4 class="modal-title text-center">Agregar mas dinero</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-2 text-center">
                                    <label><h6><b style="color: black">Banco</b></h6></label><br><br>
                                </div>
                                <div class="col-md-10">
                                    <select class="form-control"
                                            v-model="updateAmount.accountNumber" disabled>
                                        <option></option>
                                        <option v-for="bank in detailsBanks"
                                                :value="bank.accountNumber">
                                            {{bank.banks.acronyms}} : Cuenta: {{bank.accountNumber}}
                                        </option>
                                    </select><br>
                                </div>
                            </div>
                            <br>

                            <div class="col-md-12">
                                <div class="col-md-2">
                                    <label><h6><b style="color: black">Cantidad</b></h6></label>
                                </div>
                                <div class="col-md-10">
                                    <div class="input-group">
                                        <span class="input-group-addon">$</span>
                                        <input class="form-control" number
                                               placeholder="0" oninput="format(this)" onpaste=";return false"
                                               v-model="updateAmount.amount"
                                               :disabled="!updateAmount.accountNumber > 0">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success"
                                    @click="saveMoreAmount()">
                                Guardar
                            </button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
                <%--//THE END CONTENT--%>
        </div>
    </jsp:body>
</t:template>