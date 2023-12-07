package runtimerealm.controller;

import runtimerealm.model.Combatant;

import static runtimerealm.utils.Utils.getConsolePrompt;
import static runtimerealm.utils.Utils.getPlayerChoice;
public class CombatController {
    public static void takeTurn(String choice, Combatant self, Combatant target) {
        if (self.isAlive()) {
            handleEffects(self);
            boolean choiceMade = false;
            while (!choiceMade) {
                switch (choice) {
                    case "attack", "a" -> {
                        target.attack(self, target);
                        choiceMade = true;
                    }
                    case "defend", "d" -> {
                        self.defend(self);
                        choiceMade = true;
                    }
                    case "defensive ability", "cd" -> {
                        self.defensiveAbility(self);
                        choiceMade = true;
                    }
                    case "offensive ability", "co" -> {
                        self.offensiveAbility(target);
                        choiceMade = true;
                    }
                    default -> {
                        getConsolePrompt("Invalid action. Please choose again.");
                        choice = getPlayerChoice(); // TODO: Flesh this out
                    }
                }
            }
        }
    }

    // Handles applying damage / healing + decrementing ticks
    private static void handleEffects(Combatant self) {
        self.handleDoTEffect();
        self.handleHoTEffect();
        // self.handleShapeShiftedEffect(); TODO
        // self.handleSnaredEffect(); TODO
    }
}