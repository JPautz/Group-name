var app = angular.module('angular-dragula-demo', [angularDragula(angular)]);


app.controller('MainCtrl', function($scope, dragulaService) {
  dragulaService.options($scope, 'fifth-bag', {
      copy: true
    });
});
