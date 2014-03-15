var app = angular.module('MyAngularApp', ['ngSanitize', 'mgcrea.ngStrap', 'drag_and_drop_upload'], function() {})

app.controller('ServerFile', ['$scope','$http', function ($scope, $http) {	  
	
	$scope.addcateg = true;
	$scope.listclear = true;	
	
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
		$scope.listclear = true;
		
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
        $scope.visible=false;
    };

    $scope.allValues = {};
    
    $scope.$watch('incat', function() {
    	if(!$scope.categories)
    		return;
    	$scope.listclear=false;
    	for (var i = 0; i<$scope.categories.length; i++) 
    		if($scope.incat==$scope.categories[i]){
    			$scope.ncateg = $scope.incat;
    			break;
    		} else $scope.addcateg = false;
    	if($scope.incat == ''){
    		$scope.addcateg = true;
    		$scope.listclear = true;
    	}
    });
    $scope.$watch('ncateg', function(){
    	if($scope.ncateg){
    		$("#view div").remove();
    		$scope.allValues = {};
    		$scope.getImages();
    		$scope.getSounds();
    		$scope.getPathTxt();    		
    		$scope.initPlaceHolder();
    		$scope.addcateg = true;
    	}
    });   
    
    $scope.getImages = function(){
        $http.get("info/images/"+$scope.ncateg).success(function (data){           
                for (var i=0; i < data.length ; i++){
                	var key = "/"+$scope.ncateg+"/"+data[i].replace('.png','');

                	if (typeof $scope.allValues[key] !== 'object')
                		$scope.allValues[key] = {};
                	
                    $scope.allValues[key].IMG = "/images/"+$scope.ncateg+"/"+data[i];
                    if(!$scope.allValues[key].id)
                    	$scope.allValues[key].id = data[i].substring(0, data[i].lastIndexOf("."));                    
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
                	if(!$scope.allValues[key].id)
                		$scope.allValues[key].id = data[i].substring(0, data[i].lastIndexOf("."));                	
                }
        });
    }

    $scope.getPathTxt = function(){
       $http.get("info/text/"+$scope.ncateg).success(function (data){ 
    	   var length = data.length;
                for (var i=0; i < data.length ; i++){
                	var id = data[i].substring(0, data[i].lastIndexOf("."));
                	var index = 0;                	
                    $http.get("/text/"+$scope.ncateg+"/"+data[i]).success(function(data, code, f, status){
                    	index++;
                    	var key = status.url.replace('/text', '').replace('.txt','');
                    	
                    	if (typeof $scope.allValues[key] !== 'object')
                    		$scope.allValues[key] = {};
                    	
                    	$scope.allValues[key].TXT = data;
                    	if(!$scope.allValues[key].id)
                    		$scope.allValues[key].id = id;
                    	
                        if(index==length){
                        	console.log($("#view").find("div"));
                    		var allValues = $scope.allValues;
                    		for(var i in allValues){
                    			if(!allValues[i].TXT){
                    				var $h4 = $("#"+i.substring(i.lastIndexOf("/")+1, i.length)).find("h4");
                    	    		var $slide = $scope.getSlidePanel();
                    	    		$slide.on("click", $scope.drop);
                    	    		var $slideButton = $scope.getSlideButton();
                    	    		$slideButton.on("click",$scope.btnslide);
                    	    		$slide.insertBefore($h4);
                    	    		$slideButton.insertBefore($h4);
                    				$scope.getCorrectPlaceHolder("H4").insertBefore($h4);                    				
                    				$h4.remove();
                    			}
                    			if(!allValues[i].IMG){
                    				var $img = $("#"+i.substring(i.lastIndexOf("/")+1, i.length)).find("img");
                    				$scope.getCorrectPlaceHolder("IMG").insertBefore($img);                    				
                    				$img.remove();
                    			}
                      			if(!allValues[i].SOUND){
                      				
                    				var $sound = $("#"+i.substring(i.lastIndexOf("/")+1, i.length)).find("audio");
                    				console.log($sound);
                    				console.log($sound.parent());
                    				$scope.getCorrectPlaceHolder("AUDIO").insertBefore($sound);                    				
                    				$sound.remove();
                    			} 
                    		}
                    	}
                    });
                }
        });
    }
}]);

app.directive("correctRepeatDirective", function(){	
	return function(scope, element, attrs){
		$(element).find("button").css("display", "none");			
	}	
});
