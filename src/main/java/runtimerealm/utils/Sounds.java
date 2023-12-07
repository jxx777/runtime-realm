package runtimerealm.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Sounds class manages sound effects for the game.
 * It provides functionality to play, stop, and manage various sound clips.
 */
public class Sounds {
    private static final GameConfig config = GameConfig.getInstance();
    private static final Map<String, String> soundMap = new HashMap<>();
    private static Clip currentClip = null;

    // Static initializer to set up sound paths
    // Follow the package structure - Do note these will have to be created locally as I've not included the soundfiles
    static {
        // Atmosphere sounds
        soundMap.put("main_screen", "src/main/java/runtimerealm/assets/sounds/atmosphere/main_screen.wav");
        soundMap.put("arena_screen", "src/main/java/runtimerealm/assets/sounds/atmosphere/arena_screen.wav");

        // One-shots
        soundMap.put("sword_hit", "src/main/java/runtimerealm/assets/sounds/oneshots/sword_hit.wav");

        // Dialog
        soundMap.put("welcome", "src/main/java/runtimerealm/assets/sounds/dialog/narrator_intro.wav");
        // Other sounds...
    }

    /**
     * Plays a sound based on the given sound name.
     * If looping is true, the sound will loop continuously.
     *
     * @param soundName The name of the sound to play.
     * @param loop      Indicates whether the sound should loop.
     */
    public static void playSound(String soundName, boolean loop) {
        if (isSoundEnabled()) {
            return;
        }
        stopCurrentSound();

        try {
            String filePath = soundMap.getOrDefault(soundName, "default_sound_path");
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            currentClip = AudioSystem.getClip();
            currentClip.open(audioIn);
            if (loop) {
                currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                currentClip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    /**
     * Stops the currently playing sound clip, if any.
     */
    public static void stopCurrentSound() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }


    /**
     * Plays a one-shot sound effect based on the given sound name.
     * The sound will play once and then stop.
     *
     * @param soundName The name of the sound to play.
     */
    public static void playOneShotSound(String soundName) {
        if (isSoundEnabled()) {
            return;
        }
        try {
            Clip oneShotClip = createClip(soundName);
            oneShotClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates and returns a new Clip instance for the specified sound.
     *
     * @param soundName The name of the sound for which to create the clip.
     * @return The created Clip instance.
     * @throws LineUnavailableException      If a line cannot be opened because it is unavailable.
     * @throws IOException                   If an I/O error occurs during reading of the sound file.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     */
    private static Clip createClip(String soundName) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        String filePath = soundMap.getOrDefault(soundName, "default_sound_path");
        File soundFile = new File(filePath);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        return clip;
    }

    /**
     * Checks if the sound is currently enabled in the game configuration.
     *
     * @return true if sound is enabled, false otherwise.
     */
    public static boolean isSoundEnabled() {
        return !config.isSoundEnabled();
    }

    /**
     * Adds a new sound to the sound map with the specified name and file path.
     *
     * @param soundName The name of the sound to add.
     * @param filePath  The file path of the sound.
     */
    public static void addSound(String soundName, String filePath) {
        soundMap.put(soundName, filePath);
    }
}