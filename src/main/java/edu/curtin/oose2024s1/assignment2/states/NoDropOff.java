package edu.curtin.oose2024s1.assignment2.states;

import java.util.logging.Logger;

import edu.curtin.oose2024s1.assignment2.BikeShop;
import edu.curtin.oose2024s1.assignment2.bikes.Bike;


public class NoDropOff implements ShopState
{
    private static final Logger LOGGER = Logger.getLogger(NoDropOff.class.getName());

    @Override
    public void purchaseInStore(BikeShop shop) //Method to purchase a bike in store
    {
        if(shop.getAvailBikes() >= 1) //If there are available bikes
        {
            shop.setAvailBikes(shop.getAvailBikes() - 1); //Decrement the number of available bikes
            shop.setCash(shop.getCash() + 1000); //Increment the cash by 1000
            shop.setTotalBikes(shop.getTotalBikes() - 1); //Decrement the total number of bikes    
            LOGGER.info("Purchase in-store Completed");
        }
        else if(shop.getAvailBikes() < 1) //If there are no available bikes
        {
            shop.setState(new NoPurchase()); //Change the state to NoPurchase
            LOGGER.info("No Available Bikes for Sale: State Changed to NoPurchase");
            shop.purchaseInStore();
        }
    }
    
    @Override
    public void purchaseOnline(BikeShop shop, Bike bike) //Method to purchase a bike online
    {
        if(shop.getAvailBikes() >= 1) //If there are available bikes
        {
            shop.setAvailBikes(shop.getAvailBikes() - 1);  //Decrement the number of available bikes
            shop.setHoldBikes(shop.getHoldBikes() + 1); //Increment the number of bikes awaiting pick-up
            shop.addToBikeList(bike); //Add the bike to the bike list
            shop.setCash(shop.getCash() + 1000); //Increment the cash by 1000
            LOGGER.info(() -> "Purchase Online Completed for email: " + bike.getEmail());
        }
        else if(shop.getAvailBikes() < 1) //If there are no available bikes
        {
            shop.setState(new NoPurchase()); //Change the state to NoPurchase
            LOGGER.info("No Available Bike for Sale: State Changed to NoPurchase");
            shop.purchaseOnline(bike);
        }
    }

    @Override
    public void delivery(BikeShop shop) //Method to accept delivery
    {
        if(shop.getTotalBikes() <= 90 && shop.getCash() >= 10000) //check if shop has enough space and cash to accept delivery
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
        else if (shop.getCash() < 10000) //If there is not enough cash for delivery
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
            shop.setState(new AllAvailable()); //Change the state to AllAvailable
            LOGGER.info("Enough space for Drop Off: State Changed to AllAvailable");
            shop.dropOff(bike);
        }
        else if(shop.getTotalBikes() > 99) //If there is not enough space for drop off
        {
            shop.notifyEventObservers("!!!!!!!!!!!!!!!!\nFAILURE: Not enough space"); //Notify the observers of the failure
            shop.failOccured(); //Increment the fail count
            LOGGER.info("Not enough space for Drop Off, Drop Off not accepted");
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
                bike.pickUp(shop); //Pick up the bike
                found = true; 
                LOGGER.info(() -> "Bike with Email: " + email + " Pickup Completed");
                break;
            }
        }

        if(found == false) //If the bike is not found
        {
            shop.notifyEventObservers("!!!!!!!!!!!!!!!!\\nFAILURE: Bike not found"); //Notify the observers of the failure
            LOGGER.info(() -> "Bike with Email: " + email + " not found");
        }
    }


}
