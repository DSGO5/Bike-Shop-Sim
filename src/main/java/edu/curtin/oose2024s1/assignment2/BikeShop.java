package edu.curtin.oose2024s1.assignment2;
import java.util.ArrayList;
import java.util.List;
import edu.curtin.oose2024s1.assignment2.bikes.Bike;
import edu.curtin.oose2024s1.assignment2.bikes.BikeList;
import edu.curtin.oose2024s1.assignment2.observers.EventObs;
import edu.curtin.oose2024s1.assignment2.states.ShopState;


public class BikeShop
{
    private int cash; // The amount of cash the shop has.
    private int totalBikes; // The total number of bikes in the shop.
    private int availBikes; // The number of bikes available for purchase.
    private int numServBikes; // The number of bikes being serviced.
    private int holdBikes; // The number of bikes awaiting pick-up.
    private int daysPassed; // The number of days that have passed.
    private ShopState state; // The current state of the shop.
    private List <EventObs> eventObservers = new ArrayList<>(); // List of observers to notify of events.
    private BikeList<Bike> bikeList; // List of bikes in the shop (Can be either service bike or online bike)
    private int failCount; // The number of times a bike has failed to be serviced.

    public BikeShop(ShopState state)
    {
        this.cash = 15000;
        this.totalBikes = 50;
        this.availBikes = 50;
        this.numServBikes = 0;
        this.holdBikes = 0;
        this.daysPassed = 0;
        this.state = state; // Set the state of the shop to the state passed in
        this.failCount = 0;
        this.bikeList = new BikeList<>(); // Create a new BikeList object
    }
    

    //Setters
    public void setNumServBikes(int numServBikes) // Set the number of bikes being serviced
    {
        this.numServBikes = numServBikes;
    }

    public void setHoldBikes(int holdBikes) // Set the number of bikes awaiting pick-up
    {
        this.holdBikes = holdBikes;
    }

    public void setState(ShopState state) // Set the state of the shop
    {
        this.state = state;
    }

    public void setTotalBikes(int totalBikes) // Set the total number of bikes in the shop
    {
        this.totalBikes = totalBikes;
    }

    public void setAvailBikes(int availBikes) // Set the number of bikes available for purchase
    {
        this.availBikes = availBikes;
    }

    public void setCash(int cash) // Set the amount of cash the shop has
    {
        this.cash = cash;
    }

    //Getters
    public BikeList<Bike> getBikeList() // Get the list of bikes in the shop
    {
        return this.bikeList;
    }

    public int getCash() // Get the amount of cash the shop has
    {
        return this.cash;
    }

    public int getAvailBikes() // Get the number of bikes available for purchase
    {
        return this.availBikes;
    }

    public int getFailCount() // Get the number of times a bike has failed to be serviced
    {
        return this.failCount;
    }   

    public int getNumServBikes() // Get the number of bikes being serviced
    {
        return this.numServBikes;
    }

    public int getDays() // Get the number of days that have passed
    {
        return this.daysPassed;
    }

    public int getHoldBikes() // Get the number of bikes awaiting pick-up
    {
        return this.holdBikes;
    }

    public int getDaysPassed() // Get the number of days that have passed
    {
        return this.daysPassed;
    }

    public int getTotalBikes() // Get the total number of bikes in the shop
    {
        return this.totalBikes;
    }

    

    //Other methods
    public void failOccured() // Increment the number of times a bike has failed to be serviced
    {
        this.failCount++;
    }

    public void dayPass() // Increment the number of days that have passed and service all bikes
    {
        this.daysPassed++;
        String daymsg = "*******************************************\n" 
                    + "Number of Days Elapsed: " + getDays() + "\n"
                    + "Total Cash in bank account: " + getCash() + "\n"
                    + "Number of bikes available for purchase: " + getAvailBikes() + "\n"
                    + "Number of bikes being serviced: " + getNumServBikes() + "\n"
                    + "Number of bikes awaiting pick-up: " + getHoldBikes() + "\n" 
                    + "TOTAL BIKES: " + getTotalBikes() + "\n*******************************************\n";

        notifyEventObservers(daymsg);
        for(Bike bike : this.bikeList.getBikes()) // Service all bikes
        {
            bike.serviceBike(this.daysPassed, this);
        }
    }

    public void payEmployee() // Pay the employee $1000
    {
        this.cash -= 1000;
        notifyEventObservers("EVENT: PAY EMPLOYEE $1000\n");
    }

    public void addToBikeList(Bike bike) // Add a bike to the list of bikes in the shop
    {
        this.bikeList.addBike(bike);
    }

    public void removeFromBikeList(Bike bike) // Remove a bike from the list of bikes in the shop
    {
        this.bikeList.removeBike(bike);
    }
    
    //State dependent methods
    public void purchaseInStore()
    {
        state.purchaseInStore(this);
    }

    public void purchaseOnline(Bike bike)
    {
        state.purchaseOnline(this, bike);
    }

    public void delivery()
    {
        state.delivery(this);
    }

    public void dropOff(Bike bike)
    {
        
        state.dropOff(this, bike);
    }

    public void pickUp(String email)
    {
        
        state.pickUp(this, email);
    }

    //Observer methods
    public void addEventObserver(EventObs obs)
    {
        eventObservers.add(obs);
    }

    public void notifyEventObservers(String event)
    {
        for(EventObs obs : eventObservers)
        {
            obs.update(event);
        }
    }

    public void removeEventObserver(EventObs obs)
    {
        eventObservers.remove(obs);
    }
}
