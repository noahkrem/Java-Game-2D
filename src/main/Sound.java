package main;

import java.io.BufferedInputStream;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {

    private AudioInputStream ais;
    private SourceDataLine line;
    private Thread playThread;
    private volatile boolean looping;

    private final URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/res/sound/pickup-key.wav");
        soundURL[1] = getClass().getResource("/res/sound/unlock-door.wav");
        soundURL[2] = getClass().getResource("/res/sound/pickup-torch.wav");
        soundURL[3] = getClass().getResource("/res/sound/ambience-crickets.wav");
    }

    public void setFile(int i) {
        try {
            if (soundURL[i] == null) {
                System.err.println("Sound file not found: index " + i);
                ais = null;
                line = null;
                return;
            }

            // Open buffered stream for JAR compatibility
            BufferedInputStream bis = new BufferedInputStream(soundURL[i].openStream());
            AudioInputStream originalAis = AudioSystem.getAudioInputStream(bis);

            AudioFormat baseFormat = originalAis.getFormat();
            System.out.println("Original format: " + baseFormat);

            // Force PCM_SIGNED little-endian
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            ais = AudioSystem.getAudioInputStream(decodedFormat, originalAis);

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(decodedFormat);

        } catch (Exception e) {
            ais = null;
            line = null;
            System.err.println("Failed to load sound index " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void play() {
        if (ais == null || line == null) return;

        stop(); // stop any currently playing thread
        looping = false;

        playThread = new Thread(() -> streamAudio(false));
        playThread.start();
    }

    public void loop() {
        if (ais == null || line == null) return;

        stop();
        looping = true;
        playThread = new Thread(() -> streamAudio(true));
        playThread.start();
    }

    public void stop() {
        looping = false;
        if (playThread != null && playThread.isAlive()) {
            playThread.interrupt();
        }
        if (line != null && line.isRunning()) {
            line.stop();
            line.flush();
        }
    }

    private void streamAudio(boolean loop) {
        try {
            do {
                ais.reset(); // reset stream to start
                line.start();
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = ais.read(buffer, 0, buffer.length)) != -1) {
                    line.write(buffer, 0, bytesRead);
                }
                line.drain();
                line.stop();
            } while (looping && loop);
        } catch (Exception e) {
            // Stream finished or interrupted
        }
    }
}
