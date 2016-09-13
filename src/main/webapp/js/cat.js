var app = angular.module('wasdev.sample.catastrophe.ui', [ 'ngAnimate','ui.bootstrap' ]);
app.controller('CarouselCtrl', function($scope, $rootScope, $http) {
	$scope.catName = 'a cat';
	$scope.noWrapSlides = false;
	$http.get("rest/cat/cats").success(function(response) {
		$scope.slides = response;
		
		for (var i = 0; i < response.length; i++) {
			 // Add an index
			 $scope.slides[i].index = i;
	    		}
	  }); 
	  
	$scope.scoreName = function() {
		var activeSlide = $scope.getActiveSlide();
		var id = activeSlide.id;

		$http.put("rest/cat/guess/" + id + "?catName=" + $scope.catName)
				.success(function(response) {
					activeSlide.score = 'score: ' + response.score + '%';
					activeSlide.realName = 'actual name: ' + response.realName;

					// Send an event so the leaderboard can update
					$rootScope.$broadcast('score-updated');

				});
	};
	$scope.getActiveSlide = function() {
		return $scope.slides.filter(function(s) {
			return s.active;
		})[0];
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
