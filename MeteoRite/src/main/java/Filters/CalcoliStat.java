package Filters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Utilities.CassaAttrezzi;

public class CalcoliStat
{
	/**
	 * Questo metodo calcola la media della precisione riguardante il campo temp
	 * 
	 * @param ja Array contente valore precisioni per un solo giorno
	 * @return restituisce double con la media
	 */
	public double mediaTemp(JSONArray ja)
	{
		//System.out.println("MEDIA TEMP");
			double media = 0.0;
			String m = "";
			JSONObject jo = new JSONObject();
			
			int i = 0;
			for(i = 0; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				m = jo.get("temp_precision").toString();
				media += Double.valueOf(m);
			}
			media = (double) (media/i);
			CassaAttrezzi cassa = new CassaAttrezzi();
			m = cassa.arrotondatore(media, m);
			media = Double.valueOf(m);
			
			return media;
	}
	
	/**
	 * Questo metodo calcola la media della precisione riguardante il campo temp_min
	 * 
	 * @param ja Array contente valore precisioni per un solo giorno
	 * @return restituisce double con la media
	 */
	public double mediaMin(JSONArray ja)
	{
		//System.out.println("MEDIA TEMP");
			double media = 0.0;
			String m = "";
			JSONObject jo = new JSONObject();
			
			int i = 0;
			for(i = 0; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				m = jo.get("temp_min_precision").toString();
				media += Double.valueOf(m);
			}
			media = (double) (media/i);
			CassaAttrezzi cassa = new CassaAttrezzi();
			m = cassa.arrotondatore(media, m);
			media = Double.valueOf(m);
			
			return media;
	}

	/**
	 * Questo metodo calcola la media della precisione riguardante il campo temp_max
	 * 
	 * @param ja Array contente valore precisioni per un solo giorno
	 * @return restituisce double con la media
	 */
	public double mediaMax(JSONArray ja)
	{
		//System.out.println("MEDIA TEMP");
			double media = 0.0;
			String m = "";
			JSONObject jo = new JSONObject();
			
			int i = 0;
			for(i = 0; i<ja.size();i++)
			{
				jo = (JSONObject) ja.get(i);
				m = jo.get("temp_max_precision").toString();
				media += Double.valueOf(m);
			}
			media = (double) (media/i);
			CassaAttrezzi cassa = new CassaAttrezzi();
			m = cassa.arrotondatore(media, m);
			media = Double.valueOf(m);
			
			return media;
	}
	
	/**
	 * Questo metodo calcola la media delle medie delle precisioni.
	 * Serve in pratica ad avere un solo valore che indichi la precisione per un determinato giorno
	 * (in pratica semplifica il confronto per il filtro)
	 * 
	 * 
	 * @param t temp_precision;
	 * @param tm temp_min_precision;
	 * @param tmax temp_max_precision;
	 * @return double con media valori;
	 */
	public double mediaPrecision(Double t, Double tm, Double tmax)
	{
		//System.out.println("SONO DENTRO BOXER");
		double precision =0.0;
		String p = "";
		
		precision = (t+tm+tmax)/3;
		CassaAttrezzi cassa = new CassaAttrezzi();
		p = cassa.arrotondatore(precision, p);
		precision = Double.valueOf(p);
		
		return precision;
	}
}
