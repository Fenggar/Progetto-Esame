package Filters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ProgettoEsame.MeteoRite.Model.Services;


public class FunzioniFilter 
{
		/*per un prossimo futuro:
		 * if(period==1) {
				if(param.equals("temp_max")) {
					FilterTempMax filter = new FilterTempMax();
					array = filter.oneDay(cities, value);
				} 
				else if (param.equals("temp_min")) {
					FilterTempMin filter = new FilterTempMin();
					array = filter.oneDay(cities, value);
				}
				else if (param.equals("feels_like")) {
					FilterFeelsLike filter = new FilterFeelsLike();
					array = filter.oneDay(cities, value);
				}
				else if (param.equals("visibility")) {
					FilterVisibility filter = new FilterVisibility();
					array = filter.oneDay(cities, value);
				}
				else  throw new WrongParamException (param+ " non è una stringa ammessa.Inserisci una stringa tra temp_min,temp_max,feels_like e visibility");   
							
			}
		 */
		/**
		 * Legge "list" dal json delle previsioni e salva la data su un Vecotr di String.
		 * 
		 * @param list JSONArray col campo "list"
		 * @return Vector con le date.
		 */
		public Vector<String> dateLoader(JSONArray list)
		{
			Services serv = new Services();
			
			Vector<String> date = new Vector<String>();
			
			JSONObject jo = new JSONObject();
			String s = "vuota";
			String copia = "";
			
			for(int i = 0; i<list.size();i++)
			{
				copia = s;
				jo = (JSONObject) list.get(i);
				s = serv.jsonToDay(jo);
				
				if(!(s.equals(copia))) //devo inserire ogni data una volta sola
				{
					date.add(s);
				}
			}
			
			return date;
		}
		
		
		/**
		 * Questo metodo legge il file database.txt e carica un Vector di double.
		 * 
		 * @param nome nome del file da leggere
		 * @return restituisce vector coi valori letti.
		 */
		public Vector<Double> databaseReader(String nome)
		{
			Vector <Double> database = new Vector<Double>();
			database.add(100.0); //alla posizione 0 metto 100, le precisioni di oggi saranno incontestabilmente vere
			
			//JSONArray arr = new JSONArray();
			//JSONObject jo = new JSONObject();
			
			String data = "";
			String line = "";
			double d = 0.0;
			
			try
			{
				Scanner file_input = new Scanner(new BufferedReader(new FileReader(nome)));	  
				while(file_input.hasNext()) //file_input.hasNext(line)
				{
					line = file_input.nextLine();
					//System.out.println(str);
					d = Double.valueOf(line);
					
					database.add(d);
					
					for(int i = 0; i<database.size();i++)
					{
						//System.out.println("DOPO L'ADD database VALE: "+i+") " +database.get(i));
					}
				}
				file_input.close();
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return database;
		}
		
		/**
		 * Questo metodo confronta valore precisione inserito dall'utente con il vector letto da file.
		 * Usato da /filter
		 * 
		 * @param precisioni Vector caricato da file.
		 * @param precisione double inserito da utente durante chiamata.
		 * @return indice corrispondente alla precisone nel vector.
		 */
		public int trovaIndice(Vector<Double> precisioni, double precisione)
		{
			int index = 0;
			
			for(int i = 0; i<precisioni.size();i++)
			{
				
				if(precisione <= precisioni.get(i))
				{
					//System.out.println("l'if era vero che non era vero");
					index = i;
				}
			}
			
			return index;
		}
		
		/**
		 * Questo metodo confronta le date lette nel JSONArray con quelle nel vecotr date (si ferma all'indice index ottenuto da trovaIndice
		 * Carica l'oggetto nell'array solo se la data di quell'oggetto è presente nella parte di date delimitata da index.
		 * 
		 * @param date Vector con le date
 		 * @param list JSONArray con il campo "list" del json dell'api
		 * @param index indice calcolato da trovaIndice()
		 * @return restituisce array caricato.
		 */
		public JSONArray filteredComparer(Vector<String> date, JSONArray list, int index)
		{
			Services serv = new Services();
			
			JSONArray arr = new JSONArray();
			JSONObject jo = new JSONObject();
			String s = "";
			boolean flag = false;
			
			for(int i = 0; i<list.size();i++)
			{
				
				jo = (JSONObject) list.get(i);
				s = (String)serv.jsonToDay(jo);
				//System.out.println("s che ho convertito: "+s);
				
				//System.out.println("DATE SIZE: "+date.size()+" l'index è: "+index);
				for(int j = 0; j<=(index); j++)
				{
					
					if(s.equals(date.get(j)))
					{
						flag =true;
						//System.out.println("IF DATE; s:"+s +" date.get: "+date.get(j));
					}
				}
				
				if(flag)
				{
					arr.add(jo);
					//System.out.println("il flag era true; jo: "+jo);
					flag = false; //resetto il flag dopo aver fatto l'add.
				}
			}
			
			return arr;
		}
		
		
		/**
		 * Questo metodo prende l'array riempito da filterComparer(), lo legge e carica un altro JSONArray solo coi campi necessari.
		 * (Analogo a filterForecast() nella rotta /forecast 
		 * 
		 * @param arr
		 * @return restituisce un jsonArray 
		 */
		public JSONArray filteredLoader(JSONArray arr)
		{
			JSONObject app = new JSONObject();
			JSONObject box = new JSONObject();
			JSONArray ret = new JSONArray();
			
			Services ser = new Services();
			
			for(int i = 0; i<arr.size(); i++)
			{
				app = (JSONObject) arr.get(i);
				
				box = ser.extrapolator(app);
				
				ret.add(box);
			}
			return ret;
		}

}
