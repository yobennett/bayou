/**
 * Created by bennett on 3/19/15.
 */
public class DependencyCheck {

    private final String query;
    private final int expectedResult;

    public DependencyCheck(String query, int expectedResult) {
        this.query = query;
        this.expectedResult = expectedResult;
    }

    public String query() {
        return query;
    }

    public int expectedResult() {
        return expectedResult;
    }

}
