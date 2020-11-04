import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class Main {

	static Scanner scanner = new Scanner(System.in);

	static ArrayList<Media> mediaList = new ArrayList<>(); // This list stores all the books and movies.

	static HashMap<Media, Customer> rentedMedia = new HashMap<>(); // If a book or movie gets rented to a customer it
																	// will be stored in this HashMap. The product then
																	// gets linked to a customer.

	public static void checkArticleNumber(int articleNumber) {
		if (articleNumber < 10000 || articleNumber > 99999) {
			System.out.println("Invalid article ID, expecting 10000-99999. Please try again.");
			System.out.println("");
			mainMenu();
		}
	}

	public static void checkArticleNumberMatch(int articleNumber) {
		// Method to check if given id is correct
		boolean match = false;

		for (int i = 0; i < mediaList.size(); i++) {
			if (articleNumber == mediaList.get(i).articleNumber) {
				match = true;
				break;
			}
		}

		if (match == false) {
			System.out.println("There are no registered products with this ID.");
			System.out.println("");
			mainMenu();
		}

	}
	
	public static void checkForExistingMedia(int articleNumber) {
		for(Media i : rentedMedia.keySet()) {
			if(articleNumber == i.articleNumber) {
				System.out.println(i + " already exists and it's currently rented by: " + rentedMedia.get(i));
				System.out.println("");
				mainMenu();
			}
		}
		
		for (int i = 0; i < mediaList.size(); i++) {
			if (articleNumber == mediaList.get(i).articleNumber) {
				System.out.println("A book or movie with this ID already exists. Try again");
				System.out.println("");
				mainMenu();
			}
		}
	}

	public static void checkInStock(int articleNumberToCheck) {
		for (int i = 0; i < mediaList.size(); i++) {

			if (mediaList.get(i).articleNumber == articleNumberToCheck && mediaList.get(i).inStock == false) {
				System.out.print("The product is rented. Please checkin before deregister.");
				mainMenu();

			}

		}
	}

	public static void readFiles() { // This method reads the files. Seperates each word by the comma. It then
		                             // creates a new book or movie based on those values.
		
		try {
			
			FileInputStream movieInput = new FileInputStream("mediadata.txt");
            ObjectInputStream mOInput = new ObjectInputStream(movieInput);
            
            mediaList = (ArrayList<Media>) mOInput.readObject();
            
            movieInput.close();
			mOInput.close();
            
   		
			FileInputStream fInput = new FileInputStream("rented.txt");
			ObjectInputStream oInput = new ObjectInputStream(fInput);
			
			rentedMedia = (HashMap)oInput.readObject();
			
			fInput.close();
			oInput.close();
		    
			
		} catch (IOException e) {
			System.out.println("The program couldn't find the file or files.");
		} catch (ClassNotFoundException e) {
			System.out.println("The program couldn't deserialize from the file");
		}
		

	}

	
	
	public static void writeToMediaFile() { // This method takes a book and prints the values into a txt file.

		try {

			FileOutputStream fOutput = new FileOutputStream("mediadata.txt", true);
			ObjectOutputStream oOutput = new ObjectOutputStream(fOutput);

			oOutput.writeObject(mediaList);
			
			fOutput.close();
			oOutput.close();

		} catch (IOException e) {
			System.out.println("The program couldn't find the file or files.");
		}

	}


	public static void writeToRentedFile() { // This method writes the entire hashMap into a txt file called "rented".
												// This is to track what's rented to who.
		try {
			FileOutputStream fOutput = new FileOutputStream("rented.txt", true);
			ObjectOutputStream oOutput = new ObjectOutputStream(fOutput);

			oOutput.writeObject(rentedMedia);
			
			fOutput.close();
			oOutput.close();
		} catch (IOException e) {
			System.out.println("The program couldn't find the file.");
		}
	}
	
	

	public static void list() {
		// Method to list all products

		Collections.sort(mediaList);
		if (mediaList.size() > 0) {
			for (int i = 0; i < mediaList.size(); i++) {
				if (mediaList.get(i).inStock) {
					System.out.println("  " + mediaList.get(i) + " (in stock)");
				}
			}
			for (Media i : rentedMedia.keySet()) {
				System.out.println("  " + i + " is rented by: " + rentedMedia.get(i));
			}
		} else {
			System.out.println("There are no books or movies registered.");
		}
	}

	public static void checkOut(int articleNumber) { // This method is used to rent a product to a customer.

		checkArticleNumber(articleNumber);

		checkArticleNumberMatch(articleNumber);
		
		for(Media i : rentedMedia.keySet()) {
			if(articleNumber == i.articleNumber) {
				System.out.println(i + "Is already rented by: " + rentedMedia.get(i));
			}
		}

		System.out.println("Enter customer name: ");

		String name = scanner.nextLine();

		System.out.println("");

		System.out.println("Enter the customers phone number: ");

		String phoneNumber = scanner.nextLine();

		Customer customer = new Customer(name, phoneNumber);

		if (mediaList.size() == 0) {
			System.out.println("There are no books or movies in stock.");
		} else {
			for (int i = 0; i < mediaList.size(); i++) {
				if (mediaList.get(i).articleNumber == articleNumber) {
					System.out.println("Sucessfully lended " + mediaList.get(i).title + " to " + customer.getName());

					rentedMedia.put(mediaList.get(i), customer);
					
					mediaList.get(i).inStock = false;
				}
				
			}
			writeToRentedFile();
		}
		
	}
	

	public static void checkIn(int articleNumber) {
		// Method for returning a lent product
		checkArticleNumber(articleNumber);

		for (int i = 0; i < mediaList.size(); i++) {
			if (mediaList.get(i).articleNumber == articleNumber) {
				rentedMedia.remove(mediaList.get(i));
				mediaList.get(i).inStock = true;
				System.out.print(mediaList.get(i) + " is now returned to stock.\n");
			}
		}
	}

	public static void register() { // This method is used to register a new book or movie to the library.

		System.out.println("What are you registering? Book(b) or a Movie(m)");

		String input = scanner.next();

		try {
			if (input.equals("b")) {

				System.out.println("Enter the product ID:");

				int articleNumber = Integer.parseInt(scanner.next());

				checkArticleNumber(articleNumber);
				
				checkForExistingMedia(articleNumber);

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

				System.out.println("The book: " + title + " was successfully added.");

				System.out.println("");

				mainMenu();

			} else if (input.equals("m")) {

				System.out.println("");

				System.out.println("Enter the product ID:");
				int articleNumber = Integer.parseInt(scanner.next());

				checkArticleNumber(articleNumber);
				
				checkForExistingMedia(articleNumber);

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

				System.out.println("The Movie: " + title + " was successfully added.");

				System.out.println("");

				mainMenu();

			} else {
				System.out.println(input + " is not a valid input. Try again.");
				System.out.println("");
				register();
			}
		} catch (NumberFormatException e) {
			System.out.println("The input must be digits.");
			System.out.println(" ");
			register();
		}

	}

	public static void deRegister(int articleNumber) {
		// Method for deregister a book from stock. Updates files.
		checkArticleNumber(articleNumber);
		checkArticleNumberMatch(articleNumber);
		checkInStock(articleNumber);

		for (int i = 0; i < mediaList.size(); i++) {
			if (mediaList.get(i).articleNumber == articleNumber) {
				System.out.println("Successfully deregistered " + mediaList.get(i).title);
				mediaList.remove(i);
				try {
					new FileWriter("books.txt", false).close();
					new FileWriter("movies.txt", false).close();

				} catch (IOException e) {
					
					e.printStackTrace();
				}

//				for (int j = 0; j < mediaList.size(); j++) {
//
//					if (mediaList.get(j) instanceof Book) {
//						Book b = (Book) mediaList.get(j);
//
//						writeToBookFile(b);
//
//					}
//					if (mediaList.get(j) instanceof Movie) {
//						Movie m = (Movie) mediaList.get(j);
//
//						writeToMovieFile(m);
//					}
//				}

			} else {
				System.out.print("The product is already rented. Please use checkin on this book or movie before using deregister.");
				break;


			}

		}

	}

	public static void info(int articleNumber) {
		// Method that shows all information about a product.
		checkArticleNumber(articleNumber);
		checkArticleNumberMatch(articleNumber);
		for (int i = 0; i < mediaList.size(); i++) {

			if (mediaList.get(i) instanceof Book) {
				Book b = (Book) mediaList.get(i);
				if (mediaList.get(i).articleNumber == articleNumber) {
					System.out.println("ID: " + mediaList.get(i).articleNumber);
					System.out.println("Title: " + mediaList.get(i).title);
					System.out.println("Price: " + mediaList.get(i).price + " kr");
					System.out.println("Pages: " + b.pages);
					System.out.println("Publisher: " + b.publisher);
				}
			}

			if (mediaList.get(i) instanceof Movie) {
				Movie m = (Movie) mediaList.get(i);
				if (mediaList.get(i).articleNumber == articleNumber) {
					System.out.println("ID: " + mediaList.get(i).articleNumber);
					System.out.println("Title: " + mediaList.get(i).title);
					System.out.println("Price: " + mediaList.get(i).price + " kr");
					System.out.println("Length: " + m.lengthMin + " min");
					System.out.println("IMDB rating: " + m.imdbScore);
				}
			}
		}
	}

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

			System.out.println("");

			System.out.println("Write your input:");

			String userInput = scanner.nextLine();

			System.out.println("");

			System.out.println("> " + userInput);

			System.out.println("");

			Command command = parseCommand(userInput);
			if (command == command.UNKNOWN) {
				System.out.println("Unknown command. Try again.");
				continue;
			}

			int argument = 0;
			try {
				argument = parseArguments(userInput);
			} catch (NumberFormatException e) {

				System.out.println("You can only use digits with Commands.");

				mainMenu();
			}

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
				System.out.println("Thanks for using the program.");
				System.out.println("Saving...");
				writeToMediaFile();
				System.exit(0);
			}

		}

	}

	public static void main(String[] args) {

		readFiles();

		System.out.println("Welcome to the library program!");

		System.out.println("");

		System.out.println("  Currently registered books and movies.");

		System.out.println("");

		list();

		System.out.println("");

		System.out.println(
				"- list = View all the registered books or movies. \n- checkout + articlenumber = Loan a book or a movie to a customer.");
		System.out.println(
				"- checkin + articlenumber = Return a rented book or movie to the library. \n- register = Add a new book or movie to the library.");
		System.out.println(
				"- deregister + articlenumber = Remove a book or movie from the library. \n- info + articlenumber = Writes out information about the book or movie.");
		System.out.println("- quit = Exit the program");

		System.out.println("");

		mainMenu();

	}

}