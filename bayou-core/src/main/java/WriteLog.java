import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bennett on 3/16/15.
 */
public class WriteLog {

    private Set<Write> writeLog;

    public WriteLog() {
        this.writeLog = new TreeSet<>(Write.COMMITTED_TIMESTAMP_ORDER);
    }

    public void append(Write write) {
        System.out.println("Appending: " + write);
        writeLog.add(write);
    }

    @Override
    public String toString() {
        return writeLog.toString();
    }

}
