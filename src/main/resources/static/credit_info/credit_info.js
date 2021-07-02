angular.module('app').controller('creditInfoController', function ($scope, $http, $localStorage, $routeParams) {
    const contextPath = 'http://localhost:8189/app';


    $scope.loadCredit = function () {
         $http({
                    url: contextPath + '/api/v1/credits/' + $routeParams.creditIdParam,
                    method: 'GET'
                }).then(function (response) {
                    $scope.credit = response.data;
                });
         }

    $scope.updateCredit = function () {
            $scope.showCreditEditForm = true;
        }


    $scope.closeForm= function () {
           $scope.showCreditEditForm = false;
        }

    $scope.saveCredit = function () {
    $scope.creditDto.id = $routeParams.creditIdParam,
           $http.put(contextPath + '/api/v1/credits', $scope.creditDto)
           .then(function successCallback(response){
           console.log("Кредит изменен")
           $scope.credit = response.data;
           $scope.showCreditEditForm = false;
           $scope.loadCredit();
           });
    }

    $scope.loadCredit();

    });


