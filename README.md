# Hotel Reservation and Payment System

## I. Overview

In this project, our team has designed and developed a console-based application called **Hotel Reservation and Payment System** (i.e. HRPS). It is designed to facilitate hotel staff in computerizing the processes of making hotel reservation, recording of orders and displaying of records, etc.

**Demo video YouTube Link: [https://youtu.be/W4U0BkgZg48](https://youtu.be/W4U0BkgZg48)**

---

## II. Design Considerations

### 1. Use of Object-Oriented Concepts

- **Abstraction:**

    Abstraction is the generalization of classes without their implementation details, thus showing only essential information and hiding unnecessary information, which helps reduce complexity of a program. In our application, we abstracted essential entity classes from the list of required real-world objects, such as guest, reservation and room. Each class has its own attributes to store states/data and methods to modify these data and perform useful actions. 

    For example: The `Room` class is an entity, and it's mainly used for storing information related to the room. The `RoomController` class is a controller, and it's mainly responsible for receiving actions from the UI and interacting with the `Room` entity to make changes accordingly.

    Also, `OrderList` and `Guest` classes implement the `serializable` interface, such that they can use the method `writeSerializedObject(String filename, Object object)` to save data.

- **Encapsulation:**

    Encapsulation ensures that each class has its own set of data that are not accessible to other classes, by making the attributes private. Private attributes are only accessible through getter and setter methods within the class itself so that this keeps it safe from unwanted interference. Also, the implementation details of a class are hidden from outsiders. In our application, encapsulation is applied in almost every entity class.

    For example: The `Guest` entity only allows modification through its own setter methods

- **Polymorphism:**

    Polymorphism is to treat objects of difference as a single general type, usually as their base type. And the behavior of the objects depend on their actual type. 

    In our application, each entity class is inherited from the `java.lang.Object` class and has a `toString()` method. Polymorphism is applied such that the `toString()` method of each entity class is overridden to return a `String` that is customized for that entity class.

- **Inheritance:**

    Inheritance is mainly used to prevent code duplicate and redundancy. In our application, we have considered creating a `Room` parent class, and subclasses, such as `SingleRoom`, `DoubleRoom` and `DeluxeRoom`, which inherit from `Room`. 

    However, after discussion and careful consideration, we realized that these subclasses do not have unique attributes or behaviors. Thus, there's little need to create subclasses. Consequently, we have added a new attribute `roomType` inside `Room` instead of creating subclasses.

### 2. Use of SOLID principles

- **Single Responsibility Principle (SRP):**

    The Single Responsibility Principle states that a class should only focus on one unique task, which facilitates easy debugging and encourages reusability. Our application is divided into three types of classes: boundary classes, controller classes and entity classes, corresponding to view, controller and model. 

    For example, each class of type boundary is implemented for the task of getting user input and printing program output, each working for a specific function of the application. The `CheckInUI` class in `boundary` package is implemented for getting user input related to check-in, and it prints program output related to check-in but cannot manipulate other unrelated classes such as `Reservation` class. In case of a change of user need, only this one class needs to be modified.

- **Open-Closed Principle (OCP):**

    The Open-Closed Principle is applied in classes of our application, such that they are open for extension but closed for modification, which allows inheritance. Most of the classes are not static classes, hence can be used as parent classes for extension by any new classes to add new features without changing the original classes.

- **Liskov Substitution Principle (LSP):**

    The Liskov Substitution Principle states that any base class should be substitutable for their subclasses. In our project, we could have implemented a `Room` parent class, and subclasses, such as `SingleRoom`, `DoubleRoom` and `DeluxeRoom`, which inherit from `Room`. These classes are substitutable for the parent class .

- **Interface Segregation Principle (ISP):**

    Interface Segregation principle states that classes should not depend on interfaces that they do not use, which means that to have many interfaces, each for a specific purpose is better than having one general interface. This means that while an interface is inherently general – for classes to implement its methods, the classes should not be implementing an interface with methods they do not need. This is achieved by using client specific classes instead of using general purpose interface.

    For example, even though both `GuestController` and `ServiceController` are in the controller package, they do not share common behaviors and so it is unnecessary to have them both inherited from a common Controller base class or implemented a common `Controller` interface.

- **Dependency Injection Principle (DIP):**

    The Dependency Injection Principle states that a high-level module should not depend upon low-level modules. Both should depend upon abstractions. Because if a java class creates an instance of another class via the “new” operator, it cannot be used and tested independently from this class and a hard dependency is formed. Using dependency injection, the classes can be reused and tested independently from other classes. Our project uses constructor injection and method injection.

    For example, in the constructor of `Reservation` class, a guest is injected instead of instantiated using the `new` keyword.

    ```jsx
    public Reservation(Guest guest, ......) { this.guest = guest;
    ......
    }
    ```

    Also, In `RoomController` class, the method checkIn has a guest object injected instead of instantiated using the `new` keyword.

    ```jsx
    public Room checkIn(String roomNumber, Guest guest) { 
    	List<Room> rooms = findRoom(roomNumber);
    	Room room = rooms.get(0);
    	RoomStatus status = RoomStatus.valueOf("OCCUPIED"); room.setRoomStatus(status);
    	room.setGuest(guest);
    	return room;
    }
    ```

### 3. Use of Design Patterns

- **Singleton Design Pattern:**

    The Singleton Pattern restricts the instantiation of a class to one and only one object. This ensures that only one object is needed to coordinate actions across the system.

    For example: in our application, all boundary classes, such as `CheckInUI`, `CheckOutUI`, and `GuestUI` and controller classes, such as `GuestController` and `ReservationController`, are implemented using the Singleton Pattern.

    ```jsx
    private static CheckInUI single_instance = null; 
    private GuestUI guestUI = GuestUI.getInstance(); 
    ......

    // private constructor restricted to this class itself 
    private CheckInUI() {}

    // static method to create instance of CheckinUI 
    class public static CheckInUI getInstance() {
    	if (single_instance == null) single_instance = new CheckInUI();
    	return single_instance; 
    }
    ```

- **Entity-Control-Boundary Design Pattern:**

    Our application is divided into three types of classes: boundary classes, controller classes and entity classes, corresponding to view, controller and model.

    1. Actors are the users of the program, they interact with boundary objects.
    2. Boundary objects gets user input, issues commands to controller objects and prints program output.
    3. Controller objects contain the logic and will do the necessary computations, update entities correspondingly and return the output to boundary objects.
    4. Entity objects contain information tracked by the system. They serve as data storage with only getter and setter methods.

    In our project, we have the following entity, controller and boundary classes:

    ![Hotel%20Reservation%20and%20Payment%20System/Untitled.png](Hotel%20Reservation%20and%20Payment%20System/Untitled.png)

### 4. Assumptions

In additional to the assumptions made in the assignment document, we have made the following assumptions:

- Guests are saved in the system and not removed after checking out
- Credit card details have to be taken down regardless of the mode of payment
- Room information:
    1. The hotel has 48 rooms, from level 2 to 7, each level having 8 rooms

        Level 2~5 are for single and double rooms, 4 rooms on each level respectively

        Level 6 is for vip rooms

        Level 7~8 are for deluxe and suite rooms, 4 rooms on each level respectively

    2. There are 5 room types: 16 Single rooms, 16 Double rooms, 8 VIP rooms, 4 Deluxe rooms, 4 Suite rooms
    3. 4 room facing: north south east west: deluxe 1 each, vip 2 each, suite 1 each, double 4 each, single 4 each
    4. WIFI: 2 single rooms and 2 double rooms don’t have wifi
    5. Smoking free: 4 single rooms and 4 double rooms are not smoke free

## III. Class Diagram

![Hotel%20Reservation%20and%20Payment%20System/Untitled%201.png](Hotel%20Reservation%20and%20Payment%20System/Untitled%201.png)


## IV. Sequence Diagram

![Hotel%20Reservation%20and%20Payment%20System/Untitled%202.png](Hotel%20Reservation%20and%20Payment%20System/Untitled%202.png)

*`CheckOutUI`* contains two functions: *`isValidRoomNum()*` for validating user input, and *`checkOut()`* for reading user input, displaying bill, displaying ordered items, displaying payment details and connecting controller classes.

When checking out, the system asks for the guest name first. If the guest name is not found in the dataset, the system will report and ask for a valid guest name again. A list of guests will be returned by *`searchGuest()`* function. If multiple guests have the same name, the length of the returned list exceeds 1, and an index must be selected to confirm which guest is checking out according to identity information in the list.

Next, room number of the checking out room shall be entered. The system will check if the room number is in valid form as well as if the room number matches guest information by *`isValidRoomNum()`* function. The validation continues until a valid room number is entered.

To calculate room charge, *`CheckOutController`* interacts with *`RoomController`*, function *`setStayDays()`* generates check-in time and check-out time, summing prices day by day, because same room have different charges on weekdays or weekends, day of week must be checked. Function *getRoomCharge()* calculates the sum of daily room charges and returns room charge.

To calculate room service charge, *`CheckOutController`* interacts with *`OrderController`*. Room services are managed at room level. All items ordered by the checking out room will be returned, displayed and price calculated by function *`getServiceCharge()`.*

To calculate tax, the system multiples the GST rate of Singapore, 0.07, and the sum of price charge and service charge by *`getTax()`.*

Function *`getTotalAmount()`* generates the total charge of the checking out room. Total charge is the sum of room charge, service charge and tax. If promotion permitted, total charge will multiply by a promotion rate.

After all calculation of prices, payment method is queried, either cash or credit card is permitted. If a credit card is selected, corresponding details such as billing address will be displayed.

The room status will be set to vacant by *`setRoomVacant()`.*

In short, the flow goes: a. input guest name (validation) b. input room number (validation) c. confirm promotion d. calculate room charge (consider price difference on different day of week) e. calculate service charge, generate ordered items f. calculate tax g. calculate total charge h. input payment method i. display: total bill including room charges, tax, room services, total charge, promotion; payment method and credit card details (if selected) j. set room status to vacant

## To run the program:
1. Clone the repo to your local machine;
2. Open the project using an IDE;
3. Run `Launch`.
