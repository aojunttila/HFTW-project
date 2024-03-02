import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;

public class PostProcessing {
    public static BufferedImage test(BufferedImage imageIn){
        BufferedImage imageOut=new BufferedImage(imageIn.getWidth(),imageIn.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for(int x=0;x<imageIn.getWidth();x++){
            for(int y=0;y<imageIn.getHeight();y++){
                Color c1,c2;
                c1=new Color(imageIn.getRGB(x,y));
                c2=new Color(0,c1.getGreen(),c1.getBlue(),c1.getAlpha());
                imageOut.setRGB(x,y,c2.getRGB());
            }
        }
        return imageOut;
    }
}
