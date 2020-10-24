import java.util.*;

public class Main {
	
	static ArrayList<Media> mediaList = new ArrayList<>();
	
	static int counter = 0;
	
	public static void list() {
		
		
		// Metod som skriver en lista på alla böcker i lagret(utlånade eller ej.
		// Om en produkt är utlånad ska kundens namn och telefonnummer visas.

		// t.ex 12345 (Book): To Kill a Mockingbird. Borrowed by: Alice Doe,
		// 832-337-2959

		// Inga argument
		
		if(counter == 0) {
			System.out.println("There are no books or movies in stock.");
		}
		else {
			for(Media m : mediaList) {
				System.out.println(m.articleNumber + ", " + m.title);
			}
		}
	}
	
	public static void checkOut(int articleNumber) {
	    //Metod för att låna ut en produkt till en kund. 
		//Användaren ska kunna skriva ett telefonnummer och ett namn för kunden.
		
		//Argumentet som metoden tar emot är bokens artikelnummer
		
		//> checkout 12345 
		//Enter customer name:
		//Alice Doe
		//Enter customer phone number:
		//832-337-2959
		//Successfully lended To Kill a Mockingbird to Alice Doe
		
		//Såhär ska metoden funka.
	}
	
	public static void checkIn() {
		//Ta tillbaka en utlånad produkt med artikelnummer.
		
		//Argumentet tar emot artikelnummer som argument.
	}
	
	public static void register() { 
		
		 
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("What are you registering? Book(b) or a Movie(m)");
		
		String input = scanner.next();
		
		
		try {
			if (input.equals("b")) {
				System.out.println("Enter the product ID:");
				int articleNumber = Integer.parseInt(scanner.next());
				
				scanner.nextLine();

				System.out.println("Enter the title of the book:");
				String title = scanner.nextLine();

				System.out.println("Enter the price:");
				int price = Integer.parseInt(scanner.next());

				System.out.println("Enter how many pages the book has:");
				int pages = Integer.parseInt(scanner.next());
				
				scanner.nextLine();

				System.out.println("Enter the publisher:");
				String publisher = scanner.nextLine();

				Book book = new Book(articleNumber, title, price, pages, publisher);
				
				mediaList.add(book);
				
				counter++;

				System.out.println("The book: " + title + ", By: " + publisher + ", is now added.");
			} else if (input.equals("m")) {

				System.out.println("Enter the product ID:");
				int articleNumber = Integer.parseInt(scanner.next());

				scanner.nextLine();

				System.out.println("Enter the title of the movie:");
				String title = scanner.nextLine();

				System.out.println("Enter the price:");
				int price = Integer.parseInt(scanner.next());

				System.out.println("Enter how long the movie is in minutes:");
				int lengthMin = Integer.parseInt(scanner.next());

				scanner.nextLine();

				System.out.println("What is the imdb-rating of this movie?:");
				float imdbRating = Float.parseFloat(scanner.next());
				
				
				Movie movie = new Movie(articleNumber, title, price, lengthMin, imdbRating);
				
				mediaList.add(movie);
				
				counter++;

				System.out.println("The movie: " + title + ", is now added.");

			} else {
				System.out.println(input + " is not a valid input");
			}
		}
		catch(NumberFormatException e) {
			System.out.println("Ogiltig inmatning. Försök igen.");
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
		
		//> deregister 12346
		//Successfully deregistered The Great Gatsby
		
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
	
	public static String[] parseArguments(String userInput) {
		String[] commandAndArguments = userInput.split(" ");
		String[] arguments = new String[commandAndArguments.length - 1];
		for(int i=1; i < commandAndArguments.length; i++) {
			arguments[i-1] = commandAndArguments[i];
		}
		return arguments;
	}
	
	

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			
			String userInput = scanner.nextLine();
			Command command = parseCommand(userInput);
			if(command == command.UNKNOWN) {
				System.out.println("Ogiltigt kommando. Försök igen.");
				continue;
			}
			
			String[] arguments = parseArguments(userInput);
			if(command == Command.LIST) {
				list();
			}
			else if(command == Command.CHECKOUT) {

			}
			else if(command == Command.CHECKIN) {
				
				
			}
			else if(command == Command.REGISTER) {
				register();
				
			}
			else if(command == Command.DEREGISTER) {
				
			}
			else if(command == Command.INFO) {
				
			}
			else if(command == Command.QUIT) {
				//Avsluta programmet.
				
			}
			
		}
		
		
		
	}

}