package uott.seg.mealerproject.misc;

public class CookRating {
    float rating;
    int numOfRating;

    public CookRating (float rating, int numOfRating) {
        this.rating = rating;
        this.numOfRating = numOfRating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNumOfRating() {
        return numOfRating;
    }

    public void setNumOfRating(int numOfRating) {
        this.numOfRating = numOfRating;
    }
}
