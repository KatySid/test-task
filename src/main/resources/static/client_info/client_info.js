angular.module('app').controller('clientInfoController', function ($scope, $http, $localStorage, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/app';


    $scope.loadClient = function () {
         $http({
                    url: contextPath + '/api/v1/clients/' + $routeParams.clientIdParam,
                    method: 'GET'
                }).then(function (response) {
                    $scope.client = response.data;
                });
         }

    $scope.loadCreditOffers = function () {
         $http({
                    url: contextPath + '/api/v1/clients/credit_offers/' + $routeParams.clientIdParam,
                    method: 'GET'
                }).then(function (response) {
                    $scope.clientsCreditOffers = response.data;
                });
         }

    $scope.updateClient = function () {
            $scope.showClientEditForm = true;

        }


    $scope.closeForm= function () {
           $scope.showClientEditForm = false;


        }



    $scope.saveClient = function () {
                        $http.put(contextPath + '/api/v1/clients/'+ $routeParams.clientIdParam, $scope.clientDto).then(function successCallback(response){
                        console.log("Клиент изменен")
                        $scope.client = response.data;
                        $scope.showClientEditForm = false;
                        $scope.loadClient();
                        });
                }

    $scope.deleteCreditOffers = function(creditOfferId){
     $http({
               url: contextPath + '/api/v1/credit_offers',
                method: 'DELETE',
                params: {
                         id: creditOfferId
                        }
                }).then(function successCallback(response) {
                    console.log("Кредит удален")
                });
                window.location.reload();

    }

    $scope.showCreditOffersInfo = function(creditOfferId){
         $location.path('/credit_offer_info/' + creditOfferId);
        }

    $scope.loadClient();
    $scope.loadCreditOffers();

    });


