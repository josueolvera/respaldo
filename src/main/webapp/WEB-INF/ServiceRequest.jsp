<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Presupuestos">
    <jsp:attribute name="scripts">

        <script type="text/javascript">
        function isNumberKey(evt)
        {
        var charCode = (evt.which) ? evt.which : event.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
        return true;
        }
        </script>

        <script type="text/javascript">
          var vm= new Vue({
          el: '#contenidos',
          created: function(){

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
              <div class="col-xs-4">
              <h1>Solicitud de Servicio</h1>
              </div>
              <div class="col-xs-4 col-xs-offset-4">
                <label>
                  Solicitante:
                </label>
                <input class="form-control" type="text" name="name" value="" disabled="true">
              </div>
            </div>
            <br>
            <div class="row">
              <div class="col-xs-2">
               <label>
                 Tipo de Servicio:
               </label>
               <select class="form-control">
                 <option>Agua</option>
                 <option>Luz</option>
               </select>
              </div>

              <div class="col-xs-1">
                <div class="col-xs-6">
                  <button type="button" class="btn btn-default" style="margin-top: 25px; margin-left: -33px" data-toggle="modal" data-target="#busquedaArticulo">
                    <span class="glyphicon glyphicon-search"></span>
                  </button>
                </div>

                <div class="col-xs-6">
                  <button type="button" class="btn btn-default" style="margin-top: 25px; margin-left: -33px" data-toggle="modal" data-target="#nuevoArticulo">
                    <span class="glyphicon glyphicon-plus"></span>
                  </button>
                </div>
              </div>

             <div class="col-xs-2">
               <label>
                 Duración:
               </label>
               <select class="form-control" name="" id="tipoArticulo">
                 <option value="1">1 Mes</option>
                 <!-- <option value="0">Otro..</option> -->
               </select>
             </div>

             <div class="col-xs-2">
               <label>
                 Tipo de Pago:
               </label>
               <select class="form-control" name="" id="tipoArticulo">
                 <option value="1">Semanal</option>
                 <option value="2">Quincenal</option>
                 <option value="3">Mensual</option>
                 <option value="4">Bimestral</option>
               </select>
             </div>

             <div class="col-xs-2">
               <label>
                 Beneficiario:
               </label>
               <select class="form-control" name="" id="tipoArticulo">
                 <option value="1">Sucursal</option>
                 <option value="2">Empleado</option>
               </select>
             </div>
            </div>
            <br>
            <div class="row">
              <div class="col-xs-12">
                <label>
                  Descripcion del Servicio:
                </label>
                <textarea class="form-control" rows="3" cols="50"></textarea>
              </div>
            </div>
            <br>
            <div class="row">
              <div class="col-xs-12">
                <label>
                  Motivo de Contratacion:
                </label>
                <textarea class="form-control" rows="3" cols="50"></textarea>
              </div>
            </div>






          </div>

      </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
