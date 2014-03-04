var app = angular.module('testApp', [], function() {})

app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);


app.service('$fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl, catName){
        var fd = new FormData();
        fd.append(catName, file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
            alert ("Файл завантажено успішно!");
        })
        .error(function(e){
            console.log('Error while uploading file: '+e);
        });
    }
}]);

app.controller('ServerFile', ['$scope','$http', '$fileUpload', function ($scope, $http, $fileUpload) {
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
    
    $scope.uploadFile = function(){
        if ($scope.myFile == null) 
            console.log("Файл не обрано!");
        else 
            $fileUpload.uploadFileToUrl($scope.myFile, "/", $scope.ncateg);
    };
}]);

