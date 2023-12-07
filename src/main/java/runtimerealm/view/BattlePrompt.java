package runtimerealm.view;

import runtimerealm.model.Combatant;
import runtimerealm.model.characters.Character;

import static runtimerealm.utils.Utils.getConsolePrompt;

public class BattlePrompt {
    public static void versusIntroduction(Character npc) {
        getConsolePrompt("You have ventured into the Arena! \n Opponent: " + npc + " (" + npc.getClass().getSimpleName() + ")");
        getConsolePrompt("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
    }

    public static void displayAttack(Combatant self, Combatant target, double damage) {
        String effectiveness = damage >= 20 ? " CRITICAL"
                : damage <= 5 ? " (light hit)."
                : damage <= 10 ? " (moderate hit)."
                : damage <= 15 ? " (heavy hit)."
                : " (devastating hit).";

        System.out.print("⚔️ " + self.getName() + " attacks " + target.getName() + " for " + damage + " damage" + effectiveness);
        getConsolePrompt(" ");
    }

    public static String actionPrompt(Character player, Character npc) {
        StringBuilder prompt = new StringBuilder("\nChoose your action:\n");

        // Add dynamic elements based on the game state
        if (player.getHealth() < player.getMaxHealth() * 0.3) {
            prompt.append("  💀 You are critically wounded! Consider defending or using a defensive ability.\n");
        }
        if (npc.getHealth() < npc.getMaxHealth() * 0.3) {
            prompt.append("  🎯 Enemy is weak! Maybe it's time for a finishing move?\n");
        }

        // Standard action choices
        prompt
                .append("  ⚔️ Attack (A)\n  🛡️ Defend (D)\n  🌟 Use Special Ability:\n")
                .append("    - Offense (CO)\n    - Defense (CD)\n")
                .append("\nEnter your choice: ");

        return prompt.toString();
    }
}