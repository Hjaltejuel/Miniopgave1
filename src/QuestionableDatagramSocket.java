import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Hjalte on 10-09-2017.
 */
public class QuestionableDatagramSocket extends DatagramSocket {

    public QuestionableDatagramSocket(int port) throws SocketException {
        super(port);
    }
    public QuestionableDatagramSocket() throws SocketException {
        super();
    }
    public void send(DatagramPacket a)
    {
        String altered = "";
        Random random = new Random();
        if(random.nextBoolean()==true)
        {
            String temp = new String(a.getData()).trim();
            int choosen = random.nextInt(3);
            if(choosen==0){
                altered = discard(temp);
            } else if (choosen == 1){
                altered = reorder(temp);
            } else if (choosen == 2){
                altered = duplicate(temp);
            }
            a.setData(altered.getBytes());
        }
        try {
            super.send(a);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String discard(String discard){
        String[] discardarray = discard.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i <discardarray.length; i++){
            if(i%2 == 0){
                builder.append(discardarray[i]+ " ");
            }
        }
        System.out.println("The String has been cut down to [ " +builder.toString() + " ]");
        return builder.toString();
    }

    public String duplicate(String duplicate){
        String[] duplicatearray = duplicate.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<duplicatearray.length;i++) {
            if(i%2 == 0){
                builder.append(duplicatearray[i] + " ");
                builder.append(duplicatearray[i] + " ");
            } else {
                builder.append(duplicatearray[i] + " ");
            }
        }
        System.out.println("The String has been duplicated to[ "+builder.toString() +" ]");
        return builder.toString();
    }

    public String reorder(String reorder){
        String[] split = reorder.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for(int i = split.length; i>0;i--){
            builder.append(split[i - 1] + " ");

        }
        System.out.println("The String has been reordered to [ "+builder.toString()+ " ]");
        return builder.toString();
    }

}
