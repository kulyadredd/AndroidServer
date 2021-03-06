var app = angular.module('KittnsApp', ['ngSanitize', 'mgcrea.ngStrap', 'ui.dnd', 'ui.popup', 'ui.router'], function() {})

app.controller('UserList', ['$scope', '$http', function ($scope, $http) {
	
	$http.get("/showusers").
	success(function(data){
		$scope.users =data;
    });
}])

app.controller('RenderControls', ['$scope', '$http', 'fileUpload', function ($scope, $http, fileUpload) {      
    
    $scope.addcateg = false;
    $scope.listclear = false; 
    $scope.newBundle = { 'title':''};
    $scope.modalShown = false;
    $scope.allValues = {};
    
    $http.get("/info").
    success(function(data){
        $scope.categories=data;
        $scope.incat = '';
    }).
    error(logErrorHandler);
    
    $scope.toggleModal = function(uri) {
    	$scope.uri = uri;
    	$scope.modalShown = !$scope.modalShown;
    };
        
    $scope.openInput = function(type, categ){
    	$scope.type = type;
    	$scope.categ = categ;
    	$('#inFile').click();
    }
    
    $scope.clear = function() {
        $scope.incat='';
        $scope.ncateg = '';
        $scope.listclear = false;
        $scope.orderby = 0;
    }
    
    $scope.changeScope = function(){
    	$scope.newBundle.title = new Date().getTime()+'';
    	console.log($scope.newBundle.title);
    }    
    
    $scope.addcat = function() {
        $http.get("/category?newcategory="+$scope.incat).
        success(function(data, status, headers, config) {
            $scope.categories.push($scope.incat);
            $scope.ncateg = $scope.incat;
        }).
        error(logErrorHandler);
        $scope.visible=false;
    };    
    
    $scope.$watch('incat', function() {
        
        if($scope.incat == ''){
            $scope.addcateg = false;
            $scope.listclear = false;
            return;
        }
        
        if(!$scope.categories || $scope.categories.length == 0){
            $scope.addcateg = true;
            return;
        }
        $scope.listclear=true;
        for (var i = 0; i<$scope.categories.length; i++) 
            if($scope.incat==$scope.categories[i]){
                $scope.ncateg = $scope.incat;
                break;
            } else {
                $scope.addcateg = true;                
                $scope.ncateg='';
            }
    });
    
    $scope.$watch('ncateg', function(){
        if($scope.ncateg){
            $scope.allValues = {};
            $scope.getImages();
            $scope.getSounds();
            $scope.getPathTxt();
            $scope.addcateg = false;
        } else 
        	$scope.allValues = '';
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
                
                $scope.allValues[key].image = "/images/"+$scope.ncateg+"/"+data[i];
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
                $scope.allValues[key].sound = "/sounds/"+$scope.ncateg+"/"+data[i];
                addId($scope.allValues, key);                  
            }
        }).
        error(logErrorHandler);
    }

    $scope.getPathTxt = function(){
       $http.get("/info/labels/"+$scope.ncateg).success(function (data){ 
            var length = data.length;
            for (var i=0; i < data.length ; i++){
                $http.get("/labels/"+$scope.ncateg+"/"+data[i]).
                success(function(data, status, headers, config){
                    var key = config.url.replace('/labels', '').replace('.txt','');
                    
                    if (typeof $scope.allValues[key] !== 'object')
                        $scope.allValues[key] = {};
                    
                    $scope.allValues[key].label = data;
                    addId($scope.allValues, key);
                }).
                error(logErrorHandler);
            }
        });
    }
    
    $scope.fileFromInput = function(element){
 		fileUpload.uploadFileToUrl(element.files[0], $scope.type+$scope.categ , modelUpdateCallback);
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
        $http.delete($scope.uri).
        success(function(data, status, headers, config) {
        	for (var id in data){
        		if ($scope.allValues[id]){
        			for (var elementType in data[id])
        				if (!data[id][elementType] || data[id][elementType] == "null")
        					$scope.allValues[id][elementType] = undefined;
        			
        			if (!$scope.allValues[id]['label'] && !$scope.allValues[id]['image'] && !$scope.allValues[id]['sound']){
        				$scope.allValues[id]['id'] = undefined; 
        				$scope.allValues[id] = undefined;
        				delete $scope.allValues[id];
        			}
        				
        		}
            }
        	$scope.modalShown = !$scope.modalShown;
        }).
        error(logErrorHandler);
    }
    
    $scope.setTitle = function(uri, title){
    	uri = "/labels"+uri;
    	var config = {headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}};
    	var data = $.param({'title':title});
    	$http.post(uri, data, config).
    	success(updateModel);
    }
    
    $scope.setNewTitle = function(){
    	uri = "/labels/"+$scope.ncateg;
    	var config = {headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}};
    	var data = $.param({'title': $scope.newBundle.title});
    	
    	$http.post(uri, data, config).
    	success(function(data, status, headers, config){
    		updateModel(data);
    		$scope.newBundle.title = '';
        }).
    	error(logErrorHandler);
    }
    
    function modelUpdateCallback(data, status, headers, config) {
        if (status == 200)
        	updateModel(data);
        else 
            logErrorHandler(data, status, headers, config);
    }
    
    function logErrorHandler(data, status, headers, config) {
        console.log(status+" data: "+data+" headers: "+JSON.stringify(headers));
    }
    
    function updateModel(data){
    	for (var id in data){
            for (var elementType in data[id]){
                if (!$scope.allValues[id]){
                    $scope.allValues[id] = {};
                    $scope.allValues[id]['id'] = id;
                }
                $scope.allValues[id][elementType] = data[id][elementType];                
            } 
        }
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
app.config(function($stateProvider, $urlRouterProvider){
	
	$urlRouterProvider.otherwise("/main");
	
    $stateProvider        
      .state('main', {
          url: "/main",
          templateUrl: 'html/main.html',
          controller: 'RenderControls'
      })
      .state('manip', {
            url: "/manipulation",
            templateUrl: "html/catmanag.html",
            controller: 'CategoryManipulation'
      })
        .state('users', {
        url: "/showusers",
        templateUrl: "html/users.html",
        controller: 'UserList'
      })
  })
  
app.filter('myOrderBy', function() {
  return function(items, field, reverse) {
    var filtered = [];
    angular.forEach(items, function(item) {
      filtered.push(item);
    });
    return filtered;
  };
});
