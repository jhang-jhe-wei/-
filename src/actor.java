import java.awt.Graphics;
import java.io.File;

import org.magiclen.media.AudioPlayer;

import edu.fcps.karel2.Item;
import edu.fcps.karel2.util.Coordinate;
/**
 * 
 * @author 張哲瑋 wells
 *
 */
public abstract class actor extends Item {
	protected int dx;
	protected int dy;
	protected boolean isDead;
	protected int width;
	protected int heigh;
	protected int dam;
	protected int health;
	
	

	public actor(int x, int y) {
		super(x, y); 
		
		isDead=false;
		
		
	}
    /**
     * 
     * 設定x值
     */
	public void setX(int a) {
		x=a;
	}
	 /**
     * 
     * 設定y值
     */
	public void setY(int a) {
		y=a;
	}
	 /**
     * 
     * 設定dx值
     */
	public void setdx(int dx) {
		this.dx=dx;
	}
	 /**
     * 
     * 設定dy值
     */
	public void setdy(int dy) {
		this.dy=dy;
	}
	
	 /**
     * 
     * 設定isDead為true
     */
	public void isDead() {
		isDead=true;
	}
	 /**
     * 
     *取得該單位寬度
     */
	public int getWidth() {
		return width;
	}
	/**
     * 
     *取得該單位長度
     */
	public int getHeigh() {
		return heigh;
	}
	/**
     * 
     *取得該單位攻擊力
     */
	public int getDam() {
		return dam;
	}
	/**
     * 
     *設定該單位攻擊力
     */
	public void setDam(int d) {
		dam=d;
	}
	/**
     * 
     *設定該單位血量
     */
	public void setHealth(int h) {
		health=h;
	}
	/**
     * 
     *計算該單位血量
     */
	public void sumHealth(int h) {
		if(health!=-2)
		health-=h;
	}
	/**
     * 
     *取得該單位血量
     */
	public int getHealth() {
		return health;
	}
	/**
     * 
     *檢查該單位與其他單位是否有碰撞
     */
	public boolean collide(actor test) {
		if(!GameBackend.getCurrent().getprotagonists().isEmpty()) {
			if(GameBackend.getCurrent().getprotagonists().get(0).shift) {
				return false;
			}
		}
		 double d = GameLeader.distance(test.getX(),test.getY(),this.getX(),this.getY()); 
	     if(d<= (width+heigh)/4+(test.getWidth()+test.getHeigh())/4) {
					new Boom(test.getX(), test.getY());
					
				return true;	
			}
			  return false;
	}
	


	
	
	
}
