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
			
		jo = (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println("FATTO CHIAMATA ID");
		
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
		//System.out.println("CHIAMATO FORCASTNAME DA nameToID");
		JSONObject app = call.getCast();
		JSONObject city = (JSONObject) app.get("city");
		Object idCitta =city.get("id");
		//System.out.println("idCitta: "+idCitta);
		
		ID =idCitta.toString();
		//System.out.println("id:" +ID);
		call.setId(ID);
		
		//System.out.println("CONVERTITO ID");
		
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
		//System.out.println("FATTO CHIAMATA NOME");
		//return jo;
		
	}
	
	/**
	 * Questo metodo accede al campo "date" del json e tramite un complesso sistema di specchi e leve salva la data su una stringa.
	 * Salva solo il giorno (in formato numerico ma su una stringa)
	 * Usato in arrayLoader()
	 * 
	 * @param ogg JSONObject da cui leggo la data
	 * @return restituisce una stringa con la data
	 */
	public String jsonToDay(JSONObject ogg)
	{
		String dt = null;
		char data[] = new char[5];
		String dataS = null;
		
		dt = (String) ogg.get("dt_txt");
		
		data[0]= dt.charAt(8);
		data[1]= dt.charAt(9);
		
		dataS = Character.toString(data[0]) +Character.toString(data[1]);
		
		//System.out.println("STRINGA DATAS: " +dataS);
		
		return dataS;
	}
	
	
	/**
	 * Questo metodo "estrae" da ogg i parametri riguardanti la temperatura e li salva su dei double.
	 * Questi valori vengono poi passati a boxer().
	 * extrapolator() è chiamato da arrayLoader();
	 * 
	 * @param ogg json contente il "main" della risposta dell'API
	 * @return restituisce json con solo i parametri selezionati
	 */
	public JSONObject extrapolator(JSONObject ogg)
	{
//PRIMA DI ASSEGNARE I DOUBLE: è necessario convertire a stringa il json;
		//questo perchè OpenWeather tronca i decimali ogni tanto (a caso) e quindi a volte double diventano int.
		//a quanto pare java non casta un intero in un double. perchèssì.
		
		JSONObject main = null;
		double temp = 0.0; //CAMBIATO DA Double a double, controllare che funziona
		double tempmin = 0.0;
		double tempmax = 0.0;
		String feels;
		
		String t;
		String min;
		String max;
		
		JSONObject box= new JSONObject();
		
		//System.out.println("SONO DENTRO EXTRAPOLATOR");
		
		main = (JSONObject) ogg.get("main");
		
		t = main.get("temp").toString();
		temp = Double.valueOf(t);
		
		min = main.get("temp_min").toString();

		tempmin = Double.valueOf(min);
		
		max = main.get("temp_max").toString();
		tempmax = Double.valueOf(max);
		
		feels =  main.get("feels_like").toString();
		
		//System.out.println("HO ASSEGNATO TUTTE LE VARIABILI");
		
		box = boxer(temp,tempmin, tempmax, feels);//, feels);
		
		System.out.println("STO PER RESTITUIRE BOXER: " +box);
		
		return box;
	}
	
	/**
	 * Questo metodo crea un JSONObject con dentro i parametri estrapolati dal json dell'API.
	 * Chiamato da extrapolator()
	 * 
	 * @param t temperatura
	 * @param tm minima
	 * @param tmax massima
	 * @param f temperatura percepita
	 * @return Oggetto con i parametri sopraindicati
	 */
	public JSONObject boxer(Double t, Double tm, Double tmax, String f)
	{
		//System.out.println("SONO DENTRO BOXER");
		JSONObject box=new JSONObject();
		box.put("temp", t);
		box.put("temp_min", tm);
		box.put("temp_max", tmax);
		box.put("feels", f);
		
		System.out.println("HO RIEMPITO BOXER: "+box);
		
		return box;
	}
	
	/**
	 * Overload di boxer, aggiunge uno string per la data
	 * 
	 * 
	 * @param t temperatura
	 * @param tm minima
	 * @param tmax massima
	 * @param f percepita
	 * @param datastring data
	 * @return JSONObject con i parametri sopraindicati
	 */
	public JSONObject boxer(Double t, Double tm, Double tmax, String f, String datastring)
	{
		//System.out.println("SONO DENTRO BOXER");
		JSONObject box=new JSONObject();
		box.put("temp", t);
		box.put("temp_min", tm);
		box.put("temp_max", tmax);
		box.put("feels", f);
		box.put("data", datastring);
		
		System.out.println("HO RIEMPITO BOXER: "+box);
		
		
		return box;
	}
	
	/**
	 * Overload di boxer con solo 3 double (Usato solo in StatGen)
	 * 
	 * @param t temperatura
	 * @param tm minima
	 * @param tmax massima
	 * @return JSONObject con i parametri
	 */
	public JSONObject boxer(Double t, Double tm, Double tmax)
	{
		//System.out.println("SONO DENTRO BOXER");
		JSONObject box=new JSONObject();
		box.put("temp", t);
		box.put("temp_min", tm);
		box.put("temp_max", tmax);
		
		System.out.println("HO RIEMPITO BOXER: "+box);
		
		return box;
	}
	
	//creare array coi giorni dimensione 5
	//prendere dati possibilmente a mezzanotte o giudilì
	//
	
	
}

	//per il prossimo futuro
	/*
	 * for(int i=0;i<this.ja.size();i++) {
					JSONObject jo = (JSONObject) this.ja.get(i);
					System.out.println(i+") "+jo.get("title"));
				}
	 */
	

