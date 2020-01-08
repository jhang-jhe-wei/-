import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import edu.fcps.karel2.Item;
import edu.fcps.karel2.util.Coordinate;
/**
 * 
 * @author 張哲瑋wells
 *
 */
public class protagonist_bullet extends actor {	
	ImageIcon thomas=new ImageIcon("picture/pro_bul.png");
    public protagonist_bullet(int x, int y,int dx,int dy) {
    	super(x, y);
		this.dx=dx;
		this.dy=dy;
		dam=5;
		width=50;
		heigh=70;	
		
	}	
    /**
	 * 
	 * 確認該單位isDead是否為true，並做刪除的動作
	 */
    public boolean check() {
		if(GamePanel.getCurrent().isVisible(this)!=true) {
			isDead=true;
			}
		if(!isDead) {
		for(LittleMonster p:GameBackend.getCurrent().getLittleMonsters()) {
			if(collide(p)) {
			p.sumHealth(dam);	
			isDead=true;
			new Boom(x,y,50,10);
			
			}
		}
		for(MiddleMonster p:GameBackend.getCurrent().getMiddleMonsters()) {
			if(collide(p)) {
			p.sumHealth(dam);	
			isDead=true;
			new Boom(x,y,50,10);
			
			}
		}
		for(Boss p:GameBackend.getCurrent().getBosses()) {
			if(collide(p)) {
			p.sumHealth(dam);	
			isDead=true;
			new Boom(x,y,50,10);
		
			}
		}}
		
		if(isDead) {
		
			return true;
		}
		return false;
	}
	
	@Override
	public void render(Graphics g, Coordinate c) {
		if(!isDead) {
			y+=dy;
			x+=dx;
			g.drawImage(thomas.getImage(),c.x,c.y,width,heigh,null);
			}
		
	

	}

}
