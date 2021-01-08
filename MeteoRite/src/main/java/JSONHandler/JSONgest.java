package JSONHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class JSONgest 
{
	
	
	public double media(JSONArray ja)
	{
			double media = 0;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				media += (int)jo.get("temp");
				
			}
			media = (media/i);

			return media;
	}
		
	public void prova()
		{
			System.out.println("FUNZIONA");
		}

}
