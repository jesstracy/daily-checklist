package com.tiy;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

/**
 * Created by jessicatracy on 11/15/16.
 */
@RestController
public class JSONController {
    //conn is initialized in either register or login
    Connection conn;

    @RequestMapping(path = "/register.json", method = RequestMethod.POST)
    public LoginRegReturnContainer register(@RequestBody User newUser) {
        System.out.println("\nIn register method in JSON controller");
        LoginRegReturnContainer loginRegReturnContainer = null;
        try {
            conn = DriverManager.getConnection("jdbc:h2:./main");

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

                //prepare the container to return with user and null error message
                loginRegReturnContainer = new LoginRegReturnContainer(newUser, null);
            } else {
                //resultSet has data
                String message = "Cannot create new user. Already a user with email " + newUser.getEmail();
                System.out.println(message);
                //prepare the container to return with null user and error message
                loginRegReturnContainer = new LoginRegReturnContainer(null, message);
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
            //check if the user is in the database (by email)
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, user.getEmail());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                //email is attached to a user in our db
                //check to see if password matches
                if (user.getPassword().equals(resultSet.getString("password"))) {
                    //passwords match. Retrieve all fields for user object
                    User retrievedUser = new User(resultSet.getString("firstName"), resultSet.getString("lastName"), user.getEmail(), user.getPassword());
                    //prepare return container with retrieved user and null error message
                    System.out.println("User logged in with email " + user.getEmail());
                    loginRegReturnContainer = new LoginRegReturnContainer(retrievedUser, null);
                } else {
                    //passwords don't match
                    String message = "Incorrect password for user with email " + user.getEmail();
                    System.out.println(message);
                    //prepare a return container with null user and error message
                    loginRegReturnContainer = new LoginRegReturnContainer(null, message);
                }
            } else {
                //no users with that email in our db
                String message = "No users with email " + user.getEmail() + " in our database";
                System.out.println(message);
                //prepare a return container with null user and error message
                loginRegReturnContainer = new LoginRegReturnContainer(null, message);
            }
        } catch (SQLException ex) {
            System.out.println("Exception caught in login endpoint");
        }

        return loginRegReturnContainer;
    }
}
