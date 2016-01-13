var app = angular.module('app',[])

app.controller("Controller", ['$http', function($http) {
    var self = this;
    
    self.getData = function() {
        
        var parameters = "";
        parameters += document.getElementById('amount').value + "/";
        if (document.getElementById('fname').checked) {
            parameters += "fname,"
        }
        if (document.getElementById('lname').checked) {
            parameters += "lname,"
        }
        if (document.getElementById('street').checked) {
            parameters += "street,"
        }
        if (document.getElementById('city').checked) {
            parameters += "city,"
        }
        
        parameters = parameters.substring(0, parameters.length-1);
        
        console.log(parameters);
        
        $http.get("api/addresses/"+parameters).success(function (data) {
            self.persons = data;

        })
        
    }
}])

