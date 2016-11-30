angular.module('DailyChecklistApp', [])
   .controller('ChecklistNGController', function($scope, $http) {

        //Called on ng-init.
        //Returns a list of all the current user's to dos and also sets currentUserId from the userId in the query string
        $scope.getMyToDos = function(userId) {
            console.log("In getMyToDos function in checklist controller");
            currentUserId = userId;

            $http.post("/getMyToDos.json", userId)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        $scope.allToDos = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.createNewToDo = function(toDoDescription, statusString) {
            console.log("In createNewToDo function in checklist controller");

            var newToDo = {
                description: toDoDescription,
                isDone: false,
                statusString: statusString,
                userId: currentUserId
            }

            $http.post("/createNewToDo.json", newToDo)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        $scope.allToDos = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.toggleIsDone = function(toDo) {
            console.log("In toggleIsDone function in checklist controller");

            $http.post("/toggleIsDone.json", toDo)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        $scope.allToDos = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.deleteToDo = function(toDo) {
            console.log("In deleteToDo function in checklist controller");

            $http.post("/deleteToDo.json", toDo)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        $scope.allToDos = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        var currentUserId;

   });