angular.module('app').controller('clientInfoController', function ($scope, $http, $localStorage, $routeParams) {
    const contextPath = 'http://localhost:8189/app';


    $scope.loadClient = function () {
         $http({
                    url: contextPath + '/api/v1/clients/' + $routeParams.clientIdParam,
                    method: 'GET'
                }).then(function (response) {
                    $scope.client = response.data;
                });
         }

    $scope.updateClient = function () {
            $scope.showClientEditForm = true;

        }


    $scope.closeForm= function () {
           $scope.showClientEditForm = false;


        }

//     $scope.saveClient = function (){
//                    $http({
//                        url: contextPath + '/api/v1/clients',
//                        method: 'PUT',
//                        params: {
//                                 id: $routeParams.clientIdParam,
//                                 lastName: $scope.lastName,
//                                 name: $scope.name,
//                                 patronymic: $scope.patronymic,
//                                 passport: $scope.passport,
//                                 email: $scope.email
//                        }
//                        }).then(function successCallback(response) {
//                        console.log("Клиент изменен")
//                        $scope.client = response.data;
//                        $scope.showClientEditForm = false;
//                        $scope.loadClient();
//                        });
//            }

            $scope.saveClient = function () {
                        $http.put(contextPath + '/api/v1/clients/'+ $routeParams.clientIdParam, $scope.clientDto).then(function successCallback(response){
                        console.log("Клиент изменен")
                                                $scope.client = response.data;
                                                $scope.showClientEditForm = false;
                                                $scope.loadClient();

                        });
                }

    $scope.loadClient();

    });


