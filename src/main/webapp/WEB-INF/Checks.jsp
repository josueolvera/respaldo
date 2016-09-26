<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 22/09/16
  Time: 09:19
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Rembolsos">
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
                    var r = document.selection.createRange().duplicate();
                    r.moveEnd('character', o.value.length);
                    if (r.text == '') return o.value.length;
                    return o.value.lastIndexOf(r.text)
                } else return o.selectionStart
            }
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {

                },
                created: function () {
                    this.getUserInSession();
                },
                data: {
                    userInSession:null
                },
                methods: {
                    getUserInSession: function()
                    {
                        this.$http.get(ROOT_URL + "/user").
                        success(function (data)
                        {
                            this.userInSession = data;
                        }).error(function(data)
                        {
                            showAlert("Ha habido un error al obtener al usuario en sesion");
                        });
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            textarea{
                resize: vertical;
            }

            .underline {
                border-bottom: 1px solid grey;
            }

        </style>
    </jsp:attribute>

    <jsp:body>
        <div class="col-md-12" id="content">
            <h2>Comprobaciones</h2>
        </div>
    </jsp:body>
</t:template>