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
                },
                data: {
                    user: {}
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
                                this.getRolesCostCenter(this.user.dwEmployee.idRole);
                            })
                            .error(function (data) {
                                showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                            });
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

                if(obj.value == "" || obj.value == null)
                {
                    obj.value = "";
                }else{
                    if (max >= 1 && max < 40) {
                        var extraer = contar.substring(min, max);
                        if (extraer == '.00') {
                            contar = contar.replace('.,', ',');
                            contar = contar.replace(',.', ',');
                            format(input);
                        }else
                        {
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
                <div class="col-md-8">
                    <h2>Detalle de solicitu/vigente  <label class="circlegre"></label></h2>
                </div>
                <div class="col-md-4 text-right" style="margin-top: 10px">
                    <label>Solicitante</label>
                    <p>
                        <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-3">
                        <label>Centro de costos</label>
                        <select v-model="selected.costCenter" class="form-control"
                                @change="getBudgetSubcategories()" :disabled="requestBody.products.length > 0"
                                required>
                            <option v-for="costCenter in costCenterList" :value="costCenter">
                                {{costCenter.name}}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label>Concepto</label>
                        <select v-model="selected.concept" class="form-control"
                                @change="getProductCaptureBuy()" :disabled="requestBody.products.length > 0"
                                required>
                            <option v-for="concept in requestProductsBuy" :value="concept">
                                {{concept.budgetSubcategory.budgetSubcategory}}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label>Producto</label>
                        <select v-model="selected.productBuy" class="form-control"
                                required>
                            <option v-for="productBuy in requestCaptureProduct" :value="productBuy">
                                {{productBuy.cProductsRequest.productRequestName}}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <button style="margin-top: 25px" class="btn btn-default" @click="addProduct()">Agregar</button>
                    </div>
                </div>
            </div>
                <%--tabla de cotenido de productos--%>
            <div class="row" v-if="requestBody.products.length > 0">
                <div class="col-md-12">
                    <table class="table">
                        <thead>
                        <th class="col-md-3">Lista de productos</th>
                        <th class="col-md-1">Cantidad</th>
                        <th class="col-md-2"></th>
                        <th class="col-md-4">Justificación</th>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="col-md-3"></td>
                            <td class="col-md-1"></td>
                            <td class="col-md-2"></td>
                            <td class="col-md-2" rowspan="8">
                                <div class="form-group">
                                    <textarea class="form-control" rows="4" v-model="requestBody.request.purpose"></textarea>
                                </div>
                            </td>
                        </tr>
                        <tr v-for="product in requestBody.products">
                            <td class="col-md-3">{{product.productBuy.cProductsRequest.productRequestName}}</td>
                            <td class="col-md-1"><input class="form-control" maxlength="3" type="text"
                                                        onclick="return cleanField(this)"
                                                        onkeypress="return validateFloatKeyPress(this,event)"
                                                        onInput="format(this)" onblur="ponerCeros(this)"
                                                        placeholder="0" v-model="product.quantity" required/></td>
                            <td class="col-md-2">
                                <center>
                                    <button @click="removeProduct(product)" class="btn btn-danger">Eliminar
                                    </button>
                                </center>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

                <%--tabla de contenidos de productos--%>
            <br>
                <%-- subir arhivos de cotizacion--%>
            <div class="panel panel-default" v-if="requestBody.products.length > 0">
                <div class="panel-heading">Documentación</div>
                <div class="panel-body">
                    <div class="col-md-12">
                        <div class="row">
                            <table class="table table-striped">
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 1</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2">
                                        <input v-model="amount1" class="form-control" type="text" placeholder="$"
                                               onclick="return cleanField(this)"
                                               onkeypress="return validateFloatKeyPress(this,event)"
                                               maxlength="14" :disabled="button1 == true" />
                                    </td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile1($event)" type="file"
                                               class="form-control" :disabled="amount1 == '' || button1 == true"
                                               required />
                                    </td>
                                    <td class="col-md-3"></td>
                                    <td class="col-md-1" v-if="button1 == true">
                                        <button type="button" class="btn btn-warning" @click="removeDocument(1)">Cambiar factura</button>
                                    </td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 2</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2">
                                        <input v-model="amount2" class="form-control" type="text" placeholder="$"
                                               onclick="return cleanField(this)"
                                               onkeypress="return validateFloatKeyPress(this,event)"
                                               maxlength="14" :disabled="button2 == true"/></td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile2($event)" type="file"
                                               class="form-control" :disabled="amount2 == '' || button2 == true"
                                               required />
                                    </td>
                                    <td class="col-md-3"></td>
                                    <td class="col-md-1" v-if="button2 == true">
                                        <button type="button" class="btn btn-warning" @click="removeDocument(2)">Cambiar factura</button>
                                    </td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 3</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2">
                                        <input v-model="amount3" class="form-control" type="text" placeholder="$"
                                               onkeypress="return validateFloatKeyPress(this,event)"
                                               maxlength="14" :disabled="button3 == true"/></td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile3($event)" type="file"
                                               class="form-control" :disabled="amount3 == '' || button3 == true"
                                               required />
                                    </td>
                                    <td class="col-md-3"></td>
                                    <td class="col-md-1" v-if="button3 == true">
                                        <button type="button" class="btn btn-warning" @click="removeDocument(3)">Cambiar factura</button>
                                    </td>
                                </tr>

                            </table>

                        </div>
                        <div style="margin-left: 84%">
                            <button @click="showModalSolicitud()" class="btn btn-success">Solicitar</button>
                            <a href="javascript:window.history.back();">
                                <button type="button" class="btn btn-default" >Cancelar</button>
                            </a>
                        </div>
                    </div>
                </div>

            </div>
                <%--modal solicitar--%>
            <div class="modal fade" id="modalSolicitud" tabindex="-1" role="dialog" aria-labelledby=""
                 aria-hidden="true">
                <div class="modal-dialog modal-ms">
                    <div class="modal-content modal-ms">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div class="alert">
                                <h4 class="modal-title" id="" style="text-align: center"><label>Enviar solicitud</label>
                                </h4>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <p align="center" style="font-size: 16px">La solicitud será enviada a compras para continuar<br> con el proceso de compra de
                                        bienes.</p>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="sendRequest()">Aceptar</button>
                            <button type="button" class="btn btn-default" class="close" data-dismiss="modal" aria-hidden="true" >Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </jsp:body>
</t:template>

