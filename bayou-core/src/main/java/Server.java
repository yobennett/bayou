import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bennett on 3/13/15.
 */
public class Server {

    private final ServerId id;
    private final long timestamp;
    private Set<Write> writeLog;

    // TODO mutable list of known servers; initialize with self

    public Server() {
        this.id = null;
        this.timestamp = 0;
        this.writeLog = new TreeSet<Write>(Write.COMMITTED_TIMESTAMP_ORDER);
        create();
    }

    public ServerId id() {
        return id;
    }

    public long timestamp() {
        return timestamp;
    }

    public void create() {
        Write creationWrite = Write.newCreationWrite(timestamp, id);
        writeLog.add(creationWrite);

        System.out.println("write log:\n" + writeLog);

        // anti-entropy with a random known server (with self for primary)
    }

    public static void main(String[] args) {
        System.out.println("Starting server...");
        new Server();
    }

}