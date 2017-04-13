angular.module('sample.login', [
    'ui.router',
    'angular-storage',
    'ngMaterial'
])
    .config(function ($stateProvider) {
        $stateProvider.state('login', {
            url: '/login',
            controller: 'LoginCtrl',
            templateUrl: 'login/login.html'
        });
    })
    .controller('LoginCtrl', function LoginController($scope, $http, store, $state, $rootScope, $mdDialog) {

        $scope.user = {};

        $scope.login = function () {
            var errorText;
            $http({
                url: $rootScope.server_root + 'login',
                method: 'POST',
                data: $scope.user,
                transformResponse: undefined
            }).then(function (response) {
                store.set('jwt', response.data);
                $state.go('home');
            }, function (error) {
                if (error.status == 401) {
                    errorText = 'Please check to make sure all fields were filled in correctly.';
                } else {
                    errorText = 'An Error occurred logging in, please try again. Error code ' + error.status;
                }
                $mdDialog.show(
                    $mdDialog.alert()
                        .clickOutsideToClose(true)
                        .title('Unable to Login')
                        .textContent(errorText.toString())
                        .ok('Okay')
                        .openFrom({
                            top: -50,
                            width: 30,
                            height: 80
                        })
                        .closeTo({
                            left: 1500
                        })
                );
            });
        }

    })
    .directive('autofocus', function ($timeout) {
        return {
            link: function (scope, element, attrs) {
                $timeout(function () {
                    element.focus();
                });
            }
        }
    });

