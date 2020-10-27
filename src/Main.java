import java.util.*;

public class Main {
	
	static Scanner scanner = new Scanner(System.in);
	
	static ArrayList<Media> mediaList = new ArrayList<>(); //Denna lista är till för att lagra böcker som är tillgängliga (in stock)
	
	static HashMap<Media, Customer> rentedMedia = new HashMap<>(); //Denna är till för att lagra böcker som är utlånade.
	
	static int counter = 0;
	
	public static void list() {

		if(counter > 0) {
			for(int i = 0; i < mediaList.size(); i++) {
				System.out.println(mediaList.get(i) + " (in stock)");
			}
			
			for(Media i : rentedMedia.keySet()) {
				System.out.println(i + " is rented by: " + rentedMedia.get(i));
			}
		}
		else {
			System.out.println("There are no books or movies registered.");
		}
		
	}
	
	public static void checkOut(int articleNumber) {
	 
		System.out.println("Enter customer name: ");
		
		scanner.nextLine();
		
		String name = scanner.nextLine();
		
		System.out.println("");
		
		System.out.println("Enter the customers phone number: ");
		
		String phoneNumber = scanner.nextLine();
		
		Customer customer = new Customer(name, phoneNumber);
		
		if(counter == 0) {
			System.out.println("There are no registered books or movies in the library.");
		}
		else {
			for(int i = 0; i < mediaList.size(); i++) {
				if(mediaList.get(i).articleNumber == articleNumber) {
					System.out.println("Sucessfully lended " + mediaList.get(i).title + " to " + customer.getName());
					
					rentedMedia.put(mediaList.get(i), customer);
					
					mediaList.remove(i);
				}
			}
		}
	}
	
	public static void checkIn(int articleNumber) {
		//Ta tillbaka en utlånad produkt med artikelnummer.
		
		//Argumentet tar emot artikelnummer som argument.
	}
	
	public static void register() { 
		boolean b = false;
		 
		System.out.println("");
		
		System.out.println("What are you registering? Book(b) or a Movie(m)");
		
		String input = scanner.next();
		
		try {
			if (input.equals("b")) {
				
				
				System.out.println("Enter the product ID:");
				
				
				int articleNumber = Integer.parseInt(scanner.next());
				
				while(articleNumber < 10000 || articleNumber > 99999) {
					System.out.println("The product ID must be higher than 10000 and lower than 99999");
					
					articleNumber = Integer.parseInt(scanner.next());
				}
				
				
				
				
				System.out.println("");
				
				scanner.nextLine();
				

				System.out.println("Enter the title of the book:");
				String title = scanner.nextLine();
				
				System.out.println("");
				

				System.out.println("Enter the price:");
				int price = Integer.parseInt(scanner.next());
				
				System.out.println("");
				

				System.out.println("Enter how many pages the book has:");
				int pages = Integer.parseInt(scanner.next());
				
				scanner.nextLine();
				
				System.out.println("");
				

				System.out.println("Enter the publisher:");
				String publisher = scanner.nextLine();
				
				System.out.println("");
				

				Book book = new Book(articleNumber, title, price, pages, publisher);
				
				
				if(counter == 0) {
					mediaList.add(book);
					System.out.println("The book: " + title + ", is now added.");
					counter++;
				}
				else {
					
					mediaList.add(book);
					counter++;
					System.out.println("The book: " + title + " was successfully added.");

				}
				
						
					
			} else if (input.equals("m")) {

				System.out.println("");

				System.out.println("Enter the product ID:");
				int articleNumber = Integer.parseInt(scanner.next());

				while (articleNumber < 10000 || articleNumber > 99999) {
					System.out.println("The product ID must be higher than 10000 and lower than 99999");

					articleNumber = Integer.parseInt(scanner.next());

				}
				
				for(int i = 0; i < counter - 1; i++) {
					if(articleNumber == mediaList.get(i).articleNumber) {
						System.out.println("A book or movie with this ID already exists. Try again");
						register();
					}
				}

				System.out.println("");

				scanner.nextLine();
				

				System.out.println("Enter the title of the movie:");
				String title = scanner.nextLine();
				
				System.out.println("");
				

				System.out.println("Enter the price:");
				int price = Integer.parseInt(scanner.next());
				
				System.out.println("");
				

				System.out.println("Enter how long the movie is in minutes:");
				int lengthMin = Integer.parseInt(scanner.next());
				
				System.out.println("");
				

				System.out.println("What is the imdb-rating of this movie?:");
				float imdbRating = Float.parseFloat(scanner.next());
				
				System.out.println("");
				
				
				Movie movie = new Movie(articleNumber, title, price, lengthMin, imdbRating);
				
				mediaList.add(movie);
				counter++;
				System.out.println("The Movie: " + title + " was successfully added.");
					
				

			} else {
				System.out.println(input + " is not a valid input");
			}
		}
		catch(NumberFormatException e) {
			System.out.println("The input must be digits.");
			System.out.println(" ");
			register();
		}

	}
	
