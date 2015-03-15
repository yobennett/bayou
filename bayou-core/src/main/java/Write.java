import java.util.Comparator;

/**
 * Created by bennett on 3/14/15.
 */
public class Write implements Comparable<Write> {

    public static final Comparator<Write> COMMITTED_TIMESTAMP_ORDER = new ByCommittedTimestampOrder();

    private final long commitTimestamp;
    private final long acceptingServerTimestamp;
    private final ServerId acceptingServerId;
    private final String payload;

    private Write(long commitTimestamp, long acceptingServerTimestamp, ServerId acceptingServerId, String payload) {
        this.commitTimestamp = commitTimestamp;
        this.acceptingServerTimestamp = acceptingServerTimestamp;
        this.acceptingServerId = acceptingServerId;
        this.payload = payload;
    }

    public static Write newTentativeWrite(long acceptingServerTimestamp, ServerId acceptingServerId, String payload) {
        return new Write(Long.MIN_VALUE, acceptingServerTimestamp, acceptingServerId, payload);
    }

    public static Write newCommittedWrite(long commitTimestamp, long acceptingServerTimestamp, ServerId acceptingServerId, String payload) {
        return new Write(commitTimestamp, acceptingServerTimestamp, acceptingServerId, payload);
    }

    public static Write newCreationWrite(long acceptingServerTimestamp, ServerId acceptingServerId) {
        return Write.newTentativeWrite(acceptingServerTimestamp, acceptingServerId, "CREATE");
    }

    public long commitTimestamp() {
        return commitTimestamp;
    }

    public long acceptingServerTimestamp() {
        return acceptingServerTimestamp;
    }

    public ServerId acceptingServerId() {
        return acceptingServerId;
    }

    public String payload() {
        return payload;
    }

    public boolean isTentative() {
        return !isCommitted();
    }

    public boolean isCommitted() {
        return commitTimestamp > 0;
    }

    @Override
    public String toString() {
        return payload +
                " [committed at: " + commitTimestamp +
                ", accepted at: " + acceptingServerTimestamp +
                " accepted by: " + acceptingServerId + "]";
    }

    public int compareTo(Write that) {
        if (this.commitTimestamp < that.commitTimestamp()) {
            return -1;
        } else if (this.commitTimestamp == that.commitTimestamp()) {
            if ((this.acceptingServerId == null && that.acceptingServerId() == null) ||
                (this.acceptingServerId != null && this.acceptingServerId.equals(that.acceptingServerId()))) {
                if (this.acceptingServerTimestamp < that.acceptingServerTimestamp()) {
                    return -1;
                } else if (this.acceptingServerTimestamp == that.acceptingServerTimestamp()) {
                    return this.payload.compareTo(that.payload());
                } else {
                    return 1;
                }
            } else {
                return 0;
            }
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
