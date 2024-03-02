import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.nio.file.Files;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class JFrameCompBase extends JComponent{
    JPanel panel;
    int sidething=0;
    JFrameImage img;
    float BGMultiplier=1.3f;
    String data,data2;
    Timer timer3;
    JFrameParticlePolygon deathEmitter,checkpointEmitter;
    int shiftedX,shiftedY;
    boolean[]harmList={false,false,true,true,false,false,false,false,false,false,false,false,false,false,false};
    int framecount;
    int[][]csvList,csvListBG;
    Rectangle[][]collisionList;
    JFrameImage[][]imageList,imageListBG;
    ArrayList<JFrameImage>layer1ImageList=new ArrayList<JFrameImage>();
    boolean[]inputDelay=new boolean[4];
    Graphics2D g2;
    BufferedImage image,image2,imageOverlay,playerImage,pluto1;
    BufferedImage bufferImage;
    Graphics2D bufferG;
    int width;
    int playerStartX=100,playerStartY=800;
    int tileWidth=32,tileHeight=32;
    float tileMultiplierX=32,tileMultiplierY=32;
    boolean imageOver,dead,checkpointhit;
    JFramePolygon testpoly;
    int height;int h2;
    Random rand=new Random();
    JFramePolygon[]elementList=new JFramePolygon[500];
    JFramePolygon[]polyList=new JFramePolygon[2000];
    Player player;
    ArrayList<BufferedImage>textureList=new ArrayList<BufferedImage>();
    Rectangle playerRectange,collisionTest;
    JFrameParticleImage testEmitter;
    JFrameParticlePolygon testEmitter2;
    JFrameImage pluto;
    public JFrameCompBase(JPanel panel2,int w,int h){
        width=w;height=h;bufferImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        bufferG=(Graphics2D)bufferImage.createGraphics();
        //bufferG.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        h2=bufferImage.getHeight();panel=panel2;
        try{data=new String(Files.readAllBytes(Paths.get("map.csv")));}catch(Exception e){}
        try{data2=new String(Files.readAllBytes(Paths.get("mapL2.csv")));}catch(Exception e){}
        ArrayList<String>lines=new ArrayList<String>(Arrays.asList(data.split("[\\r\\n]+")));
        ArrayList<String>lines2=new ArrayList<String>(Arrays.asList(data2.split("[\\r\\n]+")));
        int width=new ArrayList<String>(Arrays.asList(lines.get(0).split(","))).size();
        imageList=new JFrameImage[width][lines.size()];
        csvList=new int[width][lines.size()];
        imageListBG=new JFrameImage[width][lines.size()];
        csvListBG=new int[width][lines.size()];
        collisionList=new Rectangle[width][lines.size()];
        for(int i=0;i<lines.size();i++){
            ArrayList<String>line=new ArrayList<String>(Arrays.asList(lines.get(i).split(",")));
            ArrayList<String>line2=new ArrayList<String>(Arrays.asList(lines2.get(i).split(",")));
            for(int i2=0;i2<line.size();i2++){
                csvList[i2][i]=Integer.parseInt(line.get(i2));
                csvListBG[i2][i]=Integer.parseInt(line2.get(i2));
                //System.out.println(csvList[i2][i]);
            }
        }

        g2=(Graphics2D)panel.getGraphics();
        try{
            playerImage=ImageIO.read(new File("player.png"));
            textureList.add(ImageIO.read(new File("tiles/rock1.png")));//0
            textureList.add(ImageIO.read(new File("tiles/rock2.png")));//1
            textureList.add(ImageIO.read(new File("tiles/spike1.png")));//2
            textureList.add(ImageIO.read(new File("tiles/spike2.png")));//3
            textureList.add(ImageIO.read(new File("tiles/grass1.png")));//4
            textureList.add(ImageIO.read(new File("tiles/checkpoint1.png")));//5
            textureList.add(ImageIO.read(new File("tiles/vineleft1.png")));//6
            textureList.add(ImageIO.read(new File("tiles/vineright1.png")));//7
            textureList.add(ImageIO.read(new File("tiles/rockdrop1.png")));//8
            textureList.add(ImageIO.read(new File("tiles/rockdrop2.png")));//9
            textureList.add(ImageIO.read(new File("tiles/rockdrop3.png")));//10
            textureList.add(ImageIO.read(new File("tiles/rock3.png")));//10
            image2=ImageIO.read(new File("apple2.jpg"));
            pluto1=ImageIO.read(new File("pluto3.png"));
            imageOver=false;
            if(imageOver){imageOverlay=ImageIO.read(new File("vignette.png"));}
        }catch(Exception e){}


        for(int x=0;x<csvList.length;x++){
            for(int y=0;y<csvList[0].length;y++){
                if(csvList[x][y]>-1){
                    collisionList[x][y]=new Rectangle((int)(x*tileMultiplierX),(int)(y*tileMultiplierX),tileWidth,tileHeight);System.out.println(x*10);
                    imageList[x][y]=new JFrameImage((int)(x*tileMultiplierX),(int)(y*tileMultiplierY),tileWidth,tileHeight,0,textureList.get(csvList[x][y]));
                }
                if(csvListBG[x][y]>-1){
                    imageListBG[x][y]=new JFrameImage((int)(x*tileMultiplierX*BGMultiplier),(int)(y*tileMultiplierY*BGMultiplier-400),(int)(tileWidth*BGMultiplier+1),(int)(tileHeight*BGMultiplier+1),0,textureList.get(csvListBG[x][y]));
                }
            }}
        
        for(int i=0;i<elementList.length;i++){
            //if(rand.nextInt(2)==0){
            int randX=rand.nextInt(2000);
            int randY=rand.nextInt(2000);
            int rS=rand.nextInt(5)+1;
            elementList[i]=new JFramePolygon(new int[]{0+randX,rS+randX,rS+randX,0+randX},new int[]{0+randY,0+randY,rS+randY,rS+randY},Color.black,Color.white,1f); 
            //int thingx=rand.nextInt(width+100);int thingy=rand.nextInt(height);
            //polyList[i]=new JFramePolygon(new int[]{thingx,thingx+rand.nextInt(100),thingx+rand.nextInt(100)},new int[]{thingy,thingy+rand.nextInt(50)+25,thingy+rand.nextInt(50)+25},new Color(0,0,0),new Color(rand.nextInt(150),rand.nextInt(150),rand.nextInt(150)),(float)3); 
            //}else{elementList[i]=new JFrameImage(rand.nextInt(1500),rand.nextInt(1000),rand.nextInt(100)+1,rand.nextInt(100)+1,rand.nextInt(200),image2); }
        }

        player=new Player(playerStartX,playerStartY,20,20,0,playerImage);
        playerRectange=new Rectangle(player.getXPos(),player.getYPos(),player.xScale,player.yScale);
        pluto=new JFrameImage(800,128,712,712,0,pluto1);
        collisionTest=new Rectangle(0,700,1500,100);
        inputDelay[2]=true;
        //testEmitter=new JFrameParticleImage(new int[]{200,700,900,0,180,10,5,2,30,30,99,120,100},false,image2);
        //testEmitter2=new JFrameParticlePolygon(new int[]{500,500,0,1000,90,360,20,10,98,120,300},new int[]{0,10,10},new int[]{0,-30,20},Color.RED,Color.BLUE,true,image2);
        //testpoly=new JFramePolygon(new int[]{20,200,1300},new int[]{900,700,800},new Color(105,0,150),new Color(10,10,50),(float)10);
        //testpoly=new JFrameImgQuad(new int[]{20,200,400,300},new int[]{30,500,100,100},new Color(105,0,150),(float)10,image);
    }

   @Override
   public void paintComponent(Graphics g){
    bufferG.clearRect(0,0,width,height);
    Graphics g3=g; 
        g3.setColor(Color.black);
        g3.fillRect(0,0,width,height);  
        bufferG.clearRect(0,0,width,height);
        render((Graphics2D)g);
        g3.drawImage((bufferImage),0,0,null);
        g3.dispose(); 
   }

   public void render(Graphics2D gb){
    //bufferG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    for(int i=0;i<elementList.length;i++){
        if(elementList[i]!=null){
            elementList[i].draw(bufferG);
        }  
    }
    //testpoly.draw(bufferG);
    pluto.draw(bufferG);
    for(int i=0;i<imageListBG.length;i++){
        for(int i2=0;i2<imageListBG[0].length;i2++){
            if(imageListBG[i][i2]!=null){
                imageListBG[i][i2].draw(bufferG);
            }}}

            if(deathEmitter!=null){deathEmitter.draw(bufferG);}
            if(checkpointEmitter!=null){checkpointEmitter.draw(bufferG);}

            if(!dead){player.draw(bufferG);}
    for(int i=0;i<imageList.length;i++){
        for(int i2=0;i2<imageList[0].length;i2++){
            if(imageList[i][i2]!=null){
                imageList[i][i2].draw(bufferG);
            }}}
    if(imageOver){bufferG.drawImage(imageOverlay,0,0,width,height,null);}
   }


   public void groundCheck(){
    playerRectange.setLocation(player.getXPos(),player.getYPos());
    boolean collision=false,ground=false,spike=false,checkpoint=false;
    for(int x=0;x<collisionList.length;x++){
        for(int y=0;y<collisionList[0].length;y++){
            if(collisionList[x][y]!=null&&playerRectange.intersects(collisionList[x][y])){
                if(harmList[csvList[x][y]]){spike=true;
                    playerRectange.setLocation(player.getXPos(),player.getYPos()-8);
                    if(playerRectange.intersects(collisionList[x][y])){playerDeath();spike=true;}
                    playerRectange.setLocation(player.getXPos(),player.getYPos());
                }else{ground=true;}
                if(csvList[x][y]==5){checkpoint=true;}
                if(csvList[x][y]==5&&checkpointhit==false){playerStartX=player.getXPos()-shiftedX;playerStartY=player.getYPos()-shiftedY;
                    player.onGround=false;checkpointParticles();checkpointhit=true;checkpoint=true;}
                    else{if(csvList[x][y]!=5&&csvList[x][y]!=4){player.onGround=true;inputDelay[2]=false;collision=true;}}
                
            }
        }
    }if(shiftedY<-2000||(spike&&!ground)){playerDeath();}if(checkpoint==false){checkpointhit=false;}
    if(collision==false){player.onGround=false;}
   }

   public void checkpointParticles(){
    checkpointEmitter=new JFrameParticlePolygon(
        new int[]{player.getXPos(),player.getYPos(),10,10,180,180,2,10,95,40,200},new int[]{0,10,10,0},new int[]{0,0,10,10},
        new Color(0,255,0,0),Color.green,true);
        timer3=new Timer(1,playerSnap);
        timer3.setRepeats(true);
        timer3.start();
        Timer timer=new Timer(100,stopParticles);
        timer.setRepeats(false);
        timer.start();
   }


   public void playerDeath(){
    //try{TimeUnit.SECONDS.sleep(1);}catch(Exception e){}
    deathEmitter=new JFrameParticlePolygon(
        new int[]{player.getXPos(),player.getYPos(),10,10,180,80,3,10,99,40,300},new int[]{0,10,10,0},new int[]{0,0,10,10},
        Color.red,Color.red,true);
    dead=true;
    Timer timer=new Timer(100,stopParticles);
    timer.setRepeats(false);
    timer.start();
    Timer timer2=new Timer(500,deathMove);
    timer2.setRepeats(false);
    timer2.start();
   }

   ActionListener deathMove = new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        checkpointhit=true;
        player.setPos(playerStartX+shiftedX,playerStartY+shiftedY);
        inputDelay[2]=true;
        dead=false;
        deathEmitter=null;
        checkpointEmitter=null;
    }};
    ActionListener playerSnap=new ActionListener() {public void actionPerformed(ActionEvent evt) {
        if(checkpointEmitter!=null){checkpointEmitter.setPos(player.getXPos(),player.getYPos());}}};

    ActionListener stopParticles=new ActionListener() {public void actionPerformed(ActionEvent evt) {
       if(timer3!=null){timer3.stop();}
        if(deathEmitter!=null){deathEmitter.framecount=-100000;}
       if(checkpointEmitter!=null){checkpointEmitter.framecount=-100000;}}};

   public void shiftScreen(int x1,int y1){
    for(int x=0;x<imageList.length;x++){
        for(int y=0;y<imageList[0].length;y++){
            if(imageList[x][y]!=null){imageList[x][y].changePos(x1,y1);}
            if(imageListBG[x][y]!=null){imageListBG[x][y].changePos(x1*BGMultiplier,0);}
            if(collisionList[x][y]!=null){collisionList[x][y].translate(x1,y1);}
        }
    }
    pluto.changePos(x1*0.1f,y1*0.1f);
    player.changePos(x1,y1);
    shiftedX+=x1;shiftedY+=y1;

   }

   public void getOutOfGround(){
    if(player.onGround){player.YVelocity=0;
    player.changePos(0,2);}
    groundCheck();
    if(player.onGround){player.changePos(0,-1);
        groundCheck();
        if(player.onGround){player.changePos(0,-1);
            groundCheck();
            if(player.onGround){player.changePos(0,-1);
                groundCheck();
                if(player.onGround){player.changePos(0,-1);
                    groundCheck();
                    if(player.onGround){player.changePos(0,-1);
                        groundCheck();
                            if(player.onGround){player.changePos(0,-1);

                            player.changePos(5,0);
                            groundCheck();
                            if(player.onGround){player.changePos(-10,0);
                                groundCheck();
                                if(player.onGround){player.changePos(5,0);}else{inputDelay[2]=true;}
                            }else{inputDelay[2]=true;}
                        }
                    }
                }
            }
        }
        //player.changePos(0,-2);
    }
    //player.changePos(0,3);
   }

   public void runPhysicsUpdate(){
    if(!dead){
    groundCheck();
    boolean temp=player.onGround;
    player.changePos((int)player.Xvelocity,0);
    groundCheck();
    if(player.onGround&&temp==false){
        player.changePos((int)-player.Xvelocity,0);inputDelay[2]=true;
    }

    player.changePos(0,(int)player.YVelocity);
    groundCheck();
    if(player.onGround&&temp==false&&player.YVelocity<0){
        player.changePos(0,(int)-player.YVelocity);
        player.YVelocity+=3;inputDelay[2]=true;
    }
    groundCheck();
    if(player.onGround){getOutOfGround();}
    else{player.YVelocity=player.YVelocity>=10?10:player.YVelocity+0.5f;}
    groundCheck();
    if(player.onGround){getOutOfGround();}
    player.Xvelocity=(float)player.Xvelocity*0.9f;
    //System.out.println(player.getYPos());
    playerRectange.setLocation(player.getXPos(),player.getYPos());
    //if(player.onGround){}else{inputDelay[2]=true;}
    int xFromMid=(width/2)-player.getXPos();int yFromMid=(height/2)-player.getYPos();
    shiftScreen(xFromMid/10,yFromMid/10);
    }
   }

   public void nextFrame(int mouseX,int mouseY,boolean mouseDown,boolean[]inputs){
    runPhysicsUpdate();
    if(inputs[0]&&inputDelay[0]==false){player.Xvelocity=-5;inputDelay[0]=true;}else{inputDelay[0]=false;}
    if(inputs[1]&&inputDelay[1]==false){player.Xvelocity=5;inputDelay[1]=true;}else{inputDelay[1]=false;}
    if(inputs[2]&&inputDelay[2]==false){player.YVelocity=-10;inputDelay[2]=true;}else{}
    if(inputs[3]&&inputDelay[3]==false){inputDelay[3]=true;}else{inputDelay[3]=false;}
    
    for(int i=0;i<elementList.length;i++){
        if(elementList[i]!=null){
            elementList[i].setPos((int)(elementList[i].ogX+shiftedX/(int)(7-elementList[i].getBoundRect().getWidth())),(int)(elementList[i].ogY+shiftedY/7-elementList[i].getBoundRect().getWidth()));
            if(elementList[i].getX()<0){elementList[i].ogX+=width;}if(elementList[i].getX()>width){elementList[i].ogX-=width;}
            if(elementList[i].getY()<0){elementList[i].ogY+=height;}if(elementList[i].getY()>height){elementList[i].ogY-=height;}
            //elementList[i].changePosNew(player.Xvelocity,player.YVelocity);
            //elementList[i].setPos((int)(elementList[i].ogX+(mouseX-(width/2))/(1+(float)elementList[i].getXScale()/20)),(int)(elementList[i].ogY+(mouseY-(height/2))/(1+(float)elementList[i].getYScale()/20)));
            //elementList[i].changePos(rand.nextInt(3)-1,rand.nextInt(3)-1);
            //if(mouseDown){elementList[i].changePos(mouseX>x?1:-1,mouseY>y?1:-1);}
            //if(elementList[i].getYPos()>h2){elementList[i].setPos(-1,0);}
            //elementList[i].changeScale(rand.nextInt(3)-1,rand.nextInt(3)-1);
        }
    }
   }

}