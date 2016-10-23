(function() {
    'use strict';

    angular
        .module('weatheropendataApp')
        .controller('WeatherEnergyDialogController', WeatherEnergyDialogController);

    WeatherEnergyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WeatherEnergy'];

    function WeatherEnergyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WeatherEnergy) {
        var vm = this;

        vm.weatherEnergy = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.weatherEnergy.id !== null) {
                WeatherEnergy.update(vm.weatherEnergy, onSaveSuccess, onSaveError);
            } else {
                WeatherEnergy.save(vm.weatherEnergy, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weatheropendataApp:weatherEnergyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDateTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
