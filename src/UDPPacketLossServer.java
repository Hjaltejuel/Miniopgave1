import sun.misc.Queue;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Michelle on 9/13/2017.
 */
public class UDPPacketLossServer {
    public static void main(String args[]){
            // create socket at agreed port
        Queue<DatagramPacket> pq = new Queue<DatagramPacket>();
            Thread recieveThread = new Thread(new Runnable() {
                @Override
            public void run() {
                    DatagramSocket aSocket = null;
                    try {
                        aSocket = new DatagramSocket(7007);

                        while(true){
                            byte[] buffer = new byte[10000];
                            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                            aSocket.receive(request);
                            DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(),
                                    request.getAddress(), 7009);
                            pq.enqueue(reply);

                        }
                    }
                    catch (SocketException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            });
        Thread replyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket replySocket = null;
                try {
                    replySocket = new DatagramSocket();
                    while (true) {
                        if(!pq.isEmpty()) {
                            DatagramPacket dp = pq.dequeue();
                            System.out.println("UDP packet from: " + new String(dp.getAddress().toString()));
                            replySocket.send(dp);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            });
        recieveThread.start();
        replyThread.start();
        }
}

