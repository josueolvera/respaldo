<%--
  Created by IntelliJ IDEA.
  User: jcesar
  Date: 01/06/2017
  Time: 11:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

<t:template pageTitle="BID Group: Solicitud">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function validateFloatKeyPress(el, evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                var number = el.value.split('.');
                if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                //just one dot
                if (number.length > 1 && charCode == 46) {
                    return false;
                }
                //get the carat position
                var caratPos = getSelectionStart(el);
                var dotPos = el.value.indexOf(".");
                if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
                    return false;
                }
                return true;
            }

            function getSelectionStart(o) {
                if (o.createTextRange) {
                    var r = document.selection.createRange().duplicate();
                    r.moveEnd('character', o.value.length);
                    if (r.text == '') return o.value.length;
                    return o.value.lastIndexOf(r.text)
                } else return o.selectionStart
            }
        </script>

        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                created: function () {

                },
                ready: function () {
                    this.getUserInSession();
                    this.getRequestInformation();
                },
                data: {
                    idRequest: ${idRequest},
                    user: {},
                    icon: false,
                    request:{},
                    downloadUrl: ROOT_URL + '/estimations/attachment/download/'
                },
                methods: {
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    getUserInSession: function () {
                        this.$http.get(ROOT_URL + "/user")
                            .success(function (data) {
                                this.user = data;
                            })
                            .error(function (data) {
                                showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                            });
                    },
                    showModalSolicitud: function () {
                        $('#modalSolicitud').modal('show');
                    },
                    getRequestInformation: function () {
                        this.$http.get(ROOT_URL + "/requests/" + this.idRequest)
                            .success(function (data) {
                                this.request = data;
                            })
                            .error(function (data) {

                            });
                    },
                    downloadEstimationDocument: function (idEstimation) {
                        this.$http.get(ROOT_URL)
                    }
                },

                filters: {
                    separate: function (value) {
                        return value.replace(/:/g, ' ');
                    },
                    currencyDisplay: {
                        read: function (val) {
                            if (typeof val == 'number') {
                                return val.formatMoney(2, '');
                            }
                        },
                        write: function (val, oldVal) {
                            var number = +val.replace(/[^\d.]/g, '');
                            return isNaN(number) ? 0 : parseFloat(number.toFixed(2));
                        }
                    }
                }
            });

        </script>
        <script type="text/javascript">
            function format(input) {
                var num = input.value.replace(/\,/g, '');
                if (!isNaN(num)) {
                    num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g, '$1,');
                    num = num.split('').reverse().join('').replace(/^[\,]/, '');
                    num = num.split('').join('').replace(/^[\,]/, '');
                    var inicio = num.substring(0, 1);
                    if (inicio == '0') {
                        showAlert("Corregir cantidad", {type: 3});
                        input.value = '';
                    } else {
                        input.value = num.split('').join('').replace(/^[\,]/, '');
                    }
                }

                else {
                    showAlert("Solo se permiten números", {type: 3});
                    input.value = '';
                }
            }

            function cleanField(obj) {
                var inicial = obj.value;
                if (obj.value == '0' || obj.value == '0.00') {
                    obj.value = '';
                } else {
                    return false;
                }
            }
            function onlyNumbers(e) {
                var key = window.Event ? e.which : e.keyCode
                return (key >= 48 && key <= 57)
            }
            function backHistory() {
                history.back();
            }
            function ponerCeros(obj) {
                var contar = obj.value;
                var min = contar.length - 3;
                var max = contar.length;

                if (obj.value == "" || obj.value == null) {
                    obj.value = "";
                } else {
                    if (max >= 1 && max < 40) {
                        var extraer = contar.substring(min, max);
                        if (extraer == '.00') {
                            contar = contar.replace('.,', ',');
                            contar = contar.replace(',.', ',');
                            format(input);
                        } else {
                            contar = contar.replace('.,', ',');
                            contar = contar.replace(',.', ',');
                            format(input);
                        }
                    }
                }
            }
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            textarea {
                resize: none;
            }
        </style>
        <style>
            textarea {
                resize: none;
            }

            label.circlered {
                width: 20px;
                height: 20px;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                background: #FF0000;
            }

            label.circleyel {
                width: 20px;
                height: 20px;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                background: #FFFF06;
            }

            label.circlegre {
                width: 20px;
                height: 20px;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                background: #00FF00;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
        <div class="row">
            <div class="col-md-5" style="margin-top: 40px">
                <h2>Validación de solicitud</h2>
            </div>
            <div class="col-md-3" style="margin-top: 45px">
                <label>Búsqueda por folio</label>
                <input type="text" class="form-control" width="50%" />
            </div>
            <div class="col-md-4 text-right" style="margin-top: 10px">
                <label>Solicitante</label>
                <p>
                    <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                </p>
            </div>
        </div>
        <br>
        <div id="accordion" role="tablist" aria-multiselectable="true">
        <div class="card">
        <div class="card-header" role="tab" id="headingThree">
            <div style="margin-left: 40%">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree"
                   @click="icon = !icon"
                   aria-expanded="false" aria-controls="collapseThree">
                    <div>
                        <b>Pendientes de autorización</b><br>
                        <span class="glyphicon glyphicon-chevron-down" v-if="icon == false"
                              style="margin-left: 11%"></span>
                        <span class="glyphicon glyphicon-chevron-up" v-if="icon == true"
                              style="margin-left: 11%"></span>
                    </div>
                </a>
            </div>
        </div>
        <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree">
            <div class="card-block">
                    <%-- subir arhivos de cotizacion--%>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="col-md-12">
                            <div class="row">
                                <table class="table table-striped">
                                    <tr>
                                        <td class="col-md-3 text-center"><b>Concepto de solicitud 1</b></td>
                                        <td class="col-md-3 text-center"><b>Fecha de solicitud</b></td>
                                        <td class="col-md-3 text-center"><b>Folio</b></td>
                                        <td class="col-md-3 text-center"><b>Detalle</b></td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-3 text-center">Equipo de computo</td>
                                        <td class="col-md-3 text-center">16-03-2017</td>
                                        <td class="col-md-3 text-center">ABC123</td>
                                        <td class="col-md-3 text-center">
                                            <button class="glyphicon glyphicon-new-window"></button>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</t:template>

