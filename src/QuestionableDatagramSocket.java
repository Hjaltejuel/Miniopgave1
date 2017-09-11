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
            int choosen = random.nextInt(3);
            if(choosen==0){
                altered = discard(new String(a.getData()));
            } else if (choosen == 1){
                altered = duplicate(new String(a.getData()));
            } else if (choosen == 2){
                altered = reorder(new String(a.getData()));
            }
            System.out.println(altered + " " + 1);
            a.setData(altered.getBytes());

        }

        try {
            super.send(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String discard(String discard){
        System.out.println("i discard");
        String[] discardarray = discard.split(" ");
        String[] choosen = new String[discardarray.length];
        int index = 0;
        for(int i = 0; i <discardarray.length; i++){
            if(i%2 == 1){
                choosen[index] = discardarray[i];
                index++;
            }

        }
        System.out.println(Arrays.toString(choosen));
        return Arrays.toString(choosen);
    }
    public String duplicate(String duplicate){
        System.out.println("i duplicate");
        String[] duplicatearray = duplicate.split(" ");
        String[] choosen = new String[duplicatearray.length*2];
        int index = 0;
        for(int i = 0; i<duplicatearray.length;i++) {
            if(i%2 == 0){
                choosen[index] = duplicatearray[i];
                choosen[index++] = duplicatearray[i];
                index++;
            } else {
                choosen[index] = duplicatearray[i];
            }
        }
        System.out.println(Arrays.toString(choosen) + "med");
        return Arrays.toString(choosen);
    }
    public String reorder(String reorder){
        System.out.println("i reorder");
        String[] reorderarray = reorder.split(" ");
        String[] choosen = new String[reorderarray.length];
        if(reorderarray.length >1) {
            for (int i = 0; i < reorderarray.length; i++) {
                if (i % 2 == 0) {
                    choosen[i] = reorderarray[i+1];
                    choosen[i+1] = reorderarray[i];
                }
            }
            System.out.println(Arrays.toString(choosen)+ "hej");
            return Arrays.toString(choosen);
        } else return Arrays.toString(reorderarray);
    }
}
