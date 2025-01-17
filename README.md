USGAE: 
  type gradle run in terminal 
  press enter key to end program. 
  output is also written to sim_results.txt

I have implemented the observer pattern in my program through the EventObs.java interface and the EventObserver.java. 
The Observer is notified whenever the subject class (BikeShop) initiates an event such as a purchase-instore or a delivery 
when a message arrives from BikeShopInput as this signifies a new event has been attempted. The Observer is also notified when an event
has failed due to any reason making the event unable to be completed or when a parsing error occurs. Finally, the Observer is notified when
a day has passed in the simulation. The Observer's stores the BikeShop as a field and is able to refer back to it to set it self up    
by calling the BikeShop's addEventObservers method and passes the called method itself. The setUp method was initially called in the constructor
of the observer but due to PMD warnings and because nothing that can potentially cause an error should be in a constructor it was moved to 
the main method where it is called to set it self up in BikeShop. The update method in the observer is called whenever the BikeShop calls the notifyObservers method and a String is passed to the Observer. 
The Observer uses the String and prints it out to the console and also writes the String to an output file: sim_results.txt, notfiying the user of the event that has occured
or failed to occur. 

I have implemented the state pattern in my program through the BikeShopState.java interface and the BikeShop.java class as the subject class. 
The diffrenet state implementations (AllAvailable, NoDelivery, NoDropOff, NoPurchase) all represent the different states the BikeShop can be in.
In the AllAvailable state, the BikeShop is able to perform all events such as purchase-instore, delivery and dropoff. In the NoDelivery state, the BikeShop is unable to perform a delivery 
due to not having space or not having cash and has the logic to handle an attempted delivery. In the NoDropOff state, the BikeShop is unable to perform a dropoff due to lack of space and can handle this case. 
In the NoPurchase state, the BikeShop is unable to perform a purchase-instore or a purchase-online due to lack of space and can handle any attempts of purchases. The state pattern also uses actual polymorphism
as the BikeShop class has a BikeState field which can be any of the above mentioned states. 

Furthermore, I implemented Generics in my program through the BikeList.java class. This class is a generic class that can store any type of Bike as it extends the Bike class. This allows me to add 
any type of Bike to the BikeList and also remove any type of Bike from the BikeList. This implementation allows me to address the actual problem statement as it lets me store all of the bikes that are on hold in BikeShop
in one list regardless of their type. This allows me to only need to use one list and not have to create multiple lists for each type of bike. Having multiple lists would make the program more complex and harder to maintain.
Using one list allows me to easily add and remove bikes from the list and also iterate through the list to check if a bike is in the list for when it needs to be picked up and also when it needs to complete its service period.

I have also implemented a factory that returns a specific type of Bike based on the input from the BikeShopInput. The BikeFactory.java class has a static method that takes in a String and returns a Bike object.
Dependency injections are used in the observer when the BikeShop is passed to the observer to set it self up. This is done to avoid tight coupling between the observer and the BikeShop. The observer only needs to know about the BikeShop
and not the BikeShop about the observer. The observer is able to call the BikeShop's addEventObservers method and pass it self to the BikeShop. This allows the observer to be notified of any events that occur in the BikeShop.
Dependency injection is also used when creating the BikeShop in the main method. The initial state of the BikeShop is passed to the BikeShop when it is created. This allows the BikeShop to be in the correct state when it is created.



