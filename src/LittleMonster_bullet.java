import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import edu.fcps.karel2.Item;
import edu.fcps.karel2.util.Coordinate;
/**
 * 
 * @author 張哲瑋wells
 *
 */

public class LittleMonster_bullet extends actor {
    ImageIcon thomas=new ImageIcon("picture/lit_but.png");
 
	public LittleMonster_bullet(int x, int y,int dx,int dy) {
		super(x, y);
		this.dx=dx;
		this.dy=dy;
		dam=5;
		width=50;
		heigh=50;
				
	}
	

	/**
	 * 
	 * 確認該單位isDead是否為true，並做刪除的動作
	 */
	public boolean check() {
		if(!isDead) {
		if(GamePanel.getCurrent().isVisible(this)!=true) {
			isDead=true;
			return true;
			}
		for(protagonist p:GameBackend.getCurrent().getprotagonists()) {
			if(collide(p)) {
			p.sumHealth(dam);	
			isDead=true;
			new Boom(x,y);
			
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
