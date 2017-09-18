import javax.xml.crypto.Data;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;

public class UDPClient{
    public static void main(String args[]) {
        // args give message contents and destination hostname
        QuestionableDatagramSocket aSocket = null;
        try {
            aSocket = new QuestionableDatagramSocket();
            aSocket.setSoTimeout(200);
            InetAddress aHost = InetAddress.getByName(args[args.length-1]);
            int serverPort = 7007;
            for(int i = 0; i<args.length-1;i++){
                byte[] m = args[i].getBytes();
                DatagramPacket request = new DatagramPacket(m, args[i].length(), aHost, serverPort);
                aSocket.send(request);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
        while (true) {
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
           aSocket.receive(reply);
            System.out.println("Reply: " + new String(reply.getData()).trim());
        }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {

        }

    }
}

