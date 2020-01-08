import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import org.omg.CORBA.FloatSeqHelper;

import com.sun.org.apache.bcel.internal.generic.I2F;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory.Zephyr;

import edu.fcps.karel2.Item;
import edu.fcps.karel2.util.Coordinate;
/**
 * 
 * @author 張哲瑋 wells
 *
 */
public class protagonist extends actor{
	private ArrayList<protagonist_bullet> list;
	public int health_Max=150;
	public int power_Max=200;
    int unlimited=-100;
	private ArrayList<protagonist_bullet> test;
	private boolean up=false;
	private boolean down=false;
	private boolean left=false;
	private boolean right=false;
	private boolean key_z=false;
	private boolean key_x=false;
	boolean shift=false;
	private boolean fact;
	private int choose=0;
	public int render;
	private int power;
	static ImageIcon power1=new ImageIcon("picture/1.gif");
	static ImageIcon power2=new ImageIcon("picture/2.gif");	
	static ImageIcon[] thomas= {
			new ImageIcon("picture/image_9.jpg"),
			new ImageIcon("picture/image_10.jpg"),
			new ImageIcon("picture/image_11.jpg"),
			new ImageIcon("picture/image_12.jpg"),
			new ImageIcon("picture/image_13.jpg"),
			new ImageIcon("picture/image_14.jpg"),
			new ImageIcon("picture/image_15.jpg"),
			new ImageIcon("picture/image_16.jpg"),
			new ImageIcon("picture/image_17.jpg"),
			new ImageIcon("picture/image_18.jpg"),
			new ImageIcon("picture/image_19.jpg"),
			new ImageIcon("picture/image_20.jpg"),
			new ImageIcon("picture/image_21.jpg"),
			new ImageIcon("picture/image_22.jpg"),
			new ImageIcon("picture/image_23.jpg"),
			new ImageIcon("picture/image_24.jpg"),
			new ImageIcon("picture/image_25.jpg"),
			new ImageIcon("picture/image_26.jpg"),			
		
	};
	public protagonist(int x, int y,int dx,int dy) {
		super(x, y);		
		this.dx=dx;
		this.dy=dy;
		width=5;
		heigh=5;
		health=100;
		power=100;
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				attack();
				
			}
		},0,200);
		list=new ArrayList<>();
		GameBackend.getCurrent().addprotagonist(this);  				
		GameFrame.getCurrent().addKeyListener(new key1());
        GameFrame.getCurrent().setFocusable(true);
  
	}
	public class key1 extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:				
				up=true;
				break;
			case KeyEvent.VK_DOWN:				
				down=true;
				break;
			case KeyEvent.VK_LEFT:				
				left=true;
				break;
			case KeyEvent.VK_RIGHT:				
				right=true;
				break;
			case KeyEvent.VK_Z:
				key_z=true;
				break;
			case KeyEvent.VK_X:
				key_x=true;
				break;
			default:
				break;
			}
			if(e.isShiftDown()) {
				shift=true;
			}
			
						
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			super.keyReleased(e);
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				up=false;
				break;
			case KeyEvent.VK_DOWN:
				down=false;
				break;
			case KeyEvent.VK_LEFT:
				left=false;
				break;
			case KeyEvent.VK_RIGHT:
				right=false;
				break;
			case KeyEvent.VK_Z:
				key_z=false;
				break;
			case KeyEvent.VK_X:
				key_x=false;
				break;
			default:
				break;
				
	}
			if(!e.isShiftDown()) {
				shift=false;
			}
			}}
	
	/**
	 * 攻擊
	 */
	synchronized public void attack() {
		sumPower(3);
		if(!key_z&&!key_x) {		
		 list.add(new protagonist_bullet(x,y,0,-GameLeader.pro_bul_delta/2-choose));
		 
		}		
		if(key_z&&!shift&&!key_x&&(power>0||power==unlimited)) {
			if(power>=20||power==unlimited) {
			sumPower(-30);
			for(int k=0;k<2;k++) {
			list.add(new protagonist_bullet(x,y,GameLeader.pro_bul_delta/5,-GameLeader.pro_bul_delta-k));
			list.add(new protagonist_bullet(x,y,GameLeader.pro_bul_delta/3,-GameLeader.pro_bul_delta-k));
			list.add(new protagonist_bullet(x,y,0,-GameLeader.pro_bul_delta-k));
			list.add(new protagonist_bullet(x,y,GameLeader.pro_bul_delta/-3,-GameLeader.pro_bul_delta-k));
			list.add(new protagonist_bullet(x,y,GameLeader.pro_bul_delta/-5,-GameLeader.pro_bul_delta-k));
			GameLeader.pause();
			GameLeader.pause();
			}
			for(int i=0;i<8;i++) {
				GameLeader.pause();
			
			}}
			}				
		}
					    	

	/**
	 * 
	 * 確認該單位isDead是否為true，並做刪除的動作
	 */
	public void check() {
		if (health<=0&&health!=unlimited) {
			isDead=true;
		}
		
		if (isDead) {			
			new Boom(x, y,300,100);
			
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				GamePanel.gameover=true;
				
			}
		}, 1000);
      
       GameBackend.getCurrent().removeprotagonist(this);
	}
	}
	/**
	 * 畫子彈
	 */
	public void drawbullet(Graphics g,ArrayList<protagonist_bullet> a) {
		for(protagonist_bullet p:a) {
        	p.render(g, GamePanel.getCurrent().coordinateToPixel(p));
        	if(p.check()) {
        		list.remove(p);
        	}
        }
	}
	/**
	 * 
	 * 計算power
	 */
	public void sumPower(int p) {
		if(power!=unlimited)
		power+=p;
		if(power>=power_Max) {
			power=power_Max;
		}
		if(power<0&&power!=unlimited) {
			power=0;
		}
	}
	/**
	 * 
	 * 設定power
	 */
	public void setPower(int p) {
		if(p<0&&p!=unlimited) {
			power=power_Max;
		}		
		if(p>=power_Max) {
			power=power_Max;
		}
	}
	@Override
	public void sumHealth(int h) {		
		if(health!=unlimited&&!shift)
			health-=h;
		if(shift) {
			if(power<h*5&&power!=unlimited) {
				health-=h;
			}
			else {
				sumPower(-h*5);
			}
		if(health>=health_Max) {
			health=health_Max;
		}	
			
		}
	}
	@Override
	public void render(Graphics g, Coordinate c) {
		check();	
		render++;
		dx=3+choose/3;
		dy=3+choose/3;
		if(shift) {
			dx+=2;
			dy+=2;
			
			if(render%8==0) {
				sumPower(-2);				
				}
			
			
		}
		if(!key_z&&key_x&&choose==17&&(power>0||power==unlimited)||render<150) {
			g.drawImage(power1.getImage(),c.x-125,c.y-125,width+250,heigh+250,null);
			g.drawImage(power2.getImage(),c.x-45,c.y-125,width+100,heigh+100,null);
			if(render%3==0) {
				sumPower(-2);				
				}
			if(render%2==0) {			
			list.add(new protagonist_bullet(x,y,0,-GameLeader.pro_bul_delta*2));
			}
			}					
		
		if((key_x||shift)&&choose<17) {
			if(render%5==0) {
				choose++;
			}
			
		}
		if(!key_z&&!key_x&&!shift) {
			if(choose==17) {
				fact=false;
			}
			else if(choose==0) {
				fact=true;
			}
			if(render%5==0) {
				if (fact) {
					choose++;
				}
				else {
					choose--;
				}
			}
		}	
	
		if (!isDead) {
			if(!key_x||shift) {
			if(left) {
			if(x>=0+width+100) {
				x-=dx;
						}			
		}
			else if(right) {
			if(x<=GameFrame.getCurrent().getWidth()-width-100) {
				x+=dx;
					}
			}
		   if(down) {
				if(y<=GameFrame.getCurrent().getHeight()-heigh-100) {
						y+=dy;
						}
				}
			else if(up) {
					if(y>=0+heigh+100) {
						y-=dy;
				}}}
			
		  if(!GameBackend.getCurrent().live()&&GameLeader.distance(x, y, GameLeader.Frame_Width/2, GameLeader.Frame_Heigh/2)<=100) {
			 GamePanel.getCurrent().pass=true;
		  }
		  else {
			  GamePanel.getCurrent().pass=false;
		}
		  
		   
		   
		   
		   
		   g.drawImage(thomas[choose].getImage(),c.x-100,c.y-100,width+200,heigh+200,null);
		   g.setColor(Color.gray);
	       g.fillRect(x-25, c.y+heigh+70, 50, 10);
	       g.setColor(Color.yellow);
	       g.fillRect(x-25, c.y+heigh+70, power*50/power_Max, 10);
			
			g.setColor(Color.red);
	        g.fillRect(x-25, c.y+heigh+60, 50, 10);
	        g.setColor(Color.GREEN);
	        g.fillRect(x-25, c.y+heigh+60, health*50/health_Max, 10);
	        test=new ArrayList<>(list);
	        		
	        drawbullet(g,test);
	       
	       	
	}}

}
