(function (module) {
    mifosX.controllers = _.extend(module, {
        ViewCreditScoreManagerController: function (scope, resourceFactory, location, $uibModal, creditRuleServices) {
        	scope.rules = [];
            scope.formulas = [];
			scope.actualRules = [];
            scope.formulas = [];
            scope.types = [
                {
                    "value":"1",
                    "type":'Range'
                },
                {
                    "value":"2",
                    "type":'Choice'  
                }
            ];
            scope.totalRules = 0;
            scope.type = [];
            scope.rulesPerPage = 15;

            scope.routeTo = function (id) {
                location.path('/viewrule/' + id);
            };

            scope.routeToFormula = function (id) {
                location.path('/viewformula/' + id);
            };

            scope.changeStatus = function(id, status){
                if("Enabled" === status){
                    data = {
                        "status": "Disabled"
                    };
                    resourceFactory.ruleStatusResource.updateStatus({ruleId: id},data, function(data){
                        scope.initPage();
                    });
                }else{
                    data = {
                        "status": "Enabled"
                    };
                    resourceFactory.ruleStatusResource.updateStatus({ruleId: id},data, function(data){
                        scope.initPage();
                    });
                }
            };

            scope.changeStatusFormula = function(id, status){
                if("Enabled" === status){
                    var data = creditRuleServices.getFormulaIndex(id);
                    data.status = "Disabled";
                    creditRuleServices.setFormulaIndex(id, data);
                }else{
                    var data = creditRuleServices.getFormulaIndex(id);
                    data.status = "Enabled";
                    creditRuleServices.setFormulaIndex(id, data);
                }
            };

            scope.setRules = function(data){
                scope.rules = data;
            };

            scope.initPage = function () {
                var items = resourceFactory.scoreManagerResource.getScoreRuleList({
                }, function (data) {
                    scope.rules = data;
                    scope.rules.forEach(function(rule){
                        scope.types.forEach(function(type){
                            if(rule.ruleType == type.value){
                                scope.type.push(type.type);
                            }
                        });
                    });
                    scope.totalRules = scope.rules.length;
                });
                scope.formulas = creditRuleServices.getFormulas();
            };
            scope.initPage();

            scope.openModal = function () {
                $uibModal.open({
                    templateUrl: 'modal.html',
                    controller: modalUpdateCtrl
                });
            };
            var modalUpdateCtrl = function ($scope, $uibModalInstance) {
                $scope.save = function () {
                    var data = JSON.parse(this.json);
                    data.forEach(function(datum){
                        resourceFactory.scoreManagerResource.addScoreRule(datum, function(data){});
                    });
                    $uibModalInstance.close('modal');
                    scope.initPage();
                };
                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            };


        }
    });
    mifosX.ng.application.controller('ViewCreditScoreManagerController', ['$scope', 'ResourceFactory', '$location', '$uibModal', 'creditRuleServices', mifosX.controllers.ViewCreditScoreManagerController]).run(function ($log) {
        $log.info("ViewCreditScoreManagerController initialized");
    });
}(mifosX.controllers || {}));