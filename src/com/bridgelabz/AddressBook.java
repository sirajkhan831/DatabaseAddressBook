package com.bridgelabz;

import java.sql.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Scanner;

public class AddressBook {

    public static void main(String[] args) throws SQLException {
        LinkedList<Contact> contact = new LinkedList<>();
        while (rs.next()) {
            sort(contact, importContact());
        }
        System.out.println("Hello welcome to your address book.");
        menu(contact);
    }

    // Menu method for user interaction
    public static void menu(LinkedList<Contact> contact) {
        System.out.println("Press 1 -> to view all your contacts.");
        System.out.println("Press 2 -> to add a contact.");
        System.out.println("Press 3 -> to remove a contact.");
        System.out.println("Press 4 -> to modify a contact.");
        System.out.println("Press 9 -> to stop the program.");
        Scanner optionScan = new Scanner(System.in);
        int option = optionScan.nextInt();
        if (option == 1) {
            printList(contact);
            contactDetails(contact);
            System.out.print("Enter 99 to continue to the menu : ");
            Scanner menuOptScan = new Scanner(System.in);
            int menuOpt = menuOptScan.nextInt();
            if (menuOpt == 99) {
                menu(contact);
            }
        } else if (option == 2) {
            Contact newContact = addContact();
            addToDatabase(newContact);
            sort(contact, newContact);
            printList(contact);
            contactDetails(contact);
            System.out.print("Enter 99 to continue to the menu : ");
            Scanner menuOptScan = new Scanner(System.in);
            int menuOpt = menuOptScan.nextInt();
            if (menuOpt == 99) {
                menu(contact);
            }
        } else if (option == 3) {
            printList(contact);
            delete(contact);
            printList(contact);
            System.out.print("Enter 99 to continue to the menu : ");
            Scanner menuOptScan = new Scanner(System.in);
            int menuOpt = menuOptScan.nextInt();
            if (menuOpt == 99) {
                menu(contact);
            }
        } else if (option == 4) {
            modify(contact);
            printList(contact);
            contactDetails(contact);
            System.out.print("Enter 99 to continue to the menu : ");
            Scanner menuOptScan = new Scanner(System.in);
            int menuOpt = menuOptScan.nextInt();
            if (menuOpt == 99) {
                menu(contact);
            }
        } else if (option == 9) {
            System.exit(1);
        }
    }

    // contactDetails method which prints out all the details of given method via getAll(); inside Contact class
    public static void contactDetails(LinkedList<Contact> contact) {
        System.out.println("Enter index position for all the details for chosen contact or press 99 for menu: ");
        Scanner numberScan = new Scanner(System.in);
        int number = numberScan.nextInt();
        if (number == 99) {
            menu(contact);
        } else
            contact.get(number).getAll();
    }

    // printList for printing the sorted contacts in the console
    public static void printList(LinkedList<Contact> contacts) {
        int index = -1;
        System.out.println("Position        |          Name");
        System.out.println("________________________________");
        for (Contact contact : contacts) {
            index++;
            if (index > 0 && index < 10) {
                System.out.println("  " + index + "             |          " + contact.getFirstName());
            } else if (index >= 10) {
                System.out.println("  " + index + "            |          " + contact.getFirstName());
            }
        }
    }

    //sort method to sort all the contacts in alphabetical order using ListIterators
    public static void sort(LinkedList<Contact> contactLinkedList, Contact contact) {
        ListIterator<Contact> contactListIterator = contactLinkedList.listIterator();
        while (contactListIterator.hasNext()) {
            String compareStr = contactListIterator.next().getFirstName();
            int compare = compareStr.compareTo(contact.getFirstName());
            if (compare == 0) {
                System.out.println("The same contact already exists.");
                return;
            } else if (compare > 0) {
                contactListIterator.previous();
                contactListIterator.add(contact);
                return;
            }
        }
        contactListIterator.add(contact);
    }

