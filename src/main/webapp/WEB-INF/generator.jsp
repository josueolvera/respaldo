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
              this.getFieldsTableSales();
          },
          ready: function ()
          {


          },
          data:
          {
              fieldsTableSale: {},
              fieldsTableChecked: []


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
                           </div>
                           <div class="col-xs-3">
                               <label>
                                   Fecha inicial
                               </label>
                           </div>
                           <div class="col-xs-3">
                               <label>
                                   Fecha final
                               </label>
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
