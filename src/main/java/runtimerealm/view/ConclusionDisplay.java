package runtimerealm.view;

import runtimerealm.model.characters.Character;

import static runtimerealm.utils.Utils.getConsolePrompt;
import static runtimerealm.view.Animations.animateLine;
import static runtimerealm.view.Animations.animateText;

public class ConclusionDisplay {
    public static void fightConclusion(Character player, Character npc) {
        if (player.isAlive()) {
            animateLine(25, "🏆", 50);
            getConsolePrompt("\n🏆 Congratulations! You have triumphed over " + npc.getName() + ".");
            animateText("You stand victorious as your foe lies defeated.", 100);
        } else {
            animateLine(25, "☠️", 50);
            getConsolePrompt("\n☠️ Alas, you have been vanquished by " + npc.getName() + ".");
            animateText("As darkness closes in, you ponder your defeat...", 100);
        }
    }
}
