angular.module('app').controller('banksController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/app';

    $scope.loadPageBanks = function (page) {
        $http({
            url: contextPath + '/api/v1/banks',
            method: 'GET',
            params: {
                      p: page,
                      title: $scope.filter ? $scope.filter.title : null,
                      }
        }).then(function (response) {
            $scope.banksPage = response.data;

            let minPageIndex = page - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = page + 2;
            if (maxPageIndex > $scope.banksPage.totalPages) {
                maxPageIndex = $scope.banksPage.totalPages;
            }
            $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
        });
    };

    $scope.createNewBank = function(){
     $http.post(contextPath + '/api/v1/banks', $scope.newBankDto).then(function successCallback(response){
                console.log("Банк сохранен"),
                $scope.showBankCreateForm = false;
                $scope.loadPageBanks(1)
     });
     }

     $scope. closeBankForm= function () {
                 $scope.showBankCreateForm = false;
     }

    $scope.addNewBank = function(){
        $scope.showBankCreateForm = true;
    }

    $scope.showBankInfo = function (bankId) {
            $location.path('/bank_info/' + bankId);
        }


    $scope.deleteBank = function (bankId){
                $http({
                    url: contextPath + '/api/v1/banks',
                    method: 'DELETE',
                    params: {
                             id: bankId
                    }
                    }).then(function successCallback(response) {
                    console.log("Банк удален")
                    $scope.loadPageBanks(1);
                    });
    }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadPageBanks(1);
});