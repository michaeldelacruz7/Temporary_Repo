(function (module) {
    mifosX.controllers = _.extend(module, {
        EditFormulaController: function (scope, routeParams, resourceFactory, location, creditRuleServices) {
            scope.rules = [];
            scope.formulaData = [];
            scope.formulaname = "";
            scope.formula = "";

            scope.addText = function(index){
                var tagData = scope.rules[index].tag;
                scope.formula = scope.formula.concat(" " + tagData + " ");
            }

            scope.submit = function(){
                var data = {
                    "formulaname":scope.formulaname,
                    "formula":scope.formula,
                    "status":"Enabled"
                };
                creditRuleServices.setFormulaIndex(routeParams.id, data);
                location.path('/scoremanager/');
            }

            scope.getFormulaData = function(){
                resourceFactory.scoreManagerResource.getScoreRuleList({
                }, function (data) {
                    scope.rules = data.filter(function(rule){
                        return rule.status != "Disabled";
                    });
                });
                scope.formulaData = creditRuleServices.getFormulaIndex(routeParams.id);
                scope.formula = scope.formulaData.formula;
                scope.formulaname = scope.formulaData.formulaname;
            };

            // resourceFactory.ruleResources.get(function(data)
            // {
            //     scope.attribute = data.attribute;
            //     scope.weightedvalue = data.weightedValue;
            //     scope.type = data.type;
            //     for(i = 0; i < data.typeData.length; i++){
            //         scope.displayedForms.push(forms[scope.type]);
            //     }
            //     scope.typeData = data.typeData;
            // });

            scope.routeTo = function(){
                location.path('/viewformula/'+ routeParams.id);
            };

            scope.getFormulaData();
        }
    });
    mifosX.ng.application.controller('EditFormulaController', ['$scope', '$routeParams', 'ResourceFactory', '$location', 'creditRuleServices', mifosX.controllers.EditFormulaController]).run(function ($log) {
        $log.info("EditFormulaController initialized");
    });
}(mifosX.controllers || {}));