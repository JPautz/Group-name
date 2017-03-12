angular.module( 'sample.signup', [
  'ui.router',
  'angular-storage',
  'ngMaterial'
])
.config(function($stateProvider) {
  $stateProvider.state('signup', {
    url: '/signup',
    controller: 'SignupCtrl',
    templateUrl: 'signup/signup.html'
  });
})
.controller( 'SignupCtrl', function SignupController($scope, $http, store, $state, $rootScope) {

  $scope.user = {};

  $scope.createUser = function() {
    $http({
      url: $rootScope.server_root + 'user',
      method: 'POST',
      data: $scope.user
    }).then(function(response) {
      store.set('jwt', response.data.id_token);
      $state.go('home');
    }, function(error) {
      alert(error.data);
    });
  }

});
