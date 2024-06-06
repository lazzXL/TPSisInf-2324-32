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

import isel.sisinf.model.Customer;
import isel.sisinf.model.Reservation;
import isel.sisinf.jpa.repo.JPAContext;
import jakarta.persistence.OptimisticLockException;

import java.sql.Timestamp;
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

    private void createCostumer() {
        try(JPAContext ctx = new JPAContext()){
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


            ctx.beginTransaction();

            Customer customer = new Customer();
            customer.setName(name);
            customer.setAddress(address);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setIdentificationNumber(identificationNumber);
            customer.setNationality(nationality);

            ctx.getCustomers().create(customer);
            ctx.commit();

            System.out.println("Customer created successfully!");

        } catch (Exception e) {
            System.out.println("An error occurred!");
            System.out.println(e.getMessage());
            //throw e;
        }
    }
  
    private void listExistingBikes() {
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            ctx.getBicycles().find("SELECT b FROM Bicycle b").forEach(b ->
                    System.out.println(b)
            );
        }catch (Exception e) {
            System.out.println("An error occurred!");
            System.out.println(e.getMessage());
            //throw e;
        }
    }

    private void checkBikeAvailability()
    {
        try(JPAContext ctx = new JPAContext()) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter bike id:");
            String bike_id = scanner.nextLine();
            long id = Long.parseLong(bike_id);

            System.out.println("Enter time (yyyy-mm-dd hh:mm:ss):");
            String time = scanner.nextLine();
            Timestamp times = Timestamp.valueOf(time);

            ctx.beginTransaction();

            int count = ctx.checkBikeisAvailable(id, times);

            if(count > 0)
                System.out.println("Bike is unavailable at that time! :(");
            else
                System.out.println("Bike is available at that time! :)");
        }catch (Exception e) {
            System.out.println("An error occurred!");
            System.out.println(e.getMessage());
            //throw e;
        }
    }

    private void obtainBookings() {
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            ctx.getReservations().find("SELECT r FROM Reservation r").forEach(r ->
                    System.out.println(r)
            );
        }catch (Exception e) {
            System.out.println("An error occurred!");
            System.out.println(e.getMessage());
            //throw e;
        }
    }

    private void makeBooking() {
        try (JPAContext ctx = new JPAContext()) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter customer id:");
            int customerId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter bicycle id:");
            int bicycleId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter shop id:");
            int shopId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter start date (YYYY-MM-DD HH:MM:SS):");
            Timestamp startDate = Timestamp.valueOf(scanner.nextLine());

            System.out.println("Enter end date (YYYY-MM-DD HH:MM:SS):");
            Timestamp endDate = Timestamp.valueOf(scanner.nextLine());

            System.out.println("Enter amount:");
            double amount = scanner.nextDouble();

            ctx.beginTransaction();
            ctx.makenewreservation(shopId, customerId, bicycleId, startDate, endDate, amount);
            ctx.commit();

            System.out.println("Reservation created successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred!");
            System.out.println(e.getMessage());
            // Optionally, handle the exception
        }
    }

    private void cancelBooking()
    {
        try(JPAContext ctx = new JPAContext()) {
            System.out.println("cancelBooking");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter booking id to cancel:");
            Long bookingId = scanner.nextLong();
            ctx.beginTransaction();
            Reservation res = ctx.getReservations().findByKey(bookingId);
            ctx.getReservations().delete(res);
            ctx.commit();
            System.out.println("Booking cancelled successfully");

        } catch (OptimisticLockException e){
            System.out.println("Optimistic locking error!");
        } catch (Exception e) {
            System.out.println("An error occurred!");
            System.out.println(e.getMessage());
            //throw e;
        }
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