import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.fcps.karel2.WorldBackend;
import edu.fcps.karel2.util.Debug;
import edu.fcps.karel2.xml.Element;
/**
 * 
 * @author 張哲瑋wells
 *
 */
public class GameParser {
	private static String propPrepend = "loadProperties_";
	private static String objPrepend = "addObject_";

	/**
	 * 
	 * 解析xml並呼叫GameBackend中相應的method
	 */
        
        public static void initiateMap(Element root)
	{
		GameBackend gb=GameBackend.getCurrent();
		
		Element objects = root.get("objects");

		
		if(objects != null)
			for(Element e : objects.getElements())
			{
				try {
					Method m = GameBackend.class.getMethod(objPrepend + e.getName() , new Class[] { Class.forName("edu.fcps.karel2.xml.Attributes") });
					m.invoke(gb, new Object[] { e.getAttributes() });
				} catch (SecurityException e1) {
					e1.printStackTrace();
					throw e1;
				} catch (NoSuchMethodException e1) {
					Debug.printWarning("Could not find method " + objPrepend + e.getName() + " for object " + e.getName() + "!  Ignoring...");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
					System.exit(2);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
					System.exit(3);
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
					System.exit(4);
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
					System.exit(5);
				} 
			}
	}
}
