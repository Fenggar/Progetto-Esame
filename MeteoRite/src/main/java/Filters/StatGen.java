package Filters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import JSONHandler.JSONgest;
import ProgettoEsame.MeteoRite.Model.GestioneFile;
import ProgettoEsame.MeteoRite.Model.Services;
import Utilities.CassaAttrezzi;

public class StatGen 
{
	/**
	 * Questo metodo legge il file creato da /saveex e usa caricaDaFile per rimpire un arrai
	 * genera quindi le statistiche sulle previsioni lette (cioè, calcola media massimo e minimo della temperatura)
	 * 
	 * Chiama: caricaDaFile, mediaTemp, maxMax, minMin, maxMinData, varianza e boxer();
	 * 
	 * @param nomeFile
	 * @return restituisce un JSONObject con le statistiche del file letto.
	 */
	public JSONObject statisticator(String nomeFile)
	{
		JSONArray jaStat = new JSONArray();
		JSONObject jo = new JSONObject();
		
		GestioneFile g  = new GestioneFile();
		JSONgest jg = new JSONgest();
		CassaAttrezzi ca = new CassaAttrezzi();
		Services ser = new Services();
		
		double t = 0.0;
		double tmin = 0.0;
		double tmax = 0.0;
		double varian =0.0;
		String data = "";
		double var = 0.0;
		
		String citta = "";
		
		jaStat = g.caricaDaFile(nomeFile);
		
		t = jg.mediaTemp(jaStat, 1);
		tmin = jg.minMin(jaStat, 1);
		tmax = jg.maxMax(jaStat, 1);
		data = ca.maxMinData(jaStat);
		
		jo = (JSONObject) jaStat.get(0);
		citta = jo.get("city").toString();
		
		
		jo = ser.boxer(t, tmin, tmax);
		jo.put("data", data);
		jo.put("city", citta);
		var = varianza(jaStat, t);
		jo.put("varianza", var);
		
		return jo;
		
	}
	
	/**
	 * Questo metodo calcola la varianza
	 * (media dei quadrati delle differenze, tra i valori Xi e la media)
	 * Chiamato da statisticator()
	 * 
	 * @param ja array coi valori
	 * @param valoreMedio media delle temperature calcolata da mediaTemp
	 * @return restituisce double con valore varianza
	 */
	public double varianza(JSONArray ja, double valoreMedio)
	{
		double var =0.0;
		JSONObject jo = new JSONObject();
		CassaAttrezzi cassa = new CassaAttrezzi();
		
		double media  = 0.0;
		double scarto = 0.0;
		double app = 0.0;
		
		int i = 0;
		for(i = 1; i< ja.size(); i++)
		{
			jo = (JSONObject) ja.get(i);
			app = (double) jo.get("temp");
			scarto = app-valoreMedio;
			scarto = scarto*scarto;
			media+= scarto;
		}
		var= media/(i-1);
		String v = Double.toString(var);
		
		v = cassa.arrotondatore(var, v);
		var = Double.valueOf(v);
		
		return var;
	}

}
