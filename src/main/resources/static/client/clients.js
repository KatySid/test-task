angular.module('app').controller('clientsController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/app';

    $scope.loadPage = function (page) {
        $http({
            url: contextPath + '/api/v1/clients',
            method: 'GET',
            params: {
                      p: page,
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

    $scope.createNewClient = function(){
              $http.post(contextPath + '/api/v1/clients', $scope.newClientDto)
              .then(function successCallback(response){
                console.log("Клиент сохранен"),
                                $scope.showClientCreateForm = false;
                                $scope.loadPage(1);
     });
     }


     $scope. closeClientForm= function () {
             $scope.showClientCreateForm = false;
             }

    $scope.showFormCreateClient = function(){
            $scope.showClientCreateForm = true;
    }

    $scope.showClientInfo = function (clientId) {
            $location.path('/client_info/' + clientId);
        }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadPage(1);
});