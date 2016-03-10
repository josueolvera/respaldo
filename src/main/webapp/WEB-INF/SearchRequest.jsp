<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Busqueda Solicitudes">
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
                
            </div>
            <br>
          </div>
      </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
