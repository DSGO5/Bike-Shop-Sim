package edu.curtin.oose2024s1.assignment2.bikes;
import edu.curtin.oose2024s1.assignment2.BikeShop;

public class ServiceBike extends Bike //ServiceBike class that extends the Bike class
{
    private int recieveDay; //Day the bike was recieved
    private Boolean isServiced; //Boolean to check if the bike has been serviced

    
    public ServiceBike(String email, int recieveDay) //Constructor for the ServiceBike class
    {
        super(email);
        this.recieveDay = recieveDay;
        this.isServiced = false;
    }
 
    public int getRecieveDay() //Getter for the recieveDay attribute
    {
        return this.recieveDay;
    }

    @Override
    //Method to pick up a bike
     public void pickUp(BikeShop shop) //logic moved to bike object as it allows for polymorphism and generic use
     {
        if(this.getServiced() == false) //If the bike has not been serviced
        {
            shop.notifyEventObservers("!!!!!!!!!!!!!!!!\nFAILURE: Bike not ready"); //Notify the observers of the failure
            shop.failOccured(); //Increment the fail count
        } 
        else //If the bike has been serviced
        {
            shop.setTotalBikes(shop.getTotalBikes() - 1); //Decrement the total number of bikes by 1
            shop.setCash(shop.getCash() + 100); //Increment the cash by 100
            shop.removeFromBikeList(this); //Remove the bike from the bike list
            shop.setHoldBikes(shop.getHoldBikes() - 1); //Decrement the number of bikes awaiting pick-up by 1
        }
    }

    @Override
    public void serviceBike(int currentDay, BikeShop shop) //Method to service a bike
    {
        if(currentDay - this.getRecieveDay() == 2) //If the bike has been in the shop for 2 days
        {
            this.setServiced(true); //Set the bike as serviced
            shop.setNumServBikes(shop.getNumServBikes() - 1);; //Decrement the number of bikes being serviced by 1
            shop.setHoldBikes(shop.getHoldBikes() + 1); //Increment the number of bikes awaiting pick-up by 1
        }
    }

    public void setServiced(Boolean isServiced) //Setter for the isServiced attribute
    {
        this.isServiced = isServiced;
    }

    public Boolean getServiced() //Getter for the isServiced attribute
    {
        return this.isServiced;
    }
}
