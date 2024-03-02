
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Player extends JComponent
{
  private float xpos;
  private float ypos;
  float Xvelocity=0,YVelocity=0;
  public int ogX,ogY;  
  int xScale;
  int yScale;
  boolean onGround;
  Ellipse2D.Double ring2;
  BufferedImage image;
  String filepath;
  Graphics2D g1;
  int tttttt=0;
  public Player(int posx,int posy,int xWidth,int yWidth,int rotation2, BufferedImage image2){
    xpos=posx;ypos=posy;
    xScale=xWidth;yScale=yWidth;
    image=image2;
    ogX=posx;ogY=posy;
  }

  public void draw(Graphics2D g2){
    g2.drawImage(image,(int)xpos,(int)ypos,xScale>0?xScale:image.getWidth(),yScale>0?yScale:image.getHeight(),null);
    //Toolkit.getDefaultToolkit().sync(); 
  }

  public void nextFrame(int sidething, Graphics2D g2){}
  public void setPos(int x,int y){xpos=x;ypos=y;}
  public void setScale(int xS,int yS){xScale=xS>0?xS:xScale;yScale=yS>0?yS:yScale;}
  public void changePos(int x,int y){xpos+=x;ypos+=y;}
  public void changePos(float x,float y){xpos+=x;ypos+=y;}
  public void changeScale(int xS,int yS){xScale=xScale+xS>1?xScale+xS:1;yScale=yScale+yS>1?yScale+yS:1;}
  public int getXPos(){return (int)xpos;}
  public int getYPos(){return (int)ypos;}
  public int getXScale(){return xScale;}
  public int getYScale(){return yScale;}
}