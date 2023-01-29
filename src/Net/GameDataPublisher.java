package Net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class GameDataPublisher {
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buff;

    public void sendMessage(String message, InetAddress address) throws IOException, CouldNotConnectToSocketException {
        Socket socket = new Socket(address.getHostName(), GameDataReceiver.SOCKETSERVER_PORT);
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        if (socket == null || os == null) {
            throw new CouldNotConnectToSocketException();
        }

        os.writeBytes( message + "\n" );
        os.close();
        socket.close();
    }

    public void multicast(String message) throws IOException {
        socket = new DatagramSocket();
        group = InetAddress.getByName(GameDataReceiver.MULTICAST_ADDRESS);
        buff = message.getBytes();

        DatagramPacket packet = new DatagramPacket(buff, buff.length, group, GameDataReceiver.MULTICAST_PORT);
        socket.send(packet);
        socket.close();
    }
}
