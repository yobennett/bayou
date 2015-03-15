/**
 * Created by bennett on 3/14/15.
 */

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

public class ServerIdTest {

    @Test
    public void equalAcceptingTimestampAndNullAcceptingServerId() {
        ServerId id1 = new ServerId(12345, null);
        ServerId id2 = new ServerId(12345, null);
        assertThat(id1, is(equalTo(id2)));
    }

    @Test
    public void equalAcceptingTimestampAndAcceptingServerId() {
        ServerId id1 = new ServerId(12345, null);
        ServerId id2 = new ServerId(56789, id1);
        ServerId id3 = new ServerId(56789, id1);
        assertThat(id2, is(equalTo(id3)));
    }

    @Test
    public void orderByAcceptingTimestamp() {
        ServerId id1 = new ServerId(12345, null);
        ServerId id2 = new ServerId(56780, id1);
        ServerId id3 = new ServerId(56789, id1);
        assertThat(id2, lessThan(id3));
    }

}
