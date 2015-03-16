/**
 * Created by bennett on 3/16/15.
 */
public class WriteStamp implements Comparable<WriteStamp> {

    private final long commitTimestamp;
    private final long acceptingServerTimestamp;
    private final ServerId acceptingServerId;

    public WriteStamp(long acceptingServerTimestamp, ServerId acceptingServerId, long commitTimestamp) {
        this.acceptingServerTimestamp = acceptingServerTimestamp;
        this.acceptingServerId = acceptingServerId;
        this.commitTimestamp = commitTimestamp;
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

    @Override
    public String toString() {
        return "accepted at: " + acceptingServerTimestamp +
                ", accepted by: " + acceptingServerId +
                ", committed at: " + commitTimestamp;
    }

    public int compareTo(WriteStamp that) {
        if (this.commitTimestamp < that.commitTimestamp()) {
            return -1;
        } else if (this.commitTimestamp == that.commitTimestamp()) {
            if ((this.acceptingServerId == null && that.acceptingServerId() == null) ||
                    (this.acceptingServerId != null && this.acceptingServerId.equals(that.acceptingServerId()))) {
                if (this.acceptingServerTimestamp < that.acceptingServerTimestamp()) {
                    return -1;
                } else if (this.acceptingServerTimestamp == that.acceptingServerTimestamp()) {
                    return 0;
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

}
