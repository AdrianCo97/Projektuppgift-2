
public abstract class Media {

	protected int articleNumber;
	protected String title;
	protected int price;
	protected boolean inStock;

	public Media(int articleNumber, String title, int price) {
		this.articleNumber = articleNumber;
		this.title = title;
		this.price = price;
		this.inStock = true;

	}

}
