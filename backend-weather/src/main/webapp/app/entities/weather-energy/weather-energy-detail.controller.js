(function() {
    'use strict';

    angular
        .module('weatheropendataApp')
        .controller('WeatherEnergyDetailController', WeatherEnergyDetailController);

    WeatherEnergyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WeatherEnergy'];

    function WeatherEnergyDetailController($scope, $rootScope, $stateParams, previousState, entity, WeatherEnergy) {
        var vm = this;

        vm.weatherEnergy = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weatheropendataApp:weatherEnergyUpdate', function(event, result) {
            vm.weatherEnergy = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
