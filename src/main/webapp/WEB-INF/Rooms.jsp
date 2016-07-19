<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Agenda">
    <jsp:attribute name="scripts">

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
            <br>
                <div class="row">
                  <div class="col-xs-12 text-center">
                      <h3>ADMINISTRACIÓN DE SALAS DE JUNTA</h3>
                  </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                    <img src="../assets/img/capacitacion1.jpg" alt="...">
                    <div class="caption">
                    <h3>Thumbnail label</h3>
                    <p>...</p>
                    <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
                    </div>
                    </div>
                    </div>
                </div>
          </div>
      </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
