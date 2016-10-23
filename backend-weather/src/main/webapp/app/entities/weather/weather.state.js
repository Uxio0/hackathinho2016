(function() {
    'use strict';

    angular
        .module('weatheropendataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('weather', {
            parent: 'entity',
            url: '/weather?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weatheropendataApp.weather.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/weather/weathers.html',
                    controller: 'WeatherController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('weather');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('weather-detail', {
            parent: 'entity',
            url: '/weather/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weatheropendataApp.weather.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/weather/weather-detail.html',
                    controller: 'WeatherDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('weather');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Weather', function($stateParams, Weather) {
                    return Weather.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'weather',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('weather-detail.edit', {
            parent: 'weather-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather/weather-dialog.html',
                    controller: 'WeatherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Weather', function(Weather) {
                            return Weather.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('weather.new', {
            parent: 'weather',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather/weather-dialog.html',
                    controller: 'WeatherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lon: null,
                                lat: null,
                                city: null,
                                weatherMain: null,
                                weatherDescription: null,
                                weatherIcon: null,
                                base: null,
                                mainTemp: null,
                                mainPressure: null,
                                mainHumidity: null,
                                mainTempMin: null,
                                mainTempMax: null,
                                mainSeaLevel: null,
                                mainGroundLevel: null,
                                windSpeed: null,
                                windDeg: null,
                                rain3H: null,
                                cloudsAll: null,
                                dt: null,
                                sysMessage: null,
                                sysCountry: null,
                                sysSunriseAsTimestamp: null,
                                sysSunsetAsTimestamp: null,
                                sysSunrise: null,
                                sysSunset: null,
                                cod: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('weather', null, { reload: 'weather' });
                }, function() {
                    $state.go('weather');
                });
            }]
        })
        .state('weather.edit', {
            parent: 'weather',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather/weather-dialog.html',
                    controller: 'WeatherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Weather', function(Weather) {
                            return Weather.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('weather', null, { reload: 'weather' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('weather.delete', {
            parent: 'weather',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather/weather-delete-dialog.html',
                    controller: 'WeatherDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Weather', function(Weather) {
                            return Weather.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('weather', null, { reload: 'weather' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
