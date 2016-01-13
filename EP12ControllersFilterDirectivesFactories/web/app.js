angular.module('examApp', [])
        .controller('ExamController', ['examFactory', function (examFactory) {
                var self = this;

                self.studentsInfo = {};
                self.studentsInfo.allCourses = examFactory.getAllCourses();
                self.studentsInfo.students = examFactory.getAllStudents();


            }])
        .filter('average', function () {
            return function (grades) {

                var sum = 0;
                var len = 0;
                for (var i = 0; i < grades.length; i++) {
                    if (grades[i].grade != undefined) {
                        // we have a non-empty grade
                        sum += parseInt(grades[i].grade);
                        len++;
                    }

                }
                return sum / len;

            }
        })
        .directive('studentGrades', function () {
            return {
                restrict: 'E',
                templateUrl: 'table.html'
            }
        })
        .factory('examFactory', ['$http', function ($http) {
                var service = {};
                var studentsInfo = {};
                studentsInfo.allCourses = [
                    {courseId: 1000, courseName: "Basic Programming"},
                    {courseId: 1001, courseName: "Advanced Programming"},
                    {courseId: 1003, courseName: "DataBase Intro"}];
                studentsInfo.students = [];
                studentsInfo.students.push({studentId: 100, name: "Peter Hansen", grades: [{grade: "10"}, {grade: "12"}, {}]});
                studentsInfo.students.push({studentId: 101, name: "Jan Olsen", grades: [{grade: "7"}, {grade: "10"}, {}]});
                studentsInfo.students.push({studentId: 102, name: "Gitte Poulsen", grades: [{grade: "7"}, {grade: "7"}, {}]});
                studentsInfo.students.push({studentId: 103, name: "John McDonald", grades: [{grade: "10"}, {}, {grade: "7"}]});

                service.getAllCourses = function () {
                    return studentsInfo.allCourses;
                }
                service.getAllStudents = function () {
                    return studentsInfo.students;
                }

                function makeRESTcall() {
                    $http.get("INSERT REST LOCATION HERE").success(function (data) {
                        studentsInfo = data; 
                    })
                }

                return service;
            }]);
