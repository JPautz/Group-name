angular.module('sample.home', [
    'ui.router',
    'angular-storage',
    'angular-jwt',
    'ngMaterial',
    'ngAnimate',
    angularDragula(angular)
])
    .config(function ($stateProvider, $mdThemingProvider) {
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
                                                    $timeout, $rootScope, $mdDialog, dragulaService) {
        $scope.jwt = store.get('jwt');
        $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

        $scope.content = [];
        $scope.flowchartArr = [];
        $scope.yearArr = [];

        $scope.getContent = function () {
            var errorText;
            $http({
                url: $rootScope.server_root + 'user',
                method: 'GET',
                headers: {
                    'X-Auth-Token': $scope.jwt
                }
            }).then(function (response) {
                $scope.content = response.data;
                $scope.flowchartArr = response.data.flowcharts;
                $scope.yearArr = response.data.flowcharts[0].years;
            }, function (error) {
                if (error.status == 500) {
                    errorText = 'An Error occurred getting user information, make sure you are logged in.';
                } else {
                    errorText = 'An Error occurred getting user information, please reload the page or try logging in again. Error code ' + error.status;
                }
                $mdDialog.show(
                    $mdDialog.alert()
                        .clickOutsideToClose(true)
                        .title('Error Retrieving Data')
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
        };

        $scope.getContent();

        $scope.logout = function () {
            store.set('jwt', '');
            location.reload();
        }

        $scope.toggleLeft = buildToggler('left');
        $scope.isOpenLeft = function () {
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
                timer = $timeout(function () {
                    timer = undefined;
                    func.apply(context, args);
                }, wait || 10);
            };
        }

        function buildToggler(navID) {
            return function () {
                $mdSidenav(navID)
                    .toggle();
            };
        }

        $scope.search = function () {
            $http({
                url: $rootScope.server_root + 'course/search',
                method: 'POST',
                data: {
                    'name': $scope.query
                }
            }).then(function (response) {
                $scope.id = response.data.id;
                $scope.name = response.data.name;
                $scope.title = response.data.title;
                $scope.units = response.data.units + ' units';
                $scope.termsOffered = response.data.termsOffered;
                $scope.prerequisites = response.data.prerequisites;
            }, function (error) {
            });
        };

        dragulaService.options($scope, 'quarter-bag', {
            copy: function (el, source) {
                return source.className.split(' ')[0] == 'searchSpace';
            },
            accepts: function (el, source) {
                return source.className.split(' ')[0] != 'searchSpace';
            }
        });

        $scope.$on('quarter-bag.drop', function (e, el, target, source) {
            var courseName = el[0].className.split(" ")[1];
            var sourceId = source[0].className.split(" ")[1];
            var targetId = target[0].className.split(" ")[1];
            var errorText;

            $http({
                url: $rootScope.server_root + 'quarter/addCourse/' + targetId,
                method: 'PUT',
                headers: {
                    'X-Auth-Token': $scope.jwt
                },
                data: {
                    'name': courseName
                }
            }).then(function () {
                if (!isNaN(sourceId)) {
                    $http({
                        url: $rootScope.server_root + 'quarter/deleteCourse/' + sourceId,
                        method: 'PUT',
                        headers: {
                            'X-Auth-Token': $scope.jwt
                        },
                        data: {
                            'name': courseName
                        }
                    });
                }
            }, function (error) {
                if (error.status == 401) {
                    errorText = 'Please assure you are properly authenticated by logging in again.';
                } else {
                    errorText = 'An Error occurred moving course, please try again. Error code ' + error.status;
                }
                $mdDialog.show(
                    $mdDialog.alert()
                        .clickOutsideToClose(true)
                        .title('Unable to Move Course')
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
        });
    })
    .controller('LeftCtrl', function ($scope, $timeout, $mdSidenav, $log) {
        $scope.close = function () {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav('left').close();
        };
    })
    .controller('ManageFlowchartMenu', function($scope, $http, store, jwtHelper, $rootScope) {
        $scope.showMenu = false;

        $scope.refreshFlowchart = function () {
            $http({
                url: $rootScope.server_root + 'user',
                method: 'GET',
                headers: {
                    'X-Auth-Token': $scope.jwt
                }
            }).then(function (response) {
                $scope.flowchartArr = response.data.flowcharts;
            });
        };

        $scope.createFlowchart = function () {
            $http({
                url: $rootScope.server_root + '/flowchart',
                method: 'POST',
                headers: {
                    'X-Auth-Token': $scope.jwt
                },
                data: {
                    'name': "test create"
                }
            }).then(function (response) {
                $scope.name = response.data.name;
            }, function (error) {
                console.log(error);
            });

            $scope.refreshFlowchart();
        };
    })
    .directive('year', function() {
        return {
            restrict: "E",
            templateUrl: 'home/year.tmpl.html'
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
    })
    .directive('autofocus', function ($timeout) {
        return {
            link: function (scope, element) {
                $timeout(function () {
                    element.focus();
                });
            }
        }
    });