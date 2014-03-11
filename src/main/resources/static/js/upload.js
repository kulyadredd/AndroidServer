﻿var app = angular.module('MyAngularApp', ['ngSanitize', 'mgcrea.ngStrap'], function() {})

app.controller('ServerFile', ['$scope','$http', function ($scope, $http) {
		
	$scope.listclear="false";
	
	$http.get("info").success(
        function(data){
            $scope.categories=data;
            $scope.incat = '';
        }
    );
	
	$scope.clear = function() {
		$scope.incat='';
		$scope.ncateg = $scope.incat;
		$("#view div" ).remove();
		
	}
	
    $scope.addcat = function() {
        $scope.categories.push($scope.incat);
        $http.get("/addcat?newcategory="+$scope.incat).success(function(data){
		    $scope.ncateg = $scope.incat;
		    $scope.allValues = {};
		    $scope.getImages();
		    $scope.getSounds();
		    $scope.getPathTxt();
        });
        $scope.visible="false";
    };

    $scope.allValues = {};

    $scope.$watch('incat', function() {
    	    if(!$scope.categories)
    	        return;
    	 	$scope.listclear="true";
    	    for (var i = 0; i<$scope.categories.length; i++) 
    	        if($scope.incat==$scope.categories[i]){
    	            $scope.ncateg = $scope.incat;
    	            break;
    	        } else $scope.visible="true";
    	 	if($scope.incat == ''){
    	 		$scope.visible="false";
    	 		$scope.listclear="false";
    	 	}
    	 });
    
    	$scope.$watch('ncateg', function(){
    		if($scope.ncateg){
	    	    $scope.allValues = {};
	    	    $scope.getImages();
	    	    $scope.getSounds();
	    	    $scope.getPathTxt();
	    	 	$scope.visible="false";
    		}
    	});
    
    $scope.getImages = function(){
    $http.get("info/images/"+$scope.ncateg).success(function (data){
            for (var i=0; i < data.length ; i++){
            	var key = "/"+$scope.ncateg+"/"+data[i].replace('.png','');
            	if (typeof $scope.allValues[key] !== 'object')
            		$scope.allValues[key] = {};
                $scope.allValues[key].IMG = "/images/"+$scope.ncateg+"/"+data[i];
                $scope.visible = "false";
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
            	$scope.visible = "false";
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
                	$scope.visible = "false";
                });
            }
        });
    }
}]);

