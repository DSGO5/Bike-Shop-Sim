package edu.curtin.oose2024s1.assignment2;


import java.util.logging.Logger;

import edu.curtin.oose2024s1.assignment2.bikes.Bike;
import edu.curtin.oose2024s1.assignment2.bikes.OnlineBike;
import edu.curtin.oose2024s1.assignment2.bikes.ServiceBike;

public class Factory
{
    private static Logger LOGGER = Logger.getLogger(Factory.class.getName());

    public static Bike makeBike(String input, String email, int recieveDay) //Factory method to create a bike
    {
        Bike bike = null;
        if(input.equals("DROP-OFF")) //If the input is DROP-OFF, create a ServiceBike object
        {
            bike = new ServiceBike(email, recieveDay);
            LOGGER.info("Service Bike Created");
        }
        else if(input.equals("PURCHASE-ONLINE")) //If the input is PURCHASE-ONLINE, create an OnlineBike object
        {
            bike = new OnlineBike(email);
            LOGGER.info("Online Bike Created");
        }
        return bike;
    }
}

