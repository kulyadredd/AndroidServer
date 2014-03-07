var DDU =  angular.module('drag_and_drop_upload', [], function() {});
//FileUploadCtrl.$inject = ['$$scope']
DDU.controller("FileUpload", ["$scope","$http", function ($scope, $http) {	
	var $placeHolder = $("<div id=\"placeHolder\" class=\"category\"></div>");
	initPlaceHolder();
	$scope.textField = "text";
    $scope.setFiles = function(element) {
    $scope.$apply(function($scope) {
      console.log('files:', element.files);
      // Turn the FileList object into an Array
        $scope.files = [];
        for (var i = 0; i < element.files.length; i++) {
          $scope.files.push(element.files[i]);
        }
      $scope.progressVisible = false;
      });
    };

    $scope.uploadFile = function() {    	
        var fd = new FormData();        
        fd.append(correctType(), $scope.file);
        
        $http.post("/", fd, {
        	             transformRequest: angular.identity,
        	             headers: {'Content-Type': undefined}
        	         })
        	         .success(function(){
        	             alert ("good");
        	         })
        	         .error(function(e){
        	             console.log('Error while uploading file: '+e);
        	         });
        
//        $.ajax({
//        	type: "post",
//        	url: "/",
//        	data: fd,
//        	success: uploadComplete
//        });
//        var xhr = new XMLHttpRequest();
//        xhr.upload.addEventListener("progress", uploadProgress, false);
//        xhr.addEventListener("load", uploadComplete, false);
//        xhr.addEventListener("error", uploadFailed, false);
//        xhr.addEventListener("abort", uploadCanceled, false);
//        xhr.open("POST", "/fileupload");
//        $scope.progressVisible = true;
//        xhr.send(fd);
    }

    function uploadProgress(evt) {
        $scope.$apply(function(){
            if (evt.lengthComputable) {
                $scope.progress = Math.round(evt.loaded * 100 / evt.total);
            } else {
                $scope.progress = 'unable to compute';
            }
        });
    }

    function uploadComplete(evt) {
        /* This event is raised when the server send back a response */
        alert("hello");
    }

    function uploadFailed(evt) {
        alert("There was an error attempting to upload the file.");
    }

    function uploadCanceled(evt) {
        $scope.$apply(function(){
            $scope.progressVisible = false;
        })
        alert("The upload has been canceled by the user or the browser dropped the connection.");
    }
    
    function correctType(){
    	for(var clazz in $scope.classes){
    		if(clazz.endsWith("PlaceHolder"))
    			return clazz.substring(0, clazz.indexOf('P'));
    	}
    	
    }
	
	function initPlaceHolder(){		
		var $viewDiv = $("#view");			
		var $textDropDiv = $("<div id=\"textDrop\" ng-class=\"dropClass\" class=\"dropElement textPlaceHolder\"><span>Drop text file here...</span></div>");
		var $imageDropDiv = $("<div id=\"imageDrop\" ng-class=\"dropClass\" class=\"dropElement imagesPlaceHolder\"><span>Drop image file here...</span></div>");
		var $soundDropDiv = $("<div id=\"soundDrop\" ng-class=\"dropClass\" class=\"dropElement soundsPlaceHolder\"><span>Drop sound file here...</span></div>");
	    $placeHolder.append($textDropDiv);
	    $placeHolder.append($imageDropDiv);
	    $placeHolder.append($soundDropDiv);
	    $viewDiv.append($placeHolder);
	    var $dropElement = $(".dropElement");
	    
	    function dragEnterLeave(evt) {
	        evt.stopPropagation();
	        evt.preventDefault();
	        $(this).removeAttr("style");
	    }
	    jQuery.event.props.push( "dataTransfer" );
	    $dropElement.on("dragenter", dragEnterLeave);
	    $dropElement.on("dragleave", dragEnterLeave);
	    $dropElement.on("dragover", function(evt) {
	        evt.stopPropagation();
	        evt.preventDefault();
	        var clazz = 'not-available';
	        var ok = evt.dataTransfer && evt.dataTransfer.types && evt.dataTransfer.types.indexOf('Files') >= 0;
	       
	        $(this).css("background-color", ok ? "#bfb" : "#F88");
	        
	    });
	    
	    $dropElement.on("drop", function(evt) {
	        
	        evt.stopPropagation();
	        evt.preventDefault();
	        $(this).removeAttr("style");
	        
	        var file = evt.dataTransfer.files[0];
	        if (file != null){	        	
	        	$scope.file = file;
	        	$scope.classes = $(this).attr("class");
	        	$scope.uploadFile();
	        }
	    });
	}
}]);

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

