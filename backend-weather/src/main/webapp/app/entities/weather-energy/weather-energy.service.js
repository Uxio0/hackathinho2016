(function() {
    'use strict';
    angular
        .module('weatheropendataApp')
        .factory('WeatherEnergy', WeatherEnergy);

    WeatherEnergy.$inject = ['$resource', 'DateUtils'];

    function WeatherEnergy ($resource, DateUtils) {
        var resourceUrl =  'api/weather-energies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDateTime = DateUtils.convertLocalDateFromServer(data.createdDateTime);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdDateTime = DateUtils.convertLocalDateToServer(copy.createdDateTime);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdDateTime = DateUtils.convertLocalDateToServer(copy.createdDateTime);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
