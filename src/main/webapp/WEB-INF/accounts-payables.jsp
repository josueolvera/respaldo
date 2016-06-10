<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />


<t:template pageTitle="BID Group: Presupuestos">
    <jsp:attribute name="styles">
        <style>
            #content {
                margin-top: 1.5rem;
            }
            .loader {
                margin-top: 2rem;
            }
            .notification.expanded {
                margin-top: 1.5rem;
                margin-bottom: 1.5rem;
            }
            .notifications-list {
                margin: 2rem 0;
            }
            .notifications-list h4 {
                margin-left: 2rem;
            }
            .notifications-group {
                margin-bottom: 2rem;
            }
            .notifications-group :last-child .card {
                border-bottom: 1px solid #c8c8c8;
            }
            .notification-details {
                padding: 2rem;
                border: 1px solid #c8c8c8;
                background: #FFFFFF;
                box-shadow: 0 -1px 0 #e5e5e5, 0 0 2px rgba(0,0,0,.12), 0 2px 4px rgba(0,0,0,.24);
            }
            .card-body {
                cursor: pointer;
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
            this.timePickerReporteInicial = $('#datefechainicial').datetimepicker({
              locale: 'es',
              format: 'DD-MM-YYYY',
              useCurrent: false,
              minDate: moment().add(1, 'minutes')
              }).data();

            this.timePickerReporteFinal = $('#datefechafinal').datetimepicker({
              locale: 'es',
              format: 'DD-MM-YYYY',
              useCurrent: false,
              minDate: moment().add(1, 'minutes')
              }).data();

              this.timePickercuentaspagadasInicial = $('#datecuentasinicial').datetimepicker({
                locale: 'es',
                format: 'DD-MM-YYYY',
                useCurrent: false,
                minDate: moment().add(1, 'minutes')
                }).data();

                this.timePickercuentaspagadasFinal = $('#datecuentasfinal').datetimepicker({
                  locale: 'es',
                  format: 'DD-MM-YYYY',
                  useCurrent: false,
                  minDate: moment().add(1, 'minutes')
                  }).data();

          },
          ready: function ()
          {
              this.getAccountsPayableofActualDay();
              this.getToday();
              this.getBalances();

          },
          data:
          {
            timePickerReporteInicial: '',
            timePickerReporteFinal: '',
            timePickercuentaspagadasInicial: '',
            timePickercuentaspagadasFinal: '',
            accountsPayablesofDay: {},
            today: '',
            allBalances: {},
            balance: '',
            transaction: {
                amount: '',
                idBalance: '',
                idCurrency: '',
                transactionNumber: 1
            },
            url: ROOT_URL+"/siad/accounts-payable-info/"
          },
          methods:
          {
              getAccountsPayableofActualDay: function(){
                  var self= this;
                  this.$http.get(ROOT_URL+"/accounts-payable/now")
                          .success(function (data)
                          {
                             this.accountsPayablesofDay= data;
                             this.accountsPayablesofDay.forEach(function(element)
                             {
                                 var folio = element.folio;

                                 self.$http.get(ROOT_URL+"/requests/folio?folio="+folio).
                                 success(function(data)
                                 {
                                     Vue.set(element, "informationRequest", data);
                                 }).error(function(data)
                                 {
                                   showAlert("Ha fallado el registro de su informacion, intente nuevamente");
                                 });


                             });
                          });
              },
              getToday: function(){
                  var dias_semana = new Array("Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sabado");
                  var meses = new Array ("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre", "Diciembre");
                  var fecha_actual = new Date();
                  this.today = dias_semana[fecha_actual.getDay()] + " " +
                               fecha_actual.getDate() + ", " + meses[fecha_actual.getMonth()] + " " + fecha_actual.getFullYear();
              },
              getInformationRequest: function(idRequest)
              {
                  location.href= ROOT_URL+"/siad/accounts-payable-info/"+idRequest ;
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
              },
              saveTransaction: function(){
                  if (this.transaction.amount <= 0)
                  {
                    showAlert("No puedes ingresar cantidades negativas");
                    this.transaction.amount= '';
                  }
                  else {
                     this.transaction.idBalance =  this.balance.idBalance;
                     this.transaction.idCurrency = this.balance.currencies.idCurrency;

                     this.$http.post(ROOT_URL+"/transactions/entry-pay-account", JSON.stringify(this.transaction))
                             .success(function (data)
                             {
                                showAlert("Abono realizado correctamente");
                                this.transaction.amount= '';
                                this.getBalances();
                             }).error(function(data)
                             {
                                 showAlert("Ha fallado el abono a la cuenta");
                                 this.transaction.amount= '';
                             });
                   }
              }

          },
        filters:
          {

          }
        });

        </script>
    </jsp:attribute>

    <jsp:body>
      <div id="contenidos">

       <div class="container-fluid">
         <div class="row">
           <div class="col-xs-12 text-left">
             <h1>Cuentas por pagar</h1>
           </div>
           <br>
             <div class="row">
               <div class="col-xs-12">
                 <div class="panel panel-default">
                   <div class="panel-heading">
                     <h3 class="panel-title">Flujo de efectivo</h3>
                   </div>
                   <div class="panel-body">
                     <div class="col-xs-12">
                       <div class="col-xs-3">
                         <label>
                           Ingresar
                         </label>
                         <br>
                         <div class="col-xs-9">
                           <div class="input-group">
                             <span class="input-group-addon">$</span>
                             <input number class="form-control" v-model="transaction.amount">
                           </div>
                         </div>
                         <div class="col-xs-3">
                           <button class="btn btn-default" @click="saveTransaction"><span class="glyphicon glyphicon-floppy-disk"></span></button>
                         </div>
                       </div>
                       <div class="col-xs-2">
                         <label>
                           Flujo de efectivo al dia
                         </label>
                         <br>
                           <div class="input-group">
                             <span class="input-group-addon">$</span>
                             <input number class="form-control" disabled="true" v-model="balance.currentAmount">
                           </div>
                       </div>
                       <div class="col-xs-6">
                         <label>
                           Generar reporte
                         </label>
                         <br>
                         <div class="col-xs-6">
                             <div class="col-xs-2">
                               <span>De</span>
                             </div>
                             <div class="col-xs-10">
                                 <div class="form-group">
                                 <div class='input-group date' id='datefechainicial'>
                                     <input type='text' class="form-control">
                                     <span class="input-group-addon">
                                         <span class="glyphicon glyphicon-calendar"></span>
                                     </span>
                                 </div>
                                 </div>
                             </div>
                         </div>

                         <div class="col-xs-6">
                             <div class="col-xs-2">
                               <span>a</span>
                             </div>
                             <div class="col-xs-10">
                                 <div class="form-group">
                                 <div class='input-group date' id='datefechafinal'>
                                     <input type='text' class="form-control">
                                     <span class="input-group-addon">
                                         <span class="glyphicon glyphicon-calendar"></span>
                                     </span>
                                 </div>
                                 </div>
                             </div>
                         </div>
                       </div>

                       <div class="col-xs-1">
                         <button class="btn btn-default" name="button" style="margin-top: 25px">
                             <span class="glyphicon glyphicon-list-alt">
                             </span>
                         </button>
                       </div>
                     </div>
                   </div>
               </div>     <!-- Panel Flujo de efectivo -->

               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h3 class="panel-title">Cuentas pagadas</h3>
                 </div>
                 <div class="panel-body">
                     <div class="row">
                       <div class="col-xs-12 text-left">
                         <label>
                             Generar reporte
                         </label>
                       </div>
                     </div>

                     <div class="row">
                       <div class="col-xs-6">
                           <div class="col-xs-1">
                             <span>De</span>
                           </div>
                           <div class="col-xs-5">
                               <div class='input-group date' id='datecuentasinicial'>
                                   <input type='text' class="form-control">
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                               </div>
                           </div>
                           <div class="col-xs-1">
                             <span>a</span>
                           </div>
                           <div class="col-xs-5">
                               <div class='input-group date' id='datecuentasfinal'>
                                   <input type='text' class="form-control">
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                               </div>
                           </div>
                       </div>

                       <div class="col-xs-1">
                           <button class="btn btn-default" name="button">
                               <span class="glyphicon glyphicon-list-alt">
                               </span>
                           </button>
                       </div>

                     </div>
                 </div>
             </div>  <!-- Panel Cuentas Pagadas -->

             <div class="row">
                 <div class="col-xs-12">
                   <h1>Hoy &nbsp<small>{{ today }}</small></h1>
                 </div>
             </div>

             <div class="row notification" v-for="accountPayable in accountsPayablesofDay">
                 <div class="card card-inline clearfix">
                     <div class="card-body clearfix">
                                       <div class="card-image">
                                         <span class="badge">CPP</span>
                                       </div>

                                        <div class="card-title">
                                            <label>
                                                {{accountPayable.informationRequest.requestTypeProduct.productType.productType}}
                                            </label>
                                        </div>

                                       <div class="card-subtitle">
                                           <label>
                                               Fecha de pago - {{accountPayable.dueDateFormats.dateNumber }}
                                           </label>
                                       </div>


                                       <div class="card-text">
                                           <label>
                                               Monto -$ {{ accountPayable.amount}}
                                           </label>
                                       </div>

                     </div> <%--div card-body clearfix --%>
                     <div class="card-actions">
                         <div class="col-xs-8">
                             <span class="label label-success">Hoy</span>
                         </div>
                         <div class="col-xs-4">
                            <a :href="url+accountPayable.informationRequest.idRequest">
                                <span class="glyphicon glyphicon-new-window">
                                </span>
                            </a>
                         </div>
                     </div>
                 </div> <%--div clearfix --%>
         </div> <%--div notification --%>

               </div>
             </div>
         </div>
       </div>
       <%-- <pre>
           {{$data.transaction | json}}
       </pre> --%>
      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
