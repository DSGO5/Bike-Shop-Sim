package edu.curtin.oose2024s1.assignment2.bikes;
import edu.curtin.oose2024s1.assignment2.BikeShop;

public class OnlineBike extends Bike //OnlineBike class that extends the Bike class
{

    public OnlineBike(String email) //Constructor for the OnlineBike class
    {
        super(email);
    }

    @Override
    //Method to pick up a bike
    public void pickUp(BikeShop shop) //logic moved to bike object as it allows for polymorphism and generic use
    {
        shop.setTotalBikes(shop.getTotalBikes() - 1);
        shop.setHoldBikes(shop.getHoldBikes() - 1);
        shop.removeFromBikeList(this);
    }

    @Override
    public void serviceBike(int currentDay, BikeShop shop)  //Method to service a bike, unnecessary for OnlineBike
    {
        // Do nothing
    }


    
}
