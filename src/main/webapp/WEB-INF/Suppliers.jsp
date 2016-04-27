<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />


<t:template pageTitle="BID Group: Presupuestos">
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
          supplier:
          {
            idSupplier: '',
            name: '',
            address: '',
            phone: '',
            accounts: []
          },
          account: ''

        },
        methods:
        {
          saludar: function()
          {
            alert(this.supplier.address);
          }


        }
      });

        </script>
    </jsp:attribute>

    <jsp:body>
      <div id="contenidos">

      <div class="container-fluid" style="margin-left: 100px">


      </div> <!-- container-fluid -->

      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
