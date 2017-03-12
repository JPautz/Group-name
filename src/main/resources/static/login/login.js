angular.module( 'sample.login', [
  'ui.router',
  'angular-storage',
  'ngMaterial'
])
.config(function($stateProvider) {
  $stateProvider.state('login', {
    url: '/login',
    controller: 'LoginCtrl',
    templateUrl: 'login/login.html'
  });
})
.controller( 'LoginCtrl', function LoginController($scope, $http, store, $state, $rootScope) {

  $scope.user = {};

  $scope.login = function() {
    $http({
      url: $rootScope.server_root + 'login',
      method: 'POST',
      data: $scope.user,
      transformResponse: undefined
    }).then(function(response) {
      store.set('jwt', response.data);
      $state.go('home');
    }, function(error) {
      console.log(error);
    });
  }

});
