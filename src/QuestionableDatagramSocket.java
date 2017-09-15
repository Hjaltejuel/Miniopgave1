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
    DatagramPacket saved = null;
    boolean reverse = false;

    public QuestionableDatagramSocket() throws SocketException {
        super();
    }

    @Override
    public void send(DatagramPacket a) {
        if (this.reverse == true) {
            reverse(a);
            reverse = false;

        } else {
            Random random = new Random();
            if (random.nextBoolean() == true) {
                int choosen = random.nextInt(3);
                if (choosen == 0) {
                    System.out.println("I DISCARD");
                    //discard
                } else if (choosen == 1) {
                    try {
                        System.out.println("I DUPLICATE");
                        super.send(a);
                        super.send(a);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (choosen == 2) {
                    System.out.println("I REVERSE");
                    reverse(a);
                    this.reverse = true;
                }
            } else try {
                super.send(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void reverse(DatagramPacket a) {
        if (saved == null) {
            saved = a;
        } else {
            try {
                System.out.println("SENDING REVERSE");
                super.send(a);
                super.send(saved);
            } catch (IOException e) {
                e.printStackTrace();
            }
            saved = null;
        }
    }
}


