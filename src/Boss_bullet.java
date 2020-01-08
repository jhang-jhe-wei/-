import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import edu.fcps.karel2.Item;
import edu.fcps.karel2.util.Coordinate;
/**
 * 
 * @author 張哲瑋 wells
 *
 */
public class Boss_bullet extends actor {
	private int sum;
	private int counter;
	int delta=GameLeader.boss_bul_delta;
	ImageIcon thomas=new ImageIcon("picture/boss_bul.gif");
	public Boss_bullet(int x, int y) {
		super(x, y);
		this.dx=dx;
		this.dy=dy;
		dam=20;
		width=100;
		heigh=100;
	}
	/**
	 * 
	 * 確認該單位isDead是否為true，並做刪除的動作
	 */
	public boolean check() {
		if(!isDead) {
		if(GamePanel.getCurrent().isVisible(this)!=true) {
			isDead=true;
			}
		for(protagonist p:GameBackend.getCurrent().getprotagonists()) {
			if(collide(p)) {
			p.sumHealth(dam);	
			isDead=true;
			
			}
		}}
		
		if(isDead) {
			new Boom(x, y,(width+heigh)/2,100);	
			
			return true;
		}
		return false;
	}
	@Override
	public void render(Graphics g, Coordinate c) {
		counter++;
		if(counter%10==0) {
			delta++;
		}
		if(counter==150) {
			isDead=true;
		}
		if(!isDead) {
			if(!GameBackend.getCurrent().getprotagonists().isEmpty()) {
		sum=(int)(Math.abs(x-GameBackend.getCurrent().getprotagonists().get(0).getX())
				+Math.abs(y-GameBackend.getCurrent().getprotagonists().get(0).getY()));
		dx=delta*(GameBackend.getCurrent().getprotagonists().get(0).getX()-x)/sum;
		dy=delta*(GameBackend.getCurrent().getprotagonists().get(0).getY()-y)/sum;}
		x+=dx;
		y+=dy;
		g.drawImage(thomas.getImage(),c.x,c.y,width,heigh,null);
			
	}
	
}}
