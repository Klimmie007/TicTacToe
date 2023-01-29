package Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class GameDataReceiver {

    /**
     * In IPv4, any address between 224.0.0.0 to 239.255.255.255
     * can be used as a multicast address
     */
    public static final String MULTICAST_ADDRESS = "230.0.0.0";
    public static final int MULTICAST_PORT = 4446;
    public static final int SOCKETSERVER_PORT = 4447;


    protected MulticastServer multicastServer;
    protected SocketServer socketServer;

    private Set<IHostsSubscriber> hostsSubscribers;
    private Set<IMessageSubscriber> messageSubscribers;
    private Set<InetAddress> foundHosts;
    private String message;

    public GameDataReceiver() {
        hostsSubscribers = new HashSet<>();
        messageSubscribers = new HashSet<>();
        foundHosts = new HashSet<>();

        multicastServer = new MulticastServer();
        new Thread(multicastServer).start();

    }


    public void startSocketServerFor(InetAddress acceptIncomingDataFrom) {
        socketServer = new SocketServer(acceptIncomingDataFrom);
        new Thread(socketServer).start();
    }

    public void stopSocketServer() throws IOException {
        if(socketServer != null) {
            if(!socketServer.serverSocket.isClosed()) {
                socketServer.stop();
            }
            socketServer = null;
        }
    }



    private class MulticastServer implements Runnable {
        private MulticastSocket socket;
        protected byte[] buff = new byte[256];

        @Override
        public void run() {
            InetAddress group;
            try {
                socket = new MulticastSocket(MULTICAST_PORT);
                group = InetAddress.getByName(MULTICAST_ADDRESS);
                socket.joinGroup(group);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            while (true) {
                DatagramPacket packet = new DatagramPacket(buff, buff.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String received = new String(packet.getData(), 0, packet.getLength());

                String[] keyValue = received.split("=");
                if (keyValue.length != 2) {
                    return;
                }
                String key = keyValue[0];
                String value = keyValue[1];

                if (key.equals("host")) {
                    try {
                        InetAddress host = InetAddress.getByName(key);
                        if (host == null
                            || foundHosts.contains(host)
                            || host.equals(InetAddress.getLocalHost())) {

                            return;
                        }
                        foundHosts.add(host);
                        notifyHostsChanged();
                    } catch (UnknownHostException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private class SocketServer implements Runnable {
        private InetAddress accept;
        private ServerSocket serverSocket;
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public SocketServer(InetAddress accept) {
            this.accept = accept;
        }

        public void stop() throws IOException {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        }

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(SOCKETSERVER_PORT);

                while(!serverSocket.isClosed()) {
                    clientSocket = serverSocket.accept();
                    if(!clientSocket.getInetAddress().equals(accept)) {
                        continue;
                    }

                    clientSocket.setSoTimeout(5*60*1000); // 5 min
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    while(clientSocket.isConnected()) {
                        String value = in.readLine();
                        message = value;
                        notifyMessageReceived();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void notifyHostsChanged() {
        hostsSubscribers.forEach(subscriber -> subscriber.hostsChanged(foundHosts));
    }

    private void notifyMessageReceived() {
        messageSubscribers.forEach(subscriber -> subscriber.messageReceived(message));
    }


    public void subscribe(IHostsSubscriber subscriber) {
        hostsSubscribers.add(subscriber);
    }

    public void subscribe(IMessageSubscriber subscriber) {
        messageSubscribers.add(subscriber);
    }

    public void unsubscribe(IHostsSubscriber subscriber) {
        hostsSubscribers.remove(subscriber);
    }

    public void unsubscribe(IMessageSubscriber subscriber) {
        messageSubscribers.remove(subscriber);
    }
}
