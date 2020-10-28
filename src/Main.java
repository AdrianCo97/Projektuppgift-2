import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	static Scanner scanner = new Scanner(System.in);

	static ArrayList<Media> mediaList = new ArrayList<>(); // Denna lista �r till f�r att lagra b�cker som �r
															// tillg�ngliga (in stock)

	static HashMap<Media, Customer> rentedMedia = new HashMap<>(); // Denna �r till f�r att lagra b�cker som �r
																	// utl�nade.

	static int counter = 0;

	public static void checkArticleNumber(int articleNumber) {
		if (articleNumber < 10000 || articleNumber > 99999) {
			System.out.println("Invalid article ID, expecting 10000-99999. Please try again.");
			System.out.println("");
			mainMenu();
		}
	}
	
	public static void checkArticleNumberMatch(int articleNumber) {
		
		boolean match = false;
		
		for(int i = 0; i < mediaList.size(); i++){
			if(articleNumber == mediaList.get(i).articleNumber) {
				match = true;
				break;
			}
		}
		
		if(match == false) {
			System.out.println("There are no registered products with this ID.");
			
			System.out.println("");
			
			mainMenu();
		}
		
	}

	public static void list() {

		if (counter > 0) {
			for (int i = 0; i < mediaList.size(); i++) {

				if (mediaList.get(i).inStock) {
					System.out.println(mediaList.get(i) + " (in stock)");

				}

			}

			for (Media i : rentedMedia.keySet()) {
				System.out.println(i + " is rented by: " + rentedMedia.get(i));
			}
		} else {
			System.out.println("There are no books or movies registered.");
		}

	}

	public static void checkOut(int articleNumber) {
		
		checkArticleNumber(articleNumber);
		
		checkArticleNumberMatch(articleNumber);
		
		
		for (Media i : rentedMedia.keySet()) {
			if (articleNumber == i.articleNumber) {
				System.out.println(i + " Is already rented by " + rentedMedia.get(i));
				mainMenu();
			}
		}
		
		scanner.nextLine();

		System.out.println("Enter customer name: ");

		String name = scanner.nextLine();

		System.out.println("");

		System.out.println("Enter the customers phone number: ");

		String phoneNumber = scanner.nextLine();

		Customer customer = new Customer(name, phoneNumber);

		if (counter == 0) {
			System.out.println("There are no registered books or movies in the library.");
		} else {
			for (int i = 0; i < mediaList.size(); i++) {
				if (mediaList.get(i).articleNumber == articleNumber) {
					System.out.println("Sucessfully lended " + mediaList.get(i).title + " to " + customer.getName());

					rentedMedia.put(mediaList.get(i), customer);

					mediaList.get(i).inStock = false;
					
				}
			}
		}
	}

	public static void checkIn(int articleNumber) {
		// Ta tillbaka en utl�nad produkt med artikelnummer.

		// Argumentet tar emot artikelnummer som argument.

		checkArticleNumber(articleNumber);

		for (int i = 0; i < mediaList.size(); i++) {
			if (mediaList.get(i).articleNumber == articleNumber) {

				// mediaList.add(articleNumber, mediaList.get(i));

				rentedMedia.remove(mediaList.get(i));
				mediaList.get(i).inStock = true;

			}
		}

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

				checkArticleNumber(articleNumber);

				for (int i = 0; i < counter; i++) {
					if (articleNumber == mediaList.get(i).articleNumber) {
						System.out.println("A book or movie with this ID already exists. Try again");
						register();
					}
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

				mediaList.add(book);
				counter++;
				System.out.println("The book: " + title + " was successfully added.");

				System.out.println("");

				mainMenu();

			} else if (input.equals("m")) {

				System.out.println("");

				System.out.println("Enter the product ID:");
				int articleNumber = Integer.parseInt(scanner.next());

				checkArticleNumber(articleNumber);

				for (int i = 0; i < counter; i++) {
					if (articleNumber == mediaList.get(i).articleNumber) {
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

				System.out.println("");

				mainMenu();

			} else {
				System.out.println(input + " is not a valid input");
			}
		} catch (NumberFormatException e) {
			System.out.println("The input must be digits.");
			System.out.println(" ");
			register();
		}

	}

	public static void deRegister(int articleNumber) {
		// Metod f�r att avregistrera en produkt.

		// Tar emot artikelnummer som argument

		// > deregister 12346
		// Successfully deregistered The Great Gatsby

		checkArticleNumber(articleNumber);
		
		checkArticleNumberMatch(articleNumber);

		for (int i = 0; i < mediaList.size(); i++) {
			if (mediaList.get(i).articleNumber == articleNumber) {
				mediaList.remove(i);
			}

		}

	}

	public static void info(int articleNumber) {
		// Skriver ut info om boken genom artikelnummer.

		// Tar emot artikelnummer som argument.

		checkArticleNumber(articleNumber);
		
		checkArticleNumberMatch(articleNumber);

		for (int i = 0; i < mediaList.size(); i++) {
			if (mediaList.get(i).articleNumber == articleNumber) {
				System.out.println("ID: " + mediaList.get(i).articleNumber);
				System.out.println("Title: " + mediaList.get(i).title);
				System.out.println("Price: " + mediaList.get(i).price);
			}
		}
	}

//	public static void info(String error) {
//		// metod för att fånga om agrumentet är String
//
//		System.out.print(error + " is not a valid id. Should be between 10000-99999");
//	}

	enum Command {
		LIST, CHECKOUT, CHECKIN, REGISTER, DEREGISTER, INFO, QUIT, UNKNOWN
	}

	public static Command parseCommand(String userInput) {
		String commandString = userInput.split(" ")[0];
		switch (commandString) {
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
		for (int i = 1; i < commandAndArguments.length; i++) {
			argument = Integer.parseInt(commandAndArguments[1]);
		}
		return argument;
	}

	public static void mainMenu() {
		Scanner scanner = new Scanner(System.in);

		while (true) {

			String userInput = scanner.nextLine();
			Command command = parseCommand(userInput);
			if (command == command.UNKNOWN) {
				System.out.println("Unknown command. Try again.");
				continue;
			}

			int argument = parseArguments(userInput);

			System.out.println("");

			if (command == Command.LIST) {
				list();
			} else if (command == Command.CHECKOUT) {
				checkOut(argument);

			} else if (command == Command.CHECKIN) {
				checkIn(argument);

			} else if (command == Command.REGISTER) {
				register();

			} else if (command == Command.DEREGISTER) {
				deRegister(argument);
			} else if (command == Command.INFO) {
				info(argument);
			} else if (command == Command.QUIT) {
				// Avsluta programmet.

			}

		}

	}
	
	public static void csvReader() {
		
		Scanner scanner = new Scanner("Media.csv");
		
		int index = 0;
		
		while(scanner.hasNextLine()) {
			String[] values = scanner.nextLine().split(", ");
			
			int articleNumber = Integer.parseInt(values[0]);
			
			String title = values[2];
			
			
			
			index++;
		}
		scanner.close();
	}

	public static void main(String[] args) {

		System.out.println("Welcome to the library program!");

		System.out.println("");
		
		

		System.out.println("- list = View all the registered books or movies \n- checkout + articlenumber = Loan a book or a movie to a customer.");
		System.out.println("- checkin + articlenumber = Return a loaned book or movie to the library \n- register = Add a new book or movie to the library.");
		System.out.println("- deregister + articlenumber = Remove a book or movie from the library \n- info + articlenumber = Writes out information about the book or movie.");
		System.out.println("- quit = Exit the program");

		mainMenu();

	}

}