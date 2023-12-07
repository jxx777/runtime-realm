package runtimerealm;

import runtimerealm.model.CharacterType;
import runtimerealm.model.characters.Character;
import runtimerealm.model.characters.Necromancer;
import runtimerealm.model.characters.Warrior;

import runtimerealm.view.BattlePrompt;
import runtimerealm.view.ConclusionDisplay;
import runtimerealm.view.MenuDisplay;
import runtimerealm.utils.GameConfig;
import runtimerealm.utils.Sounds;

import java.util.Arrays;

import static runtimerealm.game.ArenaLoop.*;
import static runtimerealm.utils.Utils.*;

/**
 * The Main class serves as the entry point for the runtimerealm game application.
 * It initializes the game settings, manages the main game loop, and facilitates transitions
 * between different game modes such as arena combat and world exploration.
 * It also allows for toggling game settings like sound.
 */
public class Main {
    /**
     * The main method that starts the execution of the game.
     * It refreshes the CLI, it initializes the game configuration, starts the main game loop, and handles user input
     * to navigate through different game options such as entering the arena, exploring the world,
     * toggling sound, and exiting the game.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        clearScreen();
        GameConfig config = GameConfig.getInstance(); // Sets up the config instance
        config.setSoundEnabled(false); // setter for config
        boolean gameRunning = true;
//        createCharacter();
        Sounds.playSound("main_screen", true);

        System.out.println(config);

        while (gameRunning) {
            // TODO: Let user create class object in runtime, maybe down the line persist it in a Db. Also do game states, at some point..
            // createCharacter();
            Necromancer player = new Necromancer("Player", 150, 150);
            Warrior playerTwo = new Warrior("Player", 150, 12); // Instantiating this for the PVP, not really fleshed out

            MenuDisplay.displayGameChoices();
            int choice = Integer.parseInt(getPlayerChoice());
            switch (choice) {
                case 1 -> enterArena(player);
                case 2 -> enterArenaPvP(player, playerTwo); // Functional, not properly implemented view layer
                case 3 -> exploreWorld(player); // Not implemented
                case 9 -> toggleSound(config); // Acts on config singleton

                // Exit Game
                case 0 -> {
                    gameRunning = false;
                    getConsolePrompt("Exiting game...");
                }
                default -> getConsolePrompt("Invalid choice. Please select again.");
            }
        }
        getInput.close();
        Sounds.stopCurrentSound();
    }

    /**
     * Transitions the game to the arena mode where the player combats a randomly generated enemy.
     * It sets up the combat environment, initiates a battle, and displays the conclusion after the battle.
     *
     * @param player The player's character, a Necromancer, entering the arena.
     */
    private static void enterArena(Necromancer player) { // TODO: Pass runtime-selected class
        Sounds.playSound("arena_screen", true);
        // Versus NPC
        Character npc = getRandomEnemy();
        BattlePrompt.versusIntroduction(npc);
        arenaLoopVersusNpc(player, npc);
        ConclusionDisplay.fightConclusion(player, npc);
    }

    /**
     * Transitions the game to the arena mode where the player combats another player, in the same terminal session.
     * It sets up the combat environment, initiates a battle, and displays the conclusion after the battle.
     * <p>
     * Eventually, players will be able to choose their class during runtime init
     *
     * @param player Player 1, entering the arena. (Necromancer)
     * @param playerTwo Player 2, entering the arena. (Warrior)
     */
    private static void enterArenaPvP(Necromancer player, Warrior playerTwo) { // TODO: Pass runtime-selected class
        Sounds.playSound("arena_screen", true);
        // PVP
        BattlePrompt.versusIntroduction(playerTwo);
        arenaLoopVersusPlayer(playerTwo, player);
        ConclusionDisplay.fightConclusion(player, playerTwo);
    }

    /**
     * Placeholder for the world exploration mode of the game.
     * This method is intended to handle the logic for the player to explore the game world.
     *
     * @param player The player's character, potentially exploring the world.
     */
    private static void exploreWorld(Necromancer player) { // TODO: Pass runtime-selected class
        // TODO: Implement world exploration logic
    }

    /**
     * Toggles the sound settings in the game. Enables or disables game sound based on the current setting.
     *
     * @param config The game configuration used to manage sound settings.
     */
    private static void toggleSound(GameConfig config) {
        config.setSoundEnabled(!config.isSoundEnabled()); // Toggles to opposite boolean condition
        getConsolePrompt("Sound is now " + (config.isSoundEnabled() ? "enabled" : "disabled"));
    }
}