package edu.curtin.oose2024s1.assignment2.bikes;
import edu.curtin.oose2024s1.assignment2.BikeShop;

public abstract class Bike //Abstract class for the bike objects
{
    protected String email; //Email of the bike owner, both bikes have this attribute

    public Bike(String email) //Constructor for the bike objects
    {
        this.email = email;
    }

    public String getEmail() //Getter for the email attribute
    {
        return this.email;
    }

    public abstract void pickUp(BikeShop shop); //Abstract method for picking up a bike
    public abstract void serviceBike(int currentDay, BikeShop shop); //Abstract method for servicing a bike
}
