import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bennett on 3/13/15.
 */
public class Server {

    private final ServerId id;
    private final boolean primary;

    private long logicalClock;
    private WriteLog writeLog;
    private VersionVector versionVector; // C timestamp vector?
    private TupleStore tupleStore;

    // TODO mutable list of known servers; initialize with self

    private Server(boolean primary) {
        this.primary = primary;
        this.id = null;
        this.logicalClock = 0;
        this.writeLog = new WriteLog();
        this.versionVector = new VersionVector();

        try {
            this.tupleStore = new TupleStore();
        } catch (ClassNotFoundException e) {
            System.err.println("BOOM!");
        }

        create();
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

    private void receiveWrites(Set<Write> writeSet, String type) {
        switch (type) {
            case "client":
                receiveClientWrites(writeSet);
                break;
            case "server":
                receiveServerWrites(writeSet);
                break;
            default:
                break;
        }
    }

    private void receiveClientWrites(Set<Write> writeSet) {
        if (writeSet.isEmpty()) {
            return;
        }
        tick();
        Write proposedWrite = writeSet.iterator().next();
        Write write = Write.newTentativeWrite(logicalClock, id, proposedWrite.payload(), proposedWrite.dependencyCheck());
        writeLog.append(write);
        new BayouWrite(
            write.payload(),
            write.dependencyCheck(),
            new MergeProc()
        );
    }

    private void receiveServerWrites(Set<Write> writeSet) {
        if (writeSet.isEmpty()) {
            return;
        }
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

    public void create() {
        Set<Write> writeSet = new TreeSet<>(Write.COMMITTED_TIMESTAMP_ORDER);
        writeSet.add(newCreationWrite("foo"));
        receiveWrites(writeSet, "client");
    }

    public static Write newCreationWrite(String alias) {
        String update = String.format("insert into servers values('%s')", alias);
        String dependencyCheckQuery = String.format("select count(*) from servers where alias=\"%s\"", alias);
        DependencyCheck dependencyCheck = new DependencyCheck(dependencyCheckQuery, 0);
        return Write.newProposedWrite(update, dependencyCheck);
    }

    public static void main(String[] args) {
        System.out.println("Starting server...");
        Server.newPrimaryServer();
    }

}
