/**
 * Created by bennett on 3/13/15.
 */
public class Server {

    private final ServerId id;

    private long logicalClock;
    private WriteLog writeLog;

    // TODO mutable list of known servers; initialize with self

    public Server() {
        this.id = null;
        this.logicalClock = 0;
        this.writeLog = new WriteLog();
        create();
    }

    public ServerId id() {
        return id;
    }

    public long logicalClock() {
        return logicalClock;
    }

    public void create() {
        Write creationWrite = Write.newCreationWrite(logicalClock, id);
        writeLog.append(creationWrite);

        System.out.println("write log:\n" + writeLog);

        // anti-entropy with a random known server (with self for primary)
    }

    public static void main(String[] args) {
        System.out.println("Starting server...");
        new Server();
    }

}
