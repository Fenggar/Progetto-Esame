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
			String m;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				m = jo.get("temp").toString();
				media += Double.valueOf(m);
			}
			media = (double) (media/i);
			
			System.out.println("Valore media: "+media);

			return media;
	}
	
	public double minMin(JSONArray ja)
	{
		System.out.println("MIN MIN");
			double m = 666.10;
			double app = 0.0;
			String s;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				//System.out.println("SONO NEL FOR DI MIN MIN");
				
				jo = (JSONObject) ja.get(i);
				
				//System.out.println(" -messo valore su jo; temp_min =" +jo.get("temp_min"));
				s=jo.get("temp_min").toString();
				app = Double.valueOf(s);
				
				//System.out.println(" -messo valore su app");
				
				if( app < m)
				{
					//System.out.println("SONO NELL'IF DI MIN MIN");
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
			String s;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				s = jo.get("temp_max").toString();
				app = Double.valueOf(s); 
				if( app > m)
				{
					m = app;
					//System.out.println("SONO NELL'IF di MAXMAX: "+m);
				}
			}
			System.out.println("Valore max: "+m);

			return m;
	}
	
	public String feelsLike(JSONArray ja)
	{
		System.out.println("SONO DENTRO FEELSLIKE");
		String f;
		double confronto = 0.0;
		
		
		double media = 0.0;
		JSONObject jo = new JSONObject();
		
		int i= 0;
		for( i=0 ; i<ja.size();i++)
		{
			jo = (JSONObject) ja.get(i);
			f =(String) jo.get("feels").toString();
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
		//JSONArray fin = new JSONArray();
		JSONObject box = new JSONObject();
		
		Services serv = new Services();
		
		System.out.println("SONO DENTRO MEDIABOX");
		
			double t = mediaTemp(arr);
			double min = minMin(arr);
			double max = maxMax(arr);
			String feel = feelsLike(arr);
			//j++;
			System.out.println("Ho chiamato tutte le funzioni: "+t +" "+min + " " +max +" " +feel);
			
			box = serv.boxer(t, min, max,feel);
			
			System.out.println("CHIAMO BOXER CON: " +box);
		
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
			box = null; //secondo me rimane zozzo box; provamo
		}
		
		return boxArray;
	}
	
	
	public JSONArray[] dataFilter(JSONArray ja, JSONArray arr[])
	{
		System.out.println("DATA FILTER");
		
		JSONObject app = (JSONObject) ja.get(0); //salvo il primo elemento per il confronto della data
		System.out.println("JA[0]: "+app);
		String d = (String) app.get("data"); //salvo la data per il confronto
		System.out.println("prima data: "+d);
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
					if(j < 5)
					{
						test.clear();
						test.add(app);
						//arr[j].add(app);
						System.out.println("D2 non è uguale. incremento j adesso vale: "+j +"ci ho messo:"+app);
					}
					else
						break;
					
				}
			}
			else
				break;
		}
		
		return arr;
	}
	

	//se non funziona così, provare a passare come parametro un jsonarray anzichè una matrice;
	//quindi la chiamata deve stare in un for;
	public JSONArray finalArrayLoader(JSONArray arr[])
	{
		System.out.println("FINAL ARRAY LOADER");
		JSONArray fin = new JSONArray();
		JSONObject box = new JSONObject();
		
		JSONArray nuovo = new JSONArray();
		
		for(int k =0; k < 5; k++)
		{
			System.out.println("SONO NEL FOR DI FINALARRAYLOADER; k= "+k);
			nuovo = arr[k];
			for(int j = 0; j< nuovo.size();j++)
			{
				box = (JSONObject) nuovo.get(j);
				System.out.println(j+") "+box);
			}
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
	

