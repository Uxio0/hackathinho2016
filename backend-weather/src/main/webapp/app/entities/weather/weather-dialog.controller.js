(function() {
    'use strict';

    angular
        .module('weatheropendataApp')
        .controller('WeatherDialogController', WeatherDialogController);

    WeatherDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Weather'];

    function WeatherDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Weather) {
        var vm = this;

        vm.weather = entity;
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
            if (vm.weather.id !== null) {
                Weather.update(vm.weather, onSaveSuccess, onSaveError);
            } else {
                Weather.save(vm.weather, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weatheropendataApp:weatherUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.sysSunrise = false;
        vm.datePickerOpenStatus.sysSunset = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
