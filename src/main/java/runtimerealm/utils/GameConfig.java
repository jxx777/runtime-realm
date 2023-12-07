package runtimerealm.utils;

import lombok.Getter;
import lombok.ToString;

/**
 * Singleton class for managing game configuration settings.
 */
@Getter
@ToString
public class GameConfig {
    private static GameConfig instance = null;
    private boolean soundEnabled;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameConfig() {
        this.soundEnabled = true;
    }

    /**
     * Gets the single instance of GameConfig.
     *
     * @return The single instance of GameConfig.
     */
    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    /**
     * Enables or disables the sound in the game.
     *
     * @param soundEnabled The new state of the sound setting.
     */
    public void setSoundEnabled(boolean soundEnabled) {
        if (this.soundEnabled && !soundEnabled) {
            Sounds.stopCurrentSound();
        }
        this.soundEnabled = soundEnabled;
    }

    // Other configuration methods...
}