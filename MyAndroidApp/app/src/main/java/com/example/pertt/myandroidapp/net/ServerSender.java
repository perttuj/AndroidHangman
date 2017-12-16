package com.example.pertt.myandroidapp.net;

import server.common.Constants;
import server.common.ServerMessageTypes;

/**
 * Class for handling formatting when
 * communicating with server
 * Created by Perttu on 2017-12-14.
 */

public class ServerSender {

    private ServerSender() {
    }

    public static String sendGuess(String s) {
        String res = SocketConnection.sendCommand(ServerMessageTypes.GUESS + Constants.DELIMETER + s);
        String fixed = formatMsg(res);
        return fixed;
    }
    public static String newGame() {
        String res = SocketConnection.sendCommand(ServerMessageTypes.NEWWORD.toString());
        String fixed = formatMsg(res);
        return fixed;
    }

    /**
     * Extracts the message received (without type)
     * @param entireMsg the original format message from the server
     * @return  the message without the type included
     */
    private static String formatMsg(String entireMsg) {
        String[] message = entireMsg.split(Constants.DELIMETER);
        return message[Constants.MESSAGE_INDEX];
        /*
        if (ServerMessageTypes.valueOf(message[Constants.TYPE_INDEX]) == ServerMessageTypes.RESPONSE) {
            return message[Constants.MESSAGE_INDEX];
        } else if (ServerMessageTypes.valueOf(message[Constants.TYPE_INDEX]) == ServerMessageTypes.DISCONNECT) {
            return "DISCONNECTED FROM SERVER";
        } else {
            return message[Constants.MESSAGE_INDEX];
        }*/
    }
}
