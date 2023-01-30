package Player;

import Game.TicTacToe;
import Net.Data.HostData;
import Net.GameDataPublisher;
import Net.GameDataReceiver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import static java.net.InetAddress.getLocalHost;

public class Player {
    private String name;

    public String getName() {
        return name;
    }

    public Player(String name)
    {
        this.name = name;
    }
}
