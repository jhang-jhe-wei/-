import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
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
public class Boss extends actor {
	private ArrayList<Boss_bullet> list;
	private ArrayList<MiddleMonster_bullet> list1;
	private ArrayList<LittleMonster_bullet> list2;
	private int health_Max=2000;
	private ArrayList<Boss_bullet> test;
	private ArrayList<MiddleMonster_bullet> test1;
	private ArrayList<LittleMonster_bullet> test2;
	private int counter;
	Timer t1 = new Timer();
    static ImageIcon thomas=new ImageIcon("picture/boss.png");
    /**
	 * Boss的constructor
	 */
	public Boss(int x, int y,int dx,int dy) {
		super(x, y);		
		this.dx=dx;
		this.dy=dy;
		width=300;
		heigh=300;
		health=health_Max;
		
		t1.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				attack();
				
			}
		},2000,700);
		list=new ArrayList<>();
		list1=new ArrayList<>();
		list2=new ArrayList<>();
		GameBackend.getCurrent().addBoss(this);
	}
	/**
	 *  確認該單位isDead是否為true，並做刪除的動作
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
				new Boom(x, y,(width+heigh),100);
		
			GameBackend.getCurrent().removeBoss(this);
			
		}
	}
	/**
	 * 攻擊
	 */
	public void attack() {
		counter++;		
		if (counter%3==0&&counter<15) {
			dx=(int)Math.random()*10-5;
		}
		if(counter%5==0) {
			
			list.add(new Boss_bullet(x, y));
		}			
		if(counter<15&&health>500) {
			
			list2.add(new LittleMonster_bullet(x,y, 0, GameLeader.lit_bul_delta));			
			list2.add(new LittleMonster_bullet(x,y, GameLeader.lit_bul_delta/3, GameLeader.lit_bul_delta));
			list2.add(new LittleMonster_bullet(x,y, GameLeader.lit_bul_delta/2, GameLeader.lit_bul_delta));
			list2.add(new LittleMonster_bullet(x,y, GameLeader.lit_bul_delta, GameLeader.lit_bul_delta));
			list2.add(new LittleMonster_bullet(x,y, GameLeader.lit_bul_delta/-2, GameLeader.lit_bul_delta));
			list2.add(new LittleMonster_bullet(x,y, GameLeader.lit_bul_delta/-3, GameLeader.lit_bul_delta));
		}
		if(counter==15) {
			dx=0;
			dy=0;
		}
		if((counter>15||health<500)&&counter<=16) {
			
			for(int i=0;i<15;i++) {			
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/5, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/4, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/3, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/2, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/-2, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/-3, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/-4, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/-5, GameLeader.lit_bul_delta));
			
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/5+GameLeader.lit_bul_delta, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/4+GameLeader.lit_bul_delta, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/3+GameLeader.lit_bul_delta, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/2+GameLeader.lit_bul_delta, GameLeader.lit_bul_delta));			
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/-2-GameLeader.lit_bul_delta, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/-3-GameLeader.lit_bul_delta, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/-4-GameLeader.lit_bul_delta, GameLeader.lit_bul_delta));
			list1.add(new MiddleMonster_bullet(x,y, GameLeader.lit_bul_delta/-5-GameLeader.lit_bul_delta, GameLeader.lit_bul_delta));
			for(int j=0;j<5;j++) {
				GameLeader.pause();	
			}
			}
				
		}
		if(counter==20) {
			new LittleMonster(100, 250, 2, 0);
			new LittleMonster(200, 250, 2, 0);
			new LittleMonster(300, 250, 2, 0);
			new LittleMonster(500, 250, 2, 0);
			new LittleMonster(600, 250, 2, 0);
			new LittleMonster(700, 250, 2, 0);
			counter=0;
			}		
	}
	/**
	 * 畫圖
	 */
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
		
	g.drawImage(thomas.getImage(),c.x,c.y,width,heigh,null);
	g.setColor(Color.red);
    g.fillRect(x-25, c.y+heigh+10, 50, 10);
    g.setColor(Color.GREEN);
    g.fillRect(x-25, c.y+heigh+10, health*50/health_Max, 10);
    test=new ArrayList<>(list);
    test1=new ArrayList<>(list1);
    test2=new ArrayList<>(list2);
    for(Boss_bullet p:test) {
    	p.render(g, GamePanel.getCurrent().coordinateToPixel(p));
    	if(p.check()) {
    		list.remove(p);
    	}
    }
	 for(MiddleMonster_bullet p:test1) {
		    	p.render(g, GamePanel.getCurrent().coordinateToPixel(p));
		    	if(p.check()) {
		    		list.remove(p);
		    	}
		    }
	 for(LittleMonster_bullet p:test2) {
	    	p.render(g, GamePanel.getCurrent().coordinateToPixel(p));
	    	if(p.check()) {
	    		list.remove(p);
	    	}
	    }
		

	}

}}
