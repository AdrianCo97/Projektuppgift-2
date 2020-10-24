
public class Movie extends Media {
	
	int lengthMin;
	float imdbScore;
	
	public Movie(int articleNumber, String title, int price, int lengthMin, float imdbScore) {
		super(articleNumber, title, price);
		this.lengthMin = lengthMin;
		this.imdbScore = imdbScore;
	}

}
