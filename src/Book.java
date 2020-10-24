
public class Book extends Media {
	
	int pages;
	String publisher;
	
	public Book(int articleNumber, String title, int price, int pages, String publisher){
		super(articleNumber, title, price);
		this.pages = pages;
		this.publisher = publisher;
		
	}

}
