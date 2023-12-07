package runtimerealm.model.characters;

import lombok.Getter;
import lombok.Setter;
import runtimerealm.model.Combatant;
import runtimerealm.utils.Color;

import static runtimerealm.utils.Utils.getConsolePrompt;
import static runtimerealm.utils.Utils.randomlyGenerated;


@Getter
@Setter
public abstract class Character implements Combatant {
    private final int maxHealth;
    private String name;
    private int health; // General attributes (stats)
    private int dotDamage, dotTicks;
    private int hotHealing, hotTicks;

    public Character(String name, int health) {
        this.name = name;
        this.health = health;
        this.maxHealth = health; // Initialize maxHealth to the starting health
    }

    public static String getRandomName(Character character) {
        String[] names = character.getNameOptions();
        return names[randomlyGenerated.nextInt(names.length)];
    }

    protected abstract String[] getNameOptions();

    @Override
    public boolean isAlive() {
        return health >= 0;
    }

    @Override
    public void receiveDamage(double damage) {
        double actualDamage = Math.max(0, damage); // Ensure damage isn't negative
        this.health -= (int) actualDamage;
    }

    public abstract String talkSmack();

    @Override
    public void applyDoTEffect(int damageOverTime, int ticks) {
        this.dotDamage = damageOverTime;
        this.dotTicks = ticks;
    }
    @Override
    public void handleDoTEffect() {
        if (dotTicks > 0) {
            getConsolePrompt(dotDamage == 0
                    ? getName() + " resisted dot"
                    : getName() + " is affected by DoT: " + dotDamage + " damage"
            );
            receiveDamage(dotDamage);
            dotTicks--;
            if (dotTicks == 0) dotDamage = 0; // Reset the DoT effect when the duration ends

        }
    }

    @Override
    public void applyHoTEffect(int healingOverTime, int ticks) {
        this.hotHealing = healingOverTime;
        this.hotTicks = ticks;
    }
    @Override
    public void handleHoTEffect() {
        if (dotTicks > 0) {
            getConsolePrompt(hotHealing != 0 ? (getName() + " heals for: " + hotHealing + " health") : " ");
            receiveHeal(hotHealing);
            hotTicks--;
            if (hotTicks == 0) hotHealing = 0; // Reset the DoT effect when the duration ends
        }
    }

    public abstract void attack(Combatant target, Combatant self);

    public abstract void receiveDamage(int damage);

    @Override
    public String toString() {
        return name;
    }
}