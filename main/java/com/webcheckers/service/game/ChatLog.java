package com.webcheckers.service.game;

import com.webcheckers.service.player.*;

import java.util.*;
import java.util.logging.Logger;

public class ChatLog {
    private static final Logger LOG = Logger.getLogger(ChatLog.class.getName());
    private final ArrayList<String> messages;

    public ChatLog() {
        messages = new ArrayList<>();
    }

    public void createMessage(Username user, String message) {
        messages.add(user.toString() + ": " + message);
        LOG.fine("Message " + message + " posted by " + user.toString());
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
}
