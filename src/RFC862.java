import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
public class RFC862{
    public static void main(String args[]){
        DatagramSocket dataSocket = null;
        try{
            dataSocket = new DatagramSocket(7007);

            byte[] buffer = new byte[1000];
            while(true){

                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                dataSocket.receive(request);
                DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(),
                        request.getAddress(), request.getPort());

                System.out.println("UDP packet from: " + new String(request.getAddress().toString()));
                dataSocket.send(reply);
            }
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {System.out.println("IO: " + e.getMessage());
        }finally {if(dataSocket != null) dataSocket.close();}
    }
}
