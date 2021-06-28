angular.module('app').controller('creditOfferController', function ($scope, $http, $localStorage, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/app';


    $scope.loadPageClients = function (page) {
            $http({
                url: contextPath + '/api/v1/clients',
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

     $scope.closeClientForm= function () {
                      $scope.showClientCreateForm = false;
     }

    $scope.createNewClientOfBank = function(){
        $scope.newClientDto.bankId =  $routeParams.bankIdParam;
         $http.post(contextPath + '/api/v1/clients', $scope.newClientDto).then(function successCallback(response){
                    console.log("Клиент сохранен"),
                    $scope.showClientCreateForm = false;
                    $scope.loadPageClients(1);

         });
         }

    });


