import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.magiclen.media.AudioPlayer;

import com.sun.corba.se.impl.orb.ParserTable.TestAcceptor1;

import javafx.event.ActionEvent;

public class test {
	static boolean ctrl=false;
	
	public static void main(String[] args) {
		File boom=new File("sounds/collide1.wav");
		AudioPlayer collidesounds2=new AudioPlayer(boom);
		int i=0;
		JFrame jFrame=new JFrame("test");
		jFrame.setSize(400, 400);
		jFrame.setVisible(true);
		jFrame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown()) {					
					ctrl=true;
				}
				
			}
		});
		collidesounds2.play();
		while(true) {
			if(ctrl||!jFrame.isActive()) {
				break;
			}
	    System.out.println(collidesounds2.getStatus());
	    
			
			if(collidesounds2.getAudioPosition()>25000)
		         collidesounds2.setAudioPosition(500);
			
		}
		
      
	}

}
