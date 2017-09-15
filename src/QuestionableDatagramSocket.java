import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Hjalte on 10-09-2017.
 */
public class QuestionableDatagramSocket extends DatagramSocket {
    //Saves the datagram to be reversed
    DatagramPacket saved = null;

    public QuestionableDatagramSocket() throws SocketException {
        super();
    }

    @Override
    public void send(DatagramPacket a) {
        //If we are on a second datagram in a reverse series
        if (saved!=null) {
            try {
                //send the datagrams in reverse order
                super.send(a);
                super.send(saved);
                saved = null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //50% chance to do something with the datagramPacket
            Random random = new Random();
            if (random.nextBoolean() == true) {
                int choosen = random.nextInt(3);
                //33% chance to do either a discard, reorder or a duplicate
                if (choosen == 0) {
                    //do nothing
                } else if (choosen == 1) {
                    try {
                        //Send twice
                        super.send(a);
                        super.send(a);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //NOTE: reverse will only send if 2 of more datagrams are being send, as it needs something to reverse the order with
                    //If less than 2 datagrams are send and we are supposed to reverse, reverse will work as a discard
                } else if (choosen == 2) {
                    //look for reverse
                    saved = a;
                }

            } else try {
                //If none of the above actions are triggered send as normal
                super.send(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}


