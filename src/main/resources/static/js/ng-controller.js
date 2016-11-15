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
                        if (response.data.errorMessage == null) {
                            $scope.currentUser = response.data.user;
                            $scope.loginOrRegErrorMessage = "";
                        } else {
                            $scope.loginOrRegErrorMessage = response.data.errorMessage;
                        }
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.login = function(loginEmail, loginPassword) {
            console.log("In login function in ng controller");

            var returningUserInfo = {
                email: loginEmail,
                password: loginPassword
            }

            $http.post("/login.json", returningUserInfo)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        if (response.data.errorMessage == null) {
                            $scope.currentUser = response.data.user;
                            $scope.loginOrRegErrorMessage = "";
                        } else {
                            $scope.loginOrRegErrorMessage = response.data.errorMessage;
                        }
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };


   });