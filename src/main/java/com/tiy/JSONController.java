package com.tiy;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jessicatracy on 11/15/16.
 */
@RestController
public class JSONController {
    //conn is initialized in either register or login
    Connection conn;
    private boolean connMade = false;

    @RequestMapping(path = "/register.json", method = RequestMethod.POST)
    public LoginRegReturnContainer register(@RequestBody User newUser) {
        System.out.println("\nIn register method in JSON controller");
        LoginRegReturnContainer loginRegReturnContainer = null;
        try {
            conn = DriverManager.getConnection("jdbc:h2:./main");
            connMade = true;

            //check to see if there is already a user with that email in our db
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, newUser.getEmail());
            ResultSet resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                //No data in result set, so can save the user.

                //insert new user into db
                PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?, ?)");
                stmt2.setString(1, newUser.getFirstName());
                stmt2.setString(2, newUser.getLastName());
                stmt2.setString(3, newUser.getEmail());
                stmt2.setString(4, newUser.getPassword());
                stmt2.execute();

                System.out.println("New User inserted into db with email " + newUser.getEmail());

                //get the id of the new user
                int userId = getUserId(newUser);

                //prepare the container to return with user and null error message
                loginRegReturnContainer = new LoginRegReturnContainer(userId, null);
            } else {
                //resultSet has data
                String message = "Cannot create new user. Already a user with email " + newUser.getEmail();
                System.out.println(message);
                //prepare the container to return with userId -1 and error message
                loginRegReturnContainer = new LoginRegReturnContainer(-1, message);
            }

        } catch (SQLException ex) {
            System.out.println("Exception caught in register endpoint");
            ex.printStackTrace();
        }

        return loginRegReturnContainer;
    }

    @RequestMapping(path = "/login.json", method = RequestMethod.POST)
    public LoginRegReturnContainer login(@RequestBody User user) {
        System.out.println("\nIn login method in JSON controller");
        LoginRegReturnContainer loginRegReturnContainer = null;
        try {
            conn = DriverManager.getConnection("jdbc:h2:./main");
            connMade = true;
            //check if the user is in the database (by email)
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, user.getEmail());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                //email is attached to a user in our db
                //check to see if password matches
                if (user.getPassword().equals(resultSet.getString("password"))) {
                    //passwords match. Retrieve the id of the record to return.
                    int userId = resultSet.getInt("id");

                    //prepare return container with retrieved user and null error message
                    loginRegReturnContainer = new LoginRegReturnContainer(userId, null);

                    System.out.println("User logged in with email " + user.getEmail());
                } else {
                    //passwords don't match
                    String message = "Incorrect password for user with email " + user.getEmail();
                    System.out.println(message);
                    //prepare the container to return with userId -1 and error message
                    loginRegReturnContainer = new LoginRegReturnContainer(-1, message);
                }
            } else {
                //no users with that email in our db
                String message = "No users with email " + user.getEmail() + " in our database";
                System.out.println(message);
                //prepare the container to return with userId -1 and error message
                loginRegReturnContainer = new LoginRegReturnContainer(-1, message);
            }
        } catch (SQLException ex) {
            System.out.println("Exception caught in login endpoint");
        }

        return loginRegReturnContainer;
    }

    @RequestMapping(path = "/getMyToDos.json", method = RequestMethod.POST)
    public ArrayList<ToDo> createNewTodo(@RequestBody int userId) {
        try {
            if (!connMade) {
                conn = DriverManager.getConnection("jdbc:h2:./main");
                connMade = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("\nIn getMyToDos method in JSON controller");
        ArrayList<ToDo> allToDos = null;
        try {
            allToDos = getAllToDosByUserId(userId, "all");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allToDos;
    }

    @RequestMapping(path = "/createNewToDo.json", method = RequestMethod.POST)
    public ArrayList<ToDo> createNewTodo(@RequestBody ToDo toDo) {
        System.out.println("\nIn createNewTodo method in JSON controller");
        ArrayList<ToDo> allToDos = null;
        try {
            //check if description is valid
            if (toDo.getDescription() != null && !toDo.getDescription().equals("")) {
                if (toDo.getUserId() != -1) {
                    //insert into database for currentUser
                    PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO todos VALUES (NULL, ?, ?, ?, ?)");
                    stmt2.setString(1, toDo.getDescription());
                    stmt2.setBoolean(2, toDo.getIsDone());
                    stmt2.setString(3, toDo.getStatusString());
                    stmt2.setInt(4, toDo.getUserId());
                    stmt2.execute();

                    System.out.println("Adding todo with description \"" + toDo.getDescription() + "\" for user with id " + toDo.getUserId());

                    //return a list of all todos for that user.
                    allToDos = getAllToDosByUserId(toDo.getUserId(), "all");

                } else {
                    System.out.println("Cannot save todo- not attached to a userId");
                }
            } else {
                System.out.println("Not saving todo because no description provided.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allToDos;
    }

    @RequestMapping(path = "/toggleIsDone.json", method = RequestMethod.POST)
    public ArrayList<ToDo> toggleIsDone(@RequestBody ToDo toDo) {
        System.out.println("\nIn toggleIsDone method in JSON controller");
        ArrayList<ToDo> allToDos = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE todos SET isDone = ? WHERE id = ?");
            stmt.setBoolean(1, !toDo.getIsDone());
            stmt.setInt(2, toDo.getId());
            stmt.execute();

            allToDos = getAllToDosByUserId(toDo.getUserId(), "all");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allToDos;
    }

    @RequestMapping(path = "/deleteToDo.json", method = RequestMethod.POST)
    public ArrayList<ToDo> deleteToDo(@RequestBody ToDo toDo) {
        System.out.println("\nIn deleteToDo method in JSON controller");
        ArrayList<ToDo> allToDos = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM todos WHERE id = ?");
            stmt.setInt(1, toDo.getId());
            stmt.execute();

            allToDos = getAllToDosByUserId(toDo.getUserId(), "all");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allToDos;
    }

    //Retrieves the user's id from the db. Returns -1 if the user is not in the db.
    public int getUserId(User user) throws SQLException {
        int userId = -1;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
        stmt.setString(1, user.getEmail());
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            userId = resultSet.getInt("id");
        }
        return userId;
    }

    //Choose status when calling: now, later, or all.
    public ArrayList<ToDo> getAllToDosByUserId(int userId, String statusString) throws SQLException {
        ArrayList<ToDo> allToDos = new ArrayList<>();
        PreparedStatement stmt;
        if (statusString.equals("all")) {
            stmt = conn.prepareStatement("SELECT * FROM todos WHERE userId = ?");
            stmt.setInt(1, userId);
        } else {
            if (!(statusString.equals("now") || statusString.equals("later"))) {
                System.out.println("Warning! Invalid statusString: " + statusString);
            }
            stmt = conn.prepareStatement("SELECT * FROM todos WHERE userId = ? AND status = ?");
            stmt.setInt(1, userId);
            stmt.setString(2, statusString);
        }
        ResultSet resultSet = stmt.executeQuery();
        int id;
        String description;
        Boolean isDone;
        ToDo todoToAdd;
        ToDoStatus status;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            description = resultSet.getString("description");
            isDone = resultSet.getBoolean("isDone");
            if (resultSet.getString("status").equals("NOW")) {
                status = ToDoStatus.NOW;
            } else {
                status = ToDoStatus.LATER;
            }
            todoToAdd = new ToDo(id, description, isDone, userId, status);
            allToDos.add(todoToAdd);
        }
        return allToDos;
    }

}
