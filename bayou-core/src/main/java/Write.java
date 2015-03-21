import java.util.Comparator;

/**
 * Created by bennett on 3/14/15.
 */
public class Write implements Comparable<Write> {

    public static final Comparator<Write> COMMITTED_TIMESTAMP_ORDER = new ByCommittedTimestampOrder();

    private final WriteStamp writeStamp;
    private final String payload;
    private final DependencyCheck dependencyCheck;

    private Write(long acceptingServerTimestamp, ServerId acceptingServerId, String payload, long commitTimestamp, DependencyCheck dependencyCheck) {
        this.writeStamp = new WriteStamp(acceptingServerTimestamp, acceptingServerId, commitTimestamp);
        this.payload = payload;
        this.dependencyCheck = dependencyCheck;
    }

    public static Write newProposedWrite(String payload, DependencyCheck dependencyCheck) {
        return new Write(Long.MAX_VALUE, null, payload, Long.MAX_VALUE, dependencyCheck);
    }

    public static Write newTentativeWrite(long acceptingServerTimestamp, ServerId acceptingServerId, String payload, DependencyCheck dependencyCheck) {
        return new Write(acceptingServerTimestamp, acceptingServerId, payload, Long.MAX_VALUE, dependencyCheck);
    }

    public WriteStamp writeStamp() {
        return writeStamp;
    }

    public String payload() {
        return payload;
    }

    public DependencyCheck dependencyCheck() {
        return dependencyCheck;
    }

    public boolean isTentative() {
        return !isCommitted();
    }

    public boolean isCommitted() {
        return writeStamp.commitTimestamp() > 0;
    }

    @Override
    public String toString() {
        return payload + " [" + writeStamp + "]";
    }

    public int compareTo(Write that) {
        if (this.writeStamp.compareTo(that.writeStamp()) < 0) {
            return -1;
        } else if (this.writeStamp == that.writeStamp()) {
            return this.payload.compareTo(that.payload());
        } else {
            return 1;
        }
    }

    private static class ByCommittedTimestampOrder implements Comparator<Write> {

        @Override
        public int compare(Write w1, Write w2) {
            if (w1.isCommitted() && w2.isTentative()) {
                return -1;
            } else if (w1.isTentative() && w2.isCommitted()) {
                return 1;
            } else {
                // either both committed or both tentative
                return w1.compareTo(w2);
            }
        }

    }

}
