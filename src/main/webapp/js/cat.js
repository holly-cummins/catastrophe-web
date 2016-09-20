var canvas;

window.onload = function() {
  canvas = new fabric.Canvas('catcanvas');
  canvas.isDrawingMode = true;
  canvas.freeDrawingBrush.width = 2;
}

var app = angular.module('wasdev.sample.catastrophe.ui', [ 'ngAnimate','ui.bootstrap' ]);

app.controller('CarouselCtrl', function($scope, $rootScope, $http) {
	$scope.catName = 'a cat';
	$scope.noWrapSlides = false;
	$http.get("rest/cat/cats").success(function(response) {
		$scope.slides = response;

		for (var i = 0; i < response.length; i++) {
			 // Add an index
			 // Bump by one to allow for the canvas
			 $scope.slides[i].index = i + 1;
   		}
		
	  }); 
	  
	$scope.scoreName = function() {
		var activeSlide = $scope.getActiveSlide();
		var id = activeSlide.id;

		var img = canvas.toDataURL('image/png');
		console.log(img);
		$http.put("rest/cat/score?encodedImage=" + img)
				.success(function(response) {
					console.log(response);
					$scope.slideScore = 'score: ' + response.score + '%';
					$scope.realName = 'actual name: ' + response.realName;
					$scope.algorithm = 'powered by: ' + response.scoringAlgorithm;

					// Send an event so the leaderboard can update
					$rootScope.$broadcast('score-updated');

				});
	};
	$scope.getActiveSlide = function() {
		return $scope.slides[$scope.active];
	};

});
app.controller('leaderboardCtrl', function($scope, $http) {
	$http.get("rest/cat/scores").success(function(response) {
		$scope.names = response;
	});

	$scope.$on('score-updated', function(event, args) {

		$http.get("rest/cat/scores").success(function(response) {
			$scope.names = response;
		});
	});
});