    // addContact method to add a new contact by creating a new class and returning it through addContact();
    public static Contact addContact() {
        System.out.println("Enter a new first name (The first letter should be in uppercase): ");
        Scanner firstNameScan = new Scanner(System.in);
        String firstNameTemp = firstNameScan.nextLine();
        String firstLetter = firstNameTemp.substring(0, 1).toUpperCase();
        String remLetter = firstNameTemp.substring(1);
        String firstName = firstLetter + remLetter;
        System.out.println("Enter last name : ");
        Scanner lastNameScan = new Scanner(System.in);
        String lastNameTemp = lastNameScan.nextLine();
        String firstLetterLastName = lastNameTemp.substring(0, 1).toUpperCase();
        String remLetterLastName = lastNameTemp.substring(1);
        String lastName = firstLetterLastName + remLetterLastName;
        System.out.println("Enter contact number : ");
        Scanner contactScan = new Scanner(System.in);
        String contact = contactScan.nextLine();
        System.out.println("Enter Email : ");
        Scanner emailScan = new Scanner(System.in);
        String email = emailScan.nextLine();
        System.out.println("Enter city : ");
        Scanner cityScan = new Scanner(System.in);
        String city = cityScan.nextLine();
        System.out.println("Enter State : ");
        Scanner stateScan = new Scanner(System.in);
        String state = stateScan.nextLine();
        System.out.println("Enter Address : ");
        Scanner addressScan = new Scanner(System.in);
        String address = addressScan.nextLine();
        System.out.println("Enter ZipCode : ");
        Scanner zipScan = new Scanner(System.in);
        int zipcode = zipScan.nextInt();

        return new Contact(firstName, lastName, contact, address, city, state, email, zipcode);
    }

    // delete method to delete selected contact by removing it from the linked list
    public static void delete(LinkedList<Contact> contacts) {
        System.out.print("Enter the NAME of contact to delete : ");
        Scanner nameScan = new Scanner(System.in);
        String name = nameScan.nextLine().toLowerCase();
        if (name.length() < 3) {
            System.out.println("Invalid name");
        } else contacts.removeIf(contact -> Objects.equals(name, contact.getFirstName().toLowerCase()));
        System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase() + " has been deleted successfully");
    }

    //modify method to modify selected contact
    public static void modify(LinkedList<Contact> contacts) {
        printList(contacts);
        System.out.println("Enter the name of contact to modify : ");
        Scanner nameScan = new Scanner(System.in);
        String name = nameScan.nextLine().toLowerCase();
        ListIterator<Contact> contactListIterator = contacts.listIterator();
        while (contactListIterator.hasNext()) {
            if (Objects.equals(name, contactListIterator.next().getFirstName().toLowerCase())) {
                contactListIterator.remove();
                Contact newContact = addContact();
                sort(contacts, newContact);
                printList(contacts);
                contactDetails(contacts);
                System.out.println("Enter 99 to continue to the menu : ");
                Scanner menuOptScan = new Scanner(System.in);
                int menuOpt = menuOptScan.nextInt();
                if (menuOpt == 99) {
                    menu(contacts);
                }

            }
        }
    }

    // Database code section starts from here

    static String tableName = "contactDatabase";
    static ResultSet rs;
    static {
        try {
            Connection conn;
            Statement statement;
            conn = DriverManager.getConnection("jdbc:sqlite:Contacts.db");
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
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Contacts.db");
            Statement statement = conn.createStatement();
            statement.execute(("INSERT INTO " + tableName + " (FirstName, LastName, Number, Email, Address, City, State, ZipCode) " +
                    "VALUES(" + "'" + contact.getFirstName() + "'" + ", " + "'" + contact.getLastName() + "'" + ", " + "'" + contact.getNumber() + "'" + ", " + "'" + contact.getEmail() + "'" + ", " + "'" + contact.getAddress() + "'" + ", " + "'" + contact.getCity() + "'" + ", " + "'" + contact.getState() + "'" + ", " + contact.getZipCode() + ")"));
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
            String lastName = "";
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