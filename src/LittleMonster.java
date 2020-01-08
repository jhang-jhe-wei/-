import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;

import com.sun.java.swing.plaf.windows.WindowsTreeUI.CollapsedIcon;

import edu.fcps.karel2.Item;
import edu.fcps.karel2.util.Coordinate;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;
/**
 * 
 * @author 張哲瑋 wells
 *
 */
public class LittleMonster extends actor {	
	static ImageIcon thomas=new ImageIcon("picture/lit.png");
	private List<LittleMonster_bullet> list;
	private int health_Max=50;
	private List<LittleMonster_bullet> test;
	Timer t1 = new Timer();
	public LittleMonster(int x, int y,int dx,int dy) {
		super(x, y);		
		this.dx=dx;
		this.dy=dy;
		width=50;
		heigh=30;
		health=health_Max;
		t1.schedule(new TimerTask() {
			
			@Override
			public void run() {
				attack();
				
			}
		},2000,1000);
		list= Collections.synchronizedList(new ArrayList<LittleMonster_bullet>());
		GameBackend.getCurrent().addLittleMonster(this);
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
			list.clear();
			test.clear();	
				new Boom(x, y,(width+heigh),50);
		
			if(!GameBackend.getCurrent().getprotagonists().isEmpty()) {
				GameBackend.getCurrent().getprotagonists().get(0).sumPower(50);
				GameBackend.getCurrent().getprotagonists().get(0).sumHealth(-3);
			}	
			GameBackend.getCurrent().removeLittleMonster(this);
			
		}
	}
	/**
	 * 攻擊
	 */
	public void attack() {
		list.add(new LittleMonster_bullet(x, y,0, GameLeader.lit_bul_delta));
		
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
        
			 for(LittleMonster_bullet p:test) {
        	p.render(g, GamePanel.getCurrent().coordinateToPixel(p));
        	if(p.check()) {
        		list.remove(p);
        	
       
			}}}}}


