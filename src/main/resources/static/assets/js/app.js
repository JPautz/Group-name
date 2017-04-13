angular.module('sample', [
    'sample.home',
    'sample.login',
    'sample.signup',
    'angular-jwt',
    'angular-storage'
])
    .run(function($rootScope) {
        $rootScope.server_root = 'http://localhost:8080/';
     })
    /*.run(function ($rootScope) {
        $rootScope.server_root = 'https://cp-groupname.herokuapp.com/';
    })*/
    .config(function myAppConfig($urlRouterProvider, jwtInterceptorProvider, $httpProvider) {
        $urlRouterProvider.otherwise('/');

        jwtInterceptorProvider.tokenGetter = function (store) {
            return store.get('jwt');
        }

        $httpProvider.interceptors.push('jwtInterceptor');
    })
    .run(function ($rootScope, $state, store, jwtHelper) {
        $rootScope.$on('$stateChangeStart', function (e, to) {
            if (to.data && to.data.requiresLogin) {
                if (!store.get('jwt') || jwtHelper.isTokenExpired(store.get('jwt'))) {
                    e.preventDefault();
                    $state.go('login');
                }
            }
        });
    })
    .controller('AppCtrl', function AppCtrl($scope, $location, $mdPanel, $mdBottomSheet, $http, $rootScope) {
        $scope.$on('$routeChangeSuccess', function (e, nextRoute) {
            if (nextRoute.$$route && angular.isDefined(nextRoute.$$route.pageTitle)) {
                $scope.pageTitle = nextRoute.$$route.pageTitle + ' | ngEurope Sample';
            }
        });

        this.tags = [];

        this.newChip = function (chip) {
            return {
                name: chip,
                type: 'unknown'
            };
        };

        this.search = function () {
            $http({
                url: $rootScope.server_root + 'course/search',
                method: 'POST',
                data: {
                    'name': this.tags[0]
                }
            }).then(function (response) {
                console.log(response.data);
            }, function (error) {
                console.log(error);
            });
        };

        this._mdPanel = $mdPanel;

        this.showDialog = function () {
            var position = this._mdPanel.newPanelPosition()
                .absolute()
                .center();

            var config = {
                attachTo: angular.element(document.body),
                controller: PanelDialogCtrl,
                controllerAs: 'ctrl',
                disableParentScroll: this.disableParentScroll,
                templateUrl: 'home/faq.tmpl.html',
                hasBackdrop: true,
                panelClass: 'faq-dialog',
                position: position,
                trapFocus: true,
                zIndex: 150,
                clickOutsideToClose: true,
                escapeToClose: true,
                focusOnOpen: true
            };

            this._mdPanel.open(config);
        };
    });

function PanelDialogCtrl(mdPanelRef) {
    this._mdPanelRef = mdPanelRef;
}

PanelDialogCtrl.prototype.closeDialog = function () {
    var panelRef = this._mdPanelRef;

    panelRef && panelRef.close().then(function () {
        angular.element(document.querySelector('.faq-dialog-open-button')).focus();
        panelRef.destroy();
    });
};
