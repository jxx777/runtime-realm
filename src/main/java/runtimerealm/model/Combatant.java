package runtimerealm.model;

import static runtimerealm.utils.Utils.randomlyGenerated;
import static runtimerealm.utils.Utils.rollDice;

public interface Combatant {

    // Abilities
    default void attack(Combatant self, Combatant target) {
        int attackRoll = rollDice(0, 20);
        target.receiveDamage(attackRoll); // Base damage
    } // Should leave body-less? This is the base behaviour for all classes

    void defend(Combatant self);

    void offensiveAbility(Combatant target);
    void defensiveAbility(Combatant self);

    // Win/Lose condition
    boolean isAlive();

    void receiveDamage(double damage);
    void receiveHeal(double healing);

    void applyDoTEffect(int damageOverTime, int ticks);
    void handleDoTEffect();

    void applyHoTEffect(int healingOverTime, int ticks);
    void handleHoTEffect();

    // This is used as to provide a general purpose getter-like pattern for the Combatant name
    // We're overriding the object toString method due to different attribute implementations
    String getName();
}