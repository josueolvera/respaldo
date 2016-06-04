<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />


<t:template pageTitle="BID Group: Presupuestos">
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

          },
          data:
          {
            timePickerReporteInicial: '',
            timePickerReporteFinal: '',
            timePickercuentaspagadasInicial: '',
            timePickercuentaspagadasFinal: ''
          },
          methods:
          {

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
                             <input type="text" class="form-control">
                           </div>
                         </div>
                         <div class="col-xs-3">
                           <button class="btn btn-default"><span class="glyphicon glyphicon-floppy-disk"></span></button>
                         </div>
                       </div>
                       <div class="col-xs-2">
                         <label>
                           Flujo de efectivo al dia
                         </label>
                         <br>
                           <div class="input-group">
                             <span class="input-group-addon">$</span>
                             <input type="text" class="form-control" disabled="true">
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



               </div>
             </div>

         </div>


       </div>

      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
