angular.module('DailyChecklistApp', [])
   .controller('NGController', function($scope, $http) {

        $scope.register = function(firstName, lastName, registerEmail, registerPassword) {
            console.log("In register function in ng controller");

            var newUserInfo = {
                firstName: firstName,
                lastName: lastName,
                email: registerEmail,
                password: registerPassword
            }

            $http.post("/register.json", newUserInfo)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.test = "Angular working!";


   });