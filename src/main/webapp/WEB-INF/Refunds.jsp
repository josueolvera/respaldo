<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 06/07/16
  Time: 10:05
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
                    var r = document.selection.createRange().duplicate()
                    r.moveEnd('character', o.value.length)
                    if (r.text == '') return o.value.length
                    return o.value.lastIndexOf(r.text)
                } else return o.selectionStart
            }
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>
        <script type="text/javascript">
            var vm= new Vue({
                el: '#content',
                ready: function () {
                    this.getAllConcepts();
                    this.getVoucherTypes();
                },
                created: function () {
                    this.getUserInSession();
                },
                data: {
                    userInSession:null,
                    requestBody:{
                        refund:{
                            refundTotal:0,
                            refundConcepts:[],
                            purpose:''
                        }
                    },
                    travelExpenseConcepts:[],
                    voucherTypes:[],
                    selected:{},
                    currentRefundConcept:{},
                    indexOfCurrentRefundConcept:null
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
                    },
                    getAllConcepts : function () {
                        this.$http.get(ROOT_URL + '/travel-expenses-concepts')
                                .success(function (data) {
                                    this.travelExpenseConcepts = data;
                                })
                                .error(function (data) {});
                    },
                    getVoucherTypes : function () {
                        this.$http.get(ROOT_URL + '/voucher-types')
                                .success(function (data) {
                                    this.voucherTypes = data;
                                })
                                .error(function (data) {});
                    },
                    getRefunConceptDocumentsTypes: function (refundConcept) {
                        this.$http.get(
                                ROOT_URL + '/refund-concept-documents-types?idVoucherType=' + refundConcept.voucherType.idVoucherType
                        )
                                .success(function (data) {
                                    refundConcept.voucherType.refundConceptDocumentTypes = data;
                                    this.requestBody.refund.refundConcepts.push(refundConcept);
                                })
                                .error(function (data) {});
                    },
                    addRefundConcept:function () {
                        var refundConcept = {};
                        var refundConceptAmount = {};
                        refundConcept.travelExpenseConcept = this.selected.travelExpenseConcept;
                        refundConcept.voucherType = this.selected.voucherType;

                        if (refundConcept.voucherType.idVoucherType === 2) {
                            Vue.set(refundConcept, 'refundConceptAmounts', []);
                            refundConcept.refundConceptAmounts.push(refundConceptAmount);
                        }

                        this.getRefunConceptDocumentsTypes(refundConcept);
                    },
                    showRefundConceptDocumentsModal : function (refundConcept, index) {
                        this.currentRefundConcept = refundConcept;
                        this.indexOfCurrentRefundConcept = index;
                        $("#refundConceptDocumentsModal").modal("show");
                    },
                    validateFileOtro: function (file) {
                        if (! file.name.match(/(\.jpg|\.jpeg|\.pdf|\.png)$/i)) {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type:3});
                            return false;
                        }

                        return true;
                    },
                    validateFilePDF: function (file) {
                        if (file.type !== 'application/pdf') {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type:3});
                            return false;
                        }

                        return true;
                    },
                    validateFileXML: function (file) {
                        if (file.type !== 'text/xml') {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type:3});
                            return false;
                        }

                        return true;
                    },
                    validateFiles: function (file, documentType) {

                        var isValidated = false;

                        switch(documentType.idRefundConceptDocumentType) {
                            case 1:
                                isValidated = this.validateFilePDF(file);
                                break;
                            case 2:
                                isValidated = this.validateFileXML(file);
                                break;
                            case 3:
                                isValidated = this.validateFileOtro(file);
                        }

                        return isValidated;
                    },
                    setFile: function (event, documentType) {
                        var self = this;
                        var file = event.target.files[0];

                        if (this.validateFiles(file, documentType)) {

                            var reader = new FileReader();

                            reader.onload = (function(theFile) {
                                return function(e) {

                                    switch(documentType.idRefundConceptDocumentType) {
                                        case 1:
                                            self.currentRefundConcept.voucherPdfFile = {
                                                name:theFile.name,
                                                size:theFile.size,
                                                type:theFile.type,
                                                dataUrl:e.target.result
                                            };
                                            break;
                                        case 2:
                                            self.currentRefundConcept.voucherXmlFile = {
                                                name:theFile.name,
                                                size:theFile.size,
                                                type:theFile.type,
                                                dataUrl:e.target.result
                                            };
                                            break;
                                        case 3:
                                            self.currentRefundConcept.otroFile = {
                                                name:theFile.name,
                                                size:theFile.size,
                                                type:theFile.type,
                                                dataUrl:e.target.result
                                            };
                                    }
                                    
                                    self.requestBody.refund.refundConcepts[self.indexOfCurrentRefundConcept] = self.currentRefundConcept;
                                };
                            })(file);
                            reader.readAsDataURL(file);
                        }
                    },
                    uploadvoucherXmlFile:function () {

                        var refundConcept = this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept];

                        this.$http.post(ROOT_URL + '/refund-concepts/voucher-xml-data',refundConcept.voucherXmlFile)
                                .success(function (data) {
                                    Vue.set(this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept], 'refundConceptAmounts', data.conceptos.conceptos);
                                    Vue.set(this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept], 'voucherFolio', data.folio);
                                    Vue.set(this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept], 'voucherTaxTotal', data.impuestos.totalImpuestosTrasladados);
                                    Vue.set(this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept], 'voucherTotal', data.total);
                                    this.calculateRefundTotal();
                                    $("#refundConceptDocumentsModal").modal("hide");
                                })
                                .error(function (data) {});

                    },
                    saveFiles: function () {

                        switch(this.currentRefundConcept.voucherType.idVoucherType) {
                            case 1:
                                this.uploadvoucherXmlFile();
                                break;
                            case 2:
                                $("#refundConceptDocumentsModal").modal("hide");
                        }
                    },
                    saveRefundConcepts:function (idRefund, refundConcept) {

                        this.$http.post(ROOT_URL + '/refund-concepts?idRefund=' + idRefund, refundConcept)
                                .success(function (data) {

                                })
                                .error(function (data) {});
                    },
                    saveRefund: function () {

                        var refund = this.requestBody.refund;
                        var refundConcepts = refund.refundConcepts;

                        delete refund.refundConcepts;

                        this.$http.post(ROOT_URL + '/refunds', refund)
                                .success(function (data) {
                                    var self = this;

                                    refundConcepts.forEach(function (refundConcept) {
                                        self.saveRefundConcepts(data, refundConcept);
                                    });
                                })
                                .error(function (data) {});
                    },
                    calculateRefundTotal: function () {
                        var self = this;
                        this.requestBody.refund.refundTotal = 0;
                        this.requestBody.refund.refundConcepts.forEach(function (refundConcept) {
                            if (isNaN(refundConcept.voucherTotal)) {
                                self.requestBody.refund.refundTotal += 0;
                            } else {
                                self.requestBody.refund.refundTotal += parseFloat(refundConcept.voucherTotal);
                            }
                        });

                        this.requestBody.refund.refundTotal = this.requestBody.refund.refundTotal.toFixed(2);
                    },
                    calculateVoucherTotal:function (refundConcept) {
                        refundConcept.voucherTotal = 0;
                        refundConcept.refundConceptAmounts.forEach(function (refundConceptAmount) {
                            if (isNaN(refundConceptAmount.importe)) {
                                refundConcept.voucherTotal += 0;
                            } else {
                                refundConcept.voucherTotal += parseFloat(refundConceptAmount.importe);
                            }
                        });
                        this.calculateRefundTotal();
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }
            .table-body .table-row:nth-child(2n+1) {
                background: #ddd;
            }
            .table-row {
                padding: 1rem;
            }
            .flex-content {
                overflow-x: hidden;
            }

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
            <h2>Reembolsos</h2>
            <div class="row">
                <div class="col-md-6">
                    <form v-on:submit.prevent="addRefundConcept">
                        <div class="col-md-5">
                            <label>Por concepto de</label>
                            <select v-model="selected.travelExpenseConcept" class="form-control" required>
                                <option v-for="travelExpenseConcept in travelExpenseConcepts" :value="travelExpenseConcept">
                                    {{travelExpenseConcept.conceptName}}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-5">
                            <label>Tipo de comprobante</label>
                            <select v-model="selected.voucherType" class="form-control" required>
                                <option v-for="voucherType in voucherTypes" :value="voucherType">
                                    {{voucherType.voucherTypeName}}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label style="visibility: hidden">add</label>
                            <button class="btn btn-default" data-toggle="tooltip"
                                    data-placement="top" title="Agreagr concepto">
                                <span class="glyphicon glyphicon-plus"></span>
                            </button>
                        </div>
                    </form>
                </div>
                <div class="col-md-6 text-right">
                    <label>Solicitante</label>
                    <p>
                        <span class="label label-default">{{userInSession.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
            <br>

            <div v-if="requestBody.refund.refundConcepts.length > 0">
                <form v-on:submit.prevent="saveRefund">
                    <div class="row">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Conceptos</h3>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-3">
                                    </div>
                                    <div class="col-md-9">
                                        <div class="row">
                                            <div class="col-md-5">
                                                <label>Concepto de factura</label>
                                            </div>
                                            <div class="col-md-3">
                                                <label>Monto</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="row" v-for="refundConcept in requestBody.refund.refundConcepts">
                                    <div class="col-md-3">
                                        <div class="row">
                                            <div class="col-md-1">
                                            </div>
                                            <div class="col-md-5">
                                                <h5><strong>{{refundConcept.travelExpenseConcept.conceptName}}</strong></h5>
                                            </div>
                                            <div class="col-md-5">
                                                <button type="button" class="btn btn-default" @click="showRefundConceptDocumentsModal(refundConcept, $index)">Subir archivos</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div v-if="refundConcept.voucherType.idVoucherType === 1">
                                        <div class="col-md-9">
                                            <div class="row" v-for="refundConceptAmount in refundConcept.refundConceptAmounts">
                                                <div class="col-md-5">
                                                    <p class="underline">
                                                        {{refundConceptAmount.descripcion}}
                                                    </p>
                                                </div>
                                                <div class="col-md-3">
                                                    <p class="underline">
                                                        {{refundConceptAmount.importe | currency}}
                                                    </p>
                                                </div>
                                            </div>
                                            <br>
                                                <%--<div class="row">--%>
                                                <%--<div class="col-md-2">--%>
                                                <%--<label>Número de factura</label>--%>
                                                <%--<p class="underline">{{refundConcept.voucherFolio}}</p>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-3">--%>
                                                <%--<label>IVA de factura</label>--%>
                                                <%--<p class="underline">--%>
                                                <%--{{refundConcept.voucherTaxTotal | currency}}--%>
                                                <%--</p>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-3">--%>
                                                <%--<label>Total de factura</label>--%>
                                                <%--<p class="underline">--%>
                                                <%--{{refundConcept.voucherTotal | currency}}--%>
                                                <%--</p>--%>
                                                <%--</div>--%>
                                                <%--</div>--%>
                                        </div>
                                    </div>
                                    <div v-if="refundConcept.voucherType.idVoucherType === 2">
                                        <div class="col-md-9">
                                            <div class="row"  v-for="refundConceptAmount in refundConcept.refundConceptAmounts">
                                                <div class="col-md-5">
                                                    <input type="text" class="form-control" v-model="refundConceptAmount.descripcion" placeholder="Concepto de factura" required>
                                                </div>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" @change="calculateVoucherTotal(refundConcept)" maxlength="10" required
                                                           v-model="refundConceptAmount.importe" placeholder="Monto" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" v-model="refundConcept.voucherFolio" placeholder="Número de factura" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="col-md-12">
                                    <div class="col-md-2 col-md-offset-10">
                                        <div v-if="requestBody.refund.refundTotal != 0">
                                            <label>Total</label>
                                            <p class="underline">
                                                {{requestBody.refund.refundTotal}}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Justificación de el reembolso</h3>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <textarea v-model="requestBody.refund.purpose" class="form-control" maxlength="65535" rows="2" required></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-offset-10 col-md-2 text-right">
                            <button class="btn btn-success form-control" type="submit">Solicitar</button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal fade" id="refundConceptDocumentsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <form v-on:submit.prevent="saveFiles" class="form-horizontal">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Cargar documento</h4>
                            </div>
                            <div class="modal-body">
                                <div class="form-group" v-for="documentType in currentRefundConcept.voucherType.refundConceptDocumentTypes">
                                    <label class="col-sm-3 control-label">{{documentType.documentName}}</label>
                                    <div class="col-sm-8">
                                        <input @change="setFile($event, documentType)" type="file" class="form-control" required>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success">Guardar</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </jsp:body>
</t:template>
