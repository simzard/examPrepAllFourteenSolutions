var app = angular.module("examDemo", []);

app.controller('Controller', ['$http', function ($http) {
        var self = this;
        self.restaurants = {};

        $http.get("api/restaurant").success(function (data) {
            self.restaurants = data;

        })
        self.order = function(predicate) {
            self.predicate = predicate;
        }
    }])

app.filter('stars', function() {
    return function(rating) {
        var stars = "";
        for (i = 0; i < rating; i++) {
            stars += "*";
        }
        return stars;
    }
})