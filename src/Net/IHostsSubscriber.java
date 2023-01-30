package Net;

import Net.Data.HostData;

import java.net.InetAddress;
import java.util.Set;

public interface IHostsSubscriber {
    public void hostsChanged(Set<HostData> payload);
}
