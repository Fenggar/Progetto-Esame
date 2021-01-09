package JSONHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ProgettoEsame.MeteoRite.Model.Services;


public class JSONgest 
{
	
	
	public double mediaTemp(JSONArray ja)
	{
		System.out.println("MEDIA TEMP");
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
	
	public double minMin(JSONArray ja)
	{
		System.out.println("MIN MIN");
			double m = 0.0;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				if((int)(jo.get("temp_min")) < m)
				{
					m += (int)jo.get("temp_min");
				}
			}

			return m;
	}
	
	public double maxMax(JSONArray ja)
	{
		System.out.println("MAX MAX");
			double m = 0.0;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				if((int)(jo.get("temp_max")) > m)
				{
					m= (int)jo.get("temp_max");
				}
			}

			return m;
	}
		
	public JSONObject mediaBox(JSONArray arr)
	{
		System.out.println("MEDIA BOX");
		//JSONArray fin = new JSONArray();
		JSONObject box = new JSONObject();
		
		Services serv = new Services();
		
		
			double t = mediaTemp(arr);
			double min = minMin(arr);
			double max = maxMax(arr);
			//j++;
			
			box = serv.boxer(t, min, max);
		
		return box;
	}
	
	public JSONArray arrayLoader(JSONArray jarr)
	{
		System.out.println("ARRAY LOADER");
		
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
	
	
	public void dataFilter(JSONArray ja, JSONArray arr[])
	{
		System.out.println("DATA FILTER in teoria era questo che dava problemi");
		//se è effettivamente qui il problema, passare come parametri 2 array (ja e arr[i] facendo un for dall'altra parte
		//cambiare quindi il for in modo che mette gli elementi solo se data corrisponde
		
		JSONObject app = (JSONObject) ja.get(0);
		String d = (String) app.get("data");
		//JSONArray arr[] = new JSONArray[5];
		
		int j = 0;	
		for(int i = 0; i<ja.size();i++)
		{
			if(j<5)
			{
			app = (JSONObject) ja.get(i);
			String d2 = (String) app.get("data");
			
			if(d2.equals(d))
			{
				arr[j].add(app);
				System.out.println("D2 è uguale. j vale:"+j +"ci ho messo" +app);
			}
			else
			{
				j++;
				arr[j].add(app);
				System.out.println("D2 non è uguale. incremento j adesso vale: "+j +"ci ho messo:"+app);
			}
			
			}
			else
				break;
		}
		
		//return arr;
	}
	
	public JSONArray finalArrayLoader(JSONArray arr[])
	{
		System.out.println("FINAL ARRAY LOADER");
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
	

