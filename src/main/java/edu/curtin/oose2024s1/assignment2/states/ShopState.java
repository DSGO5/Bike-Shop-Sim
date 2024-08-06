package edu.curtin.oose2024s1.assignment2.states;

import edu.curtin.oose2024s1.assignment2.BikeShop;
import edu.curtin.oose2024s1.assignment2.bikes.Bike;


public interface ShopState  //Interface for the shop states
{
    void purchaseInStore(BikeShop shop);
    void purchaseOnline(BikeShop shop, Bike bike);
    void delivery(BikeShop shop);
    void dropOff(BikeShop shop, Bike bike);
    void pickUp(BikeShop shop, String email);
}
