/**
 * Created by bennett on 3/16/15.
 */
public class AntiEntropySession {

    private final Server server1;
    private final Server server2;

    public AntiEntropySession(Server server1, Server server2) {
        this.server1 = server1;
        this.server2 = server2;
        run();
    }

    private void run() {
        exchangeWrites(server1, server2);
        //exchangeWrites(server2, server1);
    }

    private void exchangeWrites(Server from, Server to) {
        long maxAcceptTimestamp = to.versionVector().largestAcceptTimestamp(from.id());
        System.out.println("maxAcceptTimestamp: " + maxAcceptTimestamp);
    }

}
