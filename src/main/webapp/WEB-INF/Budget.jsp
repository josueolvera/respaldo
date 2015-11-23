<%--
    Document   : Budget
    Created on : 19/11/2015, 12:08:25 PM
    Author     : rubens
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Presupuestos">

    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',

                data: {
                    cBudgetType: [],
                    cBudgetArea: [],
                    cBudgetPeriod: [],
                },
                methods:
                {
                  saludar: function()
                  {
                    alert("Hola");
                  }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <div class="row">

                    <div class="col-xs-4">
                        <label>
                            Tipo de Presupuesto
                        </label>
                        <select class="form-control" id="cBudgetType" @load="saludar">

                        </select>
                    </div>
                    <div class="col-xs-4">
                        <label>
                            Area de Presupuesto
                        </label>
                        <select class="form-control" id="cBudgetArea">

                        </select>
                    </div>
                    <div class="col-xs-4">
                        <label>
                            Periodo Presupuesto
                        </label>
                        <select class="form-control" id="cBudgetPeriod">

                        </select>
                    </div>
                </div>
                <pre>
                  {{ $data | json }}
                </pre>
            </div>
        </div>
    </jsp:body>
</t:template>
