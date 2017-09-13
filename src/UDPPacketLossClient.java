import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by Michelle on 9/13/2017.
 */
public class UDPPacketLossClient {
     public static void main(String args[]) {
         // args give packetsize, numberofpackets and frequency

         int packetSize = Integer.parseInt(args[0]);
         int numberOfPackets = Integer.parseInt(args[1]);
         int Frequency = Integer.parseInt(args[2]);

         Thread t = new Thread(new Runnable() {
             @Override
             public void run() {
                 DatagramSocket sendSocket = null;
                 try {
                     sendSocket = new DatagramSocket();
                     byte[] m = new byte[packetSize];
                     InetAddress aHost = InetAddress.getLocalHost();
                     int serverPort = 7007;
                     for(int i = 0; i< numberOfPackets; i++){
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
                     while(true) {
                         byte[] buffer = new byte[packetSize];
                         DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                         receiveSocket.receive(reply);
                         System.out.println("we have recieved");
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
