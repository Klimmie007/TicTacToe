package Net.Data;

import Player.PlayerDB;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostData {
    public InetAddress address;
    public String hostName;
    public String format;

    public HostData() {
        try {
            address = InetAddress.getLocalHost();
        } catch (IOException e) {
            e.printStackTrace();
        }
        hostName = PlayerDB.player.getName();
        format = "Standard";
    }

    public HostData(InetAddress address, String hostName, String format) {
        this.address = address;
        this.format = format;
        this.hostName = hostName;
    }

    public HostData(String payload) throws InvalidHostDataStringException, UnknownHostException {
        String[] keyValue = payload.split("=|/?");
        if (keyValue.length != 6) {
            return;
        }

        for(int i = 0; i <= 6; i += 2)
        {
            switch(keyValue[i])
            {
                case "tictactoe-host" -> {
                    address = InetAddress.getByName(keyValue[i+1]);
                }
                case "host-name" -> {
                    hostName = keyValue[i+1];
                }
                case "format" -> {
                    format = keyValue[i+1];
                }
            }
        }
        if(hostName == null || format == null || address == null)
        {
            throw new InvalidHostDataStringException();
        }
    }



    public static String prepare(InetAddress address, String hostName, String format) {
        return "tictactoe-host=" + address.getHostAddress() + "?host-name=" + hostName + "?format=" + format;
    }

    @Override
    public String toString() {
        return prepare(address, hostName, format);
    }
}
