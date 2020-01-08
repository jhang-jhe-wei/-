

import edu.fcps.karel2.util.*;
import edu.fcps.karel2.xml.*;
import sun.java2d.opengl.OGLContext;

import java.io.*;
import java.net.*;
import java.util.*;

import com.sun.org.apache.bcel.internal.generic.RETURN;
/**
 * 
 * @author 張哲瑋 wells
 *
 */
public class GameBackend {
	private static GameBackend current=null;

	
	private List<LittleMonster> littleMonsters;
	private List<MiddleMonster> middleMonsters;
	private List<Boss> bosses;
	private List<protagonist>protagonists;
	private List<Boom>booms;
	

	
	public GameBackend() {
		super();
		current=this;
		littleMonsters= new ArrayList<LittleMonster>();
		middleMonsters=new ArrayList<MiddleMonster>();
		bosses=new ArrayList<Boss>();
		protagonists=new ArrayList<protagonist>();
		booms=new ArrayList<Boom>();
		
			
		
		
	}
	/**
	 * 
	 * 確認場上還有敵人
	 */
	public boolean live() {
		
		if(littleMonsters.size()==0) {		
		
		if(middleMonsters.size()==0) {			
		
		if(bosses.size()==0) {
		if(booms.size()==0) {	
			littleMonsters.clear();
			middleMonsters.clear();
			bosses.clear();			
			booms.clear();
			
			return false;
		}}}}
		
		return true;
	}
	/**
	 * 
	 * 經由GameParser解析後呼叫的method
	 */
	public void addObject_LittleMonster(Attributes a)
	{
		int x=Integer.parseInt(a.get("x"));
		int y=Integer.parseInt(a.get("y"));
		int dx=Integer.parseInt(a.get("dx"));
		int dy=Integer.parseInt(a.get("dy"));
		new LittleMonster(x, y, dx, dy);
	}
	/**
	 * 
	 * 經由GameParser解析後呼叫的method
	 */
	public void addObject_MiddleMonster(Attributes a)
	{
		int x=Integer.parseInt(a.get("x"));
		int y=Integer.parseInt(a.get("y"));
		int dx=Integer.parseInt(a.get("dx"));
		int dy=Integer.parseInt(a.get("dy"));
		new MiddleMonster(x, y, dx, dy);
	}
	/**
	 * 
	 * 經由GameParser解析後呼叫的method
	 */
	public void addObject_Boss(Attributes a)
	{
		int x=Integer.parseInt(a.get("x"));
		int y=Integer.parseInt(a.get("y"));
		int dx=Integer.parseInt(a.get("dx"));
		int dy=Integer.parseInt(a.get("dy"));
		new Boss(x, y, dx, dy);
	}
	/**
	 * 
	 * 給予咒文並生成怪物的魔法陣
	 */
	void parseMap(String mapName)
	{
		 Element e = new XMLParser().parse(getInputStreamForMap(mapName));
		 GameParser.initiateMap(e);
	}
	/**
	 * 
	 * 讀取目標的資料
	 */
	private InputStream getInputStreamForMap(String fileName)
	{
		FileInputStream f = null;
		
		try {
			if(fileName == null)
				throw new FileNotFoundException();
			
			f = new FileInputStream(new File(fileName));
		}
		catch (FileNotFoundException e)
		{
			if(fileName != null)
				Debug.printWarning("Map " + fileName + " not found, using default map file...");
				System.exit(1);
				}
		return f;
		}
	
	void close() {		
		for(Boss a:bosses) {
			a.isDead=true;
		}
		booms.clear();
		littleMonsters.clear();
		middleMonsters.clear();		
		
	}
	
	 List<LittleMonster>getLittleMonsters(){
		return littleMonsters;
	}
	 
	List<MiddleMonster>getMiddleMonsters(){
		return middleMonsters;
	}
	
	List<Boss>getBosses(){
		return bosses;
	}	
	
	List<protagonist>getprotagonists(){
		return protagonists;
	}
	
	List<Boom>getbooms(){
		return booms;
	}
	
	void addLittleMonster(LittleMonster l) {
		getLittleMonsters().add(l);
	}
	
	void addMiddleMonster(MiddleMonster m) {
		getMiddleMonsters().add(m);
	}
	
	void addBoss(Boss b) {
		getBosses().add(b);
	}
	
	void addprotagonist(protagonist p) {
		getprotagonists().add(p);
	}
	
	void addBoom(Boom p) {
		getbooms().add(p);
	}
	
	void removeLittleMonster(LittleMonster l) {
		
		getLittleMonsters().remove(l);
	}
	
	void removeMiddleMonster(MiddleMonster m) {
		getMiddleMonsters().remove(m);
	}
	
	void removeBoss(Boss b) {
		getBosses().remove(b);
	}
	
	void removeprotagonist(protagonist p) {
		getprotagonists().remove(p);
	}
	
	void removeBoom(Boom p) {
		getbooms().remove(p);
	}
	/**
	 * 
	 * 取得當前的GameBackend
	 */
	public static GameBackend getCurrent() {
		return current;
	}
}
