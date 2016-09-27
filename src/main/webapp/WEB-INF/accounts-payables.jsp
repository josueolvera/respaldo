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
                cursor: auto;
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
                    var fecha = new Date();
                    var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth()+1) + "-" + fecha.getDate();

                    this.timePickerReporteInicial = $('#datefechainicial').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY',
                        useCurrent: false,
                        maxDate: fecha_actual
                    }).data();

                    this.timePickercuentaspagadasInicial = $('#datecuentasinicial').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY',
                        useCurrent: false,
                        maxDate: fecha_actual
                    }).data();

                    this.timePickercuentasporpagarInicial = $('#datecuentasporpagarinicial').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY',
                        useCurrent: false,
                        minDate: fecha_actual
                    }).data();


                },
                ready: function ()
                {
                    this.getAccountsPayableofActualDay();
                    this.getToday();
                    this.getBalances();
                    this.getAccountsPayableReschedules();

                },
                data:
                {
                    timePickerReporteInicial: '',
                    timePickerReporteFinal: '',
                    timePickercuentaspagadasInicial: '',
                    timePickercuentaspagadasFinal: '',
                    timePickercuentasporpagarInicial: '',
                    timePickercuentasporpagarFinal: '',
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
                    url: ROOT_URL+"/siad/accounts-payable-info/",
                    accountsPayablesReschedule: {},
                    reporteflujoinicial: '',
                    reporteflujofinal: '',
                    reportecuentaspagadasinicial: '',
                    reportecuentaspagadasfinal: '',
                    reportType: 0
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
                    },
                    getAccountsPayableReschedules: function(){
                        var self= this;
                        this.$http.get(ROOT_URL+"/accounts-payable/reschedule")
                                .success(function (data)
                                {
                                    this.accountsPayablesReschedule = data;
                                    this.accountsPayablesReschedule.forEach(function(element)
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
                                }).error(function()
                        {
                        });
                    },
                    generateReportCash: function(){

                        var fechaofDate = this.reporteflujoinicial;
                        var dateformatedofDate = moment(fechaofDate, "DD-MM-YYYY").format("YYYY-MM-DD");
                        var dateDueDateofDate = new Date(dateformatedofDate);
                        var dateisoDueofDate = dateDueDateofDate.toISOString().slice(0, -1);

                        var fechauntilDate = this.reporteflujofinal;
                        var dateformateduntilDate= moment(fechauntilDate, "DD-MM-YYYY").format("YYYY-MM-DD");
                        var dateDueDateuntilDate = new Date(dateformateduntilDate);
                        var dateisoDueuntilDate = dateDueDateuntilDate.toISOString().slice(0, -1);

                        var report= {
                            ofDate: '',
                            untilDate: ''
                        };

                        report.ofDate = dateisoDueofDate;
                        report.untilDate = dateisoDueuntilDate;

                        location.href= ROOT_URL+"/transactions/report/transactions-by-date?fromDate="+report.ofDate+"&toDate="+report.untilDate;

                    },
                    activarPickerReporteFinal: function(fechainicio){
                        this.timePickerReporteFinal = '';
                        var fecha = new Date();
                        var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth()+1) + "-" + fecha.getDate();

                        var fecha= moment(fechainicio, 'DD-MM-YYYY').format('YYYY-MM-DD');

                        this.timePickerReporteFinal = $('#datefechafinal').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha,
                            maxDate: fecha_actual
                        }).data();
                    },
                    getReports: function(){

                        var fechafromDate = this.reportecuentaspagadasinicial;
                        var dateformatedfromDate = moment(fechafromDate, "DD-MM-YYYY").format("YYYY-MM-DD");
                        var dateDueDatefromDate = new Date(dateformatedfromDate);
                        var dateisoDuefromDate = dateDueDatefromDate.toISOString().slice(0, -1);

                        var fechatoDate = this.reportecuentaspagadasfinal;
                        var dateformatedtoDate= moment(fechatoDate, "DD-MM-YYYY").format("YYYY-MM-DD");
                        var dateDueDatetoDate = new Date(dateformatedtoDate);
                        var dateisoDuetoDate = dateDueDatetoDate.toISOString().slice(0, -1);


                        if (this.reportType == 2 ) {
                            location.href= ROOT_URL+"/accounts-payable/report/accounts-liquidated?fromDate="+dateisoDuefromDate+"&toDate="+dateisoDuetoDate;
                        }
                        if (this.reportType == 3 ) {
                            location.href= ROOT_URL+"/transactions/report/accounts-payable?fromDate="+dateisoDuefromDate+"&toDate="+dateisoDuetoDate;
                        }
                    },
                    activarTimePickersegundo: function(fechaprimertimepicker){
                        var fecha = new Date();
                        var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth()+1) + "-" + fecha.getDate();
                        var fecha= moment(this.timePickercuentaspagadasInicial.date, 'DD-MM-YYYY').format('YYYY-MM-DD');
                        this.timePickercuentaspagadasFinal = $('#datecuentasfinal').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha,
                            maxDate: fecha_actual
                        }).data();
                    },
                    destruirTimePickerSegundo: function(){
                        $("#datecuentasinicial").on("dp.change", function (e) {
                            $('#datecuentasfinal').data("DateTimePicker").minDate(e.date);
                        });
                        $("#datecuentasfinal").on("dp.change", function (e) {
                            $('#datecuentasinicial').data("DateTimePicker").maxDate(e.date);
                        });
                    },
                    destruirTimePickerPrimero: function(){
                        $("#datefechainicial").on("dp.change", function (e) {
                            $('#datefechafinal').data("DateTimePicker").minDate(e.date);
                        });
                        $("#datefechafinal").on("dp.change", function (e) {
                            $('#datefechainicial').data("DateTimePicker").maxDate(e.date);
                        });
                    },
                    activarTimePickerPagar: function(){
                        var fecha= moment(this.timePickercuentasporpagarInicial.date, 'DD-MM-YYYY').format('YYYY-MM-DD');
                        this.timePickercuentasporpagarFinal = $('#datecuentasporpagarfinal').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha
                        }).data();
                    },
                    destruirTimePickerCuentasPorPagar: function(){
                        $("#datecuentasporpagarinicial").on("dp.change", function (e) {
                            $('#datecuentasporpagarfinal').data("DateTimePicker").minDate(e.date);
                        });
                        $("#datecuentasporpagarfinal").on("dp.change", function (e) {
                            $('#datecuentasporpagarinicial').data("DateTimePicker").maxDate(e.date);
                        });
                    },
                    typeOfTimePickers: function(){
                        this.reportecuentaspagadasinicial= '';
                        this.reportecuentaspagadasfinal= '';
                    }

                },
                filters:
                {
                    separate: function (value) {
                        return value.replace(/:/g, " ");
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
                                    <div class="row"> <!--Encabezados del row -->
                                        <div class="col-xs-3">
                                            <label>
                                                Ingresar
                                            </label>
                                        </div>
                                        <div class="col-xs-2">
                                            <label>
                                                Flujo de efectivo al día
                                            </label>
                                        </div>
                                        <div class="col-xs-6 text-left">
                                            <label>
                                                Generar reporte
                                            </label>
                                        </div>
                                    </div>   <!-- Fin de Encabezados del row -->

                                    <div class="row">
                                        <div class="col-xs-3" style="padding-left: 0">
                                            <div class="col-xs-9">
                                                <div class="input-group">
                                                    <span class="input-group-addon">$</span>
                                                    <input number class="form-control" v-model="transaction.amount">
                                                </div>
                                            </div>
                                            <div class="col-xs-3">
                                                <button class="btn btn-default" @click="saveTransaction" data-toggle="tooltip" data-placement="bottom" title="Guardar">
                                       <span class="glyphicon glyphicon-floppy-disk">
                                       </span>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="col-xs-2">
                                            <div class="input-group">
                                                <span class="input-group-addon">$</span>
                                                <input number class="form-control" disabled="true" v-model="balance.currentAmount">
                                            </div>
                                        </div>
                                        <div class="col-xs-6">
                                            <div class="col-xs-6" style="padding-left: 0">
                                                <div class="col-xs-2" style="padding-left: 0; margin-top: 5px">
                                                    <span>De</span>
                                                </div>
                                                <div class="col-xs-10" style="padding-left: 0; padding-right: 0">
                                                    <div class="form-group">
                                                        <div class='input-group date' id='datefechainicial'>
                                                            <input type='text' class="form-control" v-model="reporteflujoinicial">
                                                            <span class="input-group-addon"  @click="destruirTimePickerPrimero(reporteflujoinicial)">
                                                 <span class="glyphicon glyphicon-calendar"></span>
                                             </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-xs-6">
                                                <div class="col-xs-2" style="padding-left: 0; margin-top: 5px">
                                                    <span>a</span>
                                                </div>
                                                <div class="col-xs-10" style="padding-left: 0">
                                                    <div class="form-group">
                                                        <div class='input-group date' id='datefechafinal'>
                                                            <input type='text' class="form-control" v-model="reporteflujofinal">
                                                            <span class="input-group-addon" @click="activarPickerReporteFinal(reporteflujoinicial)">
                                                 <span class="glyphicon glyphicon-calendar"></span>
                                             </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-xs-1" style="padding-left:0">
                                            <button class="btn btn-default" name="button"
                                                    @click="generateReportCash" data-toggle="tooltip" data-placement="bottom" title="Generar">
                                     <span class="glyphicon glyphicon-list-alt">
                                     </span>
                                            </button>
                                        </div>
                                    </div> <!-- Row -->
                                </div>
                            </div>     <!-- Panel Flujo de efectivo -->

                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Cuentas</h3>
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
                                        <div class="col-xs-3 text-left">
                                            <select class="form-control" v-model="reportType" @change="typeOfTimePickers">
                                                <option></option>
                                                <option value="2">Cuentas por pagar</option>
                                                <option value="3">Cuentas pagadas</option>
                                            </select>
                                        </div>

                                        <div class="col-xs-6" v-show="reportType == 3 ">
                                            <div class="col-xs-1" style="padding-left: 0; margin-top: 5px">
                                                <span>De</span>
                                            </div>
                                            <div class="col-xs-5"  style="padding-left: 0">
                                                <div class='input-group date' id='datecuentasinicial'>
                                                    <input type='text' class="form-control" v-model="reportecuentaspagadasinicial">
                                                    <span class="input-group-addon"  @click="destruirTimePickerSegundo(reportecuentaspagadasinicial)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                                </div>
                                            </div>
                                            <div class="col-xs-1" style="padding-left: 5; margin-top: 5px">
                                                <span>a</span>
                                            </div>
                                            <div class="col-xs-5" style="padding-left: 0">
                                                <div class='input-group date' id='datecuentasfinal'>
                                                    <input type='text' class="form-control" v-model="reportecuentaspagadasfinal">
                                                    <span class="input-group-addon" @click="activarTimePickersegundo(reportecuentaspagadasinicial)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-xs-6" v-show="reportType == 2 ">
                                            <div class="col-xs-1" style="padding-left: 0; margin-top: 5px">
                                                <span>De</span>
                                            </div>
                                            <div class="col-xs-5"  style="padding-left: 0">
                                                <div class='input-group date' id='datecuentasporpagarinicial'>
                                                    <input type='text' class="form-control" v-model="reportecuentaspagadasinicial">
                                                    <span class="input-group-addon"  @click="destruirTimePickerCuentasPorPagar(reportecuentaspagadasinicial)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                                </div>
                                            </div>
                                            <div class="col-xs-1" style="padding-left: 5; margin-top: 5px">
                                                <span>a</span>
                                            </div>
                                            <div class="col-xs-5" style="padding-left: 0">
                                                <div class='input-group date' id='datecuentasporpagarfinal'>
                                                    <input type='text' class="form-control" v-model="reportecuentaspagadasfinal">
                                                    <span class="input-group-addon" @click="activarTimePickerPagar">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-xs-1" style="padding-left: 0" v-show="reportType != 0">
                                            <button class="btn btn-default" name="button" data-toggle="tooltip" data-placement="bottom" title="Generar">
                               <span class="glyphicon glyphicon-list-alt" @click="getReports">
                               </span>
                                            </button>
                                        </div>

                                    </div>
                                </div>
                            </div>  <!-- Panel Cuentas Pagadas -->

                            <div class="row">
                                <div class="col-xs-12">
                                    <h4>Cuentas por pagar</h4>
                                </div>
                            </div>

                            <div class="row notification" v-for="accountPayable in accountsPayablesofDay">
                                <div class="col-xs-12">
                                    <div class="card card-inline clearfix">
                                        <div class="card-body clearfix">
                                            <div class="card-image">
                                                <span class="badge">CPP</span>
                                            </div>

                                            <div class="card-title">
                                                <p>
                                                    {{accountPayable.informationRequest.requestTypeProduct.productType.productType}}
                                                </p>
                                            </div>

                                            <div class="card-subtitle">
                                                <p>
                                                    Fecha de pago - {{accountPayable.dueDateFormats.dateNumber }}
                                                </p>
                                            </div>


                                            <div class="card-text">
                                                <p>
                                                    Monto -$ {{ accountPayable.amount}}
                                                </p>
                                            </div>

                                        </div> <%--div card-body clearfix --%>
                                        <div class="card-actions">
                                            <span class="label label-success">{{accountPayable.dueDateFormats.dateNumber}}</span>
                                            <a :href="url+accountPayable.informationRequest.idRequest+'/'+accountPayable.idAccountPayable" title="Ver cuenta por pagar">
                                <span class="glyphicon glyphicon-new-window">
                                </span>
                                            </a>
                                        </div>
                                    </div> <%--div clearfix --%>
                                </div> <%--div notification --%>
                            </div>

                            <div class="row">
                                <div class="col-xs-12">
                                    <h4>Reprogramadas</h4>
                                </div>
                            </div>

                                <%-- Reprogramadas --%>
                            <div class="row notification" v-for="accountPayable in accountsPayablesReschedule" v-if="accountPayable.dueDateFormats.dateTextLong !== today">
                                <div class="col-xs-12">
                                    <div class="card card-inline clearfix">
                                        <div class="card-body clearfix">
                                            <div class="card-image">
                                                <span class="badge">CPP</span>
                                            </div>

                                            <div class="card-title">
                                                <p>
                                                    {{accountPayable.informationRequest.requestTypeProduct.productType.productType}}
                                                </p>
                                            </div>

                                            <div class="card-subtitle">
                                                <p>
                                                    Fecha de pago - {{accountPayable.dueDateFormats.dateNumber }}
                                                </p>
                                            </div>


                                            <div class="card-text">
                                                <p>
                                                    Monto -$ {{ accountPayable.amount}}
                                                </p>
                                            </div>

                                        </div> <%--div card-body clearfix --%>
                                        <div class="card-actions">
                                            <span class="label label-success">{{accountPayable.dueDateFormats.dateNumber }}</span>
                                            <a :href="url+accountPayable.informationRequest.idRequest+'/'+accountPayable.idAccountPayable" title="Ver cuenta por pagar">
                            <span class="glyphicon glyphicon-new-window">
                            </span>
                                            </a>
                                        </div>
                                    </div> <%--div clearfix --%>
                                </div> <%--div notification --%>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

        </div> <!-- #contenidos -->
        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
