<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Busqueda Solicitudes">
    <jsp:attribute name="scripts">

        <script type="text/javascript">
          var vm= new Vue({
          el: '#contenidos',
          created: function()
          {

          },
          ready: function ()
          {


          },
          data:
          {
            folioBusqueda: ''

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
            <br>
            <div class="row">
                <div class="col-xs-4">
                  <h3>Busqueda de Solicitudes</h3>
                </div>
            </div>
            <div class="row">
              <div class="col-xs-4">
                <label>
                  Introduzca el numero de folio de la solicitud
                </label>
                <div class="input-group">
                  <span class="input-group-addon">
                    <span class="glyphicon glyphicon-search"></span>
                  </span>
                  <input type="text" class="form-control" v-model="folioBusqueda">
                </div>
              </div>
              <div class="col-xs-2">
                <button type="button" class="btn btn-info" name="button" style="margin-top: 25px">
                  Buscar
                </button>
              </div>
            </div>
            <br>
            <div class="row">
              <div class="col-xs-6">
                <label>
                  Resultados
                </label>
                  <table class="table table-striped">
                    <thead>
                      <th>
                        Folio
                      </th>
                      <th>
                        Solicitante
                      </th>
                      <th>
                        Fecha de Creacion
                      </th>
                    </thead>
                  </table>
              </div>
            </div>

            <br>
          </div>
      </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
