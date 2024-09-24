import java.util.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.sql.*;
//
// Sample program that:
// 1 - Connects to the CS325 database
// 2 - Selects, Inserts, Updates, Deletes records in the accounts table
// 
// To run the program make sure you run this command in the terminal window:
// 
// export CLASSPATH = "./.:./ojdbc8.jar"
// javac MainProgram && java MainProgram
//
public class MainProgram {
	public static String userEmail;
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) 
	{
		int choice;
		System.out.println("Ready to get your wheels muddy?");

		while(true)
		{
			System.out.println("Enter 1 to create a new account.");
			System.out.println("Enter 2 to sign in into current account.");      
			System.out.println("Enter any other number to exit");
		
			System.out.print("\nChoice: ");
      try {choice = input.nextInt();} catch (InputMismatchException e) {
        choice = 0;
      }

      AccountDao dao = new AccountDao();
	
			switch(choice)
				{
					case 1: //create a new account
						createAccount();
						break;
					case 2: //sign in into current account
							System.out.print("Please enter your user Email: ");
							String email = input.next().trim();
							System.out.print("Please enter your password: ");
							String password = input.next();
	
							Account user = dao.logIn(email,password);
							if(user != null) 
							{ 
								System.out.println("Login successful.");   
								userEmail = email; 
								if(dao.isAdmin(user.fname,user.lname) == true) 
								{
										System.out.println("Welcome, admin!");
										adminMenu();
								} else {    
										System.out.println("Welcome to Muddy Wheels!");
										mainMenu();
								}
							} else {
								System.out.println("Login failed. Please try again.\n");
							}
							break;
					default: //other choices
							System.out.println("Have a nice day!");
							System.exit(0);
				}
		}
	}//end main

	public boolean isAdmin(String fname, String lname) 
	{
		AccountDao dao = new AccountDao();
		return dao.isAdmin(fname, lname);
	}

	//Create a new account
	public static void createAccount()
	{
		//Registration info: first name, last name, 
		//street address, city, state, zip, phone, email, password
		System.out.print("Please enter your \nFirst name: ");
		String fName = input.next().trim();
		System.out.print("Last name: ");
		String lName = input.next().trim();

		java.util.Date dob = null;
		do 
		{
			System.out.print("Date of Birth (yyyy-mm-dd): ");
			String dobInput = input.next();

			try {
					LocalDate localDate = LocalDate.parse(dobInput);
					dob = java.sql.Date.valueOf(localDate);
			} catch (DateTimeParseException e) {
					System.out.println("Invalid date format! Please enter the date in yyyy-mm-dd format.");
			}
		} while (dob == null);  
		input.nextLine();
		System.out.print("Street: ");
		String street = input.nextLine().trim();
		System.out.print("City: ");
		String city = input.nextLine().trim();
		System.out.print("State: ");
		String state = input.nextLine().trim();
		System.out.print("ZIP: ");
		String zip = input.next();
		input.nextLine();
	
    String number;
		// added this by Alvin
		// check if the phone number is valid
		do {
      System.out.println("Please enter a 10-digit phone number:");
      number = input.nextLine().trim();
    } while (number.length() != 10);

		System.out.print("Email: ");
		String email = input.nextLine().trim();
		System.out.print("Password: ");
		String password = input.nextLine().trim();
		
				
		Account account = new Account(fName, lName, dob, street, city, state, zip, number, email, password);
		AccountDao dao = new AccountDao();

    dao.insert(account);

    System.out.println("\nAccount created");
    userEmail = email;

    if (dao.isAdmin(fName,lName))
    {
      adminMenu();
    }
    else
      mainMenu();
	}

	//Delete account
	public static void deleteAccount()
	{
		boolean isDone = false;

		while (!isDone) {
			try {
				System.out.print("Please enter your user Email: ");
				String email = input.next().trim();
				System.out.print("Please enter your password: ");
				String password = input.next();
				
				AccountDao dao = new AccountDao();
				dao.delete(email, password);
					
				isDone = true; //successfully delete account
				System.out.println("Account deleted.");
			} catch (Exception e) {
				System.out.println("Wrong Email and/or Password. Please try again!");
			}
		}
	}

		//Menu after user Log In or Create Account
	public static void mainMenu()
	{
		while(true)
		{
			System.out.println("Enter 1 to Bid Adieu: Delete Account.");
			System.out.println("Enter 2 to Glow Up: Modify Registration Information.");      
			System.out.println("Enter 3 to Soar with Us: Explore Flight Options.");
			System.out.println("Enter 4 to Ride with Us: Explore Car Rental Options.");
			System.out.println("Enter 5 to see your reservations.");
			System.out.println("Enter any other number to exit");
		
			int choice;
			
			System.out.print("\nChoice: ");
			try {choice = input.nextInt();} catch (InputMismatchException e) {
        choice = 0;
      }
			
			switch(choice)
			{
				case 1: //delete current account
					deleteAccount();
					System.exit(0);
					break;
				case 2: //modify registration information
					modifyAccount();
					break;
				case 3: //flight options of choice
					listFlights();
					break;
				case 4: //car rental options
						searchAvailableCars();
						break;
				case 5: //view reservation (flight + car)
					reservationView();
					break;
				default: //other choices
						System.out.println("Have a nice day!");
						System.exit(0);
			}
		}
	}

	public static void listFlights()
	{
		FlightDao fdao = new FlightDao();
		TicketDao tdao = new TicketDao();

		System.out.println("We currently only have flights departing from BOS");
		//System.out.print("Enter the departure airport code (BOS): ");
		//String departureCode = input.next().toUpperCase();

		System.out.print("Enter the destination airport code (LAX, AMS, TPA, FLL, BWI, MYR, PHL, EWR): ");      
		String destinationCode = input.next().toUpperCase();
		
		System.out.println();
		ArrayList<Object> list = fdao.selectByCode2Ways("BOS",destinationCode);
			
		while (list.isEmpty()) 
		{
			System.out.println("No flights found between BOS and " + destinationCode + ".");
			System.out.println("Please try again!\n");
			
      System.out.print("Enter the destination airport code (LAX, AMS, TPA, FLL, BWI, MYR, PHL, EWR): ");      
      destinationCode = input.next().toUpperCase();
      
      System.out.println();
      list = fdao.selectByCode2Ways("BOS",destinationCode);
		}

    for (int i=0; i<list.size(); i++)
      System.out.println((Flight) list.get(i));
		

		System.out.print("Please select the flight ID you wish to proceed with (0 to go back to the main menu): ");
		int selectedFlightId = input.nextInt();

		if (selectedFlightId == 0) 
			System.out.println("See you on the next adventure!");
		else {
			System.out.println("\nTicket information for Flight ID: " + selectedFlightId + "\n");

			ArrayList<Object> tickets = tdao.selectByFlightID(selectedFlightId);
			
			for (int i=0; i<tickets.size(); i++)
					System.out.println((Ticket) tickets.get(i));

			System.out.print("Please select the ticket ID you wish to proceed with payment (0 to go back to the main menu): ");
			int selectedTicketID = input.nextInt();

			if (selectedTicketID == 0) 
				System.out.println("See you on the next adventure!");
			else {
				processPayment(selectedTicketID);
				return;
			}
		}
	}

	public static void processPayment(int ticketID)
	{
		TicketDao tdao = new TicketDao();
		AccountDao dao = new AccountDao();

		System.out.println("\nPlease review your Ticket:");
		tdao.displayTicketFlightSummary(ticketID);

		System.out.println("Please enter your payment information:");

		System.out.print("Card number: ");
		String cardNumber = input.nextLine();
		input.nextLine();
		System.out.print("Expiration date (MM/YYYY): ");
		String expirationDate = input.nextLine();

		System.out.print("CVV: ");
		String cvv = input.nextLine();

    //System.out.println(userEmail);
		Account user = dao.selectByEmail(userEmail); 
		user.setTicketID(ticketID);
		dao.updateTicket(user);

		System.out.println("Payment processed successfully. Thank you!");
		System.out.println("You can review your reservation using option 5 in the main lobby!\n");
	}

	public static void reservationView()
	{
		TicketDao tdao = new TicketDao();
		CarRentalDao cdao = new CarRentalDao();

		// Display car reservations
    System.out.println("\nCar Reservations:");
    ArrayList<CarBooking> carBookings = cdao.getCarBookings(userEmail);
		if (carBookings.isEmpty()) 
		{
			System.out.println("No car reservations found.");
		} 
		else 
		{
			for (CarBooking booking : carBookings) 
			{
        System.out.println("Car ID: " + booking.getCarID());
				System.out.println("Car Name: " + booking.getCarName());
				System.out.println("User Email: " + booking.getEmail());
				System.out.println("Driver's License Number: " + booking.getDriverLicenseNumber());

				System.out.println();
			}
		}

    System.out.println("\nFlight Reservations: ");
		// Display flight reservations
		//System.out.println("Flight Reservations:" + userEmail);

		Ticket reservation = tdao.getReservations(userEmail);
    if(reservation == null)
      System.out.println("Empty\n");
    else
      System.out.println(reservation.toStringReservation());
	}

	//Modify registration information
	public static void modifyAccount()
	{
		AccountDao dao = new AccountDao();
		Account account = dao.selectByEmail(userEmail);
    int choice;
		System.out.println(account.toString1());

		System.out.println("Select what you want to modify:");
		System.out.println("1. First name");
		System.out.println("2. Last name");
		System.out.println("3. Address"); //Include city & state & ZIP
		System.out.println("4. Phone number");
		System.out.println("5. Password");
		System.out.println("Enter any other number to exit");
	
		System.out.print("Enter your choice: ");
		try {choice = input.nextInt();} catch (InputMismatchException e) {
        return;
      }
	
		switch(choice)
		{
				case 1: //first name
					System.out.print("Current first name: " + account.getFirstName() + "\nEnter new first name: ");
					input.nextLine();
					String newFirstName = input.nextLine();
					account.setFirstName(newFirstName);
					dao.update(account);
					System.out.println("First name updated. Current first name: " + account.getFirstName() + "\n");
					break;
				case 2: //last name
					System.out.print("Current last name: " + account.getLastName() + "\nEnter new last name: ");
					input.nextLine();
					String newLastName = input.nextLine();
					account.setLastName(newLastName);
					dao.update(account);
					System.out.println("Last name updated. Current last name: " + account.getLastName() + "\n");
					break;
				case 3: //address
					System.out.println("Current address: " + account.getStreet() + "\nCurrent city: " + 
															account.getCity() + "\nCurrent state: " + account.getState() + "\nCurrent ZIP: " + account.getZIP());
					input.nextLine();
					System.out.print("Enter new address: ");
					String newAddress = input.nextLine();
					account.setStreet(newAddress);
					System.out.print("Enter new city: ");
					String newCity = input.nextLine();
					account.setCity(newCity);
					System.out.print("Enter new state: ");
					String newState = input.nextLine();
					account.setState(newState);
					System.out.print("Enter new ZIP: ");
					String newZIP = input.nextLine();
					account.setZIP(newZIP);
					dao.update(account);
					System.out.println("Address updated. Current address: " + account.getStreet() + ", " +
															account.getCity() + ", " + account.getState() + " " + account.getZIP() + "\n");
					break;
				case 4: //phone
					System.out.println("Current phone number: " + account.getPhone());
          input.nextLine();
          String newNumber = null;
					do {
            System.out.println("Please enter a new 10-digit phone number:");
            newNumber = input.nextLine().trim();
            } while (newNumber.length() != 10);

					account.setPhone(newNumber);
					dao.update(account);
					System.out.println("Phone number updated. Current phone number: " + account.getPhone() + "\n");
					break;
				case 5: //password
					System.out.print("Current password: " + account.getPassword() + "\nEnter new password: ");
					input.nextLine();
					String newPass = input.nextLine();
					account.setPassword(newPass);
					dao.update(account);
					System.out.println("Password updated. Current password: " + account.getPassword() + "\n");
					break;
				default: break;
		}
	}


	public static void adminMenu()
	{
		int choice;
		AccountDao dao = new AccountDao();
		FlightDao fdao = new FlightDao();
		CarRentalDao cdao = new CarRentalDao();
		while(true)
		{
			System.out.println("Enter 1 to list all registered users.");  //selectAll  
			System.out.println("Enter 2 to delete current account."); //deleteAccount()
			System.out.println("Enter 3 to remove a registered user from the system."); //deleteByID
			System.out.println("Enter 4 to delete all accounts."); //deleteAll
			System.out.println("Enter 5 to modify your registration information."); //modifyAccount()
			System.out.println("Enter 6 to view registration information about a specific registered user."); //selecById
			System.out.println("Enter 7 to modify registration information about a specific registered user."); //selecById & modifyAccount()
			System.out.println("Enter 8 to list all flight.");  //selectAll  
			System.out.println("Enter 9 to list all cars.");
			System.out.println("Enter any other number to exit");
	
				System.out.print("\nChoice: ");
				try {choice = input.nextInt();} catch (InputMismatchException e) {
        choice = 0;
      }
				
				switch(choice)
				{
					case 1: //list all account
							ArrayList<Object> list = dao.selectAll();
					
							for (int i=0; i<list.size(); i++)
							{
								System.out.println((Account) list.get(i));
							}
							break;
					case 2: //delete current account
							deleteAccount();
							System.out.println("Exiting program.\nHave a nice day!");
							System.exit(0);
							break;
					case 3: //delete a user
							deleteUser();
							break;
					case 4: //delete all
							dao.deleteAll();
							System.out.println("All account deleted. Exiting program.\nHave a nice day!");
							System.exit(0);
							break;
					case 5: //modify self account
							modifyAccount();
							break;
					case 6: //view specific user's info
							viewInfo();
							break;
					case 7: //modify specific user's info
							modifyUserInfo();
							break;
					case 8: //listFlight
							ArrayList<Object> flist = fdao.selectAll();
					
							for (int i=0; i<flist.size(); i++)
							{
								System.out.println((Flight) flist.get(i));
							}
							break;
					case 9: //listCar
							ArrayList<Object> clist = cdao.selectAll();

							for (int i=0; i<clist.size(); i++)
							{
								System.out.println((CarRental) clist.get(i));
							}
							break;
					default: //other choices
							System.out.println("Have a nice day!");
							System.exit(0);
				}
		}
	}

  public static void searchAvailableCars()
  {
		CarRentalDao dao = new CarRentalDao();
		AccountDao accountDao = new AccountDao();
		boolean isBookingComplete = false;

    String driverLicenseNumber = null;
    String cardNumber = null;
    String cvv = null;
    String expiryDate = null;
    String nameOnCard = null;
		String phoneNumber = null;

		while(!isBookingComplete)
		{
			ArrayList<Integer> destinationLocationIds = dao.getDestinationLocations();
			System.out.println("Choose the destination location");		
      // System.out.println("1. Logan Intl Airport\n2. Logan Express Braintree\n3. New York City\n4. Washington D.C.\n5. Baltimore\n6. Philadelphia");
    	// System.out.println("Enter the choice: ");
			for(int i = 0; i < destinationLocationIds.size(); i++)
			{
				System.out.println((i+1) + ". " + dao.getDestinationLocationName(destinationLocationIds.get(i)));
			}
      int destinationLocationChoice;

      do{
        System.out.println("Enter the choice: ");
        try {destinationLocationChoice = input.nextInt();} catch (InputMismatchException e) {
          destinationLocationChoice = 1;
        }
      }while(destinationLocationChoice > destinationLocationIds.size() || destinationLocationChoice <= 0);
			int destinationLocationId = destinationLocationIds.get(destinationLocationChoice - 1);
      input.nextLine();
      
			ArrayList<String> pickupDates = dao.getPickupDates();
			System.out.println("Choose the pickup date");
			// System.out.println("1. 2024-07-10\n2. 2024-07-12\n3. 2024-08-08\n4. 2024-09-04");

			for(int i =0; i<pickupDates.size(); i++)
			{
				System.out.println((i+1) + ". " + pickupDates.get(i));
			}

      int pickupDateChoice;
      do{
        System.out.println("Enter the choice: ");
        try{ pickupDateChoice = input.nextInt(); } catch (InputMismatchException e) {
          pickupDateChoice = 1;
        }
      }while(pickupDateChoice > pickupDates.size() || pickupDateChoice <= 0);
			

			int pickupDateId = pickupDates.indexOf(pickupDates.get(pickupDateChoice - 1)) + 1;
			input.nextLine();

			ArrayList<String> pickupTimes = dao.getPickupTimes();
			System.out.println("Enter the pickup time");
			// System.out.println("1. 10:00 am \n2. 12:00 pm\n3. 1:30 pm\n4. 3:30 pm\n5. 5:00 pm");
			for(int i =0; i<pickupTimes.size(); i++)
			{
				System.out.println((i+1) + ". " + pickupTimes.get(i));
			}

      int pickupTimeChoice;
      do{
        System.out.println("Enter the choice: ");
        try{pickupTimeChoice = input.nextInt(); }catch (InputMismatchException e) {
          pickupTimeChoice = 1;
        }
      }while(pickupTimeChoice > pickupTimes.size() || pickupTimeChoice <= 0);

			int pickupTimeId = pickupTimes.indexOf(pickupTimes.get(pickupTimeChoice - 1)) + 1;
      input.nextLine();

			ArrayList<String> returnDates = dao.getReturnDates();
			System.out.println("Choose the return date");
			// System.out.println("1. 2024-07-13\n2. 2024-07-15\n3. 2024-08-12\n4. 2024-09-07");
			for(int i =0; i<returnDates.size(); i++)
			{
				System.out.println((i+1) + ". " + returnDates.get(i));
			}

      int returnDateChoice;
      do{
        System.out.println("Enter the choice: ");
        try { returnDateChoice = input.nextInt();} catch (InputMismatchException e) {
          returnDateChoice = input.nextInt();
        }
      }while(returnDateChoice > returnDates.size() || returnDateChoice <= 0);

			int returnDateId = returnDates.indexOf(returnDates.get(returnDateChoice - 1)) + 1;
      input.nextLine();
			
			ArrayList<String> returnTimes = dao.getReturnTimes();
			System.out.println("Enter the return time");
	  	// System.out.println("1. 11:00 am \n2. 12:30 pm\n3. 2:30 pm\n4. 4:00 pm\n5. 5:00 pm");
			for(int i =0; i<returnTimes.size(); i++)
			{
				System.out.println((i+1) + ". " + returnTimes.get(i));
			}

      int returnTimeChoice;
      do{
        System.out.println("Enter the choice: ");
        returnTimeChoice = input.nextInt();
      }while(returnTimeChoice > returnTimes.size() || returnTimeChoice <= 0);

			int returnTimeId = returnTimes.indexOf(returnTimes.get(returnTimeChoice - 1)) + 1;
      input.nextLine();

      ArrayList<CarRental> availableCars = dao.getAllAvailableCars(destinationLocationId);
      System.out.println();

      if(availableCars.isEmpty()) 
			{
        System.out.println("No available cars found.");
				System.out.println("Returning to the main menu...");
        return;
      } 
			else 
			{
				System.out.println("Available cars: ");
        for(CarRental car : availableCars)				  					  
          System.out.println(car);				   				  
        
        System.out.print("Enter the car name you want to book: ");				  
        String carName = input.nextLine();

        CarRental selectedCar = dao.getCarByName(carName);

        if(selectedCar != null) 
				{
          System.out.println("Selected car: \n" + selectedCar);
					
          boolean isValid = false;

          while(!isValid)
          {
            //System.out.println(userEmail);
						Account user = accountDao.selectByEmail(userEmail);
						phoneNumber = user.getPhone();
						System.out.print("Phone Number: " + phoneNumber);					
            input.nextLine();

						System.out.print("Enter your driver's lincense: ");							
						driverLicenseNumber = input.nextLine();					

						System.out.print("Enter your card number: ");							
						cardNumber = input.nextLine();
						// check if the card number is valid
						if(cardNumber.length() !=16)								
						{
							System.out.println("Invalid credit card number. Please enter a new card number");								
							continue;				
						}
						System.out.print("Enter your CVV: ");
						cvv = input.nextLine();
						// check if the CVV is valid
						if(cvv.length() !=3)								
						{
							System.out.println("Invalid CVV. Please enter a 3-digit number.");
							continue;
						}
						
						System.out.print("Enter your card expiry date (MM/YYYY): ");				
						expiryDate = input.nextLine();				
						
						System.out.print("Enter name on card: ");		
						nameOnCard = input.nextLine();

						isValid = true;
          }//end while     

					dao.bookCar(selectedCar.getCarName(), userEmail, driverLicenseNumber, cardNumber, cvv, expiryDate, nameOnCard);
					// System.out.println("Car Booked successfully!\n");
					isBookingComplete = true;
				}//end if 
				System.out.println("Car Booked successfully!\n");
         
      }//end else
    }
  }


   //Delete a specific account
   public static void deleteUser()
   {
      System.out.print("Please enter the user Email you want to delete: ");
      String email = input.next();
   
      AccountDao dao = new AccountDao();
      dao.deleteByEmail(email);
   
      System.out.println("Account email = " + email + " deleted.");
   }

   //viewInfo();
   public static void viewInfo()
   {
      AccountDao dao = new AccountDao();
      System.out.println("1. Select user by name.");
      System.out.println("2. Select user by email.");
      int choice;
      System.out.print("\nChoice: ");
      try {choice = input.nextInt();} catch (InputMismatchException e) {
        choice = 0;
      }
      input.nextLine();
      Account result;
   
         switch (choice)
         {
            case 1: System.out.print("Please enter the user's last name: ");
              String lname = input.nextLine();
              System.out.print("Please enter the user's first name: ");
              String fname = input.nextLine();
          
              result = dao.selectByName(fname,lname);
              if(result != null) //logIn successful
              {System.out.println(result.toString());}
            else {System.out.println("User not found.\n");}
              break;
         
         case 2: 
            System.out.print("Please enter the user's Email: ");
            String email = input.next();
         
            result = dao.selectByEmail(email);
            if(result != null) //logIn successful
            {System.out.println(result.toString());}
            else {System.out.println("User not found.\n");}
            break;

          default: System.out.println("Invalid choice, going back to the main menu\n ");
        }
   }
   
   public static void modifyUserInfo()
   {
      AccountDao dao = new AccountDao();
   
      System.out.println("Please enter the user email whose information you want to modify: ");
      String email = input.nextLine();
      input.nextLine();
      Account account = dao.selectByEmail(email);
   
      System.out.println("Select what you want to modify:");
      System.out.println("1. First name");
      System.out.println("2. Last name");
      System.out.println("3. Address"); //Include city & state & ZIP
      System.out.println("4. Phone number");
      System.out.println("5. Password");

      int choice;
      System.out.print("Enter your choice: ");
      try {choice = input.nextInt();} catch (InputMismatchException e) {
        choice = 0;
      }
   
      switch(choice)
      {
         case 1: //first name
            System.out.print("Current first name: " + account.getFirstName() + "\nEnter new first name: ");
            input.nextLine();
            String newFirstName = input.nextLine();
            account.setFirstName(newFirstName);
            dao.update(account);
            System.out.println("First name updated. Current first name: " + account.getFirstName() + "\n");
            break;
         case 2: //last name
            System.out.print("Current last name: " + account.getLastName() + "\nEnter new last name: ");
            input.nextLine();
            String newLastName = input.nextLine();
            account.setLastName(newLastName);
            dao.update(account);
            System.out.println("Last name updated. Current last name: " + account.getLastName() + "\n");
            break;
         case 3: //address
            System.out.println("Current address: " + account.getStreet() + "\nCurrent city: " + 
                               account.getCity() + "\nCurrent state: " + account.getState() + "\nCurrent ZIP: " + account.getZIP());
            input.nextLine();
            System.out.print("Enter new address: ");
            String newAddress = input.nextLine();
            account.setStreet(newAddress);
            System.out.print("Enter new city: ");
            String newCity = input.nextLine();
            account.setCity(newCity);
            System.out.print("Enter new state: ");
            String newState = input.nextLine();
            account.setState(newState);
            System.out.print("Enter new ZIP: ");
            String newZIP = input.nextLine();
            account.setZIP(newZIP);
            dao.update(account);
            System.out.println("Address updated. Current address: " + account.getStreet() + ", " +
                               account.getCity() + ", " + account.getState() + " " + account.getZIP() + "\n");
            break;
         case 4: //phone
            System.out.println("Current phone number: " + account.getPhone());
            input.nextLine();
            String newNumber = null;
					  do {
            System.out.println("Please enter a new 10-digit phone number:");
            newNumber = input.nextLine().trim();
            } while (newNumber.length() != 10);

					  account.setPhone(newNumber);
					  dao.update(account);
					  System.out.println("Phone number updated. Current phone number: " + account.getPhone() + "\n");
					  break;
         case 5: //password
            System.out.print("Current password: " + account.getPassword() + "\nEnter new password: ");
            input.nextLine();
            String newPass = input.nextLine();
            account.setPassword(newPass);
            dao.update(account);
            System.out.println("Password updated. Current password: " + account.getPassword() + "\n");
            break;
      }
   }
}


