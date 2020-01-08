import java.util.ArrayList;

import edu.fcps.karel2.util.Debug;
/**
 * @author 張哲瑋 wells 
 */
public class GameLeader {
	public static final int Frame_Width=1280;
	public static final int Frame_Heigh=720;
	public static final int lit_bul_delta=10;
	public static final int mid_bul_delta=7;
	public static final int boss_bul_delta=1;
	public static final int pro_bul_delta=15;
	
	/**
	 *  
	 * THE WORLDDDDDDDDDDDDDDDDDD!!!!!!!!!!!!!!!!
	 */
	public static void openWorld()
	{
	    closeWorld();
		new GameFrame(new GameBackend());
	}
	/**
	 * 給予咒文並生成怪物的魔法陣
	 */
	public static void continueRount(String mapName)
	{
		GameBackend.getCurrent().parseMap(mapName);
		
	}
	/**
	 * 9秒経過ッ!
     *9秒も止められたぞッ!
     *しかし時を止めていられるのは今は9秒が限界といったところか…
     *WRYYAAAAA!
     *スタンドのパワーを全開だッ! 
     *承太郎!
	 */
	static void closeWorld()
	{
		if(GameFrame.getCurrent() != null)
			GameFrame.getCurrent().close();
	}
	/**
	 * 回傳雙方直線距離
	 */
	public static double distance(double x1, double y1, double x2, double y2)
	{
	   double x=Math.abs(x2-x1);
	   double y=Math.abs(y2-y1);		   
   return  (Math.sqrt((x*x)+(y*y)));	 // enter the calculation here.
}
	/**
	 * ザ・ワールド，時は止まる！
	 */
	public static void pause() {
		try {
			Thread.sleep(30);
		} catch (Exception e) {
			
		}
	}

}