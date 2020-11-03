
public abstract class Media implements Comparable<Media> {

	protected int articleNumber;
	protected String title;
	protected int price;
	protected boolean inStock = true;

	public Media(int articleNumber, String title, int price) {
		this.articleNumber = articleNumber;
		this.title = title;
		this.price = price;

	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public int compareTo(Media artNr) {

		int compare = ((Media) artNr).getArticleNumber();
		return this.articleNumber - compare;
	}

	@Override
	public String toString() {
		return "ID: " + articleNumber + ", Title: " + title + ", Price: =" + price + "]";
	}

}
