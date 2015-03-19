/**
 * Created by bennett on 3/18/15.
 */
public class TupleStore {

    private long timestamp;

    public TupleStore() {}

    // TODO integrate in-memory postgresql

    public void rollback(long timestamp) {
        // TODO undo to timestamp
    }

}
