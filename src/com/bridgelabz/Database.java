package com.bridgelabz;

import java.sql.*;

public class Database {

    static String tableName = "contactList";
    static ResultSet rs;

    static {
        try {
            Connection conn;
            Statement statement;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactdatabase", "Siraj12", "password");
            String Query = "SELECT * FROM " + tableName;
            statement = conn.createStatement();
            rs = statement.executeQuery(Query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // addToDatabase method to add a new contact to database
    public static void addToDatabase(Contact contact) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactdatabase", "Siraj12", "password");
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO "+tableName+" (FirstName, LastName, Number, Email, Address, City, State, ZipCode) " +
                    "VALUES ('" + contact.getFirstName() + "', '" + contact.getLastName() + "', '" + contact.getNumber() + "', '" + contact.getEmail() + "', '" + contact.getAddress() + "', '" + contact.getCity() + "', '" + contact.getState() + "', '" + contact.getZipCode() + "');");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public static void removeContact(String contactName) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactdatabase", "Siraj12", "password");
            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM "+tableName+" WHERE FirstName = '"+contactName+"'");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    // importing contacts from database
    public static Contact importContact() {
        try {
            String firstName = rs.getString("FirstName");
            String lastName = rs.getString("LastName");
            String contact = rs.getString("Number");
            String email = rs.getString("Email");
            String address = rs.getString("Address");
            String city = rs.getString("City");
            String state = rs.getString("State");
            int zipcode = rs.getInt("ZipCode");
            return new Contact(firstName, lastName, contact, address, city, state, email, zipcode);
        } catch (SQLException e) {
            System.out.println("Exception : " + e);
        }
        return null;
    }
}
