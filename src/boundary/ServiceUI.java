package boundary;

import controller.MenuController;
import controller.OrderController;
import controller.RoomController;
import entity.MenuItem;
import entity.Order;

import java.security.Provider;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ServiceUI {
    private static ServiceUI single_instance = null;
    private MenuController mc = MenuController.getInstance();
    private OrderController oc = OrderController.getInstance();
    private RoomController rc = RoomController.getInstance();
    private static Scanner in = new Scanner(System.in);

    public static ServiceUI getInstance()
    {
        if (single_instance == null)
            single_instance = new ServiceUI();
        return single_instance;
    }

    public void run() {
        int option;
        String roomNum;
        option = choose();

        while (option != 0) {
            switch (option) {
                case 1:
                    mc.displayMenu();
                    break;
                case 2:
                    try {
                        System.out.print("Enter the item name: ");
                        in.nextLine();
                        String name = in.nextLine();
                        if (mc.searchMenuItem(name) == null) {
                            if (name.trim().equals("")) throw new Exception("Name cannot be blank");
                            System.out.print("Enter the item description: ");
                            String description = in.nextLine();
                            System.out.print("Enter the price: ");
                            double price = in.nextDouble();
                            in.nextLine();
                            if (price < 0) throw new Exception("Price cannot be negative");
                            mc.createMenuItem(name, description, price);
                            System.out.println("Item created!");
                        } else {
                            System.out.println("Item in menu already!");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Warning: Invalid input!");
                    } catch (Exception e) {
                        System.out.println("Warning: " + e.getMessage());
                    }
                    System.out.println();
                    break;
                case 3:
                    try {
                        String oldName, newName = null, newDescription = null;
                        double newPrice = -1;
                        System.out.print("Enter the item name to update: ");
                        in.nextLine();
                        oldName = in.nextLine();
                        if (mc.searchMenuItem(oldName) != null) {
                            System.out.print("Update item name? (y/n) ");
                            if (in.nextLine().charAt(0) == 'y') {
                                System.out.println("Enter updated name: ");
                                newName = in.nextLine();
                            }
                            System.out.print("Update item description? (y/n) ");
                            if (in.nextLine().charAt(0) == 'y') {
                                System.out.print("Enter updated description: ");
                                newDescription = in.nextLine();
                            }
                            System.out.print("Update item price? (y/n) ");
                            if (in.nextLine().charAt(0) == 'y') {
                                System.out.print("Enter updated price (negative price won't be accepted): ");
                                newPrice = in.nextDouble();
                            }
                            mc.updateMenuItem(oldName, newName, newDescription, newPrice);
                            System.out.println("Item information updated!\n");
                        } else {
                            System.out.println("Item not in menu!\n");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Warning: Invalid input!");
                    } catch (Exception e) {
                        System.out.println("Warning: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        String name;
                        System.out.print("Enter the item name to remove:");
                        in.nextLine();
                        name = in.nextLine();
                        mc.removeMenuItem(name);
                        System.out.println("Item removed!");
                    } catch (Exception e) {
                        System.out.println("Warning: " + e.getMessage());
                    }
                    System.out.println();
                    break;
                case 5:
                    System.out.print("Enter room number to make order: ");
                    in.nextLine();
                    roomNum = in.nextLine();
                    // check validity of room number
                    if (!rc.findRoom(roomNum).isEmpty()) {
                        mc.displayMenu();

                        String itemName, remarks;
                        List<MenuItem> items = new LinkedList<>();
                        System.out.println("Please select the items to order... (enter \"STOP\" to terminate)");
                        System.out.print("Item: ");
                        itemName = in.nextLine();
                        while (!itemName.equals("STOP")) {
                            if (mc.searchMenuItem(itemName) != null) {
                                items.add(mc.getMenuItem(itemName));
                                System.out.println("ITEM ADDED");
                            } else {
                                System.out.println("Item name \"" + itemName + "\" invalid!");
                            }
                            System.out.print("Item: ");
                            itemName = in.nextLine();
                        }
                        if (!items.isEmpty()) {
                            System.out.print("Enter the remarks for this order: ");
                            remarks = in.nextLine();
                            oc.makeOrder(roomNum, remarks, items);
                            System.out.println("Order made!\n");
                        } else {
                            System.out.println("Empty Order!\n");
                        }
                    } else {
                        System.out.println("Invalid room number!");
                    }
                    break;
                case 6:
                    // RoomController rc = new RoomController();
                    System.out.print("Enter room number to print order: ");
                    in.nextLine();
                    roomNum = in.nextLine();

                    System.out.println("Order Details:");
                    // check validity of room number
                    if (!rc.findRoom(roomNum).isEmpty()) {
                        oc.printOrder(roomNum, rc.findRoom(roomNum).get(0).getCheckInTime());
//                        System.out.println(oc.getTotalPrice(roomNum,
//                                LocalDateTime.of(2020, 4, 2, 0, 0),
//                                LocalDateTime.of(2020, 4, 4, 0, 0)));
                    } else {
                        System.out.println("Invalid room number!");
                    }
                    break;
                default:
                    System.out.println("Please select a valid option!");
                    break;
            }
            option = choose();
        }
    }

    private static int choose() {
        System.out.println("Select your option: ");
        System.out.println("0. Go back");
        System.out.println("1. Display Menu");
        System.out.println("2. Create Menu Item");
        System.out.println("3. Update Menu Item");
        System.out.println("4. Remove Menu Item");
        System.out.println("5. Make an order");
        System.out.println("6. Check order status");

        try {
            System.out.print("Enter the option: ");
            return in.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Input mismatch!\n");
            in.nextLine();
            return choose();
        }
    }

}
