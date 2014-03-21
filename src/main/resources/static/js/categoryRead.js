var app = angular.module('KittnsApp', ['ngSanitize', 'mgcrea.ngStrap', 'ui.dnd'], function() {})

app.controller('RenderControls', ['$scope','$http', 'fileUpload', function ($scope, $http, fileUpload) {      
    
    $scope.addcateg = true;
    $scope.listclear = true;    
    
    $http.get("/info").
    success(function(data){
            $scope.categories=data;
            $scope.incat = '';
    }).
    error(logErrorHandler);
    
    $scope.clear = function() {
        $scope.incat='';
        $scope.ncateg = $scope.incat;
        $scope.listclear = true;
    }
    
    $scope.addcat = function() {
        
        $http.get("/addcat?newcategory="+$scope.incat).
        success(function(data, status, headers, config) {
            $scope.categories.push(data);
            $scope.ncateg = data;
            $scope.allValues = {};
        }).
        error(logErrorHandler);
        $scope.visible=false;
    };

    $scope.allValues = {};
    
    $scope.$watch('incat', function() {
        //$scope.wfile = $scope.incat;
        if($scope.incat == ''){
            $scope.addcateg = true;
            $scope.listclear = true;
            return;
        }
        
        if(!$scope.categories || $scope.categories.length == 0){
            $scope.addcateg = false;
            return;
        }
        $scope.listclear=false;
        for (var i = 0; i<$scope.categories.length; i++) 
            if($scope.incat==$scope.categories[i]){
                $scope.ncateg = $scope.incat;
                break;
            } else {
                $scope.addcateg = false;
            }
    });
    
    $scope.$watch('ncateg', function(){
        if($scope.ncateg){
            $scope.allValues = {};
            $scope.getImages();
            $scope.getSounds();
            $scope.getPathTxt();
            $scope.addcateg = true;
        }
    });
    
    function addId(allValues, key){
        if(!allValues[key].id)
            allValues[key].id = key;
    }
    
    $scope.getImages = function(){
        $http.get("/info/images/"+$scope.ncateg).
        success(function (data){           
            for (var i=0; i < data.length ; i++){
                var key = "/"+$scope.ncateg+"/"+data[i].replace('.png','').replace('.jpg', '');

                if (typeof $scope.allValues[key] !== 'object')
                    $scope.allValues[key] = {};
                
                $scope.allValues[key].IMG = "/images/"+$scope.ncateg+"/"+data[i];
                addId($scope.allValues, key);                    
            }
        }).
        error(logErrorHandler);
    }

    $scope.getSounds = function(){
        $http.get("/info/sounds/"+$scope.ncateg).
        success(function(data){            
            for (var i=0; i<data.length; i++){
                var key = "/"+$scope.ncateg+"/"+data[i].replace('.mp3','');
                if (typeof $scope.allValues[key] !== 'object')
                    $scope.allValues[key] = {};                    
                $scope.allValues[key].SOUND = "/sounds/"+$scope.ncateg+"/"+data[i];
                addId($scope.allValues, key);                  
            }
        }).
        error(logErrorHandler);
    }

    $scope.getPathTxt = function(){
       $http.get("/info/text/"+$scope.ncateg).success(function (data){ 
            var length = data.length;
            for (var i=0; i < data.length ; i++){
                $http.get("/text/"+$scope.ncateg+"/"+data[i]).
                success(function(data, status, headers, config){
                    var key = config.url.replace('/text', '').replace('.txt','');
                    
                    if (typeof $scope.allValues[key] !== 'object')
                        $scope.allValues[key] = {};
                    
                    $scope.allValues[key].TXT = data;
                    addId($scope.allValues, key);
                }).
                error(logErrorHandler);
            }
        });
    }
    
    $scope.uploadFile = function(){
        fileUpload.uploadFileToUrl($scope.myFile, "/", modelUpdateCallback);
    };
    
    $scope.fileDropped = function(base, id){
        var uri = "/"+base+id;

        //Get the file
        var file = this.uploadedFile;

        //Upload the image
        fileUpload.uploadFileToUrl(file, uri, modelUpdateCallback);

        //Clear the uploaded file
        this.uploadedFile = null;
    }
    
    $scope.del = function(uri){
        $http.delete(uri).
        success(function(data, status, headers, config) {
        	for (var id in data){
        		if ($scope.allValues[id]){
        			for (var elementType in data[id])
        				if (!data[id][elementType] || data[id][elementType] == "null")
        					$scope.allValues[id][elementType] = undefined;
        			
        			if (!$scope.allValues[id]['TXT'] && !$scope.allValues[id]['IMG'] && !$scope.allValues[id]['SOUND']){
        				$scope.allValues[id]['id'] = undefined; 
        				$scope.allValues[id] = undefined;
        				delete $scope.allValues[id];
        			}
        				
        		}
            }        	
        }).
        error(logErrorHandler);
    }
    
    function modelUpdateCallback(data, status, headers, config) {
        if (status == 200)
        	for (var id in data){
                for (var elementType in data[id]){
                    if (!$scope.allValues[id])
                        $scope.allValues[id] = {};
                    
                    $scope.allValues[id][elementType] = data[id][elementType];
                } 
            }
        else 
            logErrorHandler(data, status, headers, config);
    }
    
    function logErrorHandler(data, status, headers, config) {
        console.log(status+" data: "+data+" headers: "+JSON.stringify(headers));
    }
}]);

app.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl, callback){
        var fd = new FormData();
        fd.append('file', file);
        var xhr = $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
        if (callback)
            xhr.success(callback)
            .error(callback);
    }
}]);