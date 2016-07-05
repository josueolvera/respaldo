<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 27/06/16
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Actualizar empleado">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm= new Vue({
                el: '#content',
                ready: function () {
                    this.getDwEmployee();
                },
                data: {
                    dwEmployee:{},
                    idDwEmployee: ${idDwEmployee}
                },
                methods: {
                    getDwEmployee: function () {
                        this.$http.get(ROOT_URL + '/dw-employees/' + this.idDwEmployee)
                                .success(function (data) {
                                    this.dwEmployee = data;
                                })
                                .error(function (data) {

                                });
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            {{dwEmployee.employee.fullName}}
        </div>
    </jsp:body>
</t:template>
