import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Handles playing, stopping, and looping of sounds for the game.
 *
 * @author Langdon Staab
 * @author Tyler Thomas
 */
public class Sound {
    Clip clip;

    public Sound(String filename) {
        try (InputStream in = getClass().getResourceAsStream(filename)) {
            assert in != null;
            InputStream bufferedIn = new BufferedInputStream(in);
            try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn)) {
                clip = AudioSystem.getClip();
                clip.open(audioIn);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // play, stop, loop the sound clip
    }

    public void play() {
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public boolean isPlaying() {
        //return false;
        return clip.getMicrosecondLength() > clip.getMicrosecondPosition();
    }
}