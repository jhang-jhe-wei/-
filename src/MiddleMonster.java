import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import edu.fcps.karel2.Item;
import edu.fcps.karel2.util.Coordinate;
/**
 * 
 * @author 張哲瑋wells
 *
 */
public class MiddleMonster extends actor {
	private List<MiddleMonster_bullet> list;
	private int sum;
	private int health_Max=300;
	private List<MiddleMonster_bullet> test;
	Timer t1 = new Timer();
	static ImageIcon thomas=new ImageIcon("picture/mid.png");
	public MiddleMonster(int x, int y,int dx,int dy) {
		super(x, y);		
		this.dx=dx;
		this.dy=dy;
		dam=1;
		width=150;
		heigh=150;
		health=health_Max;
		t1.schedule(new TimerTask() {
			
			@Override
			public void run() {				
						attack();
								
			}
		},2000,2000);
		list=new ArrayList<>();
		GameBackend.getCurrent().addMiddleMonster(this);
	}
	/**
	 * 
	 * 確認該單位isDead是否為true，並做刪除的動作
	 */
	public void check() {
		if (health<=0) {
			isDead=true;
		}
		for(protagonist p:GameBackend.getCurrent().getprotagonists()) {
			if(collide(p)) {
			p.sumHealth(dam);	
			p.isDead();
				
			}
		}
		if (isDead) {
			t1.cancel();
			if(!GameBackend.getCurrent().getprotagonists().isEmpty()) {
				GameBackend.getCurrent().getprotagonists().get(0).sumPower(100);
				GameBackend.getCurrent().getprotagonists().get(0).sumHealth(-20);
			}
		list.clear();
		test.clear();	
		new Boom(x, y,(width+heigh),50);

		GameBackend.getCurrent().removeMiddleMonster(this);
		
	}
	}
    /**
     * 攻擊
     */
	public void attack() {
		
		if(!GameBackend.getCurrent().getprotagonists().isEmpty()) {
		sum=(int)(Math.abs(x-GameBackend.getCurrent().getprotagonists().get(0).getX())
				+Math.abs(y-GameBackend.getCurrent().getprotagonists().get(0).getY()));
		
		for(int i=0;i<10;i++) {
			list.add(new MiddleMonster_bullet(x, y, (GameLeader.pro_bul_delta*(GameBackend.getCurrent().getprotagonists().get(0).getX()-x)/sum)
					, (GameLeader.pro_bul_delta*(GameBackend.getCurrent().getprotagonists().get(0).getY()-y)/sum)));

					
			
			GameLeader.pause();
			GameLeader.pause();
	}	
		}
	}
	@Override
	public void render(Graphics g, Coordinate c) {
		check();
		if(!isDead) {
			if(x<=width) {
				dx*=-1;
			}
			else if(x>=GameFrame.getCurrent().getWidth()-width) {
				dx*=-1;
			}
			if(y<=heigh) {
				dy*=-1;
			}
			else if(y>=GameFrame.getCurrent().getHeight()-heigh) {
				dy*=-1;
			}
			x+=dx;
			y+=dy;
			g.drawImage(thomas.getImage(),c.x,c.y,width,heigh,null);
			g.setColor(Color.red);
	        g.fillRect(x-25, c.y+heigh+10, 50, 10);
	        g.setColor(Color.GREEN);
	        g.fillRect(x-25, c.y+heigh+10, health*50/health_Max, 10);
	        test=new ArrayList<>(list);	       
	        for(MiddleMonster_bullet p:test) {
	        	p.render(g, GamePanel.getCurrent().coordinateToPixel(p));
	        	if(p.check()) {
	        		list.remove(p);
	        	
	       
				}
	       	        
		}
	}
	}
}
