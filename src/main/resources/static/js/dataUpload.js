var DDU =  angular.module('drag_and_drop_upload', [], function() {});

DDU.controller("FileUpload", ["$scope","$http", function ($scope, $http) {
	
	$("#view button").css("display","none");
	$scope.del = function($event){
		$http.get("/remove?id="+$($event.target).parent().attr("id")+"&path="+$($event.target).next().attr("src")+"&category="+ $("#categoryName").val()
				).success(function(){
			if($($event.target).next().prop('tagName')=="H4"){
				var $slide = $scope.getSlidePanel();
	    		$slide.find("span").on("click", $scope.drop);
	    		$slide.find("input").keypress(function (e) {
	    			var key = e.which;
		   			 if(key == 13) 
		   			  {
		   				 $slide.find("span").trigger("click");
		   				 return false;
		   			  };
		   		}); 
	    		var $slideButton = $scope.getSlideButton();
	    		$slideButton.on("click",$scope.btnslide);
	    		$slide.insertBefore($($event.target));
	    		$slideButton.insertBefore($($event.target));
			}
			var $placeHolder = $scope.getCorrectPlaceHolder($($event.target).next().prop('tagName'));
			$placeHolder.insertBefore($($event.target));
			$($event.target).next().remove();
			$($event.target).remove();
			if($($placeHolder).parent().find("img, h4, audio").length == 0)
				$($placeHolder).parent().remove();
		});
	}
	
	$scope.over = function($event){
		if($($event.target).prop('tagName')!='DIV' && $($event.target).prop('tagName')!='BUTTON'){
			$($event.target).parent().parent().find("button").css("display", "none");
			if($($event.target).prop('tagName')!='SPAN')
				$($event.target).prev().css("display","block");
		}
	}
	$scope.out = function($event){
		// TO DO
	}
	
    $scope.uploadFile = function() {    	
        var fd = new FormData();
        var $category = $("#categoryName").val();
        var $currentElement = $(this);
        fd.append( $category, $scope.file);
        fd.append("id", getCorrectId.call($currentElement));        
        $http.post("/", fd, {
        	             transformRequest: angular.identity,
        	             headers: {'Content-Type': undefined}
        	         })
        	         .success(function(data){
        	        	 var $newElement = getCorrectElement.call($currentElement, data);
        	        	 if(data[1]){
        	        		 $currentElement.parent().attr("id", data[1]);
        	        		 $currentElement.parent().attr("class", "category")
        	        		 $currentElement.parent().on("mouseover", $scope.over);
        	        		 $currentElement.parent().on("mouseout", $scope.out);
        	        		 $scope.initPlaceHolder();        	        		 
        	        	 }
    	        		 $newElement.insertBefore($currentElement);
    	        		 getCloseButton().insertBefore($newElement);
    	        		 if ($currentElement.attr("id")=="textDrop"){
        	        		 $currentElement.remove();
        	        		 $("#panel").remove();
        	        		 $("#slide").remove();
    	        		 }else $currentElement.remove();
        	             console.log(data);
        	             console.log($currentElement);
        	             console.log($newElement);
        	         })
        	         .error(function(e){
        	             console.log('Error while uploading file: '+e);
        	         });
    }
    
    function getCloseButton(){
    	return $("<button id=\"del\"  ng-click=\"del($event)\" class=\"close\">&times</button>").css("display", "none").on("click", $scope.del);
    }
    
    $scope.getSlidePanel = function() {
    	return $("<div id=\"panel\"><div class=\"panelgreet input-group inTXT\"><input id=\"intext\" class=\"form-control\" type=\"text\"/><span id=\"slider\" class=\"btn btn-info input-group-addon ownbtn\">+</span></div></div>");
    }
    
    $scope.getSlideButton = function() {
    	return $("<span id=\"slide\" class=\"btn btn-slide\"></span>");
    }
    
    function getCorrectId(){
    	if($(this).parent().attr("id")=="placeHolder")
    		return "";
    	return $(this).parent().attr("id");
    		
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
    
    function getCorrectElement(data){
    	var $element;
    	switch($(this).attr("id")){
    	
    		case "textDrop":
    			$element = $("<h4 class=\"text-center\">"+ data[0] +"</h4>");    			
    			break;
    		case "imageDrop":
    			$element = $("<img ng-src="+ data[0] +" width=\"176\" height=\"220\" src="+ data[0] +">");
    			break
    		case "soundDrop":
    			$element = $("<audio controls=\"\" ng-src="+ data[0] +" src="+ data[0] +"></audio>");
    			break;
    	}
    	return $element;
    }
    
    $scope.getCorrectPlaceHolder = function(value){
    	var $placeHolder;
    	switch(value){
    	case "IMG":
    		$placeHolder = $("<div id=\"imageDrop\" ng-class=\"dropClass\" class=\"dropElement imagesPlaceHolder\"><span>Drop image file here or click on me</span></div>");
    		break;
    	case "H4": 
    		$placeHolder = $("<div id=\"textDrop\" ng-class=\"dropClass\" class=\"dropElement textPlaceHolder\"><span>Drop text file here or click on me</span></div>");
    		break;
    	case "AUDIO": 
    		$placeHolder = $("<div id=\"soundDrop\" ng-class=\"dropClass\" class=\"dropElement soundsPlaceHolder\"><span>Drop sound file here or click on me</span></div>");
    		break;
    	}
    	$placeHolder.on("dragenter", $scope.dragEnterLeave);
        $placeHolder.on("dragleave", $scope.dragEnterLeave);
        $placeHolder.on("dragover", $scope.dragover);
	    $placeHolder.on("drop", $scope.drop);
	    $placeHolder.on("click", $scope.clickUpload);
	    return $placeHolder;
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
    
	$scope.btnslide = function (){
        
		$(this).prev().animate({height: 'toggle'});
		$(this).siblings("#textDrop").animate({height: 'toggle'});
        $(this).toggleClass("active"); return false;
	}
    
	$scope.initPlaceHolder = function (){
		
		var $placeHolder = $("<div id=\"placeHolder\" class=\"category\"></div>");
		var $viewDiv = $("#view");
		var $slidepanel = $("<div id=\"panel\"><div class=\"panelgreet input-group inTXT\"><input id=\"intext\" class=\"form-control\" type=\"text\"/><span id=\"slider\" class=\"btn btn-info input-group-addon ownbtn\">+</span></div></div>");
		var $btslide = $("<span id=\"slide\" class=\"btn-slide\"></span>");
		var $textDropDiv = $("<div id=\"textDrop\" ng-class=\"dropClass\" class=\"dropElement textPlaceHolder\"><span>Drop text file here or click on me.</span></div>");
		var $imageDropDiv = $("<div id=\"imageDrop\" ng-class=\"dropClass\" class=\"dropElement imagesPlaceHolder\"><span>Drop image file here or click on me</span></div>");
		var $soundDropDiv = $("<div id=\"soundDrop\" ng-class=\"dropClass\" class=\"dropElement soundsPlaceHolder\"><span>Drop sound file here or click on me</span></div>");
		
		$placeHolder.append($slidepanel);
		$placeHolder.append($btslide);
		$placeHolder.append($textDropDiv);
	    $placeHolder.append($imageDropDiv);
	    $placeHolder.append($soundDropDiv);
	    $viewDiv.append($placeHolder);
	    
	    var $dropElement = $(".dropElement");
		
	    $scope.dragEnterLeave = function (evt) {
	        evt.stopPropagation();
	        evt.preventDefault();
	        $(this).css("background-color", $scope.acceptType ? "#bfb" : "#F88");
	    }
	    $scope.dragover = function(evt) {
	        evt.stopPropagation();
	        evt.preventDefault();
	        $scope.acceptType = $scope.checkFileType.call(this, evt.dataTransfer.items[0]?evt.dataTransfer.items[0].type:evt.dataTransfer.files[0].type);
	        $(this).css("background-color", $scope.acceptType ? "#bfb" : "#F88");
	        
	    }
	    
	    $scope.checkFileType = function(type){
	    	var $id = $(this).attr("id");
	    	switch($id){
	    	case "imageDrop":
	    		return ["image/jpeg", "image/png"].indexOf(type) > -1;
	    	case "textDrop":
	    		return ["text/plain"].indexOf(type) > -1;
	    	case "soundDrop":
	    		return ["audio/mp3"].indexOf(type) > -1;
	    	}
	    }
	    
	    jQuery.event.props.push( "dataTransfer" );
	    $dropElement.on("dragenter", $scope.dragEnterLeave);
	    $dropElement.on("dragleave", $scope.dragEnterLeave);
	    $dropElement.on("dragover", $scope.dragover);
	    
	    $scope.drop = function(evt) {
	        
	        evt.stopPropagation();
	        evt.preventDefault();
	        var file;
	        if(this.id=="slider"){
	        	file = $("#intext").val();
	        	$scope.acceptType = true;
	        }else{
	        $(this).removeAttr("style");	        
	        file = evt.dataTransfer.files[0];
	        }
	        if (file && $scope.acceptType){	        	
	        	$scope.file = file;
	        	$scope.classes = $(this).attr("class");
	        	if(this.id=="slider")
	        		$scope.uploadFile.call($("#textDrop"));
	        	else 
	        		$scope.uploadFile.call(this);
	        }
	        
	    }
	    $dropElement.on("drop", $scope.drop);
		$btslide.on("click", $scope.btnslide);
		$slidepanel.find("span").on("click", $scope.drop);
		$slidepanel.find("input").keypress(function (e) {
			 var key = e.which;
			 if(key == 13) 
			  {
				 $slidepanel.find("span").trigger("click");
				 return false;
			  };
		}); 
	    $scope.clickUpload = function(evt){
	    	evt.stopImmediatePropagation();
	    	$scope.callElement = this;
	    	$("#file").click();
	    }
	    $dropElement.on("click", $scope.clickUpload);
	}
	
	$scope.$watch("wfile", function(){
		var $inputFile = $("#file")[0];
		
		if($inputFile.files[0] && $scope.checkFileType.call($scope.callElement, $inputFile.files[0].type)){
			$scope.file = $inputFile.files[0];			
			$scope.uploadFile.call($scope.callElement);
		}else{			
			$($scope.callElement).css("background-color","#F88");
			setTimeout(function(){$($scope.callElement).css("background-color","#FEFFEC");}, 500);
		}
	});
}]);

