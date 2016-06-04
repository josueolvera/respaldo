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

          },
          data:
          {

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
                                    Ruben Andrade
                                  </p>
                                </div>
                                <div class="col-xs-3">
                                  <label>
                                      RFC
                                  </label>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Días de crédito
                                    </label>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Día de corte
                                    </label>
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

                          <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-3">
                                    <label>
                                        Banco
                                    </label>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Numero de Cuenta
                                    </label>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        CLABE
                                    </label>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Moneda
                                    </label>
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
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Puesto
                                    </label>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Empresa
                                    </label>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Región
                                    </label>
                                </div>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-3">
                                    <label>
                                        Sucursal
                                    </label>
                                </div>
                                <div class="col-xs-3">
                                    <label>
                                        Área
                                    </label>
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
                              </div>
                              <div class="col-xs-3">
                                  <label>
                                      Monto
                                  </label>
                              </div>
                              <div class="col-xs-3">
                                  <label>
                                      Número de pago
                                  </label>
                              </div>
                              <div class="col-xs-3">
                                  <label>
                                      Fecha de pago
                                  </label>
                              </div>
                          </div>
                        </div>

                        <div class="row">
                          <div class="col-xs-12">
                              <label>
                                  Descripción de la Solicitud
                              </label>
                             <textarea class="form-control" rows="3" cols="40"></textarea>
                          </div>
                        </div>

                        <div class="row">
                          <div class="col-xs-3">
                            <label>
                                Autorizó
                            </label>
                          </div>
                          <div class="col-xs-3">
                              <label>
                                  Puesto
                              </label>

                          </div>
                          <div class="col-xs-2">
                              <label>
                                  Fecha de autorización
                              </label>

                          </div>
                          <div class="col-xs-4">

                              <div class="col-xs-5">
                                <button class="btn btn-default" name="button">
                                    Reprogramar
                                </button>
                              </div>
                              <div class="col-xs-3">
                                  <button class="btn btn-default" name="button">
                                      Pagar
                                  </button>
                              </div>
                              <div class="col-xs-4">
                                  <button class="btn btn-default" name="button">
                                      Cancelar
                                  </button>
                              </div>

                          </div>
                        </div>

                    </div>

                </div>    <!-- Panel Informacion de solicitud -->

                </div>



             </div>

         </div>


       </div>

      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
