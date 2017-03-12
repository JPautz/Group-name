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
.controller('HomeCtrl', function HomeController($scope, $http, store, jwtHelper, $mdSidenav, $log, $mdBottomSheet) {
    $scope.jwt = store.get('jwt');
    $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

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
                .toggle()
                .then(function () {
                    $log.debug("toggle " + navID + " is done");
                });
        };
    }
})
.controller('LeftCtrl', function ($scope, $timeout, $mdSidenav, $log) {
    $scope.close = function () {
        // Component lookup should always be available since we are not using `ng-if`
        $mdSidenav('left').close()
            .then(function () {
                $log.debug("close LEFT is done");
            });
    };
});