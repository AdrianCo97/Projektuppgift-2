
public class Movie extends Media {

	public int lengthMin;
	public float imdbScore;

	public Movie(int articleNumber, String title, int price, int lengthMin, float imdbScore) {
		super(articleNumber, title, price);
		this.lengthMin = lengthMin;
		this.imdbScore = imdbScore;
	}

	@Override
	public String toString() {
		return articleNumber + " (Movie) " + title;
	}

	public String getTitle() {
		return this.title;
	}

}
