package JSONHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ProgettoEsame.MeteoRite.Model.Services;
import Utilities.CassaAttrezzi;


public class JSONgest 
{
	/**
	 * Questo attributo è usato per salvare l'indice a cui sono arrivato a leggere l'array (vedi sotto)
	 */
	private int indexLocal;
	
	/**
	 * Costruttore vuoto visto che ho sovrascritto quello di default
	 */
	public JSONgest()
	{
		
	}
	/**
	 * Costruttore con un solo parametro
	 * @param indexLocal
	 */
	public JSONgest(int indexLocal)
	{
		this.indexLocal =indexLocal;
	}
	/**
	 * getter per indexLocal
	 * @return
	 */
	public int getIndexLocal() {
		return indexLocal;
	}
	/**
	 * Setter per indexLocal
	 * @param indexLocal
	 */
	public void setIndexLocal(int indexLocal) {
		this.indexLocal = indexLocal;
	}
	
	
	/**
	 * Questo metodo calcola media delle temperature contenute nel JSONArray
	 * @param ja Array preso come parametro
	 * @return restituisce un double con la media
	 */
	public double mediaTemp(JSONArray ja)
	{
		//System.out.println("MEDIA TEMP");
			double media = 0.0;
			String m = "";
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=0 ; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				m = jo.get("temp").toString();
				media += Double.valueOf(m);
			}
			media = (double) (media/i);
			CassaAttrezzi cassa = new CassaAttrezzi();
			m = cassa.arrotondatore(media, m);
			media = Double.valueOf(m);
			
			//System.out.println("Valore media: "+media);
			
			return media;
	}
	
	/**
	 * Questo è un overload di media;
	 * L'unica differenza è che passo anche un indice con la chiamata
	 * 
	 * @param ja Array che contiene tutti gli elementi di cui fare la media
	 * @param index indice da cui parto a calcolare la media
	 * @return restituisco media
	 */
	public double mediaTemp(JSONArray ja, int index)
	{
		//System.out.println("MEDIA TEMP");
			double media = 0.0;
			String m = "";
			JSONObject jo = new JSONObject();
			
			int i = 0;
			for(i=index; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				m = jo.get("temp").toString();
				media += Double.valueOf(m);
			}
			media = (double) (media/i);
			CassaAttrezzi cassa = new CassaAttrezzi();
			m = cassa.arrotondatore(media, m);
			media = Double.valueOf(m);
			
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
	
	/**
	 * Overload di minMin con indice per partire da una posizione qualsiasi dell'array
	 * 
	 * @param ja
	 * @param index
	 * @return
	 */
	public double minMin(JSONArray ja, int index)
	{
		//System.out.println("MIN MIN");
			double m = 666.10;
			double app = 0.0;
			String s;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i= index ; i<ja.size();i++)
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
	
	/**
	 * Overload di maxMax con indice per partire da un punto qualsiasi dell'array
	 * 
	 * @param ja
	 * @param index
	 * @return
	 */
	public double maxMax(JSONArray ja, int index)
	{
		//System.out.println("MAX MAX");
			double m = 0.0;
			double app = 0.0;
			String s;
			JSONObject jo = new JSONObject();
			
			int i= 0;
			for( i=index ; i<ja.size();i++)
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
		
	public JSONObject mediaBox(JSONArray arr, JSONObject jsondata)
	{
		//JSONArray fin = new JSONArray();
		JSONObject box = new JSONObject();
		
		Services serv = new Services();
		
		System.out.println("SONO DENTRO MEDIABOX");
		
			double t = mediaTemp(arr);
			double min = minMin(arr);
			double max = maxMax(arr);
			String feel = feelsLike(arr);
			
			String datastring =(String) jsondata.get("data");
			//j++;
			System.out.println("Ho chiamato tutte le funzioni: "+t +" "+min + " " +max +" " +feel);
			
			box = serv.boxer(t, min, max,feel, datastring);
			
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
		JSONObject jsondata = new JSONObject();
		
		//JSONArray nuovo = new JSONArray();
		
			//nuovo = arr[k];
			for(int j = 0; j< arr.size();j++)
			{
				box = (JSONObject) arr.get(j);
				System.out.println("FINALARRAYLOADER"+j+") "+box);
			}
			String pandoro = box.get("data").toString();
			jsondata.put("data", pandoro) ;
			box=mediaBox(arr, jsondata);
			System.out.println("IL BOX CHE STO PER AGGIUNGERE ALL'ARRAY FINALE e':" +box);
			//fin.add(box);
	
		return box;
	}
	
	
	/**
	 * Come dice il nome, stampa un array. Un jsonArray.
	 * 
	 * @param arr
	 */
	public void arrayPrinter(JSONArray arr)
	{
		JSONObject box = null;
		for(int k = 0; k< arr.size();k++)
		{
			box = (JSONObject) arr.get(k);
			System.out.println(k+") "+box);
		}
	}
	
	
	/**
	 * Questo metodo serve a prendere solo i parametri richiesti quando si cercano delle previsioni.
	 * Usa Services.extrapolator() e tutti i metodi che esso chiama.
	 * 
	 * Usato solo da /forecast;
	 * 
	 * @param obj prende oggetto contente tutto il json delle previsioni
	 * @return restituisce array con previsioni temperature previste ogni 3 ore per 5 giorni.
	 */
	public JSONArray forecastFilter(JSONObject obj)
	{
		JSONArray list = new JSONArray();
		JSONObject app = new JSONObject();
		JSONObject box = new JSONObject();
		JSONArray ret = new JSONArray();
		
		Services ser = new Services();
		
		list = (JSONArray) obj.get("list");
		
		for(int i = 0; i<list.size(); i++)
		{
			app = (JSONObject) list.get(i);
			
			box = ser.extrapolator(app);
			
			ret.add(box);
		}
		
		
		return ret;
	}
}
	

