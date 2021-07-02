angular.module('app').controller('creditOfferInfoController', function ($scope, $http, $localStorage, $routeParams) {
    const contextPath = 'http://localhost:8189/app';


    $scope.loadCreditOffer = function () {
         $http({
                    url: contextPath + '/api/v1/credit_offers/' + $routeParams.creditOfferIdParam,
                    method: 'GET'
                }).then(function (response) {
                    $scope.creditOfferDto = response.data;
                });
         }

    $scope.loadSchedulePage= function(page){
    $http({
            url: contextPath + '/api/v1/credit_offers/paymentSchedule',
                        method: 'GET',
                        params:{
                                creditOfferId: $routeParams.creditOfferIdParam,
                                p: page,
                                pageSize: 5
                                }
                    }).then(function (response) {
                        $scope.creditOfferPaymentsPage = response.data;

                        let minPageIndex = page - 2;
                         if (minPageIndex < 1) {
                             minPageIndex = 1;
                         }

                         let maxPageIndex = page + 2;
                         if (maxPageIndex > $scope.creditOfferPaymentsPage.totalPages) {
                               maxPageIndex = $scope.creditOfferPaymentsPage.totalPages;
                         }

                         $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
                    });
    }

    $scope.generatePagesIndexes = function (startPage, endPage) {
                let arr = [];
                for (let i = startPage; i < endPage + 1; i++) {
                    arr.push(i);
                }
                return arr;
            }

    $scope.loadCreditOffer();
    $scope.loadSchedulePage(1);

    });


