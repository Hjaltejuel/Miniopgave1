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
        Queue<DatagramPacket> packetQueue = new Queue<>();

            Thread receiveThread = new Thread(() -> {
                try {
                    DatagramSocket receiveSocket = new DatagramSocket(7007);

                    while(true){
                        //Receive, make reply, add it to queue.
                        byte[] buffer = new byte[10000];
                        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                        receiveSocket.receive(request);
                        request.setPort(7009);
                        packetQueue.enqueue(request);

                    }
                }
                catch (SocketException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

        Thread replyThread = new Thread(() -> {
            try {
                DatagramSocket replySocket = new DatagramSocket();
                //Keep taking from the priority queue, and sending the packet
                while (true) {
                    if(!packetQueue.isEmpty()) {
                        DatagramPacket packet = packetQueue.dequeue();
                        replySocket.send(packet);
                    }
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        receiveThread.start();
        replyThread.start();
        }
}

