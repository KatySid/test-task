angular.module('app').controller('creditOffersFormController', function ($scope, $http, $localStorage, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/app';

    $scope.showDurationForm = false;
    $scope.showTableSchedule=true;

    $scope.loadCreditOfferForm = function(){
                $http({
                         url: contextPath + '/api/v1/credit_offers_form',
                         method: 'GET'
                     }).then(function (response) {
                         $scope.creditOfferForm = response.data;
                       });
        }

    $scope.loadPageClients = function (page) {
            $http({
                url: contextPath + '/api/v1/clients',
                method: 'GET',
                params: {
                          p: page,
                          pageSize: 5,
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

    $scope.closeClients = function(){
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

    $scope.addNewClient = function(){
            $scope.showClientCreateForm = true;
    }

    $scope.closeClientForm= function () {
            $scope.showClientCreateForm = false;
    }

     $scope.closeClientForm= function () {
            $scope.showClientCreateForm = false;
     }

     $scope.showDuration = function(){
        $scope.showDurationForm = true;
     }

     $scope.closeDuration = function(){
           $scope.showDurationForm = false;
     }

     $scope.addDuration = function(){
            $http.get(contextPath + '/api/v1/credit_offers_form/addDuration/'+ $scope.duration).then(function (response) {
                     $scope.durationForm = response.data;
                     $scope.showDurationForm = false;
                   });
    }

    $scope.deleteDuration = function(){
                $http.get(contextPath + '/api/v1/credit_offers_form/deleteDuration/').then(function (response) {
                         $scope.durationForm = false;
                         $scope.CreditOfferSchedule = false;
                         $.scope.loadSchedulePaymentsPage(1);
                       });
    }


    $scope.addClientToOffer = function(clientId){
                $http({
                         url: contextPath + '/api/v1/credit_offers_form/addClient',
                         method: 'GET',
                         params: {
                                   id: clientId
                                  }
                     }).then(function (response) {
                        $scope.clientInOffer = response.data;
                         $scope.showClientsList = false;

                       });
        }

    $scope.deleteClient = function(){
                    $http({
                             url: contextPath + '/api/v1/credit_offers_form/deleteClient',
                             method: 'GET',
                            }).then(function (response) {
                            $scope.clientInOffer = false;
                            });
    }


    $scope.addCreditToOffer = function(creditId){
                        $http({
                                 url: contextPath + '/api/v1/credit_offers_form/addCredit',
                                 method: 'GET',
                                 params: {
                                           id: creditId
                                          }
                             }).then(function (response) {
                                 $scope.creditInOffer = response.data;
                                 $scope.showCreditsList = false;
                             });
                }

   $scope.deleteCredit = function(){
                       $http({
                                url: contextPath + '/api/v1/credit_offers_form/deleteCredit',
                                method: 'GET',
                               }).then(function (response) {
                               $scope.creditInOffer = false;
                               $scope.CreditOfferSchedule = false;
                               $scope.showTableSchedule=false;
                               });
       }

   $scope.loadSchedulePaymentsPage = function(page){
        $http({
                  url: contextPath + '/api/v1/credit_offers_form/paymentSchedule',
                  method: 'GET',
                  params: {
                            p: page,
                            pageSize: 5
                            }
              }).then(function (response) {
                  $scope.paymentsPage = response.data;
                  $scope.CreditOfferSchedule = true;
                  $scope.sumPercentButton = true;

                  let minPageIndex = page - 2;
                  if (minPageIndex < 1) {
                      minPageIndex = 1;
                  }

                  let maxPageIndex = page + 2;
                  if (maxPageIndex > $scope.paymentsPage.totalPages) {
                      maxPageIndex = $scope.paymentsPage.totalPages;
                  }

                  $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
              });
          };

   $scope.getAmount = function(){
   $http({
           url: contextPath + '/api/v1/credit_offers_form/amount',
           method: 'GET',
           }).then(function (response) {
           $scope.amount = response.data;
           $scope.amountButton = false;
           });
   }

   $scope.getSumPercent = function(){
      $http({
              url: contextPath + '/api/v1/credit_offers_form/sumPercent',
              method: 'GET',
              }).then(function (response) {
              $scope.sumPercent = response.data;
              $scope.amountButton = true;
              $scope.sumPercentButton = false;
              });
      }

   $scope.showPaymentsPage = function(){
       $scope.loadSchedulePaymentsPage(1);
       $scope.CreditOfferSchedule = false;
       $scope.showTableSchedule=true;
//       $scope.getAmount();
//       $scope.getSumPercent();

   }

   $scope.closePaymentsPage = function(){
       $scope.CreditOfferSchedule = false;
       $scope.showTableSchedule=false;
   }

    $scope.clearForm = function () {
        $http({
            url: contextPath + '/api/v1/credit_offers_form/clear',
            method: 'GET'
        }).then(function (response) {

        });
    }

   $scope.saveCreditOffer= function(){
   $http.post(contextPath + '/api/v1/credit_offers').then(function successCallback(response){
               console.log("Предложение сохранено")
               $scope.clearForm();
               });
               window.location.reload();
   }

  });


