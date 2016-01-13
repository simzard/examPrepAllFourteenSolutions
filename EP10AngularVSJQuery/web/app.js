var app = angular.module('app',['ngRoute']);

app.controller('PersonController', ['$http', '$routeParams', function($http, $routeParams) {
    var self = this;
    
    self.persons = {};
    
    $http.get("api/members").success(function (data) {
        self.persons = data;
        self.person = self.persons[$routeParams.id];
    })
    
}])

app.filter('friendsFilter', function() {
    return function(friends) {
        var friendsString = "";
        for (var i = 0; i < friends.length; i++) {
            friendsString += friends[i].name + ", ";
        }
        friendsString = friendsString.substring(0,friendsString.length - 2)
        return friendsString;
    }
})

app.config(function ($routeProvider) {
    $routeProvider
            .when("/home", {
                templateUrl: "overview.html",
                controller: "PersonController as ctrl"
            })
            .when("/detail/:id", {
                templateUrl: "detail.html",
                controller: "PersonController as ctrl"
            })
            .otherwise({
                redirectTo: "/home"
            })
});

