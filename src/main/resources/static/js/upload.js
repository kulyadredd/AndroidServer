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

    $scope.$watch('ncateg', function() {
    	if ($scope.ncateg)
    		$scope.getImages();
    });
    
    $scope.fullpathIMG = new Array();
    
    $scope.getImages = function(){
        $http.get("info/images/"+$scope.ncateg).success(
            function (data){
                $scope.fullpathIMG.length = 0;    
                for (var i=0; i < data.length ; i++)
                    $scope.fullpathIMG[i]="/images/"+$scope.ncateg+"/"+data[i];
            }
        );
    }  
    
    $scope.uploadFile = function(){
        if ($scope.myFile == null) 
        	console.log("Файл не обрано!");
        else 
        	$fileUpload.uploadFileToUrl($scope.myFile, "/", $scope.ncateg);
    };
}]);

