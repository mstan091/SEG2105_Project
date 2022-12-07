package uott.seg.mealerproject.misc;
import junit.framework.TestCase;

public class CookRatingTest extends TestCase {

    public void testGetNumOfRating() {
        float inRating=4.0f;
        int inNumOfRating=3;
        CookRating rating = new CookRating(inRating, inNumOfRating);
        int outNumOfRating = rating.getNumOfRating();
        int expNumOfRating = 3;
        assertEquals(outNumOfRating, expNumOfRating );
    }
}
