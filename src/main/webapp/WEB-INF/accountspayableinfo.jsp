<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />


<t:template pageTitle="BID Group: Presupuestos">

    <jsp:attribute name="styles">

        <style media="screen">
            .underline {
                border-bottom: 1px solid black;
            }
        </style>

    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script type="text/javascript">
        function validateFloatKeyPress(el, evt) {
          var charCode = (evt.which) ? evt.which : event.keyCode;
          var number = el.value.split('.');
          if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
          }
          //just one dot
          if(number.length>1 && charCode == 46){
            return false;
          }
          //get the carat position
          var caratPos = getSelectionStart(el);
          var dotPos = el.value.indexOf(".");
          if( caratPos > dotPos && dotPos>-1 && (number[1].length > 1)){
            return false;
          }
          return true;
        }

          //thanks: http://javascript.nwbox.com/cursor_position/
          function getSelectionStart(o) {
             if (o.createTextRange) {
                 var r = document.selection.createRange().duplicate()
                   r.moveEnd('character', o.value.length)
                     if (r.text == '') return o.value.length
                       return o.value.lastIndexOf(r.text)
                      } else return o.selectionStart
                    }
        </script>



        <script type="text/javascript">
        var vm= new Vue
        ({
          el: '#contenidos',
          created: function()
          {

          },
          ready: function ()
          {
              this.getInformationRequest();
              this.getBalances();

          },
          data:
          {
              idRequest: ${idRequest},
              infoSolicitud: {},
              infoAccountsPayable: {},
              infoAutorization: {},
              infoProvider: {},
              infoProviderContact: {},
              allBalances: {},
              balance: ''

          },
          methods:
          {
              showReprogramarModal: function()
              {
                  $("#reprogramarModal").modal('show');
                  this.timePickerReprogramar = $('#datereprogramar').datetimepicker({
                    locale: 'es',
                    format: 'DD-MM-YYYY',
                    useCurrent: false,
                    minDate: moment().add(1, 'minutes')
                    }).data();
              },
              showPayOfBill: function()
              {
                  $("#payModal").modal('show');
              },
              getInformationRequest: function(){
                  this.$http.get(ROOT_URL+"/requests/"+ this.idRequest)
                          .success(function (data)
                          {
                             this.infoSolicitud= data;
                             this.getInformationAccountsPayable();
                          });
              },
              getInformationAccountsPayable: function(){
                  this.$http.get(ROOT_URL+"/accounts-payable/folio?folio="+this.infoSolicitud.folio).
                  success(function(data)
                  {
                      this.infoAccountsPayable = data;
                      this.getAutorizations();
                  }).error(function(data)
                  {
                    showAlert("No se ha podido obtener la información de cuenta por pagar");
                  });
              },
              getAutorizations: function(){

                  this.$http.get(ROOT_URL+"/folios?folio="+this.infoSolicitud.folio).
                  success(function(data)
                  {
                     this.infoAutorization= data;
                     this.getProviderInformation();
                  }).error(function(data)
                  {
                    showAlert("No se ha podido obtener la información de autorizaciones");
                  });
              },
              getProviderInformation: function(){
                  this.$http.get(ROOT_URL+"/estimations/request/"+this.idRequest+"/authorized").
                  success(function(data)
                  {
                      this.infoProvider= data;
                      this.getProviderContactInformation();
                  }).error(function(data)
                  {
                    showAlert("No se ha podido obtener la informacion de la cotizacion autorizada");
                  });
              },
              getProviderContactInformation: function()
              {
                  this.$http.get(ROOT_URL+"/provider-contact/provider/"+this.infoProvider.account.providersAccountsList[0].idProvider).
                  success(function(data)
                  {
                      this.infoProviderContact = data;
                  }).error(function(data)
                  {
                    showAlert("Ha fallado el registro de su informacion, intente nuevamente");
                  });
              },
              redireccionar: function(){
                  window.location.href=ROOT_URL+"/siad/accounts-payables"
              },
              savePayOfBill: function(){
                  var transaction= {
                      amount: 0,
                      idBalance: 0,
                      idCurrency: 1,
                      transactionNumber: 1
                  }

                  transaction.amount = this.infoAccountsPayable[0].amount;
                  transaction.idBalance = this.balance.idBalance;

                  this.$http.post(ROOT_URL+"/accounts-payable/pay-account/"+this.infoAccountsPayable[0].idAccountPayable, JSON.stringify(transaction)).
                  success(function(data)
                  {
                      showAlert("Bien");
                  }).error(function(data)
                  {
                      showAlert("Mal");
                  });


              },
              getBalances: function(){
                  var self= this;
                  this.$http.get(ROOT_URL+"/balances")
                          .success(function (data)
                          {
                             this.allBalances = data;
                             this.allBalances.forEach(function(element)
                             {
                                 self.balance= element;
                             });
                          });
              }

          },
        filters:
          {
              deleteDot: function(param){
                  console.log(param);
                  var regreso;
                  regreso= param.replace(/:/g, " ");
                  return regreso;
              }

          }
        });

        </script>
    </jsp:attribute>

    <jsp:body>
      <div id="contenidos">

       <div class="container-fluid">
         <div class="row">
           <div class="col-xs-12 text-left">
             <h1>Cuenta por pagar</h1>
           </div>
           <br>
             <div class="row">

                <div class="col-xs-12">

                    <div class="panel panel-default">  <!-- Panel Informacion del Proveedor -->
                      <div class="panel-heading">
                        <h3 class="panel-title">Información del proveedor</h3>
                      </div>
                      <div class="panel-body">

                          <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-3">
                                  <label>
                                      Nombre/Razon Social
                                  </label>
                                  <p class="underline">
                                    {{ infoProvider.account.providersAccountsList[0].provider.providerName}}
                                  </p>
                                </div>
                                <div class="col-xs-3">
                                  <label>
                                      RFC
                                  </label>
                                  <p class="underline">
                                    {{infoProvider.account.providersAccountsList[0].provider.rfc}}
                                  </p>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Días de crédito
                                    </label>
                                    <p class="underline">
                                      {{infoProvider.account.providersAccountsList[0].provider.creditDays}}
                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Día de corte
                                    </label>
                                    <p class="underline">
                                      {{infoProvider.account.providersAccountsList[0].provider.cuttingDate}}
                                    </p>
                                </div>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-3">
                                    <label>
                                        Nombre de contacto
                                    </label>

                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Puesto
                                    </label>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Teléfono
                                    </label>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Correo
                                    </label>
                                </div>
                            </div>
                          </div>

                          <div class="row" v-for="providerContact in infoProviderContact">
                            <div class="col-xs-12">
                                <div class="col-xs-3">
                                    {{providerContact.name}}
                                </div>
                                <div class="col-xs-3">
                                    {{providerContact.post}}
                                </div>
                                <div class="col-xs-3">
                                    {{providerContact.phoneNumber}}
                                </div>
                                <div class="col-xs-3">
                                    {{providerContact.email}}
                                </div>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-3">
                                    <p class="underline">

                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <p class="underline">

                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <p class="underline">

                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <p class="underline">

                                    </p>
                                </div>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-3">
                                    <label>
                                        Banco
                                    </label>
                                    <p class="underline">
                                      {{infoProvider.account.bank.acronyms}}
                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Numero de Cuenta
                                    </label>
                                    <p class="underline">
                                      {{infoProvider.account.accountNumber}}
                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        CLABE
                                    </label>
                                    <p class="underline">
                                      {{infoProvider.account.accountClabe}}
                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Moneda
                                    </label>
                                    <p class="underline">
                                      {{infoProvider.currency.currency}}
                                    </p>
                                </div>
                            </div>
                          </div>

                      </div>

                    </div>    <!-- Panel Informacion del Proveedor -->


                    <div class="panel panel-default">  <!-- Panel Informacion del solicitante -->
                      <div class="panel-heading">
                        <h3 class="panel-title">Información del solicitante</h3>
                      </div>
                      <div class="panel-body">

                          <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-3">
                                    <label>
                                        Nombre
                                    </label>
                                    <p class="underline">
                                      {{infoSolicitud.userRequest.dwEmployee.employee.fullName}}
                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Puesto
                                    </label>
                                    <p class="underline">
                                      {{infoSolicitud.userRequest.dwEmployee.role.roleName}}
                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Empresa
                                    </label>
                                    <p class="underline">
                                      {{infoSolicitud.userRequest.dwEmployee.dwEnterprise.distributor.acronyms}}
                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Región
                                    </label>
                                    <p class="underline">
                                      {{infoSolicitud.userRequest.dwEmployee.dwEnterprise.region.regionName}}
                                    </p>
                                </div>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-3">
                                    <label>
                                        Sucursal
                                    </label>
                                    <p class="underline">
                                      {{infoSolicitud.userRequest.dwEmployee.dwEnterprise.branch.branchShort}}
                                    </p>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Área
                                    </label>
                                    <p class="underline">
                                      {{infoSolicitud.userRequest.dwEmployee.dwEnterprise.area.areaName}}
                                    </p>
                                </div>
                            </div>
                          </div>

                      </div>

                  </div>    <!-- Panel Informacion del solicitante -->

                  <div class="panel panel-default">  <!-- Panel Informacion del solicitud -->
                    <div class="panel-heading">
                      <h3 class="panel-title">Información de Solicitud</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                          <div class="col-xs-12">
                              <div class="col-xs-3">
                                  <label>
                                      Concepto
                                  </label>
                                  <div v-for="informacion in infoSolicitud.requestProductsList">
                                         {{informacion.product.product}}
                                  </div>
                              </div>
                              <div class="col-xs-3">
                                  <label>
                                      Monto
                                  </label>
                                  <p class="underline">
                                    $ {{infoAccountsPayable[0].amount}}
                                  </p>
                              </div>
                              <div class="col-xs-3">
                                  <label>
                                      Número de pago
                                  </label>
                                  <p class="underline">
                                    {{infoAccountsPayable[0].payNum}}
                                  </p>
                              </div>
                              <div class="col-xs-3">
                                  <label>
                                      Fecha de pago
                                  </label>
                                  <p class="underline">
                                    {{infoAccountsPayable[0].dueDateFormats.dateNumber}}
                                  </p>
                              </div>
                          </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-12">
                                    <label>
                                        Descripción de la Solicitud
                                    </label>
                                    <p class="underline">
                                      {{ infoSolicitud.description}}
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-3">
                                  <label>
                                      Autorizó
                                  </label>
                                  <p class="underline">
                                    {{infoAutorization.authorizations[0].users.dwEmployee.employee.fullName}}
                                  </p>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Puesto
                                    </label>
                                    <p class="underline">
                                      {{infoAutorization.authorizations[0].users.dwEmployee.role.roleName}}
                                    </p>
                                </div>
                                <div class="col-xs-2">
                                    <label>
                                        Fecha de autorización
                                    </label>
                                    <p class="underline">
                                      {{infoAutorization.authorizations[0].authorizationDateFormats.dateNumber}}
                                    </p>
                                </div>
                                <div class="col-xs-4">

                                    <div class="col-xs-5">
                                      <button class="btn btn-info" name="button" @click="showReprogramarModal" style="margin-top:15px">
                                          Reprogramar
                                      </button>
                                    </div>
                                    <div class="col-xs-3">
                                        <button class="btn btn-success" name="button" @click="showPayOfBill" style="margin-top:15px">
                                            Pagar
                                        </button>
                                    </div>
                                    <div class="col-xs-4">
                                        <button class="btn btn-default" name="button" @click="redireccionar" style="margin-top:15px">
                                            Cancelar
                                        </button>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>    <!-- Panel Informacion de solicitud -->

                </div>



             </div>

         </div>


       </div>

       <div class="modal fade" id="reprogramarModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
         <div class="modal-dialog">
           <div class="modal-content">
             <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
               <h4 class="modal-title" id="">Reprogramar fecha de pago</h4>
             </div>
             <div class="modal-body">
                 <div class="row">
                     <div class="col-xs-offset-4 col-xs-4">
                         <div class="form-group">
                         <div class='input-group date' id='datereprogramar'>
                             <input type='text' class="form-control">
                             <span class="input-group-addon">
                                 <span class="glyphicon glyphicon-calendar"></span>
                             </span>
                         </div>
                         </div>
                     </div>
                 </div>

             </div>
             <div class="modal-footer">
               <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
               <button type="button" class="btn btn-success">Guardar</button>
             </div>
           </div>
         </div>
       </div>

       <div class="modal fade" id="payModal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
         <div class="modal-dialog modal-lg">
           <div class="modal-content">
             <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
               <h4 class="modal-title" id="">Pagar</h4>
             </div>
             <div class="modal-body">

                 <div class="row">
                   <div class="col-xs-3">
                     <label>
                         Concepto
                     </label>
                     <div v-for="informacion in infoSolicitud.requestProductsList">
                            {{informacion.product.product}}
                     </div>
                   </div>
                   <div class="col-xs-3">
                     <label>
                         Monto
                     </label>
                     <p>
                     $ {{infoAccountsPayable[0].amount}}
                     </p>
                   </div>
                   <div class="col-xs-3">
                     <label>
                         Nombre/Razon Social
                     </label>
                     <p>
                     {{ infoProvider.account.providersAccountsList[0].provider.providerName}}
                     </p>
                   </div>
                   <div class="col-xs-3">
                     <label>
                         CLABE
                     </label>
                     <p>
                      {{infoProvider.account.accountClabe}}
                     </p>
                   </div>
                 </div>
                 <br>

                 <form>
                    <div class="row">
                      <div class="col-xs-12 text-left">
                        <label>
                            Documentos
                        </label>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-xs-12">
                          <div class="col-xs-4">
                            <label>
                                Factura PDF
                            </label>
                          </div>
                          <div class="col-xs-8">
                              <input type="file" class="form-control">
                          </div>
                          <br>
                          <div class="col-xs-4">
                              <label>
                                Factura XML
                              </label>
                          </div>
                          <div class="col-xs-8">
                              <input type="file" class="form-control">
                          </div>
                          <br>
                          <div class="col-xs-4">
                              <label>
                                Comprobante de Pago
                              </label>
                          </div>
                          <div class="col-xs-8">
                              <input type="file" class="form-control">
                          </div>
                          <br>
                          <div class="col-xs-4">
                              <label>
                                Otro
                              </label>
                          </div>
                          <div class="col-xs-8">
                              <input type="file" class="form-control">
                          </div>
                      </div>
                    </div>

                 </form>

             </div>
             <div class="modal-footer">
               <button type="button" class="btn btn-success" @click="savePayOfBill">Pagar</button>
               <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
             </div>
           </div>
         </div>
       </div>

      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
