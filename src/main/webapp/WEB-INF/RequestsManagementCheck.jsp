<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 26/09/16
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Comprobaciones">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {

                },
                created: function () {

                },
                data: {

                },
                methods: {

                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
        </style>
    </jsp:attribute>

    <jsp:body>
        <div class="col-md-12" id="content">

        </div>
    </jsp:body>
</t:template>
