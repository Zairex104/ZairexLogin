package com.zairex.login;

import net.fabricmc.api.ClientModInitializer;

import java.util.Locale;
import java.util.Set;

public class ZairexLogin implements ClientModInitializer {
    public static final Set<String> COMMANDS = Set.of("login", "l", "reg", "register", "changepassword");

    @Override
    public void onInitializeClient() {
        // Mixin does all the work.
    }

    /**
     * Returns the index where the password part starts (first char after the first space),
     * or -1 if the text is not one of the password commands.
     */
    public static int passwordStart(String text) {
        if (text == null || text.isEmpty() || text.charAt(0) != '/') return -1;
        int space = text.indexOf(' ');
        if (space < 0) return -1;
        String cmd = text.substring(1, space).toLowerCase(Locale.ROOT);
        return COMMANDS.contains(cmd) ? space + 1 : -1;
    }
}
