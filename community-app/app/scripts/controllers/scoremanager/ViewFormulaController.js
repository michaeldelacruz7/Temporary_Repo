(function (module) {
    mifosX.controllers = _.extend(module, {
        ViewFormulaController: function (scope, routeParams, resourceFactory, location, creditRuleServices) {
            scope.formula = [];
            scope.type = "0";
            scope.typeName;

            scope.getFormulaData = function(data){
                scope.formula = creditRuleServices.getFormulaIndex(routeParams.id);
            };

            // resourceFactory.ruleResources.get(function(data)
            // {
            //     scope.rule = data;
            //     types.forEach(function(type){
            //         if(type.value == scope.rule.type){
            //             scope.rule.type = type.type;
            //         }
            //     });
            // });

            scope.routeTo = function(){
                location.path('/editformula/'+ routeParams.id);
            };

            scope.getFormulaData();
        }
    });
    mifosX.ng.application.controller('ViewFormulaController', ['$scope', '$routeParams', 'ResourceFactory', '$location', 'creditRuleServices', mifosX.controllers.ViewFormulaController]).run(function ($log) {
        $log.info("ViewFormulaController initialized");
    });
}(mifosX.controllers || {}));