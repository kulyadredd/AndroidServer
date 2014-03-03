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
            $scope.allValue.length = 0;
    		$scope.getImages();
            $scope.getSounds();
            $scope.getPathTxt();
    });
    
    $scope.fullpathIMG = new Array();
    $scope.fullpathMID = new Array();
    $scope.txtpath = new Array();
    $scope.valueFile = new Array();
    $scope.allValue = new Array();
    
    $scope.getImages = function(){
        $http.get("info/images/"+$scope.ncateg).success(
            function (data){
                $scope.fullpathIMG.length = 0;    
                for (var i=0; i < data.length ; i++)
                    $scope.fullpathIMG[i]="/images/"+$scope.ncateg+"/"+data[i];
            }
        );
    }

    $scope.getSounds = function(){
        $http.get("info/sounds/"+$scope.ncateg).success(
            function(data){
                $scope.fullpathMID.length = 0;
                for (var i=0; i<data.length; i++)
                    $scope.fullpathMID[i]="/sounds/"+$scope.ncateg+"/"+data[i];
            }
        );
    }

    $scope.getPathTxt = function(){
       $http.get("info/text/"+$scope.ncateg).success(
            function (data){ 
                if (data!="null") {
                $scope.txtpath.length = 0;    
                for (var i=0; i < data.length ; i++)
                    $scope.txtpath[i]="/text/"+$scope.ncateg+"/"+data[i];
                $scope.getValueTxt($scope.txtpath);
            }
        }
        );
    }

    $scope.getValueTxt = function(txtpath){
        $scope.valueFile.length=0;
        var c=0;
            for(var i=0; i<txtpath.length;i++){
            $http.get(txtpath[i]).success(
                function(data){
                    $scope.valueFile[c] = data;
                    c++;
                    if (c==txtpath.length){
                        for (var i=0; i<$scope.fullpathIMG.length; i++) 
                            $scope.allValue.push({IMG : $scope.fullpathIMG[i],  MID : $scope.fullpathMID[i], TXT : $scope.valueFile[i]})
                    }
                 }
            )
        }
    }  
    
    $scope.uploadFile = function(){
        if ($scope.myFile == null) 
        	console.log("Файл не обрано!");
        else 
        	$fileUpload.uploadFileToUrl($scope.myFile, "/", $scope.ncateg);
    };
}]);