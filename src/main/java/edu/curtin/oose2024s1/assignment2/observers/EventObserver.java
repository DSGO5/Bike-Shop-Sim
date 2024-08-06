package edu.curtin.oose2024s1.assignment2.observers;
import java.io.FileWriter;
import java.io.IOException;

import edu.curtin.oose2024s1.assignment2.BikeShop;



public class EventObserver implements EventObs //Observer class to observe events
{
    private BikeShop shop; //The shop to observe

    public EventObserver(BikeShop shop) //Constructor
    {
        this.shop = shop; //Sets the shop from the parameter
    }    

    public void setUp() //Sets up the observer in the shop
    {
        shop.addEventObserver(this);
    }

    private void writeToFile(String msg) //Writes a message to a file
    {
        try(var writer = new FileWriter("sim_results.txt", true)) 
        {
            writer.write(msg + "\n");
            writer.flush(); //Flushes the writer
        }
        catch(IOException e)
        {
            System.out.println("Error writing to file");
        }
    }

    @Override
    public void update(String message) //Updates the observer with a message
    {
        System.out.println(message); //Prints the message
        writeToFile(message); //Writes the message to a file
    }
}
