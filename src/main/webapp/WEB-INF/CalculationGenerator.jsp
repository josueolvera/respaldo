<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Generador de calculo">

    <jsp:attribute name="scripts">
        <script type="text/javascript">

            var vm = new Vue({
                el: '#content',
                created: function(){
                },
                ready : function () {

                },
                data: {

                },
                methods: {

                },
                filters: {
                    currencyDisplay : {
                        read: function(val) {
                            return '$'+val.toFixed(2);
                        },
                        write: function(val, oldVal) {
                            var number = +val.replace(/[^\d.]/g, '');
                            return isNaN(number) ? 0 : parseFloat(number.toFixed(2));
                        }
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <h3>Calculo</h3>
            </div>
        </div>
    </jsp:body>
</t:template>
