package runtimerealm.game;

import runtimerealm.model.characters.Character;
import runtimerealm.model.characters.*;
import runtimerealm.model.CharacterType;


import runtimerealm.view.Animations;
import runtimerealm.view.BattlePrompt;
import runtimerealm.view.HealthDisplay;

import static runtimerealm.controller.CombatController.takeTurn;
import static runtimerealm.utils.Utils.*;
import static runtimerealm.view.Animations.animateLine;

public class ArenaLoop {
    public static void arenaLoopVersusNpc(Character player, Character npc) {
        int roundNumber = 0;
        while (npc.isAlive() && player.isAlive()) {

            System.out.print(BattlePrompt.actionPrompt(player, npc));

            String playerChoice = getPlayerChoice();
            takeTurn(playerChoice, player, npc);
            sleep(1000); // 1 second pause after player's turn

            String npcChoice = getNonPlayingCharacterChoice();
            takeTurn(npcChoice, npc, player);
            sleep(1000); // 1 second pause after NPC's turn

            turnSummary(player, npc, ++roundNumber);
        }
    }
    public static void arenaLoopVersusPlayer(Character player, Character playerTwo) {
        int roundNumber = 0;
        while (playerTwo.isAlive() && player.isAlive()) {

            System.out.print(BattlePrompt.actionPrompt(player, playerTwo));

            String playerChoice = getPlayerChoice();
            takeTurn(playerChoice, player, playerTwo);
            sleep(1000); // 1 second pause after player's turn

            getConsolePrompt(" ");

            String playerTwoChoice = getPlayerChoice();
            takeTurn(playerTwoChoice, player, playerTwo);
            sleep(1000); // 1 second pause after NPC's turn

            turnSummary(player, playerTwo, ++roundNumber);
        }
    }

    private static void turnSummary(Character player, Character npc, int roundNumber) {
        getConsolePrompt(" ");
        Animations.animateLine(25, "🔄", 50);
        getConsolePrompt("\n🔄 Round #" + roundNumber + " Summary:");
        HealthDisplay.healthStatus(player, npc);
        // TODO: More advanced, general-purpose resource recap
        System.out.println(player);
        System.out.println(npc);
        animateLine(25, "⎯", 100);
        sleep(500); // half second pause after round summary
    }

    public static String getNonPlayingCharacterChoice() {
        String[] npcActions = {"attack", "defend", "defensive ability", "offensive ability"};
        return npcActions[randomlyGenerated.nextInt(npcActions.length)];
//        return npcActions[0]; // Debugging purpose, hardcode one action
    }

    public static Character getRandomEnemy() {
        CharacterType[] enemyTypes = CharacterType.values();
        int randomIndex = randomlyGenerated.nextInt(enemyTypes.length);
        CharacterType selectedType = enemyTypes[randomIndex];
        switch (selectedType) {
            case SHAMAN:
                Shaman shamanInstance = new Shaman("", 0, 0); // Temporary instance for name generation
                String shamanName = Character.getRandomName(shamanInstance);
                int shamanHealth = randomlyGenerated.nextInt(100) + 50;
                int shamanMana = randomlyGenerated.nextInt(100) + 50;
                return new Shaman(shamanName, shamanHealth, shamanMana);

            case NECROMANCER:
                Necromancer necromancerInstance = new Necromancer("", 0, 0); // Temporary instance for name generation
                String necromancerName = Character.getRandomName(necromancerInstance);
                int necromancerHealth = randomlyGenerated.nextInt(100) + 50;
                int necromancerMana = randomlyGenerated.nextInt(100) + 50;
                return new Necromancer(necromancerName, necromancerHealth, necromancerMana);

            case WARRIOR:
                Warrior warriorInstance = new Warrior("", 0, 0); // Temporary instance for name generation
                String warriorName = Character.getRandomName(warriorInstance);
                int warriorHealth = randomlyGenerated.nextInt(100) + 50;
                int warriorStrength = randomlyGenerated.nextInt(5) + 5; // a cap of 10 strength
                return new Warrior(warriorName, warriorHealth, warriorStrength);

            // Add cases for other enemy types

            default: throw new IllegalStateException("Unexpected value: " + selectedType);
        }
    }
}