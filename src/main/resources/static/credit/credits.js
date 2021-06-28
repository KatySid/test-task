angular.module('app').controller('creditsController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/app';

    $scope.loadPageCredit = function (page) {
        $http({
            url: contextPath + '/api/v1/credits',
            method: 'GET',
            params: {
                      p: page,
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

    $scope.createNewCredit = function(){
     $http.post(contextPath + '/api/v1/credits', $scope.newCreditDto).then(function successCallback(response){
                console.log("Кредит сохранен"),
                $scope.showCreditCreateForm = false;
        });
     }

    $scope.closeCreditForm= function () {
           $scope.showCreditCreateForm = false;
             }

    $scope.addNewCredit = function(){
            $scope.showCreditCreateForm = true;
        }

    $scope.showCreditInfo = function (creditId) {
            $location.path('/credit_info/' + creditId);
        }

    $scope.deleteCredit = function (creditId){
                $http({
                    url: contextPath + '/api/v1/credits',
                    method: 'DELETE',
                    params: {
                             id: creditId
                    }
                    }).then(function successCallback(response) {
                    console.log("Кредит удален")
                    $scope.loadPageCredit(1);
                    });
        }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadPageCredit(1);
});