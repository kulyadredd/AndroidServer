var app = angular.module('testApp', [], function() {})

function ServerCategory($scope,$http) {
    $http.get("info").success(
        function(data){
            $scope.category=data;
            $scope.ncateg=category[0];
        }
    )
}

app.controller('ServerFileI', 
            ['$scope','$http', function ($scope, $http) {
                $http.get("info/images/cats").success(function(data){$scope.fileNameI=data;})    
                setInterval(function(){
                    var catName = document.getElementById("categoryName").innerHTML;
                    if (catName =="") catName = "cats";
                    $http.get("info/images/"+catName).success(
                        function (data){$scope.fileNameI = data;});},1000);
            }]
        );

app.controller('ServerFileS', 
            ['$scope','$http', function ($scope, $http) {
                $http.get("info/sounds/cats").success(function(data){$scope.fileNameS=data;})         
                setInterval(function(){
                    var catName = document.getElementById("categoryName").innerHTML;
                    if (catName =="") catName = "cats";
                    $http.get("info/sounds/"+catName).success(
                        function (data){$scope.fileNameS = data;});},1000);
            }]
        ); 

app.controller('ServerFileT', 
            ['$scope','$http', function ($scope, $http) {
                $http.get("info/text/cats").success(function(data){$scope.fileNameT=data;})         
                setInterval(function(){
                    var catName = document.getElementById("categoryName").innerHTML;
                    if (catName =="") catName = "cats";
                    $http.get("info/text/"+catName).success(
                        function (data){$scope.fileNameT = data;});},1000);
            }]
        );   
 
FileUploadCtrl.$inject = ['$scope']

function FileUploadCtrl(scope) {

    scope.setFiles = function(element) {
    scope.$apply(function(scope) {
        scope.files = []
        for (var i = 0; i < element.files.length; i++) {
          scope.files.push(element.files[i])
        }
      scope.progressVisible = false
      });
    };

    scope.uploadFile = function() {
        var catName = document.getElementById("categoryName").innerHTML;
        if (catName =="") catName = "cats";
        var fd = new FormData();
        for (var i in scope.files) {
            fd.append( catName , scope.files[i]);
        }
        var xhr = new XMLHttpRequest()
        xhr.addEventListener("load", uploadComplete, false);
        xhr.open("POST", "/up");
        xhr.setRequestHeader('Content-Type','multipart/form-data');
        scope.progressVisible = true;
        xhr.send(fd);
    }

    function uploadComplete() {
        alert("Файл завантажено успішно!");
        location.reload();
    }
}
