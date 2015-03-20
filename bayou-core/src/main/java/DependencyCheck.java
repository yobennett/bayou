/**
 * Created by bennett on 3/19/15.
 */
public class DependencyCheck {

    private final String query;
    private final String expectedResult;

    public DependencyCheck(String query, String expectedResult) {
        this.query = query;
        this.expectedResult = expectedResult;
    }

    public String query() {
        return query;
    }

    public String expectedResult() {
        return expectedResult;
    }

}
