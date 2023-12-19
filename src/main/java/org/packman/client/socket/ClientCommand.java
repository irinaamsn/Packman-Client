package org.packman.client.socket;

public interface ClientCommand {
    String sendCommand(String command, String username);
    String sendCommand(String command);
    /*String sendCommandStart(String username);
    String sendCommandGetBestPlayers();
    String sendCommandFinish();
    String sendCommandUpdateMap();
    String sendCommandMove();*/
}
