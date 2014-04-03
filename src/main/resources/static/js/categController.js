angular.module('KittnsApp').controller('CategoryManipulation', ['$scope','$http', function ($scope, $http, fileUpload) { 

	$scope.managmenu = false;
	$scope.modalShown = false;

	$scope.openmanip = function (select){
		$scope.managmenu = true;
		$scope.categoryName = select;		
	}

	$http.get("/info").
	success(function(data){
	    $scope.categories=data;
	    $scope.incat = '';
	})

	$scope.toggleModal = function() {
    	$scope.modalShown = !$scope.modalShown;
    };

	$scope.renamecat = function(){
    	$http.get("/category?oldcategory="+$scope.categoryName+"&renamecategory="+$scope.incatmanip).
    	success(function(data){
    		for(var i = 0; i<$scope.categories.length; i++)
    			if($scope.categories[i]==$scope.categoryName)
    				$scope.categories[i] = $scope.incatmanip;
    		$scope.categoryName = $scope.incatmanip;
    		$scope.incatmanip='';
    	})
    }
    
    $scope.delcat = function(){
    	$http.delete("/category/"+$scope.categoryName).
    	success(function(data){
    		for(var i = 0; i<$scope.categories.length; i++)
    			if($scope.categories[i]==$scope.categoryName)
    				$scope.categories.splice(i, 1);
    		$scope.categoryName = '';
    		$scope.managmenu = false;
    		$scope.modalShown = !$scope.modalShown;
    	})
    }
}]);