import java.util.HashMap;
import java.util.Map;

/**
 * Created by bennett on 3/17/15.
 */
public class VersionVector {

    private Map<ServerId,Long> versions;

    public VersionVector() {
        this.versions = new HashMap<ServerId, Long>();
    }

    public Map<ServerId,Long> versions() {
        return versions;
    }

    public long largestAcceptTimestamp(ServerId serverId) {
        if (serverId != null) {
            return versions.get(serverId);
        } else {
            return 0;
        }
    }

}
