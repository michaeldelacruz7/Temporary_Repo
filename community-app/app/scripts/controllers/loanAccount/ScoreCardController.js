(function (module) {
    mifosX.controllers = _.extend(module, {
        ScoreCardController: function (scope, routeParams, resourceFactory, location, creditRuleServices) {
            scope.rules = [];
            scope.formulas = [];
            scope.dataModel = [];
            scope.data = [];
            scope.localData = [];
            scope.creditScore = 0;
            scope.loanId = 0;

            scope.init = function(){
                scope.loanId = routeParams.loanId;
                resourceFactory.scoreManagerResource.getScoreRuleList({
                }, function (data) {
                    scope.localData = data.filter(function(rule){
                        return rule.status != "Disabled";
                    });
                    scope.arrangeData();
                    scope.dataModel = new Array(scope.rules.length);
                });
                resourceFactory.scoreFormulaResource.getScoreFormulaList({
                }, function (data) {
                    scope.formulas = data.filter(function(formula){
                        return formula.status != "Disabled";
                    });
                });
            };

            scope.changeFormula = function(formula){
                scope.formula = formula;
            }

            scope.arrangeData = function(){
                scope.localData.forEach(function(datum){
                    var rule = [];
                    rule.id = datum.id;
                    rule.name = datum.ruleName;
                    rule.type = datum.ruleType;
                    if(datum.ruleType == "2"){
                        rule.typeData = [];
                        datum.ruleTypeDataList.forEach(function(value, index){
                            var x = [];
                            x.name = value.choiceName;
                            x.value = value.relativeValue;
                            rule.typeData.push(x);
                        });
                    }
                    scope.rules.push(rule);
                });
            };

            scope.submit = function(){
                scope.data = [];
                scope.dataModel.forEach(function(data, index){
                    var model = {"id":index, "value":data};
                    scope.data.push(model);
                });
                scope.creditScore = scope.formula.formula;
                scope.data.forEach(function(rule){
                    if(scope.localData[rule.id].ruleType == 1){
                        scope.localData[rule.id].ruleTypeDataList.forEach(function(typeDatum){
                            if(rule.value >= typeDatum.minValue && rule.value <= typeDatum.maxValue){
                                var value = 0;
                                value = typeDatum.relativeValue * (!scope.localData[rule.id].weightedValue ? 1 : scope.localData[rule.id].weightedValue);
                                scope.creditScore = scope.creditScore.replace(scope.localData[rule.id].tag, value);
                            }
                        });
                    }else{
                        var value = 0;
                        value = rule.value * (!scope.localData[rule.id].weightedValue ? 1 : scope.localData[rule.id].weightedValue);
                        scope.creditScore = scope.creditScore.replace(scope.localData[rule.id].tag, value);
                    }
                });
                scope.creditScore = eval(scope.creditScore);
            }

            scope.init();
        }
    });
    mifosX.ng.application.controller('ScoreCardController', ['$scope', '$routeParams', 'ResourceFactory', '$location', 'creditRuleServices', mifosX.controllers.ScoreCardController]).run(function ($log) {
        $log.info("ScoreCardController initialized");
    });
}(mifosX.controllers || {}));
