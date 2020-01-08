import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.magiclen.media.AudioPlayer;

import com.sun.org.apache.bcel.internal.generic.I2F;
import com.sun.xml.internal.ws.api.policy.SourceModel;

import edu.fcps.karel2.util.Coordinate;
/**
 * 
 * @author 張哲瑋 wells
 *
 */

public class GamePanel extends JPanel{

	private static GamePanel current=null;
	private GameBackend gb=null;	
	private BufferedImage buffer;
	private Graphics graphics;
	private boolean first_render=true;
	private int counter;
	private int round=0;
	public boolean pass=true;	
	private int enter;
	public static boolean gameover;
	public static boolean good;
	AudioPlayer sounds;
	File open=new File("sounds/begin.wav");
	File start=new File("sounds/open.wav");
	File boss=new File("sounds/boss.wav");
	ImageIcon thomas=new ImageIcon("picture/backgrounp.gif");
	ImageIcon thomas2=new ImageIcon("picture/pass.gif");
	ImageIcon thomas3=new ImageIcon("picture/blackhole.gif");
	ImageIcon thomas4=new ImageIcon("picture/wait.gif");
	ImageIcon thomas5=new ImageIcon("picture/Gameover.jpg");
	ImageIcon thomas6=new ImageIcon("picture/good.gif");
	public GamePanel(GameBackend gb) {
		super();
		buffer=new BufferedImage(GameLeader.Frame_Width, GameLeader.Frame_Heigh, BufferedImage.TYPE_INT_RGB);
		graphics=buffer.getGraphics();
		this.gb=gb;
		current=this;
		GameFrame.getCurrent().addKeyListener(new key1());
        GameFrame.getCurrent().setFocusable(true);
        sounds=new AudioPlayer(open);
        
		
	}
    public class key1 extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			
			if(e.getKeyCode()==e.VK_ENTER&&enter==0) {
				
				enter++;
			}
			if (e.isControlDown()&&enter==0) {
				
				if(!gb.getprotagonists().isEmpty()) {
					gb.getprotagonists().get(0).setHealth(-100);
					gb.getprotagonists().get(0).setPower(-100);
					gb.getprotagonists().get(0).health_Max=-100;
					gb.getprotagonists().get(0).power_Max=-100;
					enter++;
				}
				
			}
            if(e.isShiftDown()&&enter==1) {
            	sounds.close();
            	sounds=new AudioPlayer(start);
				enter++;				
			}
            if(e.getKeyCode()==e.VK_ENTER&&(gameover||good)) {
            	enter=0;
            	counter=0;
            	round=0;
            	pass=true;
            	gameover=false;
            	good=false;
            	GameBackend.getCurrent().close();            	            	
            	new protagonist(600, 600,3,3);
            }
            if(e.getKeyCode()==e.VK_Z&&(enter==0||enter==1)) {
            	round++;
            	if(round>=10) {
            		round%=10;
            	}
            	
            }
			
			}}
	private void renderpro(Graphics g) {
		List<protagonist> list=new ArrayList<>(gb.getprotagonists());
		for(protagonist p:list) {
			p.render(g,coordinateToPixel(p));
		}
	}	
	private void renderlit(Graphics g) {
		List<LittleMonster> list=new ArrayList<>(gb.getLittleMonsters());
		for(LittleMonster p:list) {
			p.render(g,coordinateToPixel(p));
		}
	}
	
	private void rendermid(Graphics g) {
		List<MiddleMonster> list=new ArrayList<>(gb.getMiddleMonsters());
		for(MiddleMonster p:list) {
			p.render(g,coordinateToPixel(p));
		}
	}
	
	private void renderboss(Graphics g) {
		List<Boss> list=new ArrayList<>(gb.getBosses());
		for(Boss p:list) {
			p.render(g,coordinateToPixel(p));
		}
	}
		
	private void renderbooms(Graphics g) {
		List<Boom> list=new ArrayList<>(gb.getbooms());
		for(Boom p:list) {
			p.render(g, coordinateToPixel(p));
		}
	}
	private void renderScene(Graphics g)
	{			
		renderboss(g);	
		renderlit(g);	
		rendermid(g);	
		renderpro(g);
		renderbooms(g);	
			
	}
	/**
	 * 
	 * 修正圖片誤差
	 *
	 */
	public Coordinate coordinateToPixel(actor a)
	{
		int xc = a.getX()-a.getWidth()/2;
		int yc = a.getY()-a.getHeigh()/2;
		
		return new Coordinate(xc,yc);
	}
	/**
	 * 
	 * 確認物件xy是否在Frame中
	 * 
	 */
	public boolean isVisible(actor c)
	{
		if(c.getX() > getWidth() || c.getY() > getHeight() || c.getX() < 0 || c.getY() < 0)
			return false;
		
		return true;
	}
	protected synchronized void paintComponent(Graphics g)	{   
		
		if (!sounds.getStatus().equals(AudioPlayer.Status.START)) {
		sounds.play();
	}
		if(first_render==true) {
			first_render=false;
			return;
		}
		super.paintComponent(g);
		if(good) {
			g.drawImage(thomas6.getImage(), 0, 0, getWidth(),getHeight(),null);	
			g.setFont(new Font("serif", Font.HANGING_BASELINE, 30));
			g.setColor(Color.DARK_GRAY);			
			g.drawString("按下Enter重新開始", 500,650);
			return;
		}
		if(gameover) {
			g.drawImage(thomas5.getImage(), 0, 0, getWidth(),getHeight(),null);	
			g.setFont(new Font("serif", Font.HANGING_BASELINE, 30));
			g.setColor(Color.DARK_GRAY);			
			g.drawString("按下Enter重新開始", 500,650);
			return;
		}
		if(enter==0) {
						
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(thomas4.getImage(), 250, 0,  null);
			g.setFont(new Font("serif", Font.ITALIC, 200));
			g.setColor(Color.LIGHT_GRAY);			
			g.drawString("星際大戰", 230,300);
			g.setFont(new Font("serif", Font.HANGING_BASELINE, 50));
			g.setColor(Color.DARK_GRAY);			
			g.drawString("按下ENTER後開始", 450,600);
			g.setFont(new Font("serif", Font.HANGING_BASELINE, 30));
			g.setColor(Color.DARK_GRAY);			
			g.drawString("無盡模式按下CTRL", 500,650);
			g.setFont(new Font("serif", Font.HANGING_BASELINE, 30));
			g.setColor(Color.cyan);			
			g.drawString("ROUND:"+(round+1), 1000,100);
			return;
		}
		if(enter==1) {
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(thomas4.getImage(), 250, 0,  null);
			
			g.setFont(new Font("serif", Font.ITALIC, 50));
			g.setColor(Color.red);			
			g.drawString("使用方向鍵控制主角",200,200);
			g.drawString("按下Z，X和SHIFT消耗能量使用技能",200,250);
			g.drawString("Z為散射",200,300);
			g.drawString("X為連射(無法移動)",200,350);
			g.drawString("SHIFT閃躲攻擊，但同時也無法造成攻擊",200,400);			
			g.drawString("ps.別讓中文輸入影響您的遊戲體驗",200,450);
			g.setFont(new Font("serif", Font.HANGING_BASELINE, 50));
			g.setColor(Color.DARK_GRAY);			
			g.drawString("按下SHIFT開始", 450,600);
			g.setFont(new Font("serif", Font.HANGING_BASELINE, 30));
			g.setColor(Color.cyan);			
			g.drawString("ROUND:"+(round+1), 1000,100);
			return;
		}
		if(!pass) {
			counter=0;
		graphics.drawImage(thomas.getImage(), 0, 0, GameLeader.Frame_Width, GameLeader.Frame_Heigh, null);
		renderScene(graphics);
		if(!GameBackend.getCurrent().live()) {
			graphics.drawImage(thomas3.getImage(),GameLeader.Frame_Width/2-150,  GameLeader.Frame_Heigh/2-150, 300, 300, null);
		}
		g.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
		}
		else {
			graphics.drawImage(thomas2.getImage(), 0, 0, GameLeader.Frame_Width, GameLeader.Frame_Heigh, null);
			g.drawImage(buffer, 0, 0, getWidth(), getHeight()+40, null);
			g.setFont(new Font("serif", Font.BOLD, 80));
			g.setColor(Color.YELLOW);
			g.drawString("ROUND:"+(round+1), GameLeader.Frame_Width/2-170, GameLeader.Frame_Heigh/2);
			if((round+1)%5==0) {
				
				g.setFont(new Font("serif", Font.BOLD, 100));
				g.setColor(Color.RED);
				g.drawString("WARNING!", GameLeader.Frame_Width/2-250, GameLeader.Frame_Heigh/2+100);
			}
			counter++;
			if(round==10) {
				good=true;
				return;
			}
			if(counter==10&&((round+1)%5==0)) {
				sounds.close();
            	sounds=new AudioPlayer(boss);
			}
			if(counter==10&&((round+1)%5==1)) {
				sounds.close();
            	sounds=new AudioPlayer(start);
			}
			if(counter==300||round==10) {
				
				round++;
				GameLeader.continueRount("rounds/round"+round+".map");
				pass=false;				
			}
			
			if(!gb.getprotagonists().isEmpty()) {
				gb.getprotagonists().get(0).render=0;
			}
		}
	
	
	}
	void close()
	{
		current = null;
	}
	/**
	 * 取得當前的GamePanel
	 */
	public static GamePanel getCurrent() {
		return current;
	}
}
