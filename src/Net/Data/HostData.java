package Net.Data;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostData {
    public InetAddress address;

    public HostData() {
        try {
            address = InetAddress.getLocalHost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HostData(InetAddress address) {
        this.address = address;
    }

    public HostData(String payload) throws InvalidHostDataStringException, UnknownHostException {
        String[] keyValue = payload.split("=");
        if (keyValue.length != 2) {
            return;
        }

        String key = keyValue[0];
        String value = keyValue[1];
        if(key.equals("tictactoe-host")) {
            address = InetAddress.getByName(value);
        }
        throw new InvalidHostDataStringException();
    }



    public static String prepare(InetAddress address) {
        return "tictactoe-host=" + address.getHostAddress();
    }

    @Override
    public String toString() {
        return prepare(address);
    }
}
