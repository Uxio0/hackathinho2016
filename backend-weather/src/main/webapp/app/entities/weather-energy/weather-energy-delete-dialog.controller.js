(function() {
    'use strict';

    angular
        .module('weatheropendataApp')
        .controller('WeatherEnergyDeleteController',WeatherEnergyDeleteController);

    WeatherEnergyDeleteController.$inject = ['$uibModalInstance', 'entity', 'WeatherEnergy'];

    function WeatherEnergyDeleteController($uibModalInstance, entity, WeatherEnergy) {
        var vm = this;

        vm.weatherEnergy = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WeatherEnergy.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
