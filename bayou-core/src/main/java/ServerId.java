/**
 * Created by bennett on 3/13/15.
 */
public class ServerId implements Comparable<ServerId> {

    private final long timestamp;
    private final ServerId acceptingServerId;
    private final String prefix;

    public ServerId(long timestamp, ServerId acceptingServerId, String prefix) {
        this.timestamp = timestamp;
        this.acceptingServerId = acceptingServerId;
        this.prefix = null;
    }

    public ServerId(long timestamp, ServerId acceptingServerId) {
        this(timestamp, acceptingServerId, null);
    }

    public long timestamp() {
        return timestamp;
    }

    public ServerId acceptingServerId() {
        return acceptingServerId;
    }

    public String toString() {
        return "<" +
            timestamp + "," +
            (prefix != null ? prefix + "," : "") +
            (acceptingServerId != null ? acceptingServerId + "," : "") +
            ">";
    }

    public int compareTo(ServerId that) {
        if (this.timestamp == that.timestamp && this.acceptingServerId.equals(that.acceptingServerId())) {
            return 0;
        } else if (this.timestamp < that.timestamp()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof ServerId))
            return false;
        ServerId otherServerId = (ServerId) other;
        return
                (timestamp == otherServerId.timestamp())
                        && ((acceptingServerId == null)
                        ? otherServerId.acceptingServerId() == null
                        : acceptingServerId.equals(otherServerId.acceptingServerId()));
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + (int)(timestamp^(timestamp>>>32));
        hash = hash * 31 + (acceptingServerId == null ? 0 : acceptingServerId.hashCode());
        return hash;
    }

}
