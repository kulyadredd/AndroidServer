var app = angular.module('mgcrea.ngStrapDocs', ['ngAnimate', 'ngSanitize', 'mgcrea.ngStrap'], function() {})

angular.module('mgcrea.ngStrapDocs')

app.controller('ServerFile', ['$scope','$http', function ($scope, $http) {
    
    $scope.nameFilter = '';
    $http.get("info").success(
        function(data){
            $scope.categories=data;
            $scope.ncateg = data[0];
        }
    );

    $scope.addcat = function() {
        $scope.categories.push($scope.ncateg);
        $http.get("/addcat?newcategory="+$scope.ncateg);
    };

    $scope.allValues = {};

    $scope.$watch('ncateg', function() {
    	 $scope.allValues = {};
        for (var i = 0; i<$scope.categories.length; i++) {
            if($scope.ncateg==$scope.categories[i]){
                $scope.getImages();
                $scope.getSounds();
                $scope.getPathTxt();
                $scope.visible="false";
                break;
            }else $scope.visible="true";
        };
        if ($scope.ncateg=='')
        	$scope.visible="false";
    });
    
    $scope.getImages = function(){
        $http.get("info/images/"+$scope.ncateg).success(function (data){
            if (data!=null) {
                for (var i=0; i < data.length ; i++){
                	var key = "/"+$scope.ncateg+"/"+data[i].replace('.png','');

                	if (typeof $scope.allValues[key] !== 'object')
                		$scope.allValues[key] = {};
                	
                    $scope.allValues[key].IMG = "/images/"+$scope.ncateg+"/"+data[i];
                }
            };
        });
    }

    $scope.getSounds = function(){
        $http.get("info/sounds/"+$scope.ncateg).success(function(data){
            if (data!=null) {
                for (var i=0; i<data.length; i++){
                	var key = "/"+$scope.ncateg+"/"+data[i].replace('.mp3','');

                	if (typeof $scope.allValues[key] !== 'object')
                		$scope.allValues[key] = {};
                	
                	$scope.allValues[key].SOUND = "/sounds/"+$scope.ncateg+"/"+data[i];
                }
            };
        });
    }

    $scope.getPathTxt = function(){
       $http.get("info/text/"+$scope.ncateg).success(function (data){
           if (data!=null) {                
                for (var i=0; i < data.length ; i++){
                    $http.get("/text/"+$scope.ncateg+"/"+data[i]).success(function(data, code, f, status){
                    	var key = status.url.replace('/text', '').replace('.txt','');

                    	if (typeof $scope.allValues[key] !== 'object')
                    		$scope.allValues[key] = {};
                    	
                    	$scope.allValues[key].TXT = data;
                    });
                }
            };
        });
    }
}]);

