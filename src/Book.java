
public class Book extends Media {
	
	private int pages;
	private String publisher;
	
	public Book(int articleNumber, String title, int price, int pages, String publisher){
		super(articleNumber, title, price);
		this.pages = pages;
		this.publisher = publisher;
		
	}
	
    public void printBook() {
		System.out.println(this.articleNumber + "(Book)" + this.title);
	}
	
	
	

}
