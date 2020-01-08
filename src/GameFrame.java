import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * 
 * @author 張哲瑋wells
 *
 */
public class GameFrame extends JFrame{
	private static GameFrame current=null;
	Timer repaint=new Timer();
	public GameFrame(GameBackend gb) {
		super();
		
		setTitle("星際大戰_張哲瑋_B10730224");
		current=this;		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		
		setSize(GameLeader.Frame_Width, GameLeader.Frame_Heigh);
		setLocation(0, 0);
		setContentPane(new GamePanel(gb));
		setResizable(false);
		setVisible(true);		
		repaint.schedule(new TimerTask() {
		
			@Override
			public void run() {
											   
			   GamePanel.getCurrent().repaint();								
			}
		}, 30,15);

		addWindowListener(new WindowListener() {
			
			
			@Override
			public void windowClosing(WindowEvent e) {			
				repaint.cancel();
				int value= JOptionPane.showConfirmDialog(null, "確定要關閉嗎？");
				if (value==JOptionPane.OK_OPTION) {
					GameFrame.getCurrent().close();
					repaint.cancel();
					System.exit(0);
					
				}
				else  {
					repaint=new Timer();
					repaint.schedule(new TimerTask() {
						
						@Override
						public void run() {
														   
						   GamePanel.getCurrent().repaint();								
						}
					}, 30,15);
				}
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}			
			
		});
		new protagonist(600, 600,3,3);	
	}
	
     void close(){
    	 GameBackend.getCurrent().close();
    	 GamePanel.getCurrent().close();
    	 current=null;
    	 dispose();
     }
    
     /**
      * 
      * 取的當前的GameFrame
      */
     public static GameFrame getCurrent() {
    	 return current;
     }
    
	
}
