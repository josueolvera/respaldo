<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

<t:template pageTitle="BID Group: Presupuestos">

    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function validateFloatKeyPress(el, evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                var number = el.value.split('.');
                if (charCode != 46 && charCode > 31 && ( charCode < 48 || charCode > 57)) {
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
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>
        <script>
            Number.prototype.formatMoney = function (places, symbol, thousand, decimal) {
                places = !isNaN(places = Math.abs(places)) ? places : 2;
                symbol = symbol !== undefined ? symbol : "$";
                thousand = thousand || ",";
                decimal = decimal || ".";
                var number = this,
                    negative = number < 0 ? "-" : "",
                    i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
                    j = (j = i.length) > 3 ? j % 3 : 0;
                return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
            };
        </script>
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                created: function () {
                    this.setYears();
                },
                ready: function () {
                    this.getUserInSession();
                    this.getBudgetTypes();
                    this.getNature();
                },
                data: {
                    now: "${now}",
                    minYear: null,
                    maxYear: null,
                    totalCostCenter: null,
                    years: [],
                    budgets: [],
                    budgetsReports: [],
                    concepts: [],
                    budgetTypes: [],
                    user: {},
                    selected: {
                        budgetNature: null,
                        budgetType: null,
                        costCenter: null,
                        budgetCategory: null,
                        year: null,
                        yearFromCopy: null,
                        yearToCopy: null,
                        budget: null,
                        subbudget: null,
                        concept: ''
                    },
                    emails: {},
                    searching: false,
                    rolesCostCenter: [],
                    costCenterList: [],
                    budgetNatureList: [],
                    budgetCategories: [],
                    role: null,
                    authorizationBudget: {},
                    errorMessage: '',
                    validation: false,
                    authorization: false,
                    annual: false,
                    disableNature: false,
                    sumTotals: {
                        januaryBudgetAmount: 0.00,
                        februaryBudgetAmount: 0.00,
                        marchBudgetAmount: 0.00,
                        aprilBudgetAmount: 0.00,
                        mayBudgetAmount: 0.00,
                        juneBudgetAmount: 0.00,
                        julyBudgetAmount: 0.00,
                        augustBudgetAmount: 0.00,
                        septemberBudgetAmount: 0.00,
                        octoberBudgetAmount: 0.00,
                        novemberBudgetAmount: 0.00,
                        decemberBudgetAmount: 0.00,
                        totalLastYearAmount: 0.00,
                        totalCurrentYear: 0.00
                    },
                    budgetTotals: {
                        januaryBudgetAmount: 0.00,
                        februaryBudgetAmount: 0.00,
                        marchBudgetAmount: 0.00,
                        aprilBudgetAmount: 0.00,
                        mayBudgetAmount: 0.00,
                        juneBudgetAmount: 0.00,
                        julyBudgetAmount: 0.00,
                        augustBudgetAmount: 0.00,
                        septemberBudgetAmount: 0.00,
                        octoberBudgetAmount: 0.00,
                        novemberBudgetAmount: 0.00,
                        decemberBudgetAmount: 0.00,
                        totalLastYearAmount: 0.00,
                        totalCurrentYear: 0.00
                    },
                    costCenterStatus: [],
                    flag: false,
                    costCenter: null,
                    year: null,
                    conceptBudgetData: {
                        idCostCenter: null,
                        idAccountingAccount: null,
                        idBudgetNature: null,
                        idBudgetType: null,
                        idConceptBudget: null
                    },
                    distributorCostCenter: [],
                    conceptsSelectize: ''
                },
                methods: {
                    getUserInSession: function () {
                        this.$http.get(ROOT_URL + "/user")
                            .success(function (data) {
                                this.user = data;
                                this.role = this.user.dwEmployee.idRole;
                                this.getRolesCostCenter(this.role);
                            })
                            .error(function (data) {
                                showAlert("Ha habido un error al obtener al usuario en sesión", {type: 3});
                            });
                    },
                    getRolesCostCenter: function (idRole) {
                        this.$http.get(ROOT_URL + '/roles-cost-center/role/' + idRole)
                            .success(function (data) {
                                var self = this;
                                var index;
                                this.rolesCostCenter = data;
                                if (data.length < 2) {
                                    this.selected.budgetNature = data[0].budgetNature;
                                }
                                data.forEach(function (item) {
                                    index = self.arrayObjectIndexOf(self.costCenterList, item.costCenter.idCostCenter, 'idCostCenter');
                                    if (index == -1) self.costCenterList.push(item.costCenter);
                                });
                                /*data.forEach(function (item) {
                                 index = self.arrayObjectIndexOf(self.budgetNatureList, item.budgetNature.idBudgetNature, 'idBudgetNature');
                                 if (index == -1) self.budgetNatureList.push(item.budgetNature);
                                 });*/
                            })
                            .error(function (data) {
                            });
                    },
                    getNature: function () {
                        this.$http.get(ROOT_URL + '/c-budget-nature').success(function (data) {
                            this.budgetNatureList = data;
                        });
                    },
                    getBudgetTypes: function () {
                        this.$http.get(ROOT_URL + '/budget-types')
                            .success(function (data) {
                                this.budgetTypes = data;
                            });
                    },
                    setYears: function () {
                        var now = new Date(this.now);
                        this.selected.year = now.getFullYear();
                        this.minYear = this.selected.year - 0;
                        this.maxYear = this.selected.year + 1;
                        for (var i = this.minYear; i <= this.maxYear; i++) {
                            this.years.push(i)
                        }
                    },
                    searchBudget: function (year, budgetType, budgetNature, budgetCategory) {
                        this.searching = true;
                        this.getBudgets(year, budgetType, budgetNature, budgetCategory);
                    },
                    getBudgets: function (year, budgetType, budgetNature, budgetCategory) {
                        var url = ROOT_URL +
                            '/budgets?cost_center=' + this.selected.costCenter.idCostCenter;
                        if (this.selected.year != null) {
                            url += '&year=' + this.selected.year;
                        }
                        if (this.selected.budgetType != null) {
                            url += '&budget_type=' + this.selected.budgetType.idBudgetType;
                        }
                        if (this.selected.budgetNature.idBudgetNature != null) {
                            url += '&budget_nature=' + this.selected.budgetNature.idBudgetNature;
                        }
                        this.$http.get(url)
                            .success(function (data) {
                                var self = this;
                                this.budgets = data;
                                this.searching = false;
                                this.getAuthorizationBudget();
                                this.getTotalCostCenter();
                                if (data.length <= 0) {
                                    showAlert('Sin rubros asignados', {type: 3});
                                }
                            })
                            .error(function (data) {
                            });
                    },
                    getAuthorizationBudget: function () {
                        this.$http.get(ROOT_URL + '/authorizathion-costcenter/cost?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            this.authorizationBudget = data;
                            if (this.authorizationBudget.validation) {
                                this.validation = true;
                            }
                            if (this.authorizationBudget.authorization) {
                                this.authorization = true;
                            }
                        }).error(function (data) {
                        });
                    },
                    getBudgetsReport: function (costCenter, year) {
                        this.$http.get(ROOT_URL + '/budgets/authorized?cost_center=' + costCenter.idCostCenter + '&year=' + year).success(function (data) {
                            this.budgetsReports = data;
                            this.sumTotalsByMonth();
                            this.budgetTotals.januaryBudgetAmount = this.sumTotals.januaryBudgetAmount.toFixed(2);
                            this.budgetTotals.februaryBudgetAmount = this.sumTotals.februaryBudgetAmount.toFixed(2);
                            this.budgetTotals.marchBudgetAmount = this.sumTotals.marchBudgetAmount.toFixed(2);
                            this.budgetTotals.aprilBudgetAmount = this.sumTotals.aprilBudgetAmount.toFixed(2);
                            this.budgetTotals.mayBudgetAmount = this.sumTotals.mayBudgetAmount.toFixed(2);
                            this.budgetTotals.juneBudgetAmount = this.sumTotals.juneBudgetAmount.toFixed(2);
                            this.budgetTotals.julyBudgetAmount = this.sumTotals.julyBudgetAmount.toFixed(2);
                            this.budgetTotals.augustBudgetAmount = this.sumTotals.augustBudgetAmount.toFixed(2);
                            this.budgetTotals.septemberBudgetAmount = this.sumTotals.septemberBudgetAmount.toFixed(2);
                            this.budgetTotals.octoberBudgetAmount = this.sumTotals.octoberBudgetAmount.toFixed(2);
                            this.budgetTotals.novemberBudgetAmount = this.sumTotals.novemberBudgetAmount.toFixed(2);
                            this.budgetTotals.decemberBudgetAmount = this.sumTotals.decemberBudgetAmount.toFixed(2);
                            this.budgetTotals.totalLastYearAmount = this.sumTotals.totalLastYearAmount.toFixed(2);
                            this.budgetTotals.totalCurrentYear = this.sumTotals.totalCurrentYear.toFixed(2);
                        });
                    },
                    sumTotalsByMonth: function () {
                        var self = this;
                        this.budgetsReports.forEach(function (concept) {
                            self.sumTotals.januaryBudgetAmount += concept.januaryBudgetAmount;
                            self.sumTotals.februaryBudgetAmount += concept.februaryBudgetAmount;
                            self.sumTotals.marchBudgetAmount += concept.marchBudgetAmount;
                            self.sumTotals.aprilBudgetAmount += concept.aprilBudgetAmount;
                            self.sumTotals.mayBudgetAmount += concept.mayBudgetAmount;
                            self.sumTotals.juneBudgetAmount += concept.juneBudgetAmount;
                            self.sumTotals.julyBudgetAmount += concept.julyBudgetAmount;
                            self.sumTotals.augustBudgetAmount += concept.augustBudgetAmount;
                            self.sumTotals.septemberBudgetAmount += concept.septemberBudgetAmount;
                            self.sumTotals.octoberBudgetAmount += concept.octoberBudgetAmount;
                            self.sumTotals.novemberBudgetAmount += concept.novemberBudgetAmount;
                            self.sumTotals.decemberBudgetAmount += concept.decemberBudgetAmount;
                            self.sumTotals.totalLastYearAmount += concept.totalLastYearAmount;
                            self.sumTotals.totalCurrentYear += concept.totalBudgetAmount;
                        });
                    },
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    deleteBudget: function (budget, idBudget) {
                        var budgetYearConceptListToSave = [];
                        budgetYearConceptListToSave.push(budget);
                        var isValid = false;
                        budgetYearConceptListToSave.every(function (budget, index, _ary) {
                            budget.budgetConcept = {};
                            isValid = true;
                        });
                        if (isValid) {
                            budgetYearConceptListToSave.forEach(function (budget) {
                            });
                            this.$http.post(ROOT_URL + '/budget-year-concept/update/' + idBudget, JSON.stringify(budgetYearConceptListToSave))
                                .success(function (data) {
                                    showAlert('Presupuesto eliminado');
                                    this.getBudgets(this.selected.year, this.selected.budgetType, this.selected.budgetNature, this.selected.budgetCategory);
                                })
                                .error(function (data) {
                                    showAlert('Ha habido un error con la solicitud, intente nuevamente');
                                });
                        }
                    },
                    saveBudget: function (budget, idBudget) {
                        var budgetYearConceptListToSave = [];
                        budgetYearConceptListToSave.push(budget);
                        var isValid = false;
                        budgetYearConceptListToSave.every(function (budget, index, _ary) {
                            budget.budgetConcept = {};
                            isValid = true;
                        });
                        if (isValid) {
                            budgetYearConceptListToSave.forEach(function (budget) {
                            });
                            this.$http.post(ROOT_URL + '/budget-year-concept/' + idBudget, JSON.stringify(budgetYearConceptListToSave))
                                .success(function (data) {
                                    showAlert('Presupuesto guardado');
                                    this.getBudgets(this.selected.year, this.selected.budgetType, this.selected.budgetNature, this.selected.budgetCategory);
                                })
                                .error(function (data) {
                                    showAlert('Ha habido un error con la solicitud, intente nuevamente');
                                });
                        }
                    },
                    equalsImport: function (indexOfBudget, budget) {
                        if (budget.equals) {
                            if (budget.januaryBudgetAmount != '') {
                                budget.februaryBudgetAmount = budget.januaryBudgetAmount;
                                budget.marchBudgetAmount = budget.januaryBudgetAmount;
                                budget.aprilBudgetAmount = budget.januaryBudgetAmount;
                                budget.mayBudgetAmount = budget.januaryBudgetAmount;
                                budget.juneBudgetAmount = budget.januaryBudgetAmount;
                                budget.julyBudgetAmount = budget.januaryBudgetAmount;
                                budget.augustBudgetAmount = budget.januaryBudgetAmount;
                                budget.septemberBudgetAmount = budget.januaryBudgetAmount;
                                budget.octoberBudgetAmount = budget.januaryBudgetAmount;
                                budget.novemberBudgetAmount = budget.januaryBudgetAmount;
                                budget.decemberBudgetAmount = budget.januaryBudgetAmount;
                                this.getBudgetYearConcept(indexOfBudget, budget);
                            } else {
                                showAlert('Debes ingresar un monto en el primer mes para esta acción', {type: 2});
                                subbudget.equals = false;
                            }
                        } else {
                            budget.februaryBudgetAmount = 0;
                            budget.marchBudgetAmount = 0;
                            budget.aprilBudgetAmount = 0;
                            budget.mayBudgetAmount = 0;
                            budget.juneBudgetAmount = 0;
                            budget.julyBudgetAmount = 0;
                            budget.augustBudgetAmount = 0;
                            budget.septemberBudgetAmount = 0;
                            budget.octoberBudgetAmount = 0;
                            budget.novemberBudgetAmount = 0;
                            budget.decemberBudgetAmount = 0;
                            this.getBudgetYearConcept(indexOfBudget, budget);
                        }
                    },
                    copyBudget: function (overwrite) {
                        var self = this;
                        var url = '/budget-year-concept/copy-budget?cost_center=' + this.selected.costCenter.idCostCenter +
                            '&year_from_copy=' + this.selected.yearFromCopy + '&budget_type=' + this.selected.budgetType.idBudgetType +
                            '&budget_nature=' + this.selected.budgetNature.idBudgetNature +
                            '&year_to_copy=' + this.selected.yearToCopy;
                        if (this.selected.yearFromCopy == this.selected.yearToCopy) {
                            showAlert("No se puede copiar presupesto en el mismo año", {type: 3});
                        } else {
                            self.$http.post(ROOT_URL + url).success(function (element) {
                                this.closeCopyBudgetModal();
                                showAlert('Transacción exitosa');
                                showAlert(data.success.message);
                            }).error(function (data) {
                                showAlert(data.error.message, {type: 3});
                            });
                        }
                    },
                    getEmails: function () {
                        this.$http.get(ROOT_URL + '/budgets/get-emails?cost_center=' + this.selected.costCenter.idCostCenter).success(function (data) {
                            this.emails = data;
                        }).error(function (data) {
                            showAlert(data.error.message, {type: 3});
                        });
                    },
                    getEmailsModify: function () {
                        this.emails = '';
                        this.$http.get(ROOT_URL + '/budgets/get-emails-modify?cost_center=' + this.selected.costCenter.idCostCenter).success(function (data) {
                            this.emails = data;
                        }).error(function (data) {
                            showAlert(data.error.message, {type: 3});
                        });
                    },
                    onClickAcept: function () {
                        this.copyBudget(false);
                    },
                    onClickOverwrite: function () {
                        this.copyBudget(true);
                    },
                    showCopyBudgetModal: function () {
                        $('#copyBudgetModal').modal('show');
                    },
                    closeCopyBudgetModal: function () {
                        $('#copyBudgetModal').modal('hide');
                    },
                    showSendValidation: function () {
                        this.cleanFieldsSum();
                        this.flag = true;
                        this.getBudgetsReport(this.selected.costCenter, this.selected.year);
                        this.getEmails();
                        $('#sendValidation').modal('show');
                    },
                    closeSendValidation: function () {
                        this.cleanFieldsSum();
                        $('#sendValidation').modal('hide');
                    },
                    showOverwriteModal: function () {
                        $('#overwriteModal').modal('show');
                    },
                    closeOverwriteModal: function () {
                        $('#overwriteModal').modal('hide');
                    },
                    showBudget: function () {
                        this.closeSendValidation();
                        $('#budgetInformation').modal('show');
                    },
                    closeBudget: function () {
                        this.cleanFieldsSum();
                        $('#budgetInformation').modal('hide');
                    },
                    showConfirm: function () {
                        this.cleanFieldsSum();
                        this.closeBudget();
                        this.closeSendValidation();
                        $('#confirmBudget').modal('show');
                    },
                    showRequestModification: function () {
                        this.getEmailsModify();
                        $('#requestModification').modal('show');
                    },
                    closeRequestModification: function () {
                        $('#requestModification').modal('hide');
                    },
                    closeConfirm: function () {
                        $('#confirmBudget').modal('hide');
                    },
                    getBudgetYearConcept: function (indexOfBudget, budget) {
                        budget.totalAmount = 0;
                        budget.totalAmount += budget.januaryBudgetAmount;
                        budget.totalAmount += budget.februaryAmount;
                        budget.totalAmount += budget.marchAmount;
                        budget.totalAmount += budget.aprilAmount;
                        budget.totalAmount += budget.mayAmount;
                        budget.totalAmount += budget.juneAmount;
                        budget.totalAmount += budget.julyAmount;
                        budget.totalAmount += budget.augustAmount;
                        budget.totalAmount += budget.septemberAmount;
                        budget.totalAmount += budget.octoberAmount;
                        budget.totalAmount += budget.novemberAmount;
                        budget.totalAmount += budget.decemberAmount;
                    },
                    getTotalCostCenter: function () {
                        var self = this;
                        this.totalCostCenter = 0;
                        this.budgets.totalCategoryAmount = 0;
                        this.budgets.forEach(function (budget) {
                            if (budget == null) {
                            } else {
                                self.totalCostCenter += budget.totalBudgetAmount;
                            }
                        });
                    },
                    onChangeFilter: function () {
                        this.budgets = [];
                    },
                    onChangeFilterClassification: function () {
                        if (this.selected.budgetType.idBudgetType == 2) {
                            this.disableNature = true;
                            this.selected.budgetNature = {};

                        } else {
                            this.disableNature = false;
                            this.selected.budgetNature = {};
                        }
                    },
                    submitValidation: function () {
                        this.$http.post(ROOT_URL + '/budgets/authorization?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            this.closeConfirm();
                            this.budgets = data;
                            showAlert('Presupuesto enviado');
                            this.getBudgets(this.selected.year, this.selected.budgetType, this.selected.budgetNature, this.selected.budgetCategory);
                        }).error(function (data) {
                            showAlert('Ha habido un error con la solicitud, intente nuevamente');
                        });
                    },
                    sendModifyBudget: function () {
                        this.closeRequestModification();
                        this.$http.post(ROOT_URL + '/authorizathion-costcenter/modify?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            showAlert('Solicitud de modificación enviada');
                            this.getBudgets(this.selected.year, this.selected.budgetType, this.selected.budgetNature, this.selected.budgetCategory);
                        }).error(function (data) {
                            showAlert('Ha habido un error con la solicitud, intente nuevamente');
                        });
                    },
                    cleanFieldsSum: function () {
                        this.sumTotals.januaryBudgetAmount = 0.00;
                        this.sumTotals.februaryBudgetAmount = 0.00;
                        this.sumTotals.marchBudgetAmount = 0.00;
                        this.sumTotals.aprilBudgetAmount = 0.00;
                        this.sumTotals.mayBudgetAmount = 0.00;
                        this.sumTotals.juneBudgetAmount = 0.00;
                        this.sumTotals.julyBudgetAmount = 0.00;
                        this.sumTotals.augustBudgetAmount = 0.00;
                        this.sumTotals.septemberBudgetAmount = 0.00;
                        this.sumTotals.octoberBudgetAmount = 0.00;
                        this.sumTotals.novemberBudgetAmount = 0.00;
                        this.sumTotals.decemberBudgetAmount = 0.00;
                        this.sumTotals.totalLastYearAmount = 0.00;
                        this.sumTotals.totalCurrentYear = 0.00;
                    },
                    getAllBudgetsStatusByRole: function () {
                        this.$http.get(ROOT_URL + "/authorizathion-costcenter/role/" +  this.role).success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (costCenter) {
                                if (isNaN(costCenter.costCenter)) {
                                    jsonObjectIndex[costCenter.costCenter._id] = costCenter.costCenter;
                                } else {
                                    costCenter.costCenter = jsonObjectIndex[costCenter.costCenter];
                                }
                            });

                            data.forEach(function (cCostCenterStatus) {
                                if (isNaN(cCostCenterStatus.cCostCenterStatus)) {
                                    jsonObjectIndex[cCostCenterStatus.cCostCenterStatus._id] = cCostCenterStatus.cCostCenterStatus;
                                } else {
                                    cCostCenterStatus.cCostCenterStatus = jsonObjectIndex[cCostCenterStatus.cCostCenterStatus];
                                }
                            });

                            data.forEach(function (user) {
                                if (isNaN(user.users)) {
                                    jsonObjectIndex[user.users._id] = user.users;
                                } else {
                                    user.users = jsonObjectIndex[user.users];
                                }
                            });

                            this.costCenterStatus = data;
                        }).error(function () {
                            showAlert("Error al realizar la solicitud", {type: 3});
                        });
                    },
                    getAllBudgetsStatus: function () {
                        this.getAllBudgetsStatusByRole();
                        $("#budgetsStatus").modal("show");
                    },
                    cancelBudgetStatus: function () {
                        $("#budgetsStatus").modal("hide");
                    },
                    getReportModal: function (costCenter, year) {
                        this.costCenter = costCenter;
                        this.year = year;
                        this.getBudgetsReport(costCenter, year);
                        this.flag = false;
                        $("#budgetsStatus").modal("hide");
                        $("#budgetInformation").modal("show");
                    },
                    findDisCCByCC: function () {
                        this.$http.get(ROOT_URL + "/distributor-cost-center/cost-center/" + this.selected.costCenter.idCostCenter).success(function (data) {
                            this.distributorCostCenter = data;
                        });
                    },
                    openModalHighConcept: function () {
                        this.conceptBudgetData.idCostCenter = null;
                        this.conceptBudgetData.idAccountingAccount = null;
                        this.conceptBudgetData.idBudgetNature = null;
                        this.conceptBudgetData.idBudgetType = null;
                        this.findDisCCByCC();
                        this.findAllConcept();
                        $("#modalConcept").modal("show");
                    },
                    findAllConcept: function () {
                        this.$http.get(ROOT_URL + "/concept-budget").success(function (data) {
                            this.createSelectForConcepts(data);
                        })
                    },
                    createSelectForConcepts : function (concepts) {
                        var self = this;
                        this.conceptsSelectize = $('#concept-budget').selectize({
                            maxItems: 1,
                            valueField: 'idConceptBudget',
                            labelField: 'nameConcept',
                            searchField: 'nameConcept',
                            options: concepts,
                            create: function (input, callback) {
                                self.$http.post(ROOT_URL + '/concept-budget/add', {
                                    budgetConcept: input
                                }).success(function (data){
                                    showAlert('Concepto guardado');
                                    self.findAllConcept();
                                    callback(data);
                                }).error(function (){
                                    callback();
                                });
                            },
                            render: {
                                option_create: function(data, escape) {
                                    return '<div data-selectable class="create">' +
                                        'Agregar <strong>' + escape(data.input) + '</strong>' +
                                        '</div>'
                                }
                            }
                        });

                        return this.conceptsSelectize;
                    },
                    addConcept: function () {
                        this.conceptBudgetData.idCostCenter = this.selected.costCenter.idCostCenter;
                        this.conceptBudgetData.idBudgetNature = this.selected.budgetNature.idBudgetNature;
                        this.conceptBudgetData.idBudgetType = this.selected.budgetType.idBudgetType;
                        this.conceptBudgetData.idConceptBudget = this.conceptsSelectize[0].value;

                        if (this.conceptBudgetData.idCostCenter != null
                            && this.conceptBudgetData.idBudgetNature != null
                            && this.conceptBudgetData.idBudgetType != null
                            && this.conceptBudgetData.idAccountingAccount != null
                            && this.conceptBudgetData.idConceptBudget.length > 0){
                            this.$http.post(ROOT_URL + "/budgets/save", JSON.stringify(this.conceptBudgetData)).success(function () {
                                this.getBudgets(this.selected.year, this.selected.budgetType, this.selected.budgetNature, this.selected.budgetCategory);
                                $("#modalConcept").modal("hide");
                                showAlert("Concepto agregado exitosamente");
                            }).error(function () {
                                showAlert("Error al guardar el concepto", {type: 3});
                            });
                        }else{
                            showAlert("Es necesario llenar todos los campos", {type: 3});
                        }

                    },
                    closeModalConcept: function () {
                        $("#modalConcept").modal("hide");
                    }
                },
                filters: {
                    currencyDisplay: {
                        read: function (val) {
                            return val.formatMoney(2, '');
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
            function format(input)
            {
                var num = input.value.replace(/\,/g,'');
                if(!isNaN(num)){
                    num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g,'$1,');
                    num = num.split('').reverse().join('').replace(/^[\,]/,'');
                    num = num.split('').join('').replace(/^[\,]/,'');
                    var inicio = num.substring(0, 1);
                    if (inicio == '0') {
                        showAlert("Corregir cantidad", {type:3});
                        input.value = '';
                    }else{
                        input.value = num.split('').join('').replace(/^[\,]/,'');
                    }
                }

                else{
                    showAlert("Solo se permiten numeros", {type:3});
                    input.value = '';
                }
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
                            contar = contar.replace('.00', '');
                            obj.value = contar;
                            format(input);
                        }else
                        {
                            contar = contar.replace('.,', ',');
                            contar = contar.replace(',.', ',');
                            contar = contar.replace('.00', '');
                            obj.value = contar + '.00';
                            format(input);
                        }
                    }
                }
            }
            function cleanField(obj){
                var inicial = obj.value;
                if (obj.value == '0' || obj.value == '0.00'){
                    obj.value = '';
                }else {
                    return false;
                }
            }
        </script>
    </jsp:attribute>
    <jsp:attribute name="styles">
        <style>
            @media (min-width: 992px) {
                .container-scroll {
                    overflow-x: auto;
                }

                .container-scroll > .row {
                    width: 1500px;
                }
            }
        </style>
    </jsp:attribute>
    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-5">
                        <h2>CAPTURA DE PRESUPUESTO</h2>
                    </div>
                    <div class="col-md-3">
                        <h2 v-if="(authorizationBudget.idCCostCenterStatus == 5 || authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 3 || authorizationBudget.idCCostCenterStatus == 4) && authorizationBudget.adjustment != 1"
                                              style="color: #1b6d85">ANUAL</h2>
                        <h2 v-if="authorizationBudget.adjustment == 1"
                            style="color: #1b6d85">AJUSTADO</h2>
                    </div>
                    <div class="col-md-2">
                        <div v-if="budgets.length > 0">
                            <label>Buscar por concepto</label>
                            <input class="form-control" v-model="selected.concept" required>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <button style="margin-top: 25px" class="btn btn-warning"
                                @click="getAllBudgetsStatus()">
                            Estatus Presupuesto
                        </button>
                    </div>
                </div>
                <br>
                <br>
                <div class="row">
                    <form v-on:submit.prevent="getBudgets">
                        <div class="row">
                            <div class="col-md-3">
                                <label>Centro de beneficios/Centro de costos</label>
                                <select v-model="selected.costCenter" class="form-control" @change="onChangeFilter"
                                        data-live-search="true" required>
                                    <option v-for="costCenter in costCenterList" :value="costCenter">
                                        {{costCenter.name}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label>Clasificación</label>
                                <select v-model="selected.budgetType" class="form-control multiselect-ui"
                                        @change="onChangeFilterClassification"
                                        required>
                                    <option v-for="budgetType in budgetTypes" :value="budgetType">
                                        {{budgetType.budgetType}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label>Tipo de gasto</label>
                                <select v-model="selected.budgetNature" class="form-control" @change="onChangeFilter"
                                        :disabled="disableNature">
                                    <option v-for="budgetNature in budgetNatureList" :value="budgetNature">
                                        {{budgetNature.budgetNature}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label>Año</label>
                                <select v-model="selected.year" class="form-control" @change="onChangeFilter" required>
                                    <option v-for="year in years" :value="year">
                                        {{year}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <button type="submit" style="margin-top: 25px" class="btn btn-info">Seleccionar</button>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-md-10"
                                 v-if="authorizationBudget.idCCostCenterStatus == 1 && budgets.length > 0">
                                <h2 style="color: dimgray">
                                    <span class="glyphicon glyphicon-info-sign">{{authorizationBudget.cCostCenterStatus.costCenterStatus}}
                                        </span>
                                </h2>
                            </div>
                            <div class="col-md-10"
                                 v-if="authorizationBudget.idCCostCenterStatus == 2 && budgets.length > 0">
                                <h2 style="color: orange">
                                    <span class="glyphicon glyphicon-question-sign">{{authorizationBudget.cCostCenterStatus.costCenterStatus}}
                                    </span>
                                </h2>
                            </div>
                            <div class="col-md-10"
                                 v-if="authorizationBudget.idCCostCenterStatus == 3 && budgets.length > 0">
                                <h2 style="color: firebrick">
                                    <span class="glyphicon glyphicon-remove-sign">{{authorizationBudget.cCostCenterStatus.costCenterStatus}}
                                    </span>
                                </h2>
                            </div>
                            <div class="col-md-10"
                                 v-if="authorizationBudget.idCCostCenterStatus == 4 && budgets.length > 0">
                                <h2 style="color: green">
                                    <span class="glyphicon glyphicon-ok-sign">{{authorizationBudget.cCostCenterStatus.costCenterStatus}}
                                    </span>
                                </h2>
                            </div>
                            <div class="col-md-10"
                                 v-if="authorizationBudget.idCCostCenterStatus == 5 && budgets.length > 0">
                                <h2 style="color: orangered">
                                    <span class="glyphicon glyphicon-edit">{{authorizationBudget.cCostCenterStatus.costCenterStatus}}
                                    </span>
                                </h2>
                            </div>
                            <div class="col-md-2 pull-right"
                                 v-if="budgets.length > 0 && authorizationBudget.idCCostCenterStatus == 4">
                                <button style="margin-top: 25px" class="btn btn-success" @click="showRequestModification">
                                    Solicitar modificación
                                </button>
                            </div>
                            <div class="col-md-2 pull-right"
                                 v-if="(authorizationBudget.idCCostCenterStatus == 3 || authorizationBudget.idCCostCenterStatus == 1) && budgets.length > 0">
                                <button style="margin-top: 25px; margin-left: 13px" class="btn btn-success" @click="showSendValidation">
                                    Enviar a validación
                                </button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-7" v-if="budgets.length > 0">
                                <h3 align="right">Total centro de costo: <b class="text-primary">{{totalCostCenter | currency}}</b>
                                </h3>
                            </div>
                            <div class="col-md-2" v-if="budgets.length > 0">
                                <button style="margin-top: 15px" class="btn btn-info" type="button"
                                        @click="openModalHighConcept()">
                                    Alta de concepto
                                </button>
                            </div>

                           <!-- <div class="col-md-2"
                                 v-if="budgets.length > 0 && authorizationBudget.idCCostCenterStatus == 4">
                                <button style="margin-top: 25px" class="btn btn-default" @click="showCopyBudgetModal">
                                    Copiar presupuesto
                                </button>
                            </div>-->
                        </div>
                    </form>
                </div>

                <div v-if="searching" class="col-md-12"
                     style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>
                <div v-else="!searching">
                    <div class="row" v-for="(indexOfBudget, budget) in budgets | filterBy selected.concept in 'budget'"
                         style="margin-left: 0px; margin-right: 0px" v-if="budgets.length > 0 && budget!=null">
                        <div class="col-xs-12" style="background: darkgray">
                            <div class="row">
                                <div class="col-md-7">
                                    <h4><b>{{budget.budget.conceptBudget.nameConcept}}</b></h4>
                                </div>
                                <div class="col-md-5 text-right">
                                    <h4 style="color: black">Total: <b class="text-primary" style="color: black">{{budget.totalBudgetAmount
                                        |currency}}</b></h4>
                                </div>
                            </div>
                            <div class="row"
                                 style="margin-left: 0px; margin-right: 0px">
                                <div class="well">
                                    <div class="row" style="margin-left: 0px">
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Ene</label>
                                            <input style="border-color: #61c4b8; font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.januaryBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Feb</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.februaryBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Mar</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.marchBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Abr</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.aprilBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>May</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.mayBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Jun</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.juneBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Jul</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.julyBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Ago</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.augustBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Sep</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.septemberBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Oct</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.octoberBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Nov</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.novemberBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Dic</label>
                                            <input style="font-size: 11px" type="text" class="form-control"
                                                   v-model="budget.decemberBudgetAmount | currencyDisplay"
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onclick="return cleanField(this)"
                                                   onkeypress="return validateFloatKeyPress(this,event)"
                                                   onInput="format(this)" onblur="ponerCeros(this)"
                                                   :disabled="authorizationBudget.idCCostCenterStatus == 2 || authorizationBudget.idCCostCenterStatus == 4 || authorizationBudget.idCCostCenterStatus == 5">
                                        </div>
                                        <br>
                                        <div class="col-md-8"
                                             v-if="authorizationBudget.idCCostCenterStatus == 3 || authorizationBudget.idCCostCenterStatus == 1">
                                            <div class="checkbox">
                                                <label style="margin-top: 27px">
                                                    <input style="border-color: #61c4b8" type="checkbox"
                                                           v-model="budget.equals"
                                                           @change="equalsImport(indexOfBudget,budget)"> Aplicar a
                                                    todos
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-md-2" align="right" style="width: 25%"
                                             v-if="authorizationBudget.idCCostCenterStatus == 3 || authorizationBudget.idCCostCenterStatus == 1">
                                            <button style="margin-top: 27px" type="button" class="btn btn-warning"
                                                    @click="deleteBudget(budget, budget.budget.idBudget)">
                                                <!--@click="deleteBudget(subbudget, subbudget.budget.idBudget)">-->
                                                Borrar datos
                                            </button>
                                        </div>
                                        <div class="col-md-1" aria-hidden="true" align="right" style="width: 5%"
                                             v-if="authorizationBudget.idCCostCenterStatus == 3 || authorizationBudget.idCCostCenterStatus == 1">
                                            <button style="margin-top: 27px" type="button" class="btn btn-success"
                                                    @click="saveBudget(budget, budget.budget.idBudget)">
                                                Guardar
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <!--Dialogs-->
                <div class="modal fade bd-example-modal-lg" id="copyBudgetModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <form v-on:submit.prevent="onClickAcept">
                                <div class="modal-header">
                                    <button type="button" class="close" aria-label="Close"
                                            @click="closeCopyBudgetModal">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="copyBudgetModalLabel">Copiar presupuesto</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-md-6 text-center"><p>Años anteriores</p></div>
                                        <div class="col-md-6 text-center"><p>Años posteriores</p></div>
                                        <br>
                                        <div class="col-md-1">
                                            <p>De</p>
                                        </div>
                                        <div class="col-md-5">
                                            <select v-model="selected.yearFromCopy" class="form-control" required>
                                                <option v-for="year in years" :value="year">
                                                    {{year}}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-md-1">
                                            <p>a</p>
                                        </div>
                                        <div class="col-md-5">
                                            <select v-model="selected.yearToCopy" class="form-control" required>
                                                <option v-for="year in years" :value="year">
                                                    {{year}}
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button class="btn btn-success">Guardar</button>
                                    <button type="button" class="btn btn-danger" @click="closeCopyBudgetModal">
                                        Cancelar
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="overwriteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-label="Close"
                                        @click="closeOverwriteModal"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="overwriteModalLabel">Aviso</h4>
                            </div>
                            <div class="modal-body">
                                {{errorMessage}}
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" @click="closeOverwriteModal">Cancelar
                                </button>
                                <button type="button" class="btn btn-success" @click="onClickOverwrite">Aceptar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="sendValidation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-label="Close" @click="closeSendValidation">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="sendValidationLabel"><b>Revisar presupuesto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp ¿Deseas revisar presupuesto antes de enviar?</p>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <!--<button class="btn btn-success" @click="submitValidation">Guardar</button>-->
                                <button class="btn btn-success" @click="showBudget">Si</button>
                                <button class="btn btn-warning" @click="showConfirm">No</button>
                                <button class="btn btn-default" @click="closeSendValidation">Cancelar</button>
                                <!--<button type="button" class="btn btn-default" @click="closeSendValidation">Cancelar</button>-->
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="budgetInformation" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-label="Close" @click="closeBudget">
                                    <span aria-hidden="true">&times;</span></button>
                            </div>
                            <div class="modal-body">
                                <div class="container-fluid container-scroll"
                                     style="font-size: 11px; height:70%; overflow-y:auto;">
                                    <div class="row table-header col-md-12" v-if="flag == true">
                                        <div class="col-md-12">
                                            <div class="col-md-3"><b>Centro de costos: {{selected.costCenter.name}}</b>
                                            </div>
                                            <div class="col-md-1"><b>Año: {{selected.year}}</b></div>
                                            <div class="col-md-3"><b>TOTAL CENTRO DE COSTOS: {{totalCostCenter
                                                |currency}}</b></div>
                                            <div class="col-md-1"><b></b></div>
                                            <div class="col-md-1"><b></b></div>
                                            <div class="col-md-1"><b></b></div>
                                        </div>
                                    </div>
                                    <div class="row table-header col-md-12" v-if="flag == false">
                                        <div class="col-md-12">
                                            <div class="col-md-3"><b>Centro de costos: {{costCenter.name}}</b>
                                            </div>
                                            <div class="col-md-1"><b>Año: {{year}}</b></div>
                                            <div class="col-md-3"><b></b></div>
                                            <div class="col-md-1"><b></b></div>
                                            <div class="col-md-1"><b></b></div>
                                            <div class="col-md-1"><b></b></div>
                                        </div>
                                    </div>
                                    <div class="row table-header col-md-12" style="background: #23527c ; color: white;">
                                        <div class="col-md-12">
                                            <div class="col-md-1">Clasificación</div>
                                            <div class="col-md-1">Tipo de Gasto</div>
                                            <div class="col-md-1"></div>
                                            <div class="col-md-1">Total Año Anterior</div>
                                            <div class="col-md-7 text-center">
                                                <div class="col-xs-1">Enero</div>
                                                <div class="col-xs-1">Febrero</div>
                                                <div class="col-xs-1">Marzo</div>
                                                <div class="col-xs-1">Abril</div>
                                                <div class="col-xs-1">Mayo</div>
                                                <div class="col-xs-1">Junio</div>
                                                <div class="col-xs-1">Julio</div>
                                                <div class="col-xs-1">Agosto</div>
                                                <div class="col-xs-1">Septiembre</div>
                                                <div class="col-xs-1">Octubre</div>
                                                <div class="col-xs-1">Noviembre</div>
                                                <div class="col-xs-1">Diciembre</div>
                                            </div>
                                            <div class="col-md-1">Total Año Actual</div>
                                        </div>
                                    </div>
                                    <div class="row table-header col-md-12" style="background: #31708f; color: white">
                                        <div class="col-md-12"
                                             v-for="(indexOfBudgetReport, budgetsReport) in budgetsReports">
                                            <div class="col-md-1">{{budgetsReport.budget.budgetNature.budgetNature}}
                                            </div>
                                            <div class="col-md-1">{{budgetsReport.budget.budgetType.budgetType}}</div>
                                            <div class="col-md-1">{{budgetsReport.budget.conceptBudget.nameConcept}}
                                            </div>
                                            <div class="col-md-1">{{budgetsReport.totalLastYearAmount | currency}}</div>
                                            <div class="col-md-7 text-center">
                                                <div class="col-xs-1">{{budgetsReport.januaryBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.februaryBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.marchBudgetAmount|currency}}</div>
                                                <div class="col-xs-1">{{budgetsReport.aprilBudgetAmount|currency}}</div>
                                                <div class="col-xs-1">{{budgetsReport.mayBudgetAmount|currency}}</div>
                                                <div class="col-xs-1">{{budgetsReport.juneBudgetAmount|currency}}</div>
                                                <div class="col-xs-1">{{budgetsReport.julyBudgetAmount|currency}}</div>
                                                <div class="col-xs-1">{{budgetsReport.augustBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.septemberBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.octoberBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.novemberBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.decemberBudgetAmount|currency}}
                                                </div>
                                            </div>
                                            <div class="col-md-1">{{budgetsReport.totalBudgetAmount | currency}}</div>
                                        </div>
                                        <div class="col-md-12">
                                        </div>
                                        <div class="col-md-12">
                                        </div>
                                        <div class="col-md-12">
                                        </div>
                                        <div class="col-md-12">
                                        </div>
                                        <div class="col-md-12">
                                            <div class="col-md-1"></div>
                                            <div class="col-md-1"></div>
                                            <div class="col-md-1">TOTALES:</div>
                                            <div class="col-md-1">{{budgetTotals.totalLastYearAmount | currency}}</div>
                                            <div class="col-md-7 text-center">
                                                <div class="col-xs-1">{{budgetTotals.januaryBudgetAmount |currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetTotals.februaryBudgetAmount |currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetTotals.marchBudgetAmount |currency}}</div>
                                                <div class="col-xs-1">{{budgetTotals.aprilBudgetAmount |currency}}</div>
                                                <div class="col-xs-1">{{budgetTotals.mayBudgetAmount |currency}}</div>
                                                <div class="col-xs-1">{{budgetTotals.juneBudgetAmount |currency}}</div>
                                                <div class="col-xs-1">{{budgetTotals.julyBudgetAmount |currency}}</div>
                                                <div class="col-xs-1">{{budgetTotals.augustBudgetAmount |currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetTotals.septemberBudgetAmount |currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetTotals.octoberBudgetAmount |currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetTotals.novemberBudgetAmount |currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetTotals.decemberBudgetAmount |currency}}
                                                </div>
                                            </div>
                                            <div class="col-md-1">{{budgetTotals.totalCurrentYear | currency}}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <br>
                            <div class="modal-footer">
                                <div v-if="flag == true">
                                    <button class="btn btn-success" @click="showConfirm">Aceptar</button>
                                    <button class="btn btn-warning" @click="closeBudget">Modificar</button>
                                </div>
                                <div v-if="flag == false">
                                    <button class="btn btn-danger" @click="closeBudget">Salir</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="confirmBudget" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-label="Close" @click="closeConfirm">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="confirmBudgetLabel"><b>Enviar presupuesto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp El presupuesto para el centro de costos
                                            {{selected.costCenter.name}} será enviado a:</p>
                                    </div>
                                </div>
                                <div class="row" v-for="email in emails.emailRecipientsList">
                                    <label>&nbsp &nbsp &nbsp {{email.emailAddress}}</label>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="submitValidation">Aceptar</button>
                                <button class="btn btn-default" @click="closeConfirm">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="requestModification" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-label="Close"
                                        @click="closeRequestModification">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="requestModificationLabel"><b>Modificar presupuesto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp Se enviará una solicitud de modificación del centro de costos {{selected.costCenter.name}}
                                            será enviado a: </p>
                                        <div class="row" v-for="email in emails.emailRecipientsList">
                                            <label>&nbsp &nbsp &nbsp &nbsp{{email.emailAddress}}</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="sendModifyBudget">Aceptar</button>
                                <button class="btn btn-default" @click="closeRequestModification">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade bd-example-modal-lg" id="budgetsStatus" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-label="Close"
                                        @click="cancelBudgetStatus()">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="budgetsStatusLabel"><b>Estatus Presupuesto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <table class="table table-striped">
                                            <thead>
                                            </thead>
                                            <tbody>
                                            <tr v-for="costCenter in costCenterStatus"
                                                v-if="costCenter.idCCostCenterStatus != 1">
                                                <td class="col-xs-2"></td>
                                                <td class="col-xs-1"><label style="margin-top: 10px">{{costCenter.costCenter.name}}</label>
                                                </td>
                                                <td class="col-xs-1"><label
                                                        style="margin-top: 10px">{{costCenter.year}}</label></td>
                                                <td class="col-xs-1"></td>
                                                <td class="col-xs-1" style="margin-top: 12px">
                                                <span class="glyphicon glyphicon-triangle-bottom"
                                                      style="color: #7A7A7A; font-size: 200%"
                                                      v-if="costCenter.idCCostCenterStatus == 1"></span>
                                                    <span class="glyphicon glyphicon-triangle-bottom"
                                                          style="color: #DF9A1B; font-size: 200%"
                                                          v-if="costCenter.idCCostCenterStatus == 2"></span>
                                                    <span class="glyphicon glyphicon-triangle-bottom"
                                                          style="color: #EE0909; font-size: 200%"
                                                          v-if="costCenter.idCCostCenterStatus == 3"></span>
                                                    <span class="glyphicon glyphicon-triangle-bottom"
                                                          style="color: #457a1a; font-size: 200%"
                                                          v-if="costCenter.idCCostCenterStatus == 4"></span>
                                                    <span class="glyphicon glyphicon-triangle-bottom"
                                                          style="color: #457a1a;  font-size: 200%"
                                                          v-if="costCenter.idCCostCenterStatus == 5"></span>
                                                </td>
                                                <td class="col-xs-1"></td>
                                                <td class="col-xs-1" style="margin-top: 12px">
                                                    <div class="col-xs-1" style="margin-right: 50px; margin-left: 50px">
                                                        <button class="label btn-default"
                                                                v-if="costCenter.idCCostCenterStatus == 1" @click="getReportModal(costCenter.costCenter,costCenter.year)">Sin enviar
                                                        </button>
                                                    </div>
                                                    <div class="col-xs-1" style="margin-right: 50px; margin-left: 50px">
                                                        <button class="label btn-warning"
                                                                v-if="costCenter.idCCostCenterStatus == 2" @click="getReportModal(costCenter.costCenter,costCenter.year)">Sin autorizar</button>
                                                    </div>
                                                    <div class="col-xs-1" style="margin-right: 50px; margin-left: 50px">
                                                        <button class="label btn-danger"
                                                                v-if="costCenter.idCCostCenterStatus == 3" @click="getReportModal(costCenter.costCenter,costCenter.year)">Rechazado
                                                        </button>
                                                    </div>
                                                    <div class="col-xs-1" style=" margin-right: 50px; margin-left: 50px">
                                                        <button class="label btn-success"
                                                                v-if="costCenter.idCCostCenterStatus == 4 || costCenter.idCCostCenterStatus == 5" @click="getReportModal(costCenter.costCenter,costCenter.year)">
                                                            Autorizado
                                                        </button>
                                                    </div>
                                                </td>
                                                <td class="col-xs-2"></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" @click="cancelBudgetStatus()">Cancelar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="modalConcept" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-label="Close"
                                        @click="closeModalConcept">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title"><b>Alta de concepto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-4">
                                            <label>Centro de costos</label>
                                            <input class="form-control" v-model="selected.costCenter.name" disabled>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Cuenta contable</label>
                                            <select class="form-control"v-model="conceptBudgetData.idAccountingAccount">
                                                <option v-for="disCC in distributorCostCenter" :value="disCC.idAccountingAccount">
                                                    {{disCC.accountingAccounts.acronyms}}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Concepto</label>
                                            <select class="form-control" id="concept-budget">
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="addConcept()">Aceptar</button>
                                <button class="btn btn-default" @click="closeModalConcept">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </jsp:body>
</t:template>