	public static void deRegister(int articleNumber) {
		//Metod för att avregistrera en produkt.
		
		//Tar emot artikelnummer som argument
		
		//> deregister 12346
		//Successfully deregistered The Great Gatsby
		
	}
	
	public static void info(int articleNumber) {
		//Skriver ut info om boken genom artikelnummer.
		
		//Tar emot artikelnummer som argument.
		
		
	}
	
	enum Command{
		LIST,
		CHECKOUT,
		CHECKIN,
		REGISTER,
		DEREGISTER,
		INFO,
		QUIT,
		UNKNOWN
	}
	
	public static Command parseCommand(String userInput) {
		String commandString = userInput.split(" ")[0];
		switch(commandString) {
		case "list":
			return Command.LIST;
		case "checkout":
			return Command.CHECKOUT;
		case "checkin":
			return Command.CHECKIN;
		case "register":
			return Command.REGISTER;
		case "deregister":
			return Command.DEREGISTER;
		case "info":
			return Command.INFO;
		case "quit":
			return Command.QUIT;
		default:
			return Command.UNKNOWN;
		}
	}
	
	public static int parseArguments(String userInput) {
		String[] commandAndArguments = userInput.split(" ");
		int argument = 0;
		for(int i=1; i < commandAndArguments.length; i++) {
			argument = Integer.parseInt(commandAndArguments[1]);
		}
		return argument;
	}
	
	public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the library program!");
        
        System.out.println("");
        
        System.out.println("- list = View all the registered books or movies \n- checkout + articlenumber = Loan a book or a movie to a customer.");
        System.out.println("- checkin + articlenumber = Return a loaned book or movie to the library \n- register = Add a new book or movie to the library.");
        System.out.println("- deregister + articlenumber = Remove a book or movie from the library \n- info + articlenumber = Writes out information about the book or movie.");
        System.out.println("- quit = Exit the program");
		
		while(true) {
			
			String userInput = scanner.nextLine();
			Command command = parseCommand(userInput);
			if(command == command.UNKNOWN) {
				System.out.println("Unknown command. Try again.");
				continue;
			}
			
			int argument = parseArguments(userInput);
			
			
			if(command == Command.LIST) {
				list();
			}
			else if(command == Command.CHECKOUT) {
			    checkOut(argument);

			}
			else if(command == Command.CHECKIN) {
				checkIn(argument);
				
			}
			else if(command == Command.REGISTER) {
				register();
				
			}
			else if(command == Command.DEREGISTER) {
				deRegister(argument);
			}
			else if(command == Command.INFO) {
				info(argument);
			}
			else if(command == Command.QUIT) {
				//Avsluta programmet.
				
			}

		}

	}

	public static void main(String[] args) {
		mainMenu();

	}

}