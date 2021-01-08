package JSONHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class JSONgest 
{
	
	
	public double mediaTemp(JSONArray ja)
	{
			double media = 0.0;
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
	
	public double mediaMin(JSONArray ja)
	{
			double media = 0.0;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				media += (int)jo.get("temp_min");
				
			}
			media = (media/i);

			return media;
	}
	
	public double mediaMax(JSONArray ja)
	{
			double media = 0.0;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				media += (int)jo.get("temp_max");
				
			}
			media = (media/i);

			return media;
	}
		
	
}
