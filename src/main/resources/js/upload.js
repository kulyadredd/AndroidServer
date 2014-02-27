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
    this.uploadFileToUrl = function(file, uploadUrl){
        var catName = document.getElementById("categoryName").innerHTML;
        if (catName =="") catName = "cats";
        var fd = new FormData();
        fd.append(catName, file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(alert ("Файл завантажено успішно!"))
    }
}]);

app.controller('ServerFile', 
            ['$scope','$http', function ($scope, $http) {
                var pathi="http://localhost:8080/images/cats/";
                $http.get("info/images/cats").success(function(data){
                    $scope.fileNameI=data;
                    var fullpath=new Array();
                    for (var i=0; $scope.fileNameI[i]; i++)
                        fullpath[i]=pathi+$scope.fileNameI[i];
                    $scope.fullpathIMG=fullpath;
                });  
                setInterval(function(){
                    var catName = document.getElementById("categoryName").innerHTML;
                    if (catName =="") catName = "cats";
                    pathi="http://localhost:8080/images/"+catName+"/";
                    $http.get("info/images/"+catName).success(
                        function (data){
                            $scope.fileNameI = data;
                            var fullpath=new Array();
                            for (var i=0; $scope.fileNameI[i]; i++)
                                fullpath[i]=pathi+$scope.fileNameI[i];
                            $scope.fullpathIMG=fullpath;
                        });
                },1000);
            }]
        );
 
app.controller('myCtrl', ['$scope', '$fileUpload', function($scope, $fileUpload){
    
    $scope.uploadFile = function(){
        var file = $scope.myFile;
        var uploadUrl = "/";
        if (file==null) alert("Файл не обрано!");
        else $fileUpload.uploadFileToUrl(file, uploadUrl);
    };
    
}]);

function ServerCategory($scope,$http) {
    $http.get("info").success(
        function(data){
            $scope.category=data;
            $scope.ncateg=category[1];
        }
    )
}