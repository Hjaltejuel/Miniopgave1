import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Michelle on 9/13/2017.
 */
public class UDPPacketLossClient {
     public static void main(String args[]) {
         // args give packetsize, numberofpackets and frequency
         int packetSize = Integer.parseInt(args[0]);
         int numberOfPackets = Integer.parseInt(args[1]);
         int Frequency = Integer.parseInt(args[2]);

        //Make a new thread for sending packages
         Thread t = new Thread(new Runnable() {
             @Override
             public void run() {
                 DatagramSocket sendSocket = null;
                 try {
                     sendSocket = new DatagramSocket();
                     InetAddress aHost = InetAddress.getByName("localhost");
                     int serverPort = 7007;
                     byte[] m = new byte[packetSize];
                     //Change the first part of the byte array to be a sequence number for detecting duplicates
                     for(int i = 0; i< numberOfPackets; i++){
                         String s = ""+i;
                         byte[] sm = s.getBytes();
                         for(int j = 0; j < sm.length; j++){
                             m[j] = sm[j];
                         }
                         //send the packet and make the thread sleep for the argument time
                         DatagramPacket request = new DatagramPacket(m, packetSize, aHost, serverPort);
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
         //Create a recieving thread
         Thread q = new Thread(new Runnable() {
             @Override
             public void run() {
                 int i = 0;
                 int k = 0;
                 try {
                     //Make a new socket and set the port to 7009, set timeout so we can stop recieving
                     DatagramSocket receiveSocket = new DatagramSocket(7009);
                     receiveSocket.setSoTimeout(2000);
                     //Create a hashet for duplicate detection
                     HashSet<Integer> duplicatorCheck = new HashSet<Integer>();
                     //Keep on recieving
                     while(true) {
                         byte[] buffer = new byte[packetSize];
                         DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                         receiveSocket.receive(reply);
                         //K measures number of recieved packages, used to measure package loss
                         k++;
                         int check = Integer.parseInt(new String(reply.getData()).trim());
                         //check to see if there are any duplicates
                         if(duplicatorCheck.contains(check)){i++;}
                         duplicatorCheck.add(check);
                     }
                 } catch (SocketTimeoutException e) {
                     //When we stop recieving
                     System.out.println("number of duplicates : " +i);
                     System.out.println("procent number of duplicates : " + ((double)i/(double)numberOfPackets)*100);
                     int lost = (numberOfPackets - (k-i));
                     System.out.println("number of lost packages : " + lost);
                     System.out.println("procent of number of lost packages : " + (((double)lost/(double)numberOfPackets)*100));

                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         });
         //Start the two threads
         t.start();
         q.start();

    }

}
