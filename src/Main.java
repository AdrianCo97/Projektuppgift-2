import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	static Scanner scanner = new Scanner(System.in);

	static ArrayList<Media> mediaList = new ArrayList<>(); // Denna lista �r till f�r att lagra b�cker som �r
															// tillg�ngliga (in stock)

	static HashMap<Media, Customer> rentedMedia = new HashMap<>(); // Denna �r till f�r att lagra b�cker som �r
																	// utl�nade.

	public static void checkArticleNumber(int articleNumber) {
		if (articleNumber < 10000 || articleNumber > 99999) {
			System.out.println("Invalid article ID, expecting 10000-99999. Please try again.");
			System.out.println("");
			mainMenu();
		}
	}

	public static void checkArticleNumberMatch(int articleNumber) {

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

	public static void readFiles() {

		try {

			BufferedReader bufferedBookReader = new BufferedReader(new FileReader("books.txt"));

			String bookStr;

			while ((bookStr = bufferedBookReader.readLine()) != null) {
				String[] values = bookStr.split(", ");

				int articleNumber = Integer.parseInt(values[0]);

				String title = values[1];

				int price = Integer.parseInt(values[2]);

				int pages = Integer.parseInt(values[3]);

				String publisher = values[4];

				Book book = new Book(articleNumber, title, price, pages, publisher);

				mediaList.add(book);

			}

			BufferedReader bufferedMovieReader = new BufferedReader(new FileReader("movies.txt"));
			String movieStr;

			while ((movieStr = bufferedMovieReader.readLine()) != null) {
				String[] values = movieStr.split(", ");

				int articleNumber = Integer.parseInt(values[0]);

				String title = values[1];

				int price = Integer.parseInt(values[2]);

				int length = Integer.parseInt(values[3]);

				float rating = Float.parseFloat(values[4]);

				Movie movie = new Movie(articleNumber, title, price, length, rating);

				mediaList.add(movie);

			}
			bufferedBookReader.close();
			bufferedMovieReader.close();
		} catch (IOException e) {
			System.out.println("The program couldn't find the file or files.");
		}

	}

	public static void writeToBookFile(Book book) {

		try {
			FileWriter fW = new FileWriter("books.txt");
			BufferedWriter bF = new BufferedWriter(fW);
			PrintWriter pW = new PrintWriter(bF);

			pW.println(book.articleNumber + ", " + book.title + ", " + book.price + ", " + book.pages + ", "
					+ book.publisher);
		} catch (IOException e) {
			System.out.println("The program couldn't find the file or files.");
		}

	}

	public static void writeToMovieFile(Movie movie) {

		try {
			FileWriter fW = new FileWriter("books.txt");
			BufferedWriter bF = new BufferedWriter(fW);
			PrintWriter pW = new PrintWriter(bF);

			pW.println(movie.articleNumber + ", " + movie.title + ", " + movie.price + ", " + movie.lengthMin + ", "
					+ movie.imdbScore);
		} catch (IOException e) {
			System.out.println("The program couldn't find the file or files.");
		}

	}

	public static void list() {

		if (mediaList.size() > 0) {
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

		System.out.println("");

		System.out.println("What are you registering? Book(b) or a Movie(m)");

		String input = scanner.next();

		try {
			if (input.equals("b")) {

				System.out.println("Enter the product ID:");

				int articleNumber = Integer.parseInt(scanner.next());

				checkArticleNumber(articleNumber);

				for (int i = 0; i < mediaList.size(); i++) {
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

				writeToBookFile(book);

				System.out.println("The book: " + title + " was successfully added.");

				System.out.println("");

				mainMenu();

			} else if (input.equals("m")) {

				System.out.println("");

				System.out.println("Enter the product ID:");
				int articleNumber = Integer.parseInt(scanner.next());

				checkArticleNumber(articleNumber);

				for (int i = 0; i < mediaList.size(); i++) {
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

				writeToMovieFile(movie);

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
				System.out.println("Successfully deregistered " + mediaList.get(i).title);
				mediaList.remove(i);
			}

		}

	}

	public static void info(int articleNumber) {
		
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

	public static void main(String[] args) {

		System.out.println("Welcome to the library program!");

		System.out.println("");

		System.out.println(
				"- list = View all the registered books or movies \n- checkout + articlenumber = Loan a book or a movie to a customer.");
		System.out.println(
				"- checkin + articlenumber = Return a loaned book or movie to the library \n- register = Add a new book or movie to the library.");
		System.out.println(
				"- deregister + articlenumber = Remove a book or movie from the library \n- info + articlenumber = Writes out information about the book or movie.");
		System.out.println("- quit = Exit the program");

		System.out.println("");

		readFiles();

		mainMenu();

	}

}