import java.util.Map;
import java.util.Set;

/**
 * Created by bennett on 3/13/15.
 */
public class Server {

    private final ServerId id;
    private final boolean primary;

    private long logicalClock;
    private WriteLog writeLog;
    private VersionVector versionVector; // C timestamp vector?

    // TODO mutable list of known servers; initialize with self

    private Server(boolean primary) {
        this.primary = primary;
        this.id = null;
        this.logicalClock = 0;
        this.writeLog = new WriteLog();
        this.versionVector = new VersionVector();
        create();
    }

    private Server() {
        this(false);
    }

    public static Server newPrimaryServer() {
        return new Server(true);
    }

    public ServerId id() {
        return id;
    }

    public long logicalClock() {
        return logicalClock;
    }

    public VersionVector versionVector() {
        return versionVector;
    }

    private void tick() {
        logicalClock = Math.max(System.currentTimeMillis(), logicalClock + 1);
    }

    public boolean isPrimary() {
        return primary;
    }

    public void appendWrite(Write write) {
        System.out.println("Appending: " + write);
        writeLog.append(write);
    }

    private void receiveWrites(Set<Write> writeSet, String type) {
        switch (type) {
            case "client":
                receiveClientWrites(writeSet);
                break;
            case "server":
                break;
            default:
                break;
        }
    }

    private void receiveClientWrites(Set<Write> writeSet) {
        if (writeSet.size() > 0) {
            tick();
            Write write = writeSet.iterator().next();
            appendWrite(write);
            // new BayouWrite();
        }
    }

    private void receiveServerWrites(Set<Write> writeSet) {
        if (writeSet.size() > 0) {
            Write write = writeSet.iterator().next();
            // find insertion point using write.id
            // roll back tuple store
            // insert writeSet at insertion point
            // loop through previous writes after insertion point
                // new BayouWrite() per write
            // get the last write from the writeSet

            // update logical clock
            // logicalClock = Math.max(logicalClock, write.writeStamp().acceptingServerTimestamp());
        }
    }

    public void create() {
        Write creationWrite = Write.newCreationWrite(logicalClock, id);
        appendWrite(creationWrite);
    }

    public static void main(String[] args) {
        System.out.println("Starting server...");
        Server.newPrimaryServer();
    }

}
