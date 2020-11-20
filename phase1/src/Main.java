import controllers.*;
import entities.*;
import gateways.*;
import useCases.*;
import userInterfaces.MenuUserInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //need to get all users and messages from files
        UserIO.readFile("./phase1/src/data/users.txt");
        EventIO.readFile("./phase1/src/data/events.txt");
        MessageIO.readFile("./phase1/src/data/messages.txt");
        while (true) {
            User loggedInUser = null;
            Scanner s = new Scanner(System.in);
            while (loggedInUser == null) {
                System.out.println("Please enter your Username:");
                String username = s.nextLine();
                System.out.println("Please enter your Password:");
                String password = s.nextLine();
                loggedInUser = LoginSystem.loginCheck(username, password);
                if (loggedInUser == null) {
                    System.out.println("Your username or password is incorrect. Please enter them again.");
                }
            }
            MenuUserInterface.loadMenu(loggedInUser);
            UserIO.writeFile("./phase1/src/data/users.txt");
            MessageIO.writeFile("./phase1/src/data/messages.txt");
            EventIO.writeFile("./phase1/src/data/events.txt");
        }
    }
}
