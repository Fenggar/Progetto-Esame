package JSONHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ProgettoEsame.MeteoRite.Model.Services;


public class JSONgest 
{
	private int indexLocal;
	
	public JSONgest()
	{
		
	}
	public JSONgest(int indexLocal)
	{
		this.indexLocal =indexLocal;
	}
	
	public int getIndexLocal() {
		return indexLocal;
	}
	public void setIndexLocal(int indexLocal) {
		this.indexLocal = indexLocal;
	}
	
	
	
	public double mediaTemp(JSONArray ja)
	{
		//System.out.println("MEDIA TEMP");
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
			
			//System.out.println("Valore media: "+media);

			return media;
	}
	
	public double minMin(JSONArray ja)
	{
		//System.out.println("MIN MIN");
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
			//System.out.println("Valore min: " +m);

			return m;
	}
	
	public double maxMax(JSONArray ja)
	{
		//System.out.println("MAX MAX");
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
			//System.out.println("Valore max: "+m);

			return m;
	}
	
	public String feelsLike(JSONArray ja)
	{
		//System.out.println("SONO DENTRO FEELSLIKE");
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
		
		//System.out.println("Valore media feels: "+media);
		
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
		//System.out.println("ARRAY LOADER");
		
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
			
			//System.out.println("SONO DENTRO IL FOR DI ARRAYLOADER; ogg:" +ogg +" box: "+box);
			
			boxArray.add(box);
			box = null; //secondo me rimane zozzo box; provamo
		}
		
		return boxArray;
	}
	
	
	public JSONArray dataFilter(JSONArray ja ,int index)
	{
		//System.out.println("DATA FILTER");
		
		JSONArray arr = new JSONArray();
		
		JSONObject app = (JSONObject) ja.get(index); //salvo il primo elemento per il confronto della data
		//System.out.println("JA[0]: "+app);
		String d = (String) app.get("data"); //salvo la data per il confronto
		//System.out.println("prima data: "+d);
		String d2;
		
		//JSONArray test = new JSONArray();
		
		
		//int j = 0;	
		for(int i = index; i<ja.size();i++)
		{
			//System.out.println("SONO NEL FOR DI DATA FILTER");
			
				app = (JSONObject) ja.get(i);
				d2 = (String) app.get("data");
				
				//System.out.println("SONO NEL PRIMO IF DI DATAFILTER");
				
				if(d2.equals(d))
				{
					//System.out.println("SONO NEL SECONDO IF DI DATAFILTER");
					arr.add(app);
					//arr[j].add(app);
					System.out.println("D2 è uguale. i vale:"+i +"ci ho messo" +app);
				}
				else
				{
					index = i;
					this.indexLocal = index;
					i = 9999;
					System.out.println("ELSE DI DATAFILTER; index vale: "+index +" i: "+i +" indexLocal = "+indexLocal );
					break;
				}	
		}
		
		
		return arr;
	}
	

	//se non funziona così, provare a passare come parametro un jsonarray anzichè una matrice;
	//quindi la chiamata deve stare in un for;
	public JSONObject finalArrayLoader(JSONArray arr)
	{
		//System.out.println("FINAL ARRAY LOADER");
		//JSONArray fin = new JSONArray();
		JSONObject box = new JSONObject();
		
		//JSONArray nuovo = new JSONArray();
		
			//nuovo = arr[k];
			for(int j = 0; j< arr.size();j++)
			{
				box = (JSONObject) arr.get(j);
				System.out.println("FINALARRAYLOADER"+j+") "+box);
			}
			box=mediaBox(arr);
			System.out.println("IL BOX CHE STO PER AGGIUNGERE ALL'ARRAY FINALE e':" +box);
			//fin.add(box);
	
		return box;
	}
	
	public void arrayPrinter(JSONArray arr)
	{
		JSONObject box = null;
		for(int k = 0; k< arr.size();k++)
		{
			box = (JSONObject) arr.get(k);
			System.out.println(k+") "+box);
		}
	}
}
	

