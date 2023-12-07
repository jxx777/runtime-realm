package runtimerealm.view;

import runtimerealm.model.characters.Character;
import runtimerealm.utils.Color;

import static runtimerealm.utils.Utils.getConsolePrompt;

public class HealthDisplay {
    static String getHealthColor(int currentHealth, int maxHealth) {
        double healthRatio = (double) currentHealth / maxHealth;
        if (healthRatio < 0.25) return Color.RED.getCode();
        else if (healthRatio < 0.5) return Color.YELLOW.getCode();
        else return Color.GREEN.getCode();
    }

    public static void displayHealthBar(int currentHealth, int maxHealth) {
        int barLength = 20; // Length of the health bar
        int healthPortion = (int) (((double) currentHealth / maxHealth) * barLength);
        healthPortion = Math.max(0, healthPortion); // Ensure healthPortion is not negative

        String healthBar = "[" + "=".repeat(healthPortion) + " ".repeat(barLength - healthPortion) + "]";
        String color = getHealthColor(currentHealth, maxHealth);
        getConsolePrompt(color + healthBar + " " + Math.max(0, currentHealth) + "/" + maxHealth + Color.RESET.getCode());
    }

    public static void healthStatus(Character player, Character npc) {
        System.out.print(player.getName() + " health 💖: ");
        displayHealthBar(player.getHealth(), player.getMaxHealth());
        System.out.print(npc.getName() + " health 💀: ");
        displayHealthBar(npc.getHealth(), npc.getMaxHealth());
    }
}