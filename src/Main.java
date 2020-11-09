import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	static Scanner scanner = new Scanner(System.in);

	static ArrayList<Media> mediaList = new ArrayList<>(); // This list stores all the books and movies.

	static HashMap<Media, Customer> rentedMedia = new HashMap<>(); // If a book or movie gets rented to a customer it
																	// will be stored in this HashMap. The product then
																	// gets linked to a customer.

	public static void checkArticleNumber(int articleNumber) {
		if (articleNumber < 10000 || articleNumber > 99999) {
			System.out.println("  Invalid article ID, expecting 10000-99999. Please try again.\n");
			System.out.println("  _____________________________________________________________________");
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
			System.out.println("  There are no registered products with this ID.\n");
			System.out.println("  _____________________________________________________________________");
			mainMenu();
		}

	}

	public static void checkForExistingMedia(int articleNumber) {
		for (Media i : rentedMedia.keySet()) {
			if (articleNumber == i.articleNumber) {
				System.out.println("  " + i + " already exists and it's currently rented by: " + rentedMedia.get(i) + "\n");
				System.out.println("  _____________________________________________________________________");
				mainMenu();
			}
		}

		for (int i = 0; i < mediaList.size(); i++) {
			if (articleNumber == mediaList.get(i).articleNumber) {
				System.out.println("  A book or movie with this ID already exists. Try again\n");
				System.out.println("  _____________________________________________________________________");
				mainMenu();
			}
		}
	}

	public static void checkInStock(int articleNumberToCheck) {
		for (int i = 0; i < mediaList.size(); i++) {

			if (mediaList.get(i).articleNumber == articleNumberToCheck && mediaList.get(i).inStock == false) {
				System.out.print("  The product is currently rented. Please use checkin before using deregister.\n");
				System.out.println("  _____________________________________________________________________");
				mainMenu();

			}

		}
	}
	
	public static void createNewFiles() {
		File mediaData = new File("mediadata.txt");			
		File rentedFile = new File("rented.txt");
		try {
			if(!mediaData.exists()) {
				mediaData.createNewFile();
			}
			else if(!rentedFile.exists()) {
				rentedFile.createNewFile();
			}
		}
		catch(IOException e) {
		}
	}

	public static void readFiles() { //This method reads the bytes from the files storing the ArrayList and HashMap. 
		
		createNewFiles();
		
		try {
			

			FileInputStream movieInput = new FileInputStream("mediadata.txt");
			ObjectInputStream mOInput = new ObjectInputStream(movieInput);

			mediaList = (ArrayList<Media>) mOInput.readObject();

			movieInput.close();
			mOInput.close();

			FileInputStream fInput = new FileInputStream("rented.txt");
			ObjectInputStream oInput = new ObjectInputStream(fInput);

			rentedMedia = (HashMap) oInput.readObject();

			fInput.close();
			oInput.close();

		} catch (IOException e) {

		} catch (ClassNotFoundException e) {
			System.out.println("  The program couldn't dezerialize from the file.");
		}

	}

	public static void writeToMediaFile() { // This method serializes the ArrayList(mediaList) and prints it in binary in the file mediadata.txt.
		
		createNewFiles();

		try {

			FileOutputStream fOutput = new FileOutputStream("mediadata.txt");
			ObjectOutputStream oOutput = new ObjectOutputStream(fOutput);

			oOutput.writeObject(mediaList);

			fOutput.close();
			oOutput.close();

		} catch (IOException e) {
			System.out.println("  The program couldn't find the file or files.");
		}

	}

	public static void writeToRentedFile() { // This method writes the entire hashMap into a txt file called "rented".
												// This is to track what's rented to who.
		createNewFiles();
		
		try {
			FileOutputStream fOutput = new FileOutputStream("rented.txt");
			ObjectOutputStream oOutput = new ObjectOutputStream(fOutput);

			oOutput.writeObject(rentedMedia);

			fOutput.close();
			oOutput.close();
		} catch (IOException e) {
			System.out.println("  The program couldn't find the file.");
		}
	}

	public static void list() {
		// Method to list all products

		Collections.sort(mediaList);

		if (mediaList.size() == 0) {
			System.out.println("  There are no registered books or movies.");
		}
		else {
			System.out.println("  Currently registered books and movies:\n");
			for (Media m : mediaList) {
				if (m.inStock) {
					System.out.println("  " + m + " (in stock)");
				}
			}
		}
		System.out.println("");
		
		ArrayList<Media> keyValues = new ArrayList<>(rentedMedia.keySet());
		
		Collections.sort(keyValues);
		
		if (rentedMedia.size() > 0) {
			System.out.println("  Currently rented books and movies:\n");
		}
		for (Media m : keyValues) {
			System.out.println("  " + m + " is rented by: " + rentedMedia.get(m));
		}
		System.out.println("  _____________________________________________________________________");
	}

	public static void checkOut(int articleNumber) { // This method is used to rent a product to a customer.
		
		createNewFiles();

		checkArticleNumber(articleNumber);

		checkArticleNumberMatch(articleNumber);

		for (Media i : rentedMedia.keySet()) {
			if (articleNumber == i.articleNumber) {
				System.out.println("  " + i + " is already rented by: " + rentedMedia.get(i));
				System.out.println("  _____________________________________________________________________");
				mainMenu();
			}
		}

		System.out.println("  Enter customer name: ");

		String name = scanner.nextLine();
		
		System.out.println("\n> " + name + "\n");

		System.out.println("  Enter the customers phone number: ");

		String phoneNumber = scanner.nextLine();
		
		System.out.println("\n> " + phoneNumber + "\n");

		Customer customer = new Customer(name, phoneNumber);

		if (mediaList.size() == 0) {
			System.out.println("  There are no books or movies in stock.");
		} else {
			for (int i = 0; i < mediaList.size(); i++) {
				if (mediaList.get(i).articleNumber == articleNumber) {
					System.out.println("  Successfully lended " + mediaList.get(i).title + " to " + customer.getName() + "\n");

					rentedMedia.put(mediaList.get(i), customer);

					mediaList.get(i).inStock = false;
				}

			}
			writeToRentedFile();
			writeToMediaFile();
			System.out.println("  _____________________________________________________________________");
		}

	}

	public static void checkIn(int articleNumber) {
		// Method for returning a lent product
		
		createNewFiles();
		
		checkArticleNumber(articleNumber);
		checkArticleNumberMatch(articleNumber);

		for (int i = 0; i < mediaList.size(); i++) {
			if (mediaList.get(i).articleNumber == articleNumber && mediaList.get(i).inStock == true) {
				System.out.print("  This product is currenly not rented by any customer.\n");
				System.out.println("  _____________________________________________________________________");
				mainMenu();
			}
		}

		for (Media i : rentedMedia.keySet()) {
			if (articleNumber == i.articleNumber) {
				
				rentedMedia.remove(i);

				for (int j = 0; j < mediaList.size(); j++) {
					if (mediaList.get(j).articleNumber == articleNumber) {
						mediaList.get(j).inStock = true;
					}
				}

				System.out.print("  " + i + " is now returned to stock.\n");
				writeToRentedFile();
				writeToMediaFile();
				
				System.out.println("  _____________________________________________________________________");
				mainMenu();
			}
		}
	}

	public static void register() { // This method is used to register a new book or movie to the library.
		
		createNewFiles();

		System.out.println("  What are you registering? Book(b) or a Movie(m)");

		String input = scanner.nextLine();

		try {
			if (input.equals("b")) {

				System.out.println("\n  Enter the product ID:\n");

				int articleNumber = Integer.parseInt(scanner.nextLine());
				
				System.out.println("\n> " + articleNumber + "\n");

				checkArticleNumber(articleNumber);

				checkForExistingMedia(articleNumber);

				System.out.println("  Enter the title of the book:\n");
				String title = scanner.nextLine();
				
				System.out.println("\n> " + title + "\n");

				System.out.println("  Enter the price:\n");
				int price = Integer.parseInt(scanner.nextLine());
				
				System.out.println("\n> " + price + "\n");

				System.out.println("  Enter how many pages the book has:\n");
				int pages = Integer.parseInt(scanner.nextLine());
				
				System.out.println("\n> " + pages + "\n");

				System.out.println("  Enter the publisher:\n");
				String publisher = scanner.nextLine();
				
				System.out.println("\n> " + publisher + "\n");

				Book book = new Book(articleNumber, title, price, pages, publisher);

				mediaList.add(book);

				System.out.println("  The book: " + title + " was successfully added.\n");

				writeToMediaFile();
				
				System.out.println("  _____________________________________________________________________");

				mainMenu();

			} else if (input.equals("m")) {

				System.out.println("");

				System.out.println("  Enter the product ID:\n");
				int articleNumber = Integer.parseInt(scanner.nextLine());
				
				System.out.println("\n> " + articleNumber + "\n");

				checkArticleNumber(articleNumber);

				checkForExistingMedia(articleNumber);

				System.out.println("  Enter the title of the movie:\n");
				String title = scanner.nextLine();
				
				System.out.println("\n> " + title + "\n");

				System.out.println("  Enter the price:\n");
				int price = Integer.parseInt(scanner.nextLine());
				
				System.out.println("\n> " + price + "\n");

				System.out.println("  Enter how long the movie is in minutes:\n");
				int lengthMin = Integer.parseInt(scanner.nextLine());
				
				System.out.println("\n> " + lengthMin + "\n");

				System.out.println("  What is the imdb-rating of this movie?:\n");
				float imdbRating = Float.parseFloat(scanner.nextLine());
				
				System.out.println("\n> " + imdbRating + "\n");

				Movie movie = new Movie(articleNumber, title, price, lengthMin, imdbRating);

				mediaList.add(movie);

				System.out.println("  The Movie: " + title + " was successfully added.\n");

				writeToMediaFile();
				
				System.out.println("  _____________________________________________________________________");

				mainMenu();

			} else {
				System.out.println("  " + input + " is not a valid input. Try again.\n");
				register();
			}
		} catch (NumberFormatException e) {
			System.out.println("The input must be digits.\n");
			register();
		}

	}

	public static void deRegister(int articleNumber) {
		// Method for deregister a book from stock. Updates files.
		
		createNewFiles();
		checkArticleNumber(articleNumber);
		checkArticleNumberMatch(articleNumber);
		checkInStock(articleNumber);

		for (int i = 0; i < mediaList.size(); i++) {
			if (mediaList.get(i).articleNumber == articleNumber) {
				System.out.println("  Successfully deregistered: " + mediaList.get(i).title);
				mediaList.remove(i);
				writeToMediaFile();
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
					System.out.println("  ID: " + mediaList.get(i).articleNumber);
					System.out.println("  Title: " + mediaList.get(i).title);
					System.out.println("  Price: " + mediaList.get(i).price + " kr");
					System.out.println("  Pages: " + b.pages);
					System.out.println("  Publisher: " + b.publisher);
				}
			}

			if (mediaList.get(i) instanceof Movie) {
				Movie m = (Movie) mediaList.get(i);
				if (mediaList.get(i).articleNumber == articleNumber) {
					System.out.println("  ID: " + mediaList.get(i).articleNumber);
					System.out.println("  Title: " + mediaList.get(i).title);
					System.out.println("  Price: " + mediaList.get(i).price + " kr");
					System.out.println("  Length: " + m.lengthMin + " min");
					System.out.println("  IMDB rating: " + m.imdbScore);
				}
			}
		}
		System.out.println("  _____________________________________________________________________");
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

			System.out.println("  Write your command:\n");

			String userInput = scanner.nextLine();

			System.out.println("\n> " + userInput + "\n");

			Command command = parseCommand(userInput);
			if (command == command.UNKNOWN) {
				System.out.println("  Unknown command. Try again.");
				continue;
			}

			int argument = 0;
			try {
				argument = parseArguments(userInput);
			} catch (NumberFormatException e) {

				System.out.println("  You can only use digits with Commands.");

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
				System.out.println("  Thanks for using the program.");
				System.out.println("  Saving...");
				writeToMediaFile();
				System.exit(0);
			}

		}

	}

	public static void main(String[] args) {

		readFiles();

		System.out.println("\n  Welcome to the library program!\n");

		list();

		System.out.println("");

		System.out.println("- list = View all the registered books or movies. \n- checkout + articlenumber = Loan a book or a movie to a customer.");
		System.out.println("- checkin + articlenumber = Return a rented book or movie to the library. \n- register = Add a new book or movie to the library.");
		System.out.println("- deregister + articlenumber = Remove a book or movie from the library. \n- info + articlenumber = Writes out information about the book or movie.");
		System.out.println("- quit = Exit the program");

		System.out.println("");

		mainMenu();

	}

}