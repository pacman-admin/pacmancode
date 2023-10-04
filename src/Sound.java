import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Handles playing, stopping, and looping of sounds for the game.
 *
 * @author Tyler Thomas
 */
public class Sound {
    private Clip clip;

    public Sound(String filename) {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        try {
            URL url = getClass().getResource(filename);
            assert url != null;
            //File file = new File(url.getPath().replace("%20"," "));
            File file = new File(url.toURI().getPath());
            //System.out.println(url.toURI().getPath());
            //System.out.println(url.toURI());

            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                // load the sound into memory (a Clip)
                clip = AudioSystem.getClip();
                clip.open(sound);
            } else {
                System.out.println("Sound: file not found: " + url.toURI().getPath());
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
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
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
        return clip.getMicrosecondLength() > clip.getMicrosecondPosition();
    }
}


/*
//System.out.println(url.toURI());
            //System.out.println(url.getPath());
            //System.out.println();
 //System.out.println(file.getAbsolutePath());
 */