package dk.grp1.tanks.common.data.parts;

import org.junit.Test;

import static org.junit.Assert.*;

public class TexturePartTest {

    @Test
    public void getSrcPath() {
        String src = "/path/to/a/cool/texture.png";
        TexturePart texturePart = new TexturePart(src);
        assertEquals(texturePart.getSrcPath(), src);
    }

    @Test
    public void setSrcPath() {
        String src = "/path/to/a/cool/texture.png";
        TexturePart texturePart = new TexturePart(src);
        assertEquals(texturePart.getSrcPath(), src);

        String newSrc = "/path/to/a/new/cool/texture.png";
        texturePart.setSrcPath(newSrc);
        assertEquals(texturePart.getSrcPath(), newSrc);
    }
}