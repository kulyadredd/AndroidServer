var app = angular.module('testApp', ['drag_and_drop_upload'], function() {})

app.controller('ServerFile', ['$scope','$http', function ($scope, $http) {
    $http.get("info").success(
        function(data){
            $scope.categories=data;
            $scope.ncateg = data[0];
        }
    );
    
    $scope.allValues = {};   
    $scope.$watch('ncateg', function() {
        if ($scope.ncateg) {
        	$scope.allValues = {};
            $scope.getImages();
            $scope.getSounds();
            $scope.getPathTxt();
        }
    });
    
    $scope.getImages = function(){
        $http.get("info/images/"+$scope.ncateg).success(function (data){
            for (var i=0; i < data.length ; i++){
            	var key = "/"+$scope.ncateg+"/"+data[i].replace('.png','');

            	if (typeof $scope.allValues[key] !== 'object')
            		$scope.allValues[key] = {};
            	
                $scope.allValues[key].IMG = "/images/"+$scope.ncateg+"/"+data[i];
            }
        });
    }

    $scope.getSounds = function(){
        $http.get("info/sounds/"+$scope.ncateg).success(function(data){
            for (var i=0; i<data.length; i++){
            	var key = "/"+$scope.ncateg+"/"+data[i].replace('.mp3','');

            	if (typeof $scope.allValues[key] !== 'object')
            		$scope.allValues[key] = {};
            	
            	$scope.allValues[key].SOUND = "/sounds/"+$scope.ncateg+"/"+data[i];
            }
        });
    }

    $scope.getPathTxt = function(){
       $http.get("info/text/"+$scope.ncateg).success(function (data){                
            for (var i=0; i < data.length ; i++){
                $http.get("/text/"+$scope.ncateg+"/"+data[i]).success(function(data, code, f, status){
                	var key = status.url.replace('/text', '').replace('.txt','');

                	if (typeof $scope.allValues[key] !== 'object')
                		$scope.allValues[key] = {};
                	
                	$scope.allValues[key].TXT = data;
                });
            }
        });
    }    

}]);

