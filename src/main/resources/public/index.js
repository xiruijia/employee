bdtApp.controller('indexController', function($scope,$http,IndexService){
    $scope.message='123';
    $scope.title="半导体郗";
    IndexService.test();
});