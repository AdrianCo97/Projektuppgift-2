import java.util.*;

public class Main {
	
	public static void list() {
		// Metod som skriver en lista på alla böcker i lagret(utlånade eller ej.
		// Om en produkt är utlånad ska kundens namn och telefonnummer visas.

		// t.ex 12345 (Book): To Kill a Mockingbird. Borrowed by: Alice Doe,
		// 832-337-2959

		// Inga argument
	}
	
	public static void checkOut(int artikelNummer) {
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
	
	public static void register(int artikelNummer) {
		//Metod för att lägga till en ny produkt och registrera den i systemet
		
		//inga argument
		
		//What are you registering? Book (b), Movie (m)
//		> m
//		Enter product ID: 
//		> 77777
//		Enter title: 
//		> The Dark Knight
//		Enter value: 
//		> 199
//		Enter length: 
//		> 152
//		Enter rating: 
//		> 9.0
//		Successfully registered The Dark Knight!
		
		//Såhär ska flödet i metoden se ut.
	}
	
	public static void deRegister(int artikelNummer) {
		//Metod för att avregistrera en produkt.
		
		//Tar emot artikelnummer som argument
		
		//> deregister 12346
		//Successfully deregistered The Great Gatsby
		
	}
	
	public static void info(int artikelNummer) {
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
				
			}
			else if(command == Command.CHECKOUT) {

			}
			else if(command == Command.CHECKIN) {
				
				
			}
			else if(command == Command.REGISTER) {
				
				
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