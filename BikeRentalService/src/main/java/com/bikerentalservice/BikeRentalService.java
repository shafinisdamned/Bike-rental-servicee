import java.util.List;
import java.util.Scanner;

public class BikeRentalService
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        BikeService bikeService = new BikeService();
        RentalService rentalService = new RentalService();

        boolean running = true;

        while (running) {
            System.out.println("1. Login");
            System.out.println("2. Logout");
            System.out.println("3. View Available Bikes");
            System.out.println("4. Rent a Bike");
            System.out.println("5. Return a Bike");
            System.out.println("6. View My Rentals");
            System.out.println("7. Show Bike Details");
            System.out.println("8. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    if (userService.login(username, password)) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Login failed!");
                    }
                    break;
                case 2:
                    userService.logout();
                    System.out.println("Logged out!");
                    break;
                case 3:
                    List<String> bikes = bikeService.viewAvailableBikes();
                    System.out.println("Available Bikes:");
                    for (String bike : bikes) {
                        System.out.println(bike);
                    }
                    break;
                case 4:
                    System.out.print("Bike ID to rent: ");
                    int bikeIdToRent = scanner.nextInt();
                    if (rentalService.rentBike(userService.getLoggedInUserId(), bikeIdToRent)) {
                        System.out.println("Bike rented successfully!");
                    } else {
                        System.out.println("Failed to rent bike!");
                    }
                    break;
                case 5:
                    System.out.print("Bike ID to return: ");
                    int bikeIdToReturn = scanner.nextInt();
                    if (rentalService.returnBike(userService.getLoggedInUserId(), bikeIdToReturn)) {
                        System.out.println("Bike returned successfully!");
                    } else {
                        System.out.println("Failed to return bike!");
                    }
                    break;
                case 6:
                    List<String> rentals = rentalService.viewMyRentals(userService.getLoggedInUserId());
                    System.out.println("My Rentals:");
                    for (String rental : rentals) {
                        System.out.println(rental);
                    }
                    break;
                case 7:
                    System.out.print("Bike ID to view: ");
                    int bikeIdToView = scanner.nextInt();
                    String details = bikeService.viewBikeDetails(bikeIdToView);
                    System.out.println("Bike Details:");
                    System.out.println(details);
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }

        scanner.close();
    }
}
