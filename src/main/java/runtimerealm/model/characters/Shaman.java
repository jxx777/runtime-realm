package runtimerealm.model.characters;

import lombok.Getter;
import lombok.Setter;
import runtimerealm.model.Combatant;
import runtimerealm.utils.Color;

import static runtimerealm.utils.Utils.*;
import static runtimerealm.view.BattlePrompt.displayAttack;

@Getter
@Setter
public class Shaman extends Character {
    private static final String[] shamanNames = {"BlazeLeaf", "TrippySage", "MysticMunch", "PuffWise", "HerbalVision"};

    // Getters & Setters
    private int regeneration; // General attributes (stats)

    public Shaman(String name, int health, int regeneration) {
        super(name, health);
        this.regeneration = regeneration;
    }

    public boolean goneBerserk() {
        return getHealth() < (0.25 * getMaxHealth());
    }

    @Override
    protected String[] getNameOptions() {
        return shamanNames;
    }

    @Override
    public void attack(Combatant self, Combatant target) {
        int attackRoll = rollDice(0, 20);
        double healthPercentage = (double) getHealth() / getMaxHealth();
        double scalingFactor = 5.0; // Maximum damage multiplier when at minimum health
        int damage = (int) (attackRoll * (1 + (scalingFactor - 1) * (1 - healthPercentage)));
        double finalDamage = goneBerserk() ? (damage * 1.25) : damage;
        target.receiveDamage(finalDamage);
        displayAttack(self, target, finalDamage);
    }

    @Override
    public void defend(Combatant self) {
        // TODO: Base defense
        getConsolePrompt(Color.RED.colorize("Casts base defense"));
    }

    @Override
    public void offensiveAbility(Combatant target) {
        // TODO: Offensive ability
        getConsolePrompt(Color.RED.colorize("Casts fury of wind"));
    }

    @Override
    public void defensiveAbility(Combatant self) {
        getConsolePrompt(getName() + " starts healing");
        int healingFactor = rollDice(5, 15);
        int newHealth = getHealth() + healingFactor;
        setHealth(Math.min(newHealth, getMaxHealth()));
    }

    @Override
    public void receiveDamage(int damage) {
        double actualDamage = Math.max(0, damage); // Ensure damage isn't negative
        setHealth((int) (getHealth() - actualDamage));
    }

    @Override
    public void receiveHeal(double healing) {
        setHealth((int) (super.getHealth() + healing));
    }

    @Override
    public String talkSmack() {
        String[] shamanReplies = {"'The spirits guide me!'", "'Your energy wanes!'", "'Nature stands with me!'"};
        return getName() + " says: " + shamanReplies[randomlyGenerated.nextInt(shamanReplies.length)];
    }

    @Override
    public String toString() {
        return super.getName() + ": " +
                "HP: " + super.getMaxHealth() +
                " | " +
                "Regeneration Power = " + this.regeneration;
    }
}