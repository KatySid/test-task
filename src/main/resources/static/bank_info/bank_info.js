angular.module('app').controller('bankInfoController', function ($scope, $http, $localStorage, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/app';


    $scope.loadBank = function () {
         $http({
                    url: contextPath + '/api/v1/banks/' + $routeParams.bankIdParam,
                    method: 'GET'
                }).then(function (response) {
                    $scope.bank = response.data;
                });
         }

    $scope.loadPageClients = function (page) {
            $http({
                url: contextPath + '/api/v1/banks/clientsOfBank/'+$routeParams.bankIdParam,
                method: 'GET',
                params: {
                          p: page,
                          pageSize: 5,
                          lastName: $scope.filter ? $scope.filter.lastName : null,
                          name: $scope.filter ? $scope.filter.name : null,
                          patronymic: $scope.filter ? $scope.filter.patronymic : null,
                          bankId: $routeParams.bankIdParam
                          }
            }).then(function (response) {
                $scope.clientsOfBankPage = response.data;

                let minPageIndex = page - 2;
                if (minPageIndex < 1) {
                    minPageIndex = 1;
                }

                let maxPageIndex = page + 2;
                if (maxPageIndex > $scope.clientsOfBankPage.totalPages) {
                    maxPageIndex = $scope.clientsOfBankPage.totalPages;
                }

                $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
            });
        };

    $scope.loadPageCredits = function (page) {
            $http({
                url: contextPath + '/api/v1/banks/creditsOfBank/'+$routeParams.bankIdParam,
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
                $scope.creditsOfBankPage = response.data;

                let minPageIndex = page - 2;
                if (minPageIndex < 1) {
                    minPageIndex = 1;
                }

                let maxPageIndex = page + 2;
                if (maxPageIndex > $scope.creditsOfBankPage.totalPages) {
                    maxPageIndex = $scope.creditsOfBankPage.totalPages;
                }

                $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
            });
        };


    $scope.updateBank = function() {
            $scope.showBankEditForm = true;

        }

    $scope.showClients = function(){
        $scope.showClientsList = true;
         $scope.loadPageClients(1);
    }

    $scope.closeShowClients = function(){
        $scope.showClientsList = false;
    }

    $scope.showCredits = function(){
        $scope.showCreditsList = true;
         $scope.loadPageCredits(1);
    }

    $scope.closeShowCredits = function(){
        $scope.showCreditsList = false;
    }

    $scope.closeBankEditForm= function () {
           $scope.showClientEditForm = false;
        }

    $scope.saveBank = function () {
           $http.put(contextPath + '/api/v1/banks/'+ $routeParams.bankIdParam, $scope.BankDto).
           then(function successCallback(response){
                        console.log("Банк изменен")
                         $scope.bank = response.data;
                         $scope.showBankEditForm = false;
                         $scope.loadBank(1);
                         $scope.loadPageClients(1);

                        });
                }

    $scope.generatePagesIndexes = function (startPage, endPage) {
            let arr = [];
            for (let i = startPage; i < endPage + 1; i++) {
                arr.push(i);
            }
            return arr;
        }

    $scope.showClientInfo = function(clientId){
        $location.path('/client_info/' + clientId);
    }

    $scope.showCreditInfo = function(creditId){
         $location.path('/credit_info/' + creditId);
        }

    $scope.addNewCredit = function(){
                $scope.showCreditCreateForm = true;

    }

     $scope.addNewClient = function(){
                    $scope.showClientCreateForm = true;
        }

     $scope.closeClientForm= function () {
                      $scope.showClientCreateForm = false;
     }

    $scope.createNewClientOfBank = function(){
        $scope.newClientDto.bankId =  $routeParams.bankIdParam;
         $http.post(contextPath + '/api/v1/clients', $scope.newClientDto).then(function successCallback(response){
                    console.log("Клиент сохранен");
                    $scope.showClientCreateForm = false;
                    $scope.loadPageClients(1);

         });
         }

    $scope.createNewCredit = function(){
    $scope.newCreditDto.bankId =  $routeParams.bankIdParam;
         $http.post(contextPath + '/api/v1/credits', $scope.newCreditDto).then(function successCallback(response){
                    console.log("Кредит сохранен"),
                    $scope.showCreditCreateForm = false;
                     $scope.loadBank(1);
                     $scope.loadPageCredits(1);
            });

         }

    $scope.closeCreditForm= function () {
        $scope.showCreditCreateForm = false;
    }

    $scope.updateBank = function(){
        $scope.showBankUpdateForm = true;
    }
    $scope.closeBankForm= function(){
        $scope.showBankUpdateForm = false;
    }

    $scope.saveBank = function () {
        $scope.bankDto.id = $routeParams.bankIdParam,
              $http.put(contextPath + '/api/v1/banks', $scope.bankDto)
              .then(function successCallback(response){
                     console.log("Данные банка изменены");
                     $scope.bank = response.data;
                     $scope.showBankUpdateForm = false;
                     $scope.loadBank(1);
              });
    }

    $scope.deleteClient = function(clientId){
        $http({
                url: contextPath + '/api/v1/banks/deleteClient',
                method: 'GET',
                params: {
                          clientId: clientId,
                          bankId: $routeParams.bankIdParam
                          }
                }).then(function successCallback(response){
                $scope.loadPageClients(1);
                 });
    }

    $scope.deleteCredit = function(creditId){
            $http({
                    url: contextPath + '/api/v1/credits',
                    method: 'DELETE',
                    params: {
                              id: creditId
                              }
                    }).then(function successCallback(response){
                    $scope.loadPageCredits(1);
    });
    }

    $scope.loadBank(1);

    });


