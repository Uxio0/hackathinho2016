(function() {
    'use strict';

    angular
        .module('weatheropendataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('weather-energy', {
            parent: 'entity',
            url: '/weather-energy?page&sort&search',
            data: {
                authorities: [],
                pageTitle: 'weatheropendataApp.weatherEnergy.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/weather-energy/weather-energies.html',
                    controller: 'WeatherEnergyController',
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
                    $translatePartialLoader.addPart('weatherEnergy');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('weather-energy-detail', {
            parent: 'entity',
            url: '/weather-energy/{id}',
            data: {
                authorities: [],
                pageTitle: 'weatheropendataApp.weatherEnergy.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/weather-energy/weather-energy-detail.html',
                    controller: 'WeatherEnergyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('weatherEnergy');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WeatherEnergy', function($stateParams, WeatherEnergy) {
                    return WeatherEnergy.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'weather-energy',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('weather-energy-detail.edit', {
            parent: 'weather-energy-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather-energy/weather-energy-dialog.html',
                    controller: 'WeatherEnergyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WeatherEnergy', function(WeatherEnergy) {
                            return WeatherEnergy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('weather-energy.new', {
            parent: 'weather-energy',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather-energy/weather-energy-dialog.html',
                    controller: 'WeatherEnergyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                isoCode: null,
                                lat: null,
                                lon: null,
                                solar: null,
                                minTemp: null,
                                maxTemp: null,
                                temp: null,
                                sunset: null,
                                sunrise: null,
                                hidraulic: null,
                                rain: null,
                                eolic: null,
                                windSpeed: null,
                                clouds: null,
                                createdDateTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('weather-energy', null, { reload: 'weather-energy' });
                }, function() {
                    $state.go('weather-energy');
                });
            }]
        })
        .state('weather-energy.edit', {
            parent: 'weather-energy',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather-energy/weather-energy-dialog.html',
                    controller: 'WeatherEnergyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WeatherEnergy', function(WeatherEnergy) {
                            return WeatherEnergy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('weather-energy', null, { reload: 'weather-energy' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('weather-energy.delete', {
            parent: 'weather-energy',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather-energy/weather-energy-delete-dialog.html',
                    controller: 'WeatherEnergyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WeatherEnergy', function(WeatherEnergy) {
                            return WeatherEnergy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('weather-energy', null, { reload: 'weather-energy' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
