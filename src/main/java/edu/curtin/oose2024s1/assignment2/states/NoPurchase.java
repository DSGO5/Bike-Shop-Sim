package edu.curtin.oose2024s1.assignment2.states;
import edu.curtin.oose2024s1.assignment2.BikeShop;
import edu.curtin.oose2024s1.assignment2.bikes.Bike;


import java.util.logging.Logger;
public class NoPurchase implements ShopState //State for when shop cant accept purchase
{
    private static final Logger LOGGER = Logger.getLogger(NoPurchase.class.getName());

    @Override
    public void purchaseInStore(BikeShop shop)  //Method to purchase a bike in store
    {
        int bikes = shop.getAvailBikes(); //Get the number of available bikes
        if(bikes > 0) //if there are available bikes for purchase
        {
            shop.setState(new AllAvailable()); //Change the state to AllAvailable
            LOGGER.info("Bikes are available for purchase in store: State Changed to AllAvailable");
            shop.purchaseInStore();
        }
        else //If there are no available bikes for purchase
        {
            shop.notifyEventObservers("!!!!!!!!!!!!!!!!\nFAILURE: No bikes left");  //Notify the observers of the failure
            shop.failOccured(); //Increment the fail count
            LOGGER.info("No Available Bikes for purchase, purchase unsuccessful");
        }
    }

    @Override
    public void purchaseOnline(BikeShop shop, Bike bike) //Method to purchase a bike online
    {
        int bikes = shop.getAvailBikes(); //Get the number of available bikes
        if(bikes > 0) //If there are available bikes for purchase
        {
            shop.setState(new AllAvailable()); //Change the state to AllAvailable
            LOGGER.info("Bikes are available for purchase online: State Changed to AllAvailable");
            shop.purchaseOnline(bike);
        }
        else //If there are no available bikes for purchase
        {
            shop.notifyEventObservers("!!!!!!!!!!!!!!!!\nFAILURE: No bikes left"); //Notify the observers of the failure
            shop.failOccured(); //Increment the fail count
            LOGGER.info("No Available Bikes for purchase, purchase unsuccessful");
        }
    }

    @Override
    public void delivery(BikeShop shop) //Method to accept delivery
    {
        if(shop.getTotalBikes() <= 90 && shop.getCash() >= 10000) //Check if the shop has enough space and cash to accept delivery
        {
            shop.setTotalBikes(shop.getTotalBikes() + 10); //Increment the total number of bikes by 10
            shop.setAvailBikes(shop.getAvailBikes() + 10); //Increment the number of available bikes by 10
            shop.setCash(shop.getCash() - 5000); //Decrement the cash by 5000
            LOGGER.info("Delivery Completed");
        }
        else if(shop.getTotalBikes() > 90) //If there is not enough space for delivery
        {
            shop.setState(new NoDelivery()); //Change the state to NoDelivery
            LOGGER.info("Not enough space for Delivery: State Changed to NoDelivery");
            shop.delivery();
        }
        else if(shop.getCash() < 10000) //If there is not enough cash for delivery
        {
            shop.setState(new NoDelivery()); //Change the state to NoDelivery
            LOGGER.info("Not enough cash for Delivery: State Changed to NoDelivery");
            shop.delivery();
        }
    }

    @Override
    public void dropOff(BikeShop shop, Bike bike) //Method to drop off a bike
    {
        if(shop.getTotalBikes() <= 99) //If there is space for drop off
        { 
            shop.setTotalBikes(shop.getTotalBikes() + 1); //Increment the total number of bikes by 1
            shop.setNumServBikes(shop.getNumServBikes() + 1); //Increment the number of bikes being serviced by 1
            shop.addToBikeList(bike); //Add the bike to the bike list
            LOGGER.info(() -> "Drop off Completed for email: " + bike.getEmail());
        }
        else if(shop.getTotalBikes() > 99) //If there is not enough space for drop off
        {
            shop.setState(new NoDropOff()); //Change the state to NoDropOff
            LOGGER.info("Not Enough Space for Drop off: State Changed to noDropOff");
            shop.dropOff(bike);
        }
    }

    @Override
    public void pickUp(BikeShop shop, String email) //Method to pick up a bike
    {
        boolean found = false;
        for(Bike bike : shop.getBikeList().getBikes()) //For each bike in the bike list
        { 
            if(bike.getEmail().equals(email)) //If the bike has the same email as the input
            {
                bike.pickUp(shop); //Call the pickUp method for the bike
                found = true; //Set found to true;
                LOGGER.info(() -> "Bike with Email: " + email + " Pickup Completed");
                break;
            }
        }

        if(found == false) //If the bike is not found
        {
            shop.notifyEventObservers("!!!!!!!!!!!!!!!!\\nFAILURE: Bike not found"); //Notify the event observers
            LOGGER.info(() -> "Bike with Email: " + email + " not found");
        }
    }
}