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

            function getSelectionStart(o) {
                if (o.createTextRange) {
                    var r = document.selection.createRange().duplicate();
                    r.moveEnd('character', o.value.length);
                    if (r.text == '') return o.value.length;
                    return o.value.lastIndexOf(r.text)
                } else return o.selectionStart
            }
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode;
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
                    concepts:[],
                    voucherTypes:[],
                    selected:{
                        voucherType: null,
                        concept: null
                    },
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
                        this.$http.get(ROOT_URL + '/budget-concepts?category=5')
                                .success(function (data) {
                                    this.concepts = data;
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
                        ).success(function (data) {
                            refundConcept.voucherType.refundConceptDocumentTypes = data;
                            this.requestBody.refund.refundConcepts.push(refundConcept);
                        }).error(function (data) {});
                    },
                    addRefundConcept:function () {
                        var refundConcept = {};
                        var refundConceptAmount = {
                            descripcion: '',
                            importe: 0
                        };
                        refundConcept.concept = this.selected.concept;
                        refundConcept.voucherType = this.selected.voucherType;

                        if (refundConcept.voucherType.idVoucherType === 2) {
                            Vue.set(refundConcept, 'refundConceptAmounts', []);
                            refundConcept.refundConceptAmounts.push(refundConceptAmount);
                        }

                        this.getRefunConceptDocumentsTypes(refundConcept);

                        this.selected.voucherType = null;
                        this.selected.concept = null;
                    },
                    removeRefundConcept: function (refundConcept) {
                        this.requestBody.refund.refundConcepts.$remove(refundConcept);
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
                    uploadVoucherXmlFile:function () {

                        var refundConcept = this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept];

                        this.$http.post(ROOT_URL + '/refund-concepts/voucher-xml-data',refundConcept.voucherXmlFile)
                                .success(function (data) {
                                    Vue.set(this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept], 'refundConceptAmounts', data.conceptos.conceptos);
                                    Vue.set(this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept], 'voucherFolio', data.folio);
                                    Vue.set(this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept], 'voucherTaxTotal', data.impuestos.totalImpuestosTrasladados);
                                    Vue.set(this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept], 'voucherTotal', data.total);
                                    this.calculateRefundTotal();
                                    this.hideRefundConceptDocumentsModal();

                                })
                                .error(function (data) {});

                    },
                    saveFiles: function () {

                        switch(this.currentRefundConcept.voucherType.idVoucherType) {
                            case 1:
                                this.uploadVoucherXmlFile();
                                break;
                            case 2:
                                this.hideRefundConceptDocumentsModal();
                        }

                    },
                    clearFileInputs: function () {

                        var self = this;

                        this.currentRefundConcept
                                .voucherType
                                .refundConceptDocumentTypes
                                .forEach(function (refundConceptDocumentType, i) {
                                    self.clearFileInput('file-' + i);
                                });
                    },
                    clearFileInput: function (id) {
                        var oldInput = document.getElementById(id);

                        var newInput = document.createElement("input");

                        newInput.type = "file";
                        newInput.id = oldInput.id;
                        newInput.name = oldInput.name;
                        newInput.className = oldInput.className;
                        newInput.style.cssText = oldInput.style.cssText;

                        oldInput.parentNode.replaceChild(newInput, oldInput);
                    },
                    saveRefundConcepts:function (idRefund, refundConcept) {

                        this.$http.post(ROOT_URL + '/refund-concepts/' + idRefund, refundConcept)
                                .success(function (data) {

                                }).error(function (data) {});
                    },
                    saveRefund: function () {
                        var self = this;
                        var save = false;

                        var refund = this.requestBody.refund;
                        var refundConcepts = refund.refundConcepts;

                        refund.refundConcepts.every(function (refundConcept, index) {
                            if ('refundConceptAmounts' in refundConcept) {
                                save = true;
                            } else {
                                save = false;
                                showAlert('Selecciona archivos', {type:2});
                                document.getElementById('uploadFiles-' + index).focus();
                                return save;
                            }
                        });

                        if (save) {
                            delete refund.refundConcepts;

                            this.$http.post(ROOT_URL + '/refunds', refund)
                                    .success(function (data) {

                                        refundConcepts.forEach(function (refundConcept) {
                                            self.saveRefundConcepts(data, refundConcept);
                                        });
                                    }).error(function (data) {});
                        }
                    },
                    hideRefundConceptDocumentsModal : function () {
                        this.clearFileInputs();
                        $("#refundConceptDocumentsModal").modal("hide");
                    },
                    cancelFileUpload: function () {
                        this.hideRefundConceptDocumentsModal();
                        delete this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept].voucherPdfFile;
                        delete this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept].voucherXmlFile;
                        delete this.requestBody.refund.refundConcepts[this.indexOfCurrentRefundConcept].otroFile;
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
                },
                filters: {
                    currencyDisplay : {
                        read: function(val) {
                            return val.formatMoney(2, '');
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

    <jsp:attribute name="styles">
        <style>
            textarea{
                resize: none;
            }

            .underline {
                border-bottom: 1px solid grey;
            }

            @media (min-width: 992px) {
                .container-scroll {
                    overflow-x: auto;
                    background-color: #f5f5f5;
                    border: solid 1px #ddd;
                }
                .container-scroll > .row {
                    width: 100%;
                }
            }

            .table-header {
                margin-top: 10px;
                margin-bottom: 10px;
            }

            .table-footer {
                margin-top: 10px;
                margin-bottom: 10px;
            }

            .table-body {
                height: 400px;
                overflow-y: auto;
                border: solid 1px #ddd;
                background-color: white;
            }

            .table-body .table-row:nth-child(2n+1) {
                background: #efefef;
            }
            .table-body > .table-row {
                padding-top: 8px;
                padding-bottom: 8px;
            }

            .col-md-1,
            .col-md-2,
            .col-md-3,
            .col-md-4,
            .col-md-5,
            .col-md-6,
            .col-md-7,
            .col-md-8,
            .col-md-9,
            .col-md-10,
            .col-md-11,
            .col-md-12 {
                float:none;
                display:inline-block;
                vertical-align:middle;
                margin-right:-4px;
            }

        </style>
    </jsp:attribute>

    <jsp:body>
        <div class="col-md-12" id="content">
            <div class="row">
                <div class="col-md-8">
                    <h2>Reembolsos</h2>
                </div>
                <div class="col-md-4"></div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-6">
                    <form v-on:submit.prevent="addRefundConcept">
                        <div class="row">
                            <div class="col-md-5">
                                <label>Por concepto de</label>
                                <select v-model="selected.concept" class="form-control" required>
                                    <option v-for="concept in concepts" :value="concept">
                                        {{concept.budgetConcept}}
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
                            <div class="col-md-2" style="margin-top: 25px">
                                <button class="btn btn-default">
                                    Agregar
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-6 text-right" style="margin-top: 30px">
                    <label>Solicitante: </label>
                    <span class="label label-default">{{userInSession.dwEmployee.employee.fullName}}</span>
                </div>
            </div>
            <br>

            <form v-on:submit.prevent="saveRefund">
                <div class="container-fluid container-scroll">
                    <div class="col-md-12 table-header">
                        <div class="row">
                            <div class="col-md-2">
                                <div class="col-md-6">
                                    <h5><strong>Concepto</strong></h5>
                                </div>
                                <div class="col-md-6"></div>
                            </div>
                            <div class="col-md-7">
                                <div class="col-md-6">
                                    <h5><strong>Concepto de factura</strong></h5>
                                </div>
                                <div class="col-md-3">
                                    <h5><strong>Monto</strong></h5>
                                </div>
                                <div class="col-md-3">
                                    <h5><strong>Número de factura</strong></h5>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="col-md-6">
                                    <h5><strong>Archivos</strong></h5>
                                </div>
                                <div class="col-md-6"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 table-body">
                        <div class="row table-row" v-for="refundConcept in requestBody.refund.refundConcepts">
                            <div class="col-md-2">
                                <div class="col-md-6">
                                    <p class="text-primary">{{refundConcept.concept.budgetConcept}}</p>
                                </div>
                                <div class="col-md-6"></div>
                            </div>
                            <div class="col-md-7" v-if="refundConcept.voucherType.idVoucherType === 1">
                                <div class="row" v-for="refundConceptAmount in refundConcept.refundConceptAmounts">
                                    <div class="col-md-6">
                                        <p class="underline">
                                            {{refundConceptAmount.descripcion}}
                                        </p>
                                    </div>
                                    <div class="col-md-3">
                                        <p class="underline">
                                            {{refundConceptAmount.importe | currency}}
                                        </p>
                                    </div>
                                    <div class="col-md-3">
                                        <p class="underline">
                                            {{refundConcept.voucherFolio}}
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-7" v-if="refundConcept.voucherType.idVoucherType === 2">
                                <div class="row"  v-for="refundConceptAmount in refundConcept.refundConceptAmounts">
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" v-model="refundConceptAmount.descripcion" required>
                                    </div>
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" @change="calculateVoucherTotal(refundConcept)" required
                                               v-model="refundConceptAmount.importe | currencyDisplay" onkeypress="return validateFloatKeyPress(this,event)">
                                    </div>
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" v-model="refundConcept.voucherFolio" required>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="col-md-6">
                                    <div class="row">
                                        <button type="button" id="uploadFiles-{{$index}}" class="btn btn-default" @click="showRefundConceptDocumentsModal(refundConcept, $index)">Subir archivos</button>
                                    </div>
                                </div>
                                <div class="col-md-6 text-right">
                                    <div class="row">
                                        <button type="button" class="btn btn-danger" @click="removeRefundConcept(refundConcept)">Eliminar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 table-footer">
                        <div class="row">
                            <div class="col-md-2 col-md-offset-10">
                                <label>Total</label>
                                <p class="underline">
                                    {{requestBody.refund.refundTotal | currency}}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Justificación de el reembolso</h3>
                    </div>
                    <div class="panel-body">
                        <div class="form-group">
                            <textarea v-model="requestBody.refund.purpose" class="form-control" maxlength="65535" rows="8" required></textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-offset-10 col-md-2 text-right">
                        <button class="btn btn-success form-control" type="submit">Solicitar</button>
                    </div>
                </div>
            </form>
            <br>
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
                                    <label class="col-md-3 control-label">{{documentType.documentName}}</label>
                                    <div class="col-md-8">
                                        <input @change="setFile($event, documentType)" id="file-{{$index}}" name="file-{{$index}}" type="file" class="form-control" required>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success">Guardar</button>
                                <button type="button" class="btn btn-default" @click="cancelFileUpload">Cancelar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </jsp:body>
</t:template>
