angular.module( 'sample.login', [
  'ui.router',
  'angular-storage'
])
.config(function($stateProvider) {
  $stateProvider.state('login', {
    url: '/login',
    controller: 'LoginCtrl',
    templateUrl: 'login/login.html'
  });
})
.controller( 'LoginCtrl', function LoginController( $scope, $http, store, $state) {

  $scope.user = {};

  $scope.login = function() {
    $http({
      url: 'http://localhost:8080/login',
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
