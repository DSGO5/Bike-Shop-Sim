package edu.curtin.oose2024s1.assignment2.bikes;

import java.util.*;

public class BikeList<B extends Bike> //Generics used to allow for any type of bike to be added to the list
{
    private List<B> bikes = new ArrayList<>(); //List of bikes

    public void addBike(B bike) //Method to add a bike to the list
    {
        this.bikes.add(bike);
    }

    public void removeBike(B bike) //Method to remove a bike from the list
    {
        this.bikes.remove(bike);
    }

    public List<B> getBikes() //Getter for the list of bikes
    {
        return this.bikes;
    }
}
