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
				media += (double)jo.get("temp");
				
			}
			media = (media/i);
			
			System.out.println("Valore media: "+media);

			return media;
	}
	
	public double minMin(JSONArray ja)
	{
		System.out.println("MIN MIN");
			double m = 110.10;
			double app = 0.0;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				System.out.println("SONO NEL FOR DI MIN MIN");
				
				jo = (JSONObject) ja.get(i);
				
				System.out.println(" -messo valore su jo; temp_min =" +jo.get("temp_min"));
				app = (double)jo.get("temp_min");
				
				System.out.println(" -messo valore su app");
				
				if( app < m)
				{
					System.out.println("SONO NELL'IF DI MIN MIN");
					m = app;
				}
			}
			System.out.println("Valore min: " +m);

			return m;
	}
	
	public double maxMax(JSONArray ja)
	{
		System.out.println("MAX MAX");
			double m = 0.0;
			double app = 0.0;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				app = (double)jo.get("temp_max");
				if( app > m)
				{
					m = app;
					System.out.println("SONO NELL'IF di MAXMAX: "+m);
				}
			}
			System.out.println("Valore max: "+m);

			return m;
	}
	
	public String feelsLike(JSONArray ja)
	{
		String f;
		double confronto = 0.0;
		
		
		double media = 0.0;
		JSONObject jo = new JSONObject();
		
		int i= 0;
		for( i=0 ; i<ja.size();i++)
		{
			jo = (JSONObject) ja.get(i);
			f = jo.get("feels_like").toString();
			confronto= Double.valueOf(f);
			media += confronto;
		}
		media = (media/i);
		
		System.out.println("Valore media: "+media);
		
		confronto = media;
		
		if(confronto < 0)
		{
			f = "io non uscirei";
		}
		else if(confronto < 10)
		{
			f = "fa freschino";
		}
		else if(confronto < 18)
		{
			f="lascia a casa il giubbetto";
		}
		else if(confronto >= 18)
		{
			f = "mettete le maniche corte";
		}
		else
		{
			f="la temperatura percepita è una menzogna";
		}
		
		
		return f;
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
			String feel = feelsLike(arr);
			//j++;
			
			box = serv.boxer(t, min, max,feel);
		
		return box;
	}
	
	public JSONArray arrayLoader(JSONArray jarr)
	{
		System.out.println("ARRAY LOADER");
		
		JSONObject ogg = null;
		JSONObject box = null;
		String data= null;
	
		Services serv = new Services();
		
		JSONArray boxArray = new JSONArray();
		// DICHIARAZIONE ARRAY DI JSONARRAY JSONArray arr[] = new JSONArray[5];
		
		
		for(int i=0;i< jarr.size(); i++)
		{
			ogg = (JSONObject) jarr.get(i); //qua c'è un elemento di list[]
			box = serv.extrapolator(ogg); //qua c'è il json con le temperature
			data= serv.jsonToDay(ogg); //qua c'è la data 
			box.put("data", data);
			
			System.out.println("SONO DENTRO IL FOR DI ARRAYLOADER; ogg:" +ogg +" box: "+box);
			
			boxArray.add(box);
		}
		
		return boxArray;
	}
	
	
	public JSONArray[] dataFilter(JSONArray ja, JSONArray arr[])
	{
		System.out.println("DATA FILTER");
		
		JSONObject app = (JSONObject) ja.get(0); //salvo il primo elemento per il confronto della data
		String d = (String) app.get("data"); //salvo la data per il confronto
		String d2;
		
		JSONArray test = new JSONArray();
		//JSONArray arr[] = new JSONArray[5];
		
		int j = 0;	
		for(int i = 0; i<ja.size();i++)
		{
			System.out.println("SONO NEL FOR DI DATA FILTER");
			if(j<5)
			{
				app = (JSONObject) ja.get(i);
				d2 = (String) app.get("data");
				
				System.out.println("SONO NEL PRIMO IF DI DATAFILTER");
				
				if(d2.equals(d))
				{
					System.out.println("SONO NEL SECONDO IF DI DATAFILTER");
					test.add(app);
					//arr[j].add(app);
					System.out.println("D2 è uguale. j vale:"+j +"ci ho messo" +app);
				}
				else
				{
					System.out.println("SONO NEL PRIMO ELSE DI DATAFILTER");
					arr[j] = test;
					j++;
					test.clear();
					test.add(app);
					//arr[j].add(app);
					System.out.println("D2 non è uguale. incremento j adesso vale: "+j +"ci ho messo:"+app);
				}
			}
			else
				break;
		}
		
		return arr;
	}
	
	public JSONArray finalArrayLoader(JSONArray arr[])
	{
		System.out.println("FINAL ARRAY LOADER");
		JSONArray fin = new JSONArray();
		JSONObject box = new JSONObject();
		
		for(int k =0; k < 5; k++)
		{
			System.out.println("SONO NEL FOR DI FINALARRAYLOADER");
			box=mediaBox(arr[k]);
			System.out.println("IL BOX CHE STO PER AGGIUNGERE ALL'ARRAY FINALE e':" +box);
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
	

