var canvas;

window.onload = function() {
  canvas = new fabric.Canvas('catcanvas');
  canvas.isDrawingMode = true;
  canvas.freeDrawingBrush.width = 2;
}

var app = angular.module('wasdev.sample.catastrophe.ui', [ 'ngAnimate','ui.bootstrap' ]);

app.controller('CarouselCtrl', function($scope, $rootScope, $http) {
    $scope.catastopheScoring = ' . . . ';
    $scope.catastopheCats = ' . . . ';
	$scope.catastropheUsers = ' . . . ';
	$scope.catName = 'a cat';
	$scope.noWrapSlides = false;
	$http.get("rest/cat/cats").success(function(response) {
		$scope.slides = response.cats;
		$scope.catastropheUsers = response.catastropheUsers;

		for (var i = 0; i < response.cats.length; i++) {
			 // Add an index
			 // Bump by one to allow for the canvas
			 $scope.slides[i].index = i + 1;
   		}
		
	  }); 
	  
	$scope.scoreName = function() {
		$scope.status = 'scoring  . . . ';
		$scope.slideScore = '';
		$scope.bestGuess = '';
		$scope.algorithm = '';
		$scope.fact = '';
		var activeSlide = $scope.getActiveSlide();

		var img = canvas.toDataURL('image/png');
		$http.put("rest/cat/score", {}, {params: {"encodedImage": img}})
				.success(function(response) {
					$scope.status = '';
					$scope.slideScore = 'score: ' + response.score + '%';
					$scope.bestGuess = 'this looks most like: a ' + response.bestGuess;
					$scope.algorithm = 'powered by: ' + response.scoringAlgorithm;
                    $scope.fact = response.fact;
                    $scope.catastropheScoring = response.catastropheScoring;
                    $scope.catastropheCats = response.catastropheCats;

                    // Send an event so the leaderboard can update
					$rootScope.$broadcast('score-updated');

				});
	};
	$scope.getActiveSlide = function() {
		if ($scope.slides)
	    {
		   return $scope.slides[$scope.active];
	    } else {
		  return undefined;
	    }
	};

});
app.controller('leaderboardCtrl', function($scope, $http) {
	$scope.catastropheUsers = ' . . . ';
	$http.get("rest/cat/scores").success(function(response) {
		$scope.names = response.scores;
		$scope.catastropheUsers = response.catastropheUsers;
	});

	$scope.$on('score-updated', function(event, args) {

		$http.get("rest/cat/scores").success(function(response) {
			$scope.names = response.scores;
			$scope.catastropheUsers = response.catastropheUsers;
		});
	});
});
