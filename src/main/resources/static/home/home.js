angular.module('sample.home', [
    'ui.router',
    'angular-storage',
    'angular-jwt',
    'ngMaterial',
    angularDragula(angular)
])
.config(function($stateProvider, $mdThemingProvider) {
    $stateProvider.state('home', {
        url: '/',
        controller: 'HomeCtrl',
    templateUrl: 'home/home.html',
        data: {
            requiresLogin: true
        }
    });

    $mdThemingProvider.theme('default')
        .primaryPalette('green');
})
.controller('HomeCtrl', function HomeController($scope, $http, store, jwtHelper, $mdSidenav, $log, $mdBottomSheet,
        $timeout, $rootScope) {
    $scope.jwt = store.get('jwt');
    $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

    $scope.content = [];
    $scope.quarterArr = [];

    $scope.getContent = function() {
        $http({
            url: $rootScope.server_root + 'user',
            method: 'GET',
            headers: {
                'X-Auth-Token': $scope.jwt
            }
        }).then(function(response) {
            $scope.content = response.data;
            $scope.quarterArr = response.data.flowcharts[0].quarters;
        }, function(error) {
            console.log(error);
        });
    };

    $scope.getContent();

    /*$scope.intervalContent = function() {
        $timeout(function() {
            $scope.getContent();
            $scope.intervalData();
        }, 10000);
    };

    $scope.intervalContent();*/

    $scope.logout = function() {
        store.set('jwt', '');
        location.reload();
    }

    $scope.toggleLeft = buildToggler('left');
    $scope.isOpenLeft = function(){
        return $mdSidenav('left').isOpen();
    };

    /**
     * Supplies a function that will continue to operate until the
     * time is up.
     */
    function debounce(func, wait, context) {
        var timer;

        return function debounced() {
            var context = $scope,
                    args = Array.prototype.slice.call(arguments);
            $timeout.cancel(timer);
            timer = $timeout(function() {
                timer = undefined;
                func.apply(context, args);
            }, wait || 10);
        };
    }

    function buildToggler(navID) {
        return function() {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav(navID)
                .toggle();
        };
    }

    $scope.$on('quarter-bag.drop', function (e, el, target, source) {
        var courseId = el[0].className.split(" ")[0];
        var courseName = el[0].className.split(" ")[1];
        var sourceId = source[0].className.split(" ")[1];
        var targetId = target[0].className.split(" ")[1];

        $http({
            url: $rootScope.server_root + 'quarter/addCourse/' + targetId,
            method: 'PUT',
            headers: {
                'X-Auth-Token': $scope.jwt
            },
            data: {
                'name': courseName
            }
        }).then(function(response) {
            $http({
                url: $rootScope.server_root + 'quarter/deleteCourse/' + sourceId,
                method: 'PUT',
                headers: {
                    'X-Auth-Token': $scope.jwt
                },
                data: {
                    'name': courseName
                }
            }).then(function(response) {

            }, function(error) {
                console.log(error);
            });
        }, function(error) {
            console.log(error);
        });
    });
})
.controller('LeftCtrl', function ($scope, $timeout, $mdSidenav, $log) {
    $scope.close = function () {
        // Component lookup should always be available since we are not using `ng-if`
        $mdSidenav('left').close();
    };
})
.directive('quarter', function () {
    return {
        restrict: "E",
        templateUrl: 'home/quarter.tmpl.html'
    };
})
.directive('course', function () {
    return {
        restrict: "E",
        templateUrl: 'home/card.tmpl.html'
    };
});