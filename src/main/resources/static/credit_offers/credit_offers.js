angular.module('app').controller('creditOffersController', function ($scope, $http, $localStorage, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/app';

    $scope.loadPageCreditOffers = function (page) {
            $http({
                url: contextPath + '/api/v1/credit_offers',
                method: 'GET',
                params: {
                          p: page,
                          pageSize: 5,
                         }
            }).then(function (response) {
                $scope.creditOffersPage = response.data;

                let minPageIndex = page - 2;
                if (minPageIndex < 1) {
                    minPageIndex = 1;
                }

                let maxPageIndex = page + 2;
                if (maxPageIndex > $scope.creditOffersPage.totalPages) {
                    maxPageIndex = $scope.creditOffersPage.totalPages;
                }

                $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
            });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
                let arr = [];
                for (let i = startPage; i < endPage + 1; i++) {
                    arr.push(i);
                }
                return arr;
    };

    $scope.deleteCreditOffers = function(creditOfferId){
    $http({
           url: contextPath + '/api/v1/credit_offers',
            method: 'DELETE',
            params: {
                     id: creditOfferId
                    }
            }).then(function successCallback(response) {
                console.log("Кредит удален")
                $scope.loadPageCreditOffers(1);
            });
    }

    $scope.showCreditOffersInfo = function(creditOfferId){
     $location.path('/credit_offer_info/' + creditOfferId);
    }

    $scope.loadPageCreditOffers(1);

    });


