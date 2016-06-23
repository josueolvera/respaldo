<%--
    Document   : generator
    Created on : 16/06/2016, 01:42:32 PM
    Author     : rubens
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />


<t:template pageTitle="BID Group: Generador de Reglas de Negocio">
    <jsp:attribute name="styles">
        <style>

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
              this.getFieldsTableSales();
              this.getDistributors();
              this.getOperacionsSql();

              this.timePickerReporteInicial = $('#datefechainicial').datetimepicker({
                locale: 'es',
                format: 'DD-MM-YYYY',
                useCurrent: false,
                maxDate: fecha_actual
                }).data();
          },
          ready: function ()
          {


          },
          data:
          {
              fieldsTableSale: {},
              fieldsTableChecked: [],
              distributorsSelect: {},
              timePickerReporteInicial: '',
              reporteFechaInicial: '',
              reportefechaFinal: '',
              timePickerReporteFinal: '',
              rules: [],
              operationsSql: {},
              operationData: '',
              fieldData: '',
              alias: ''
          },
          methods:
          {
              getFieldsTableSales: function(){
                  this.$http.get(ROOT_URL+"/fields-table-sales")
                          .success(function (data)
                          {
                              this.fieldsTableSale = data;
                          });
              },
              queryGenerator: function(){
                  if (this.fieldsTableChecked.length > 0) {
                    var cadena= "SELECT "

                    this.fieldsTableChecked.forEach(function(elemento){
                        cadena += elemento.fieldName + ",";
                    });

                    cadena = cadena.slice(0, -1);

                    cadena += " FROM SAP_SALES";

                    console.log(cadena);
                  }
                  else{
                      showAlert("Debes seleccionar al menos un campo para el calculo");
                  }
              },
              getDistributors: function(){
                  this.$http.get(ROOT_URL+"/distributors/agreement")
                          .success(function (data)
                          {
                              this.distributorsSelect = data;
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
              activarTimePickerReporteFechaFinal: function(){

                  var fecha = new Date();
                  var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth()+1) + "-" + fecha.getDate();
                  var fecha= moment(this.timePickerReporteInicial.date, 'DD-MM-YYYY').format('YYYY-MM-DD');
                  this.timePickerReporteFinal = $('#datefechafinal').datetimepicker({
                    locale: 'es',
                    format: 'DD-MM-YYYY',
                    useCurrent: false,
                    minDate: fecha,
                    maxDate: fecha_actual
                    }).data();
              },
              getOperacionsSql: function(){
                  this.$http.get(ROOT_URL+"/sql-operations")
                          .success(function (data)
                          {
                              this.operationsSql = data;
                          });
              },
              addOperation: function(){
                  var operation= {
                      fieldName: '',
                      fieldUser: '',
                      viewInTable: 1
                  }

                  operation.fieldName = this.operationData.sqlText + "("+ this.fieldData.fieldName + ")";
                  operation.fieldUser= this.alias;

                  this.fieldsTableChecked.push(operation);

              },
              removeOperation: function(operation){
                  this.fieldsTableChecked.$remove(operation);
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
               <h1>Motor de reglas para cálculo SISCOM</h1>

               <div class="row">
                 <div class="col-xs-12">
                   <div class="panel panel-default">
                     <div class="panel-heading">
                       <h3 class="panel-title">Criterios de busqueda</h3>
                     </div>
                     <div class="panel-body">
                         <div class="row">
                           <div class="col-xs-3">
                              <label>
                                  Distribuidor
                              </label>
                              <select class="form-control" name="">
                                  <option></option>
                                  <option v-for="distributor in distributorsSelect" value="{{distributor}}">
                                  {{distributor.acronyms}}
                                  </option>
                              </select>
                           </div>
                           <div class="col-xs-3">
                               <label>
                                   Fecha inicial
                               </label>
                               <div class="form-group">
                               <div class='input-group date' id='datefechainicial'>
                                   <input type='text' class="form-control" v-model="reporteFechaInicial">
                                   <span class="input-group-addon" @click="destruirTimePickerPrimero">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                               </div>
                               </div>
                           </div>
                           <div class="col-xs-3">
                               <label>
                                   Fecha final
                               </label>
                               <div class="form-group">
                               <div class='input-group date' id='datefechafinal'>
                                   <input type='text' class="form-control" v-model="reportefechaFinal">
                                   <span class="input-group-addon" @click="activarTimePickerReporteFechaFinal">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                               </div>
                               </div>
                           </div>
                         </div>
                     </div>
                   </div>
                 </div>
               </div>

               <div class="row">
                 <div class="col-xs-12">
                   <div class="panel panel-default">
                     <div class="panel-heading">
                       <h3 class="panel-title">Busquedas</h3>
                     </div>
                     <div class="panel-body">
                         <div class="row">
                           <div class="col-xs-12">
                              <label>
                                  Selecciona los campos que deseas utilizar
                              </label>
                           </div>
                         </div>

                         <div class="row">
                           <div class="col-xs-3" v-for="field in fieldsTableSale">
                                       <input type="checkbox" value="{{field}}" v-model="fieldsTableChecked" >
                                       <span>{{field.fieldUser}}</span>
                           </div>
                         </div>

                     </div>
                   </div>
                 </div>

               </div>

               <div class="row">
                   <div class="col-xs-12">
                     <div class="panel panel-default">
                       <div class="panel-heading">
                         <h3 class="panel-title">Operaciones</h3>
                       </div>
                       <div class="panel-body">
                           <div class="row">
                             <div class="col-xs-12">
                               <label>
                                   Seleccione el tipo de operacion que desea realizar, el campo que necesita y nombre a su funcion.
                               </label>
                             </div>
                           </div>
                           <div class="row">
                            <div class="col-xs-3">
                                <select class="form-control" v-model="operationData">
                                    <option></option>
                                    <option v-for="operation in operationsSql" value="{{operation}}"
                                        v-if="operation.idSqlFunctionsCategories == 2">
                                        {{operation.userText}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <select class="form-control" v-model="fieldData">
                                    <option></option>
                                    <option v-for="field in fieldsTableChecked" value="{{field}}">
                                        {{field.fieldUser}}
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <input class="form-control" v-model="alias">
                            </div>

                            <div class="col-xs-3">
                              <button class="btn btn-default" name="button" @click="addOperation">
                                  Agregar Operacion
                              </button>
                            </div>
                           </div>

                           <div class="row">
                             <div class="col-xs-4">
                                 <table class="table table-striped">
                                     <thead>
                                         <th>
                                             Nombre de la Operación
                                         </th>
                                         <th>
                                             Eliminar Operación
                                         </th>
                                     </thead>
                                     <tbody>
                                         <tr v-for="field in fieldsTableChecked" v-if="field.viewInTable == 1">
                                             <td>
                                                 {{field.fieldUser}}
                                             </td>
                                             <td>
                                                 <button class="btn btn-danger" @click="removeOperation(field)">
                                                     <span class="glyphicon glyphicon-trash">
                                                     </span>
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

               <div class="row">
                 <div class="col-xs-12">
                   <div class="panel panel-default">
                     <div class="panel-heading">
                       <h3 class="panel-title">Reglas</h3>
                     </div>
                     <div class="panel-body">

                     </div>
                   </div>
                 </div>
               </div>

               <div class="row">
                 <div class="col-xs-12">
                   <div class="panel panel-default">
                     <div class="panel-heading">
                       <h3 class="panel-title">Condiciones</h3>
                     </div>
                     <div class="panel-body">

                     </div>
                   </div>
                 </div>
               </div>

               <div class="row">
                   <div class="col-xs-12 text-right">
                       <button type="button" class="btn btn-info" name="button" @click="queryGenerator">
                           Generar
                       </button>
                   </div>
               </div>
           </div>

      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
