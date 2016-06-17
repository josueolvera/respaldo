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

          },
          ready: function ()
          {


          },
          data:
          {
              fieldsTableSales: [
                  {
                    id: 1,
                    columnName: 'Clave Sap'
                  },
                  {
                    id: 2,
                    columnName: 'RFC'
                  }
              ],
              fieldSelected: ''

          },
          methods:
          {
              pushField: function(){
                  
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
               <h1>Motor de Reglas para calculo SISCOM</h1>

               <div class="row">
                 <div class="col-xs-6">
                   <div class="panel panel-default">
                     <div class="panel-heading">
                       <h3 class="panel-title">Busquedas</h3>
                     </div>
                     <div class="panel-body">
                         <div class="row">
                           <div class="col-xs-12">
                              <label>
                                  Selecciona las columnas que deseas utilizar
                              </label>
                           </div>
                         </div>

                         <div class="row">
                           <div class="col-xs-6">
                             <select class="form-control" v-model="fieldSelected">
                                 <option v-for= "fields in fieldsTableSales" :value="fields">
                                     {{fields.columnName}}
                                 </option>
                             </select>
                           </div>
                           <div class="col-xs-6 text-left">
                               <button type="button" class="btn btn-default"
                                   title="Agregar" @click="pushField">
                                   <span class="glyphicon glyphicon-plus">
                                   </span>
                               </button>
                           </div>
                         </div>

                         <div class="row">
                           <div class="col-xs-12">
                             <table class="table table-responsive">
                                 <thead>
                                     <th>
                                         #
                                     </th>
                                     <th>
                                         Campo
                                     </th>
                                     <th>
                                         Eliminar
                                     </th>
                                 </thead>
                                 <tbody>
                                     <tr v-for="fields in fieldsSelected">
                                         <td>
                                             {{index}}
                                         </td>
                                         <td>
                                             {{fields.columnName}}
                                         </td>
                                         <td>
                                             <span class="glyphicon glyphicon-remove">
                                             </span>
                                         </td>
                                     </tr>
                                 </tbody>
                             </table>
                           </div>
                         </div>

                     </div>
                   </div>
                 </div>

                 <div class="col-xs-6">
                   <div class="panel panel-default">
                     <div class="panel-heading">
                       <h3 class="panel-title">Operaciones</h3>
                     </div>
                     <div class="panel-body">

                     </div>
                   </div>
                 </div>
               </div>

               <div class="row">
                 <div class="col-xs-6">
                   <div class="panel panel-default">
                     <div class="panel-heading">
                       <h3 class="panel-title">Reglas</h3>
                     </div>
                     <div class="panel-body">

                     </div>
                   </div>
                 </div>

                 <div class="col-xs-6">
                   <div class="panel panel-default">
                     <div class="panel-heading">
                       <h3 class="panel-title">Condiciones</h3>
                     </div>
                     <div class="panel-body">

                     </div>
                   </div>
                 </div>
               </div>

               <pre>
                   {{$data | json}}
               </pre>

           </div>
      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
