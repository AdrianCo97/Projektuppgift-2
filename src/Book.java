
public class Book extends Media{
	
	public int pages;
	public String publisher;
	
	
	public Book(int articleNumber, String title, int price, int pages, String publisher){
		super(articleNumber, title, price);
		this.pages = pages;
		this.publisher = publisher;
		
	}
	@Override
    public String toString() {
		return articleNumber + " (Book) " + this.title;
	}
}
