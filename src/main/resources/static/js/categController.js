angular.module('KittnsApp').controller('CategoryManipulation', ['$scope','$http', function ($scope, $http) { 

	$scope.managmenu = false;
	$scope.modalShown = false;
	$scope.categories = {};
	
	$http.get("/info").
	success(function(data){
		for(var i=0; i<data.length; i++){
            $scope.categories[i] = {};
			$scope.categories[i].name = data[i];
			$scope.categories[i].visible = true;  
		}
	    $scope.incat = '';
	});
	
	$scope.showinput = function(rencateg){
		for(var i in $scope.categories)
			if ($scope.categories[i].name==rencateg){
				$scope.categories[i].newName = rencateg;
				$scope.categories[i].visible = false;
				break;
			}
		$scope.categoryName = rencateg;		
	};
	
	$scope.toggleModal = function(select) {
    	$scope.modalShown = !$scope.modalShown;
    	$scope.categoryName = select;
    	
    };
    
	$scope.renamecat = function (newName){
    	$http.get("/category?oldcategory="+$scope.categoryName+"&renamecategory=" + newName).
    	success(function(data){
    		for(var i in $scope.categories)
    			if($scope.categories[i].name == $scope.categoryName){
    				$scope.categories[i].name = newName;
    				$scope.categories[i].visible = true;
    				$scope.search = '';
    				return;
    			}   		
    	})
    };
    
    $scope.delcat = function(){
    	$http.delete("/category/"+$scope.categoryName).
    	success(function(data){
    		for(var i in $scope.categories)
    			if($scope.categories[i].name == $scope.categoryName){
    				delete $scope.categories[i];
    				$scope.categoryName = '';
    	    		$scope.modalShown = !$scope.modalShown;
    	    		$scope.search = '';
    	    		return;
    			}
    	})
    };
}]);
app.filter('myFilter', function() {
	  return function(categories, searchcateg) {
	    if (!searchcateg){
	        return categories;
	    }
	    var result = [];
	    for(var i in categories)
	        if (categories[i].name.toString().toLowerCase().search(searchcateg) != -1)
	        	result.push(categories[i]);
	    return result;
	  }
	});
