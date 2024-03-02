//package Utils;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JFrameBase extends JFrame{
  Random random=new Random();int w=1500;int h=1000;int s1=1;double s=s1;
  BufferedImage i;
  int framecount=-1;
  JFrame frame;
  JPanel panel;
  //left, right, up, down
  boolean[]inputs=new boolean[4];
  Boolean mouseDown=false;
  int scrollAmount;
  int mouseX=0;
  int mouseY=0;
  JFrameCompBase comp;
  ArrayList<Float>frametimes=new ArrayList<Float>(); 
  //Canvas canvas=new Canvas(w,h);
    public JFrameBase(){
      h=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
      w=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      Util ut=new Util();
      i=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
      frame=new JFrame();
      panel=new JPanel();
      comp=new JFrameCompBase(panel,w,h);
      frame.setSize(w+100,h+100);
      //frame.add(comp);
      //p.getGraphics().drawLine(10,10, 100, 200);
      frame.setVisible(true);
      //setContentPane(p);setSize(w,h);setVisible(true);
      //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);setLayout(null);
      panel.setVisible(true);
      panel.setLayout(new OverlayLayout(panel));
      panel.add(comp);
      comp.paintComponent((Graphics2D)frame.getGraphics());
      //((Graphics2D)frame.getGraphics()).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      frame.add(panel);
      frame.setFocusable(true);
      frame.requestFocusInWindow();
      fullDraw();
      ScheduledExecutorService executor=Executors.newScheduledThreadPool(1);
      Runnable task=()->{

        if(framecount>1){ut.startTimer();}
        comp.nextFrame(mouseX,mouseY,mouseDown,inputs);comp.repaint();
        if(framecount>1){frametimes.add(ut.stopTimer(false));}
        framecount+=1;
        //System.out.println(mouseX+" "+mouseY);
        if(framecount==1){
          for(int i=0;i<frametimes.size();i++){
            float frametotal=0;
            frametotal+=frametimes.get(i);
            System.out.println(Util.colorText("Average frame time: "+(frametotal/framecount)+" ms",1,150,1));
          }
        }
      };
      executor.scheduleAtFixedRate(task,0,1000/60,TimeUnit.MILLISECONDS);//*/

    panel.addMouseWheelListener(new MouseWheelListener() {
      public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println(e.getWheelRotation());
        if(e.getWheelRotation()==1){scrollAmount+=e.getWheelRotation();}
        else if(s>s1){scrollAmount+=e.getWheelRotation();}
        System.out.println(s1+scrollAmount/4);
        s=s1+scrollAmount/4;

      }
    });
    panel.addMouseListener(new MouseAdapter(){
      public void mousePressed(MouseEvent e){mouseDown=true;panel.requestFocusInWindow();}
      public void mouseReleased(MouseEvent e){mouseDown=false;}});
      panel.addMouseMotionListener(new MouseAdapter(){
      public void mouseMoved(MouseEvent e){mouseX=e.getX();mouseY=e.getY();}
      public void mouseDragged(MouseEvent e){mouseX=e.getX();mouseY=e.getY();}});
      panel.addKeyListener(new KeyListener(){
      public void keyTyped(KeyEvent e){}
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==37||e.getKeyCode()==65){inputs[0]=true;}
        if(e.getKeyCode()==39||e.getKeyCode()==68){inputs[1]=true;}
        if(e.getKeyCode()==38||e.getKeyCode()==87||e.getKeyCode()==32){inputs[2]=true;}
        if(e.getKeyCode()==40||e.getKeyCode()==83){inputs[3]=true;}
        //System.out.println(e.getKeyCode());
      }
      public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==37||e.getKeyCode()==65){inputs[0]=false;}
        if(e.getKeyCode()==39||e.getKeyCode()==68){inputs[1]=false;}
        if(e.getKeyCode()==38||e.getKeyCode()==87||e.getKeyCode()==32){inputs[2]=false;}
        if(e.getKeyCode()==40||e.getKeyCode()==83){inputs[3]=false;}
        //System.out.println(e.getKeyCode());
      }
      });
      //*/
    }


    public void run(){
      long lastTime = System.nanoTime();
      final double ns = 1000000000.0 / 60.0;
      double delta = 0;
      while(true){
          long now = System.nanoTime();
          delta += (now - lastTime) / ns;
          lastTime = now;
          while(delta >= 1){
            comp.nextFrame(mouseX,mouseY,mouseDown,inputs);comp.repaint();
              delta--;
              }
          } 
     }
      private void fullDraw(){}
    public static void main(String[]args){
      SwingUtilities.invokeLater(()->new JFrameBase());}
}