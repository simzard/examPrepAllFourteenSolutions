var app = angular.module('app', ['ngRoute']);

var users = [];

app.controller("UserController", function ($http, $routeParams) {
    var self = this;

    if (users.length === 0) {

        $http.get("data/data.json").success(function (data) {
            users = data.users;
            self.users = users;
            // used for page refresh on details
            // otherwise the HTTP request hasn't finished
            self.user = users[$routeParams.id];
        })
    }
    else { //We used the cache property on the http request instead
        self.users = users;
    }


    if (users != null) {
        console.log("Adding user: " + $routeParams.id)
        self.user = users[$routeParams.id];
    }
});

app.config(function ($routeProvider) {
    $routeProvider
            .when("/home", {
                templateUrl: "overview.html",
                controller: "UserController as ctrl"
            })
            .when("/detail/:id", {
                templateUrl: "detail.html",
                controller: "UserController as ctrl"
            })
            .otherwise({
                redirectTo: "/home"
            })
});
