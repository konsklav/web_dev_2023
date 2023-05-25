package mainpackage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

import cinemamodelpackage.Cinemas;
import cinemamodelpackage.Films;
import cinemamodelpackage.Provoles;
import usersmodelpackage.Admins;
import usersmodelpackage.ContentAdmins;
import usersmodelpackage.Customers;
import usersmodelpackage.Users;

class MainClass {

    private static Scanner scanner = new Scanner(System.in);

    public static String[] getCredentials() {
        String[] creds = new String[2];

        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password");
        String pass = scanner.nextLine();

        creds[0] = username;
        creds[1] = pass;

        return creds;
    }

    public static void login() {
        System.out.println("Welcome to the Cinema!");
        System.out.println("Choose a login option from the menu bellow: \n" + "1)Login as a Customer \n" + "2)Login as a Content Admin \n" + "3)Login as an Admin \n" + "4)Create an account");

        int login_option = Integer.parseInt(scanner.nextLine());

        String[] creds = getCredentials();

        int action_option;

        switch (login_option) {
            case 1:
                Customers customer = new Customers("", creds[0], creds[1]);
                customer.login();System.out.println("What would you like to do? \n" + "1)See available films \n" + "2)Make a reservation \n" + "3)View your reservation");
                action_option = Integer.parseInt(scanner.nextLine());
                if (action_option == 1) {
                    customer.showAvailableFilms();
                }else if (action_option == 2) {
                    //Will ask for the provoliId field and execute the makeReservation method
                }else if (action_option == 3) {
                    customer.viewReservation();
                }
                break;
            case 2:
                ContentAdmins contentAdmin = new ContentAdmins("", creds[0], creds[1]);
                contentAdmin.login();
                System.out.println("What would you like to do? \n" + "1)Insert a new film \n" + "2)Delete a film \n" + "3)Create a new provoli");
                action_option = Integer.parseInt(scanner.nextLine());
                if (action_option == 1) {
                    Films film = new Films("cr2023", "Creed III", "Drama", "The sequel of Creed II!", Duration.ofMinutes(124));
                    contentAdmin.insertFilm(film);
                }else if (action_option == 2) {
                    //Asks for the filmId and executes the deleteFilm method
                }else if (action_option == 3) {
                    Films film = new Films("cr2023", "Creed III", "Drama", "The sequel of Creed II!", Duration.ofMinutes(124));
                    Cinemas cinema = new Cinemas("vi123", true, 50);
                    Provoles provoli = new Provoles();
                    provoli = contentAdmin.createNewProvoli(film, cinema,	LocalDateTime.of(2023, 4, 25, 19, 30, 0));
                }
                break;
            case 3:
                Admins admin = new Admins("", creds[0], creds[1]);
                admin.login();
                System.out.println("What would you like to do? \n" + "1)Create a user \n" + "2)Update an existing user \n" + "3)Delete a user \n" + "4)Search for a user");
                action_option = Integer.parseInt(scanner.nextLine());
                if (action_option == 1) {
                    Users user = new Users();
                    admin.createUser(user);
                }else if (action_option == 2) {
                    Users newUser = new Users("a_name", "a_username", "a_password");
                    admin.updateUser("a_username", newUser);
                }else if (action_option == 3) {
                    admin.deleteUser("a_username");
                }else if (action_option == 4) {
                    admin.searchUser("a_username");
                }
                break;
            case 4:
                //Will prompt options for an account type(Customer, ContentAdmin or Admin) for the user to choose
                //and create a new object according to this choice and the input of the users credentials
                //then it will create an anonymous ContentAdmin object for the "creation" of the user and storage of its credentials
                //in the db.
                break;
            default:
                System.out.println("Please enter a number in range 1 - 4!");
                break;
        }

        scanner.close();
    }

    public static void main(String[] args) {

        try {
            login();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //Creating objects for the purpose of Askisi 1
        Customers customer = new Customers();
        ContentAdmins contentAdmin = new ContentAdmins();
        Admins admin = new Admins();
        Films film = new Films();
        Cinemas cinema = new Cinemas();
        Provoles provoli = new Provoles();
        try {	//Saves the created objects in the objects.txt file
            PrintWriter pw = new PrintWriter(new File("objects.txt"));
            pw.println(customer.toString());
            pw.println(contentAdmin.toString());
            pw.println(admin.toString());
            pw.println(film.toString());
            pw.println(cinema.toString());
            pw.println(provoli.toString());
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}