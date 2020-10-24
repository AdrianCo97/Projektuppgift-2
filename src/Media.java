
public abstract class Media {
	
	protected int articleNumber;
	protected String title;
	protected int price;
	
	public Media(int articleNumber, String title, int price){
		this.articleNumber = articleNumber;
		this.title = title;
		this.price = price;
	}
	
	public int getArticleNumber() {
		return this.articleNumber;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	

}
