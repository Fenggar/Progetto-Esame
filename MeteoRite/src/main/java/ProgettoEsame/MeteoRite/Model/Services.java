package ProgettoEsame.MeteoRite.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class Services 
{
	private JSONObject jo= null;
	private JSONArray ja = null;
	private APICall call = new APICall("Pandoiano");
	

	/**
	 * Questo metodo effettua la chiamata all'API OpenWeatherMap.
	 * Chiama il metodo nameToID() per convertire nomeCitta in un ID da usare poi per fare la chiamata
	 * 
	 * @param nomeCitta Questo parametro contiene il nome inserito dall'utente in fase di chiamata.
	 * @return Il metodo restituisce un oggetto di tipo JSONObject contenete il json col risultato della chiaamata.
	 * @throws MalformedURLException
	 */
	public JSONObject forecastID(String nomeCitta) throws MalformedURLException
	{
		nameToID(nomeCitta);
		
		String indirizzo = "http://api.openweathermap.org/data/2.5/forecast?id="+ call.getId() +"&appid="+ call.getKey() +call.getUnita();
		System.out.println(indirizzo);
		
		URL url = new URL (indirizzo);
		
		try {
			URLConnection openConnection = new URL(indirizzo).openConnection();
			InputStream in = openConnection.getInputStream();
			
			String data = "";
			String line = "";
			try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
			   }
			} 
			finally 
			{
			   in.close();
			}
			
		jo = (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("FATTO CHIAMATA ID");
		
		return jo;
	}
	
	/**
	 * Metodo che converte il nome della città inserita dall'utente in un ID usato poi per la chiamata.
	 * Per fare questa conversione, chiama il metodo forecastNome() passandogli il nome della città.
	 * A questo punto, accede al JSONObject cast, e salva una stringa contente l'ID sul rispettivo campo dell'oggetto call.
	 * 
	 * 
	 * @param nome Questo parametro corrisponde al nome della città inserito dall'utente
	 * @throws MalformedURLException
	 */
	public void nameToID(String nome) throws MalformedURLException
	{
		String ID ="";
		
		forecastNome(nome);
		System.out.println("CHIAMATO FORCASTNAME DA nameToID");
		JSONObject app = call.getCast();
		JSONObject city = (JSONObject) app.get("city");
		Object idCitta =city.get("id");
		System.out.println("idCitta: "+idCitta);
		
		ID =idCitta.toString();
		System.out.println("id:" +ID);
		call.setId(ID);
		
		System.out.println("CONVERTITO ID");
		
	}
	
	/**
	 * Questo metodo effettua una chiamata all'API OpenWeatherMap.
	 * A differenza del metodo forecastID(), usa il nome della città. 
	 * Una volta fatta la chiamata e ottenuto il JSON, lo salva nel campo cast dell'oggetto call.
	 * 
	 * @param nomeCitta QUesto parametro contiene il nome della città inserito dall'utente.
	 * @throws MalformedURLException
	 */
	public void forecastNome(String nomeCitta) throws MalformedURLException
	{
		call.setNomeCitta(nomeCitta);
		//System.out.println(call.getNomeCitta());
		
		String indirizzo = "http://api.openweathermap.org/data/2.5/forecast?q="+ call.getNomeCitta() +"&appid="+ call.getKey() +call.getUnita();
		//System.out.println(indirizzo);
		
		URL url = new URL (indirizzo);
		
		try {
			URLConnection openConnection = new URL(indirizzo).openConnection();
			InputStream in = openConnection.getInputStream();
			
			String data = "";
			String line = "";
			try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
			   }
			} 
			finally 
			{
			   in.close();
			}
			
				jo = (JSONObject) JSONValue.parseWithException(data);

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		call.setCast(jo);
		System.out.println("FATTO CHIAMATA NOME");
		//return jo;
		
	}
	
	public String jsonToDay(JSONObject ogg)
	{
		String dt = null;
		char data[] = new char[5];
		String dataS = null;
		
		dt = (String) ogg.get("dt_txt");
		
		data[0]= dt.charAt(8);
		data[1]= dt.charAt(9);
		
		dataS = Character.toString(data[0]) +Character.toString(data[1]);
		
		System.out.println("STRINGA DATAS: " +dataS);
		
		return dataS;
	}
	
	public JSONObject extrapolator(JSONObject ogg)
	{
		JSONObject main = null;
		Double temp = 0.0;
		Double tempmin = 0.0;
		Double tempmax = 0.0;
		Object feels = 0.0;
		
		JSONObject box= new JSONObject();
		
		System.out.println("SONO DENTRO EXTRAPOLATOR");
		
		main = (JSONObject) ogg.get("main");
		temp = (Double) main.get("temp");
		tempmin = (Double) main.get("temp_min");
		tempmax = (Double) main.get("temp_max");
		//feels =  main.get("feels_like");
		
		System.out.println("HO ASSEGNATO TUTTE LE VARIABILI");
		
		box = boxer(temp,tempmin, tempmax);//, feels);
		
		System.out.println("STO PER RESTITUIRE BOXER");
		
		return box;
	}
	
	public JSONObject boxer(Double t, Double tm, Double tmax)//, Object f)
	{
		System.out.println("SONO DENTRO BOXER");
		JSONObject box=new JSONObject();
		box.put("temp", t);
		box.put("tempmin", tm);
		box.put("tempmax", tmax);
		//box.put("feels", f);
		
		System.out.println("HO RIEMPITO BOXER");
		
		
		return box;
	}
	
	
	
	
}

	//per il prossimo futuro
	/*
	 * for(int i=0;i<this.ja.size();i++) {
					JSONObject jo = (JSONObject) this.ja.get(i);
					System.out.println(i+") "+jo.get("title"));
				}
	 */
	

