(function() {
    'use strict';
    angular
        .module('weatheropendataApp')
        .factory('Weather', Weather);

    Weather.$inject = ['$resource', 'DateUtils'];

    function Weather ($resource, DateUtils) {
        var resourceUrl =  'api/weathers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.sysSunrise = DateUtils.convertDateTimeFromServer(data.sysSunrise);
                        data.sysSunset = DateUtils.convertDateTimeFromServer(data.sysSunset);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
