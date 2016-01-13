var app = angular.module("app",[]);

app.filter('name', function() {
    return function(personObj) {
        return personObj.lastName + ", " + personObj.firstName;
    }
})

app.directive('loginForm', function() {
    return {
        restrict: 'EA',
        scope: {
            header: "@"
        },
        templateUrl: 'loginForm.html'
    }
})
app.factory('Factory', function() {
    var service = {};
    
    service.titleCase = function(text) {
        var tokens = text.split(" ");
        var result = "";
        for (var i = 0; i < tokens.length; i++) {
            result += tokens[i][0].toUpperCase() + tokens[i].substring(1) + " ";
        }
        result = result.substring(0, result.length - 1);
        return result;
    }
    
    service.camelCase = function(text) {
        var tokens = text.split(" ");
        var result = "";
        for (var i = 0; i < tokens.length; i++) {
            result += tokens[i][0].toUpperCase() + tokens[i].substring(1);
        }
        return result;
    }
    
    service.dashCase = function(text) {
        var tokens = text.split(" ");
        var result = "";
        for (var i = 0; i < tokens.length; i++) {
            result += tokens[i].toLowerCase() + "-";
        }
        result = result.substring(0, result.length - 1);
        return result;
    }
    
    return service;
})

app.controller('Controller', ['Factory', function(factory) {
    var self = this;
    var text = "my example service";
    self.titleCase = factory.titleCase(text);
    self.camelCase = factory.camelCase(text);
    self.dashCase = factory.dashCase(text);
    self.person = { firstName: "Peter", lastName: "Smith" };
    
}])

