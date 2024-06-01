/*
MIT License

Copyright (c) 2024, Nuno Datia, Matilde Pato, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package isel.sisinf.ui;

import isel.sisinf.jpa.Bicycle;
import isel.sisinf.jpa.Customer;
import isel.sisinf.jpa.Reservation;
import isel.sisinf.jpa.Shop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

interface DbWorker
{
    void doWork();
}
class UI
{
    private enum Option
    {
        // DO NOT CHANGE ANYTHING!
        Unknown,
        Exit,
        createCostumer,
        listExistingBikes,
        checkBikeAvailability,
        obtainBookings,
        makeBooking,
        cancelBooking,
        about
    }
    private static UI __instance = null;
  
    private HashMap<Option,DbWorker> __dbMethods;

    private UI()
    {
        // DO NOT CHANGE ANYTHING!
        __dbMethods = new HashMap<Option,DbWorker>();
        __dbMethods.put(Option.createCostumer, () -> UI.this.createCostumer());
        __dbMethods.put(Option.listExistingBikes, () -> UI.this.listExistingBikes()); 
        __dbMethods.put(Option.checkBikeAvailability, () -> UI.this.checkBikeAvailability());
        __dbMethods.put(Option.obtainBookings, new DbWorker() {public void doWork() {UI.this.obtainBookings();}});
        __dbMethods.put(Option.makeBooking, new DbWorker() {public void doWork() {UI.this.makeBooking();}});
        __dbMethods.put(Option.cancelBooking, new DbWorker() {public void doWork() {UI.this.cancelBooking();}});
        __dbMethods.put(Option.about, new DbWorker() {public void doWork() {UI.this.about();}});

    }

    public static UI getInstance()
    {
        // DO NOT CHANGE ANYTHING!
        if(__instance == null)
        {
            __instance = new UI();
        }
        return __instance;
    }

    private Option DisplayMenu()
    {
        Option option = Option.Unknown;
        Scanner s = new Scanner(System.in);
        try
        {
            // DO NOT CHANGE ANYTHING!
            System.out.println("Bicycle reservation");
            System.out.println();
            System.out.println("1. Exit");
            System.out.println("2. Create Costumer");
            System.out.println("3. List Existing Bikes");
            System.out.println("4. Check Bike Availability");
            System.out.println("5. Current Bookings");
            System.out.println("6. Make a booking");
            System.out.println("7. Cancel Booking");
            System.out.println("8. About");
            System.out.print(">");
            s = new Scanner(System.in);
            int result = s.nextInt();
            option = Option.values()[result];
        }
        catch(RuntimeException ex)
        {
            //nothing to do.
        }
        finally
        {
        }
        return option;

    }
    private static void clearConsole() throws Exception
    {
        // DO NOT CHANGE ANYTHING!
        for (int y = 0; y < 25; y++) //console is 80 columns and 25 lines
            System.out.println("\n");
    }

    public void Run() throws Exception
    {
        // DO NOT CHANGE ANYTHING!
        Option userInput;
        do
        {
            clearConsole();
            userInput = DisplayMenu();
            clearConsole();
            try
            {
                __dbMethods.get(userInput).doWork();
                System.in.read();
            }
            catch(NullPointerException ex)
            {
                //Nothing to do. The option was not a valid one. Read another.
            }

        }while(userInput!=Option.Exit);
    }

    /**
    To implement from this point forward. Do not need to change the code above.
    -------------------------------------------------------------------------------     
        IMPORTANT:
    --- DO NOT MOVE IN THE CODE ABOVE. JUST HAVE TO IMPLEMENT THE METHODS BELOW ---
    --- Other Methods and properties can be added to support implementation -------
    -------------------------------------------------------------------------------
    
    */

    private static final int TAB_SIZE = 24;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GoCycle-G32");
    EntityManager em = emf.createEntityManager();
    private void createCostumer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter customer name:");
        String name = scanner.nextLine();

        System.out.println("Enter customer address:");
        String address = scanner.nextLine();

        System.out.println("Enter customer email:");
        String email = scanner.nextLine();

        System.out.println("Enter customer phone:");
        String phone = scanner.nextLine();

        System.out.println("Enter customer identification number:");
        String identificationNumber = scanner.nextLine();

        System.out.println("Enter customer nationality:");
        String nationality = scanner.nextLine();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setIdentificationNumber(identificationNumber);
        customer.setNationality(nationality);

        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();

        System.out.println("Customer created successfully!");

    }
  
    private void listExistingBikes() {
        List<Bicycle> bicycles = em.createQuery("SELECT b FROM Bicycle b", Bicycle.class).getResultList();

        if (bicycles.isEmpty()) {
            System.out.println("No bicycles found.");
        } else {
            System.out.println("Listing all bicycles:");
            for (Bicycle bicycle : bicycles) {
                System.out.println(bicycle.toString());
            }
        }
    }
    private void checkBikeAvailability()
    {
        List<Bicycle> availableBicycles = em.createQuery("SELECT b FROM Bicycle b WHERE b.status = 'free'", Bicycle.class).getResultList();

        if (availableBicycles.isEmpty()) {
            System.out.println("No bicycles are available.");
        } else {
            System.out.println("Listing all available bicycles:");
            for (Bicycle bicycle : availableBicycles) {
                System.out.println(bicycle.toString());
            }
        }

    }

    private void obtainBookings() {
        List<Reservation> reservations = em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("Listing all reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation.toString());
            }
        }
    }

    private void makeBooking()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter customer id:");
        Long customerId = scanner.nextLong();

        System.out.println("Enter bicycle id:");
        Long bicycleId = scanner.nextLong();

        System.out.println("Enter shop id:");
        Long shopId = scanner.nextLong();

        System.out.println("Enter start date:");
        String startDate = scanner.next();

        System.out.println("Enter end date:");
        String endDate = scanner.next();

        System.out.println("Enter amount:");
        double amount = scanner.nextDouble();

        Reservation reservation = new Reservation();
        reservation.setCustomer(em.find(Customer.class, customerId));

        reservation.setBicycle(em.find(Bicycle.class, bicycleId));
        reservation.setShop(em.find(Shop.class, shopId));
        reservation.setStartDate(LocalDateTime.parse(startDate));
        reservation.setEndDate(LocalDateTime.parse(endDate));
        reservation.setAmount(amount);

        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();

        System.out.println("Reservation created successfully!");

        
    }

    private void cancelBooking()
    {
        // TODO
        System.out.println("cancelBooking");
        
    }
    private void about()
    {
        System.out.println("Grupo 32 LEIC43d \nGabriel Lemos - 50997 \nTiago Adriano - 50968 \nTomas Silva - 50458");
        System.out.println("DAL version:"+ isel.sisinf.jpa.Dal.version());
        System.out.println("Core version:"+ isel.sisinf.model.Core.version());
        
    }
}

public class App{
    public static void main(String[] args) throws Exception{
        UI.getInstance().Run();
    }
}