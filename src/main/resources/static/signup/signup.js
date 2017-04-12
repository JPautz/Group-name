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
.controller( 'SignupCtrl', function SignupController($scope, $http, store, $state, $rootScope, $mdDialog) {

  $scope.user = {};

  $scope.createUser = function() {
    var errorText;
    $http({
      url: $rootScope.server_root + 'user',
      method: 'POST',
      data: $scope.user
    }).then(function(response) {
      store.set('jwt', response.data.id_token);
      $state.go('home');
    }, function(error) {
      console.log(error);
      if(error.status == 409) {
        errorText = 'This Email address is already registered.';
      } else if(error.status == 400) {
        errorText = 'Please check to make sure all required fields were filled in correctly.';
      } else {
        errorText = 'Sorry, there was an error in creating your account. Error code ' + error.status;
      }
      $mdDialog.show(
        $mdDialog.alert()
          .clickOutsideToClose(true)
          .title('Account Creation Error')
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
.directive('autofocus', function($timeout) {
  return {
      link: function(scope, element, attrs) {
          $timeout(function() {
              element.focus();
          });
      }
  }
});