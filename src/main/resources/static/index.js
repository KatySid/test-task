(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .when('/banks', {
                 templateUrl: 'bank/banks.html',
                 controller: 'banksController'
            })
            .when('/bank_info/:bankIdParam', {
                  templateUrl: 'bank_info/bank_info.html',
                  controller: 'bankInfoController'
            })
            .when('/clients', {
                 templateUrl: 'client/clients.html',
                 controller: 'clientsController'
            })
            .when('/credits', {
                  templateUrl: 'credit/credits.html',
                  controller: 'creditsController'
            })
            .when('/credit_offers', {
                             templateUrl: 'credit_offers/credit_offers.html',
                             controller: 'creditOffersController'
            })
            .when('/client_info/:clientIdParam', {
                    templateUrl: 'client_info/client_info.html',
                    controller: 'clientInfoController'
            })
            .when('/credit_info/:creditIdParam', {
                                templateUrl: 'credit_info/credit_info.html',
                                controller: 'creditInfoController'
                        })
            .otherwise({
                redirectTo: '/'
            });
    }
    function run($rootScope, $http, $localStorage) {
        }

})();
angular.module('app').controller('indexController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/app';
});