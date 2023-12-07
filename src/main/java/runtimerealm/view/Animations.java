package runtimerealm.view;

import static runtimerealm.utils.Utils.getConsolePrompt;
import static runtimerealm.utils.Utils.sleep;


public class Animations {
    public static void animateLine(int lineLength, String symbol, int milliseconds) {
        for (int i = 0; i < lineLength; i++) {
            System.out.print(symbol + " ");
            sleep(milliseconds);
        }
        getConsolePrompt(" ");
    }

    static void animateText(String text, int milliseconds) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            sleep(milliseconds);
        }
        getConsolePrompt(" ");
    }
}