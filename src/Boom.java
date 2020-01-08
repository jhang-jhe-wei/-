import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;

import org.magiclen.media.AudioPlayer;

import com.sun.org.apache.bcel.internal.generic.I2F;

import edu.fcps.karel2.Item;
import edu.fcps.karel2.util.Coordinate;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;
import sun.misc.GC;
import sun.nio.cs.ext.ISCII91;
/**
 * 
 * @author 張哲瑋 wells
 *
 */
public class Boom extends actor {
	private int counter;
	private int counter_MAX=15;	
	private int size;	
	static ImageIcon thomas=new ImageIcon("picture/boom.gif");
	static ImageIcon power=new ImageIcon("picture/boomex.gif");	
	static File dead=new File("sounds/dead.wav");	
	static AudioPlayer collidesounds2=new AudioPlayer(dead);
	static AudioPlayer collidesounds=new AudioPlayer(dead);
	public Boom(int x,int y) {
		
		super(x, y);
		width=30;
		heigh=30;
		GameBackend.getCurrent().addBoom(this);		
		play();
	}
	
	public Boom(int x,int y,int size,int counter) {
		super(x, y);
		counter_MAX=counter;
		width=size;
		heigh=size;
		this.size=size;
		GameBackend.getCurrent().addBoom(this);		
		play();
	}
	public void play() {
		if((width+heigh)/2>50) {			
			collidesounds.play();
			
		}
		else {			
			collidesounds2.play();
			if(collidesounds2.getAudioPosition()>50000) {
				collidesounds2.setAudioPosition(10);
			}
		         
		}
		
	}
	/**
	 * 
	 * 確認該單位isDead是否為true，並做刪除的動作
	 */
	public void check() {
		if(isDead) {			
			GameBackend.getCurrent().removeBoom(this);
		}
	}
	/**
	 * 
	 * 把該單位isDead設為true
	 */
	public void setisDead(boolean a) {
		isDead=a;
	}


	@Override
	public void render(Graphics g, Coordinate c) {
		
		check();
		counter++;
		if(counter==counter_MAX) {
			setisDead(true);
		}
		
		if((width+heigh)/2>50) {
		g.drawImage(power.getImage(), c.x, c.y, width,heigh,null);
		
		}
		else {
			g.drawImage(thomas.getImage(), c.x, c.y, width,heigh,null);
		}

	}

}
