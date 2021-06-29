angular.module('app').controller('creditOffersController', function ($scope, $http, $localStorage, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/app';

    $scope.showDurationForm = false;

    $scope.loadCreditOfferForm = function(){
                $http({
                         url: contextPath + '/api/v1/credit_offers',
                         method: 'GET'
                     }).then(function (response) {
                         $scope.creditOfferForm = response.data;
                       });
        }

    $scope.loadPageClients = function (page) {
            $http({
                url: contextPath + '/api/v1/clients',
                method: 'GET',
                params: {
                          p: page,
                          pageSize: 5,
                          lastName: $scope.filter ? $scope.filter.lastName : null,
                          name: $scope.filter ? $scope.filter.name : null,
                          patronymic: $scope.filter ? $scope.filter.patronymic : null
                         }
            }).then(function (response) {
                $scope.clientsPage = response.data;

                let minPageIndex = page - 2;
                if (minPageIndex < 1) {
                    minPageIndex = 1;
                }

                let maxPageIndex = page + 2;
                if (maxPageIndex > $scope.clientsPage.totalPages) {
                    maxPageIndex = $scope.clientsPage.totalPages;
                }

                $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
            });
        };

    $scope.loadPageCredits = function (page) {
            $http({
                url: contextPath + '/api/v1/credits',
                method: 'GET',
                params: {
                          p: page,
                          pageSize: 5,
                          min_percent: $scope.filter ? $scope.filter.min_percent : null,
                          max_percent: $scope.filter ? $scope.filter.max_percent : null,
                          min_limitation: $scope.filter ? $scope.filter.min_limitation : null,
                          min_limitation: $scope.filter ? $scope.filter.min_limitation : null
                          }
            }).then(function (response) {
                $scope.creditsPage = response.data;

                let minPageIndex = page - 2;
                if (minPageIndex < 1) {
                    minPageIndex = 1;
                }

                let maxPageIndex = page + 2;
                if (maxPageIndex > $scope.creditsPage.totalPages) {
                    maxPageIndex = $scope.creditsPage.totalPages;
                }

                $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
            });
        };

    $scope.showClients = function(){
        $scope.showClientsList = true;
         $scope.loadPageClients(1);
    }

    $scope.closeClients = function(){
        $scope.showClientsList = false;
    }

    $scope.showCredits = function(){
        $scope.showCreditsList = true;
         $scope.loadPageCredits(1);
    }

    $scope.closeShowCredits = function(){
        $scope.showCreditsList = false;
    }

    $scope.generatePagesIndexes = function (startPage, endPage) {
            let arr = [];
            for (let i = startPage; i < endPage + 1; i++) {
                arr.push(i);
            }
            return arr;
        }

    $scope.addCredit = function(){
                $scope.showCreditCreateForm = true;

    }
    $scope.closeCreditForm= function () {
                   $scope.showCreditCreateForm = false;
        }

    $scope.addClient = function(){
            $scope.showClientCreateForm = true;
    }

    $scope.addNewClient = function(){
            $scope.showClientCreateForm = true;
    }

    $scope.closeClientForm= function () {
            $scope.showClientCreateForm = false;
    }

     $scope.closeClientForm= function () {
            $scope.showClientCreateForm = false;
     }

     $scope.showDuration = function(){
        $scope.showDurationForm = true;
     }

     $scope.closeDuration = function(){
           $scope.showDurationForm = false;
     }

     $scope.addDuration = function(){
            $http({
                     url: contextPath + '/api/v1/credit_offers/addDuration',
                     method: 'GET',
                     params: {
                               duration: duration
                              }
                 }).then(function (response) {
                     $scope.duration = response.data;
                     $scope.showDurationForm = false;
                     $scope.loadCreditOfferForm();
                   });
    }
    $scope.addClientToOffer = function(clientId){
                $http({
                         url: contextPath + '/api/v1/credit_offers/addClient',
                         method: 'GET',
                         params: {
                                   id: clientId
                                  }
                     }).then(function (response) {
                         $scope.showClientsList = false;
                         $scope.loadCreditOfferForm();
                       });
        }

        $scope.addCreditToOffer = function(creditId){
                        $http({
                                 url: contextPath + '/api/v1/credit_offers/addCredit',
                                 method: 'GET',
                                 params: {
                                           id: creditId
                                          }
                             }).then(function (response) {
                                 $scope.creditInOffer = response.data;
                                 $scope.showCreditsList = false;
                                 $scope.loadCreditOfferForm();
                               });
                }
//    loadPageCredits(1);
//    loadPageClients(1);
    });


