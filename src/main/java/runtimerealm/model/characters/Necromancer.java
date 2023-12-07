package runtimerealm.model.characters;

import lombok.Getter;
import lombok.Setter;
import runtimerealm.model.Combatant;
import runtimerealm.utils.Color;

import static runtimerealm.utils.Utils.*;
import static runtimerealm.view.BattlePrompt.displayAttack;

@Getter
@Setter
public class Necromancer extends Character {
    private static final String[] necromancerNames = {"GothMancer", "SoulGloom", "CapeBrood", "DarkEmoKid", "GraveChill"};
    int mana; // General attributes (stats)
    // Getters & Setters
    int souls;

    public Necromancer(String name, int health, int mana) {
        super(name, health);
        this.mana = mana;
    }

    @Override
    protected String[] getNameOptions() {
        return necromancerNames;
    }

    @Override
    public void attack(Combatant self, Combatant target) {
        int attackRoll = rollDice(0, 20);
        int damage = this.souls > 5 ? (attackRoll * 2) : attackRoll;
        target.receiveDamage(damage);
        displayAttack(self, target, damage);
        if (souls > 0) souls--;
    }

    @Override
    public void defend(Combatant self) {
        if (mana > 15) {
            int soulRoll = rollDice(1, 3);
            this.souls += soulRoll;
            getConsolePrompt(self.getName() + " harnesses " + soulRoll + " souls. ");
            this.mana -= 20;
        } else {
            getConsolePrompt("Unsuccessful attempt, no mana!");
            if (souls > 0) souls--;
        }
    }

    @Override
    public void offensiveAbility(Combatant target) {
        if (mana > 15) {
            int spellRoll = rollDice(5, 10);
            int damageOverTime = spellRoll * souls; // Damage based on the number of souls
            this.mana -= 35;
            if (souls > 0) souls--;

            // Apply DoT effect to the target
            target.applyDoTEffect(damageOverTime, 3); // Example: 3 turns of DoT
            // TODO: proper display offensiveAbility
        } else {
            getConsolePrompt("Unsuccessful attempt, no mana!");
            if (souls > 0) souls--;
        }
    }

    @Override
    public void defensiveAbility(Combatant self) {
        // TODO: Defensive Ability
        getConsolePrompt(Color.RED.colorize(self.getName() + " Casts veil of soul "));
    }

    @Override
    public void receiveDamage(int damage) {
        double actualDamage = Math.max(0, damage); // Ensure damage isn't negative
        setHealth((int) (getHealth() - actualDamage));
    }

    @Override
    public void receiveHeal(double healing) {
    }

    // Class specific Win/Lose condition mechanic
    @Override
    public boolean isAlive() {
        return getHealth() > 0 || souls > 0;
    }

    @Override
    public String talkSmack() {
        String[] necromancerReplies = {"'Your soul will be mine!'", "'Darkness consumes you!'", "'I am the master of the dead!'"};
        return getName() + " says: " + necromancerReplies[randomlyGenerated.nextInt(necromancerReplies.length)];
    }

    @Override
    public String toString() {
        return super.getName() + ": " +
                "HP = " + super.getMaxHealth() +
                " | " +
                "Mana = " + mana +
                " | " +
                "Soul Power = " + souls;
    }
}