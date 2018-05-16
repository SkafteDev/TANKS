package dk.grp1.tanks.core.internal.managers;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dk.grp1.tanks.core.internal.AnimationWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GameAssetManager {
    private Map<String, Animation> animationMap;
    private Map<String, Texture> textureMap;


    public GameAssetManager() {
        animationMap = new HashMap<>();
        textureMap = new HashMap<>();
    }

    /**
     * checks if animation is retrieved and/or returns the required animation
     * @param animationWrapper
     * @return
     */
    public Animation checkGetAnimation(AnimationWrapper animationWrapper) {
        Animation animation = animationMap.get(animationWrapper.getPath());

        if (animation == null) {
            InputStream is = animationWrapper.getOrigin().getClass().getClassLoader().getResourceAsStream(animationWrapper.getPath());

            try {
                Gdx2DPixmap gmp = new Gdx2DPixmap(is, Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
                Pixmap pix = new Pixmap(gmp);
                Texture texture = new Texture(pix);
                TextureRegion textureRegion = new TextureRegion(texture);
                TextureRegion[][] tmp = textureRegion.split(texture.getWidth() / animationWrapper.getFrameCols(), texture.getHeight() / animationWrapper.getFrameRows());
                TextureRegion[] explosionFrames = new TextureRegion[animationWrapper.getFrameCols() * animationWrapper.getFrameRows()];
                int index = 0;
                for (int i = 0; i < animationWrapper.getFrameRows(); i++) {
                    for (int j = 0; j < animationWrapper.getFrameCols(); j++) {
                        explosionFrames[index++] = tmp[i][j];
                    }
                }
                animation = new Animation(0.025f, explosionFrames);
                animation.setPlayMode(Animation.PlayMode.NORMAL);
                animationMap.put(animationWrapper.getPath(), animation);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        return animation;
    }

    /**
     * checks if texture is retrieved and/or returns the required texture
     * @param objectClass
     * @param path
     * @return
     */
    public Texture checkGetTexture(Class objectClass, String path) {
        Texture texture = textureMap.get(path);

        if (texture == null) {

            InputStream is = objectClass.getClassLoader().getResourceAsStream(
                    path
            );
            try {
                Gdx2DPixmap gmp = new Gdx2DPixmap(is, Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
                Pixmap pix = new Pixmap(gmp);
                texture = new Texture(pix);
                textureMap.put(path, texture);
                pix.dispose();
                is.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return texture;
    }


}
