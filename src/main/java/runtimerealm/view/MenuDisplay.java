package runtimerealm.view;

import static runtimerealm.utils.Color.*;
import static runtimerealm.utils.Utils.getConsolePrompt;

public class MenuDisplay {
    public static void displayGameChoices() {

        getConsolePrompt("DEVELOPER NOTES");
        getConsolePrompt(YELLOW.colorize("NOTE: Yellow color: buggy / not implemented properly yet"));
        getConsolePrompt(RED.colorize("NOTE: Red color: not implemented at all"));
        getConsolePrompt("--------------------");

        getConsolePrompt("Where would you like to next?");
        getConsolePrompt(RESET.underline("\nTHE ARENA - Wacky RNG battles"));
        getConsolePrompt("1. Arena versus NPC");
        getConsolePrompt(YELLOW.colorize("2. Arena versus Player (PVP)"));

        getConsolePrompt(RESET.underline("\nThe Runtime Realm"));
        getConsolePrompt(RED.colorize("3. Explore the world (COMING SOON, at some point...)"));

        getConsolePrompt("--------------------");
        getConsolePrompt("9. Toggle Sound");
        getConsolePrompt("0. Exit Game");
    }
}