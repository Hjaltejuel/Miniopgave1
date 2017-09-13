import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by Michelle on 9/13/2017.
 */
public class UDPPacketLossClient {
     public static void main(String args[]) {
         // args give packetsize, numberofpackets and frequency

         ArrayList<Byte> barray = new ArrayList<Byte>();
         int packetSize = Integer.parseInt(args[0]);
         int numberOfPackets = Integer.parseInt(args[1]);
         int Frequency = Integer.parseInt(args[2]);


         Thread t = new Thread(new Runnable() {
             @Override
             public void run() {
                 int x = 0;
                 DatagramSocket sendSocket = null;
                 try {
                     sendSocket = new DatagramSocket();
                     byte[] m = new byte[packetSize];

                     InetAddress aHost = InetAddress.getByName("10.26.8.235");
                     int serverPort = 7007;
                     for(int i = 0; i< numberOfPackets; i++){
                         String s ="[" + x + "]";
                         x++;
                         byte[] sm = s.getBytes();
                         for(int j = 0; j < sm.length; j++){
                             m[j] = sm[j];
                         }
                         DatagramPacket request = new DatagramPacket(m, args[0].length(), aHost, serverPort);
                         sendSocket.send(request);
                         Thread.sleep(Frequency);

                     }
                 } catch (SocketException e) {
                     e.printStackTrace();
                 } catch (UnknownHostException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                     e.printStackTrace();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }

             }
         });
         Thread q = new Thread(new Runnable() {
             @Override
             public void run() {

                 try {
                     DatagramSocket receiveSocket = new DatagramSocket(7009);
                     int i = 0;
                     while(true) {
                         byte[] buffer = new byte[packetSize];
                         DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                         receiveSocket.receive(reply);
                         i++;
                         System.out.println(new String (reply.getData()).trim() );
                     }
                 } catch (SocketException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         });
         t.start();
         q.start();

    }

}
