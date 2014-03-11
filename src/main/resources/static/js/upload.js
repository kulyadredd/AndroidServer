var app = angular.module('mgcrea.ngStrapDocs', ['ngAnimate', 'ngSanitize', 'mgcrea.ngStrap', 'drag_and_drop_upload'], function() {})

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
    	if($scope.categories)
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
           // if (data!=null) {
                for (var i=0; i < data.length ; i++){
                	var key = "/"+$scope.ncateg+"/"+data[i].replace('.png','');

                	if (typeof $scope.allValues[key] !== 'object')
                		$scope.allValues[key] = {};
                	
                    $scope.allValues[key].IMG = "/images/"+$scope.ncateg+"/"+data[i];
                    if(!$scope.allValues[key].id)
                    	$scope.allValues[key].id = data[i].substring(0, data[i].lastIndexOf("."));
                    
                }
            //};
        });
    }

    $scope.getSounds = function(){
        $http.get("info/sounds/"+$scope.ncateg).success(function(data){
            //if (data!=null) {
                for (var i=0; i<data.length; i++){
                	var key = "/"+$scope.ncateg+"/"+data[i].replace('.mp3','');

                	if (typeof $scope.allValues[key] !== 'object')
                		$scope.allValues[key] = {};
                	
                	$scope.allValues[key].SOUND = "/sounds/"+$scope.ncateg+"/"+data[i];
                	if(!$scope.allValues[key].id)
                		$scope.allValues[key].id = data[i].substring(0, data[i].lastIndexOf("."));
                }
            //};
        });
    }

    $scope.getPathTxt = function(){
       $http.get("info/text/"+$scope.ncateg).success(function (data){
           //if (data!=null) {
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
                    		var allValues = $scope.allValues;
                    		for(var i in allValues){
                    			if(!allValues[i].TXT){
                    				var $h4 = $("#"+i.substring(i.lastIndexOf("/")+1, i.length)).find("h4");
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

            //};
        });
    }
}]);

app.directive("correctRepeatDirective", function(){	
	return function(scope, element, attrs){
		$(element).find("button").css("display", "none");
//		console.log(scope.allValues['/cats/3'].SOUND);
//		for(var item in scope.allValues)
//			console.log(scope.allValues[item]);
			//console.log(scope.$parent.allValues[scope.$parent.$index].TXT);
			
	}	
});