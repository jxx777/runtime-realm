package runtimerealm.model.characters;

import lombok.Getter;
import lombok.Setter;
import runtimerealm.model.Combatant;
import runtimerealm.utils.Color;
import runtimerealm.utils.Sounds;

import static runtimerealm.utils.Utils.*;
import static runtimerealm.view.BattlePrompt.displayAttack;

@Getter
@Setter
public class Warrior extends Character {
    private static final String[] warriorNames = {"MuscleClash", "BruteSwing", "GymRage", "ProteinSlash", "SweatBeard"}; // should / could be on a yml file (similar to faker)
    int strength;
    int defenseModifier;

    public Warrior(String name, int health, int strength) {
        super(name, health);
        this.strength = strength;
    }

    @Override
    protected String[] getNameOptions() { // could be an implementation similar to faker, get string from a .yml or .json
        return warriorNames;
    }

    @Override
    public void attack(Combatant self, Combatant target) {
        int attackRoll = rollDice(0, 20); // Let's say this is a value between 1 and 20
        int damage = this.strength + attackRoll; // Simple damage calculation
        Sounds.playOneShotSound("sword_hit");
        target.receiveDamage(damage);
        displayAttack(self, self, damage);
    }

    @Override
    public void defend(Combatant self) {
        setDefenseModifier(rollDice(0, 20));
        getConsolePrompt("\uD83D\uDEE1️ " + self.getName() + "puts up their defensive stance for a reduction of " + defenseModifier + " damage");
    }

    @Override
    public void offensiveAbility(Combatant target) {
        // TODO: Offensive ability
        getConsolePrompt(Color.RED.colorize("Casts heavy damage"));
    }

    @Override
    public void defensiveAbility(Combatant self) {
        // TODO: Defensive ability
        getConsolePrompt(Color.RED.colorize(self.getName() + " Casts impenetrable shield wall"));
    }

    @Override
    public void receiveDamage(int damage) {
        double actualDamage = Math.max(0, damage - getDefenseModifier()); // Ensure damage isn't negative
        setHealth((int) (getHealth() - actualDamage));
        setDefenseModifier(0);
    }

    @Override
    public void receiveHeal(double healing) {
    }

    @Override
    public String talkSmack() {
        String[] warriorReplies = {"'Feel my wrath!'", "'Is that all you've got?'", "'You'll need more than that to defeat me!'"}; // should / could be on a yml file (similar to faker)
        return getName() + " says: " + warriorReplies[randomlyGenerated.nextInt(warriorReplies.length)];
    }

    @Override
    public String toString() {
        return super.getName() + ": " + "HP = " + super.getMaxHealth() + " | " + "Strength = " + this.strength;
    }
}