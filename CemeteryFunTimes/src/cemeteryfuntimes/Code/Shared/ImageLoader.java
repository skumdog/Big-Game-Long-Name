package cemeteryfuntimes.Code.Shared;

// @author David Kozloff & Tyler Law

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ImageLoader implements Globals {
    
    private static final HashMap<String,BufferedImage> images = new HashMap();
    
    public static BufferedImage getImage(String imageName, double rotation) {
        BufferedImage image = images.get(imageName);
        if (image == null) { return null; }
        return Utilities.rotateImage(image, rotation);
    }
    
    public static void loadImage(String imagePath, int width, int height) {
        if (images.get(imagePath) == null) {
            images.put(imagePath,Utilities.getScaledInstance(IMAGEPATH+imagePath,width,height));
        }
    }
    
    public void clearImages() {
        images.clear();
    }
    
}
