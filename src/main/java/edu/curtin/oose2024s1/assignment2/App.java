package edu.curtin.oose2024s1.assignment2;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.curtin.oose2024s1.assignment2.bikes.Bike;
import edu.curtin.oose2024s1.assignment2.observers.EventObserver;
import edu.curtin.oose2024s1.assignment2.states.AllAvailable;
import edu.curtin.oose2024s1.assignment2.states.ShopState;

/**
 * Use this code to get started on Assignment 2. You are free to modify or replace this file as
 * needed (to fulfil the assignment requirements, of course).
 */
public class App
{
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    public static void main(String[] args)
    {
       
        BikeShopInput inp = new BikeShopInput(); // Create BikeShopInput Object to recieve random inputs
        ShopState initState = new AllAvailable(); // Create InitialState and set it to AllAvaiable for Dependency Injection 
        BikeShop shop = new BikeShop(initState); //Dependecy Injection of Initial Shop State, Create shop used for sim 
        EventObserver obs = new EventObserver(shop); //Dependency Injection of Shop to EventObserver to link it back to the shop class
        obs.setUp(); //PMD warning when doing inside EventObserver constructor, so moved here. Adds itself to shop
        int numInput = 0;
        LOGGER.info("Program Start");
        
        try (FileWriter writer = new FileWriter("sim_results.txt", false))
        {
            // This will clear the file
            writer.write("");
            LOGGER.info("Cleared Output File");
        } 
        catch (IOException e) 
        {
            System.out.println("Error clearing sim_results.txt");
            LOGGER.log(Level.SEVERE, "Error clearing Output File", e);
        }

        try
        {
            while(System.in.available() == 0) //while enterc button isn't clicked
            {
                System.out.println("DAY " + shop.getDays() + "\n-------"); 
                String msg = inp.nextMessage();
                String[] parts;
                while(msg != null)
                {
                    numInput++; // each time an input is received, increment numInput
                    parts = msg.split(" "); //split message
                    String message = msg; 
                    String mainInput = parts[0]; //get first part of message
                    switch(mainInput)
                    {
                        case "DELIVERY":
                            LOGGER.info("Attempting Delivery");
                            shop.notifyEventObservers("EVENT: DELIVERY");
                            shop.delivery(); //call delivery method
                            break;
                        case "PURCHASE-IN-STORE":
                            LOGGER.info("Attempting Purchase In Store");
                            shop.notifyEventObservers("EVENT: PURCHASE-IN-STORE");
                            shop.purchaseInStore(); //call purchaseInStore method
                            break;
                        case "PICK-UP":
                            String logEmail1 = parts[1];
                            LOGGER.info(() -> "Attempting Pick Up, Email: " + logEmail1);
                            shop.notifyEventObservers("EVENT: PICK-UP " + parts[1]);
                            shop.pickUp(parts[1]); //call pickUp method
                            break;
                        case "PURCHASE-ONLINE": case "DROP-OFF":
                            String email = parts[1];
                            Bike bike = Factory.makeBike(mainInput, email, shop.getDays()); //create bike object using factory
                            if(mainInput.equals("PURCHASE-ONLINE"))
                            {
                    
                                LOGGER.info(() -> "Attempting Purchase Online, Email: " + email);
                                shop.notifyEventObservers("EVENT: PURCHASE-ONLINE " + email);
                                shop.purchaseOnline(bike); //call purchaseOnline method
                            }
                            else if(mainInput.equals("DROP-OFF"))
                            {
                                LOGGER.info(() -> "Attempting Drop Off, Email: " + email);
                                shop.notifyEventObservers("EVENT: DROP-OFF " + bike.getEmail());
                                shop.dropOff(bike); //call dropOff method
                            }
                            break;
                        default:
                            shop.notifyEventObservers("!!!!!!!!!!!!!!!!\nInvalid input (parsing error) " + msg); //notify observers of invalid input
                            LOGGER.fine(() -> "Invalid input (parsing error) " + message);
                            shop.failOccured(); //increment fail count
                            break;
                    }
                    
                    msg = inp.nextMessage(); //get next message
                }
                if(shop.getDays() % 7 == 0 && shop.getDays() != 0) //if 7 days have passed, pay employee
                {
                    LOGGER.info("Paying Employee $1000");
                    shop.payEmployee();
                }

                // Wait 1 second
                try
                {
                    Thread.sleep(1000);
                    LOGGER.info(() -> "Day Passed. Days elapsed: " + shop.getDays());
                }
                catch(InterruptedException e)
                {
                    LOGGER.log(Level.SEVERE, "Error in Thread Sleep", e);
                    throw new AssertionError(e);
                }

                shop.dayPass(); //increment days
            }

            //Print final stats to console
            printFinalStats(shop, numInput);
            int finalInpNum = numInput;

            LOGGER.info(() -> "Total Number of inputs: " + finalInpNum + "\nTotal Number of failures: " + shop.getFailCount() + "\n PROGRAM END");
        }
        catch(IOException e) //catch any IOExceptions
        {
            System.out.println("Error reading user input");
            LOGGER.log(Level.SEVERE, "Error reading user input", e);
        }
    }

    private static void printFinalStats(BikeShop shop, int numInput) //print final stats to console method
    {
        System.out.println("\n\n####################################");
        System.out.println("Total Number of inputs: " + numInput);
        System.out.println("Total Number of failures: " + shop.getFailCount());
        System.out.println("####################################");
    }
    
 
}
