package dk.grp1.tanks.common.data.parts;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SoundPartTest {

    private SoundPart soundPart;
    String shootSoundPath = "/path/to/some/sound.mp3";
    String onHitSoundPath = "/path/to/some/onhit.mp3";

    @Before
    public void initialize() {
        soundPart = new SoundPart(shootSoundPath, onHitSoundPath);
    }

    @Test
    public void getShootSoundPath() {
        assertEquals(soundPart.getShootSoundPath(), shootSoundPath);

    }

    @Test
    public void getOnHitSoundPath() {
        assertEquals(soundPart.getOnHitSoundPath(), onHitSoundPath);
    }
}