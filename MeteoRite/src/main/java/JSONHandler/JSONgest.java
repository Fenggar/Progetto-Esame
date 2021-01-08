package JSONHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ProgettoEsame.MeteoRite.Model.Services;


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
		
	public JSONObject mediaBox(JSONArray arr)
	{
		//JSONArray fin = new JSONArray();
		JSONObject box = new JSONObject();
		
		Services serv = new Services();
		
		
			double t = mediaTemp(arr);
			double min = mediaMin(arr);
			double max = mediaMax(arr);
			//j++;
			
			box = serv.boxer(t, min, max);
		
		return box;
	}
	
	public JSONArray arrayLoader(JSONArray jarr)
	{
		
		JSONObject ogg = null;
		JSONObject box = null;
		String data= null;
	
		Services serv = new Services();
		
		JSONArray jar = new JSONArray();
		// DICHIARAZIONE ARRAY DI JSONARRAY JSONArray arr[] = new JSONArray[5];
		
		
		for(int i=0;i< jarr.size(); i++)
		{
			ogg = (JSONObject) jarr.get(i); //qua c'è un elemento di list[]
			box = serv.extrapolator(ogg); //qua c'è il json con le temperature
			data= serv.jsonToDay(ogg); //qua c'è la data 
			box.put("data", data);
			
			jar.add(box);
			
		}
		
		return jar;
	}
	
	
	public JSONArray dataFilter(JSONArray ja, JSONArray arr[])
	{
		JSONObject app = (JSONObject) ja.get(0);
		String d = (String) app.get("data");
		//JSONArray arr[] = new JSONArray[5];
		
		int j = 0;	
		for(int i = 0; i<ja.size();i++)
		{
			app = (JSONObject) ja.get(i);
			String d2 = (String) app.get("data");
			
			if(d2.equals(d))
			{
				arr[j].add(app);
			}
			else
			{
				j++;
				arr[j].add(app);
			}
		}
		
		return ja;
	}
	
	public JSONArray finalArrayLoader(JSONArray arr[])
	{
		JSONArray fin = new JSONArray();
		JSONObject box = new JSONObject();
		
		for(int k =0; k < 5; k++)
		{
			box=mediaBox(arr[k]);
			fin.add(box);
		}
		
		/*
		 * questa è una stampa (che in teoria funziona)
		for(int k = 0; k< fin.size();k++)
		{
			box = (JSONObject) fin.get(k);
			System.out.println(k+") "+box);
		}
		*
		*/
		return fin;
	}
}
	

