package dk.grp1.tanks.core.internal.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that wraps the LibGDX AssetManager API. Supports OSGi bundles.
 */
public class SoundAssetManager {

    private AssetManager assetManager;
    private Map<String, String> tempFileMap;
    private String localStoragePath;

    public SoundAssetManager(String localStoragePath) {
        this.assetManager = new AssetManager();
        this.tempFileMap = new HashMap<>();
        this.localStoragePath = localStoragePath;
    }

    /**
     * Creates a new temporary file from the given Class' ClassLoader and FileName.
     * @param clazz
     * @param fileName
     * @return
     */
    private File createTempFileFromBundle(Class clazz, String fileName) {
        InputStream is = clazz.getClassLoader().getResourceAsStream(fileName);
        File tempFile = null;
        String filePath = this.localStoragePath;
        try {
            tempFile = new File(filePath+"/"+ clazz.getSimpleName()+fileName);

            try (FileOutputStream fos = new FileOutputStream(tempFile)){
                byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            // Store the file-name/type and the tempFile-path for later lookup
            // Replacing backslashes with forward-slashes for compatibility issues in LibGDX AssetManager.
            tempFileMap.put(clazz.getSimpleName()+fileName, tempFile.getAbsolutePath().replace("\\", "/"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return tempFile;
    }

    /**
     * Loads a sound (short duration clip) with the given file name from the class' class-path.
     * @param clazz
     * @param fileName
     */
    public void loadSoundAsset(Class clazz, String fileName) {
        if(tempFileMap.containsKey(clazz.getSimpleName()+fileName))
            return;
        
        File assetFile = createTempFileFromBundle(clazz, fileName);

        assetManager.load(assetFile.getAbsolutePath(), Sound.class);

        // Has to be called in order to instruct the asset manager to actually load the added files.
        assetManager.finishLoading();
    }

    /**
     * Gets the given file name as a Sound. The class is associated to which class path, the sound initially was loaded from.
     * @param clazz
     * @param fileName
     * @return
     */
    public Sound getSoundAsset(Class clazz, String fileName) {
        String tmpFileName = tempFileMap.get(clazz.getSimpleName()+fileName);

        Sound sound = null;

        try {

            sound = assetManager.get(tmpFileName);

        } catch (NullPointerException ex) {
            System.out.println("The sound asset: " + fileName + ", could not be found.");
        }

        return sound;
    }

    /**
     * Loads Music (long duration clip) with the given file name from the class' class-path.
     * @param clazz
     * @param fileName
     */
    public void loadMusicAsset(Class clazz, String fileName) {
        if(tempFileMap.containsKey(clazz.getSimpleName()+fileName))
            return;

        File assetFile = createTempFileFromBundle(clazz, fileName);

        assetManager.load(assetFile.getAbsolutePath(), Music.class);

        // Has to be called in order to instruct the asset manager to actually load the added files.
        assetManager.finishLoading();
    }

    /**
     * Gets the given file name as Music. The class is associated to which class path, the sound initially was loaded from.
     * @param clazz
     * @param fileName
     * @return
     */
    public Music getMusicAsset(Class clazz, String fileName) {
        String tmpFileName = tempFileMap.get(clazz.getSimpleName()+fileName);

        Music sound = null;

        try {

            sound = assetManager.get(tmpFileName);

        } catch (NullPointerException ex) {
            System.out.println("The sound asset: " + fileName + ", could not be found.");
        }

        return sound;
    }
}
