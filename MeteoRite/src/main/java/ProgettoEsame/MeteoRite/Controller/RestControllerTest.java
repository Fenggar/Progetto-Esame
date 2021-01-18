package ProgettoEsame.MeteoRite.Controller;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Filters.Corpo;
import Filters.FunzioniFilter;
import Filters.StatGen;
import JSONHandler.JSONgest;
import ProgettoEsame.MeteoRite.Model.GestioneFile;
import ProgettoEsame.MeteoRite.Model.Services;
import Utilities.CassaAttrezzi;

@RestController
public class RestControllerTest {
	
	/*Esempio RequestBody
	 * 
	@PostMapping("/test") //converte il json che metto su postman in un oggetti di Prova
	public Prova provaPost(@RequestBody Prova body)
	{
		return body;
	}
	*/
	
	/**
	 * Questa rotta effettua la chiamata all'API OpenWeatherMap.
	 * 
	 * 
	 * @param par Questo parametro è usato per inserire il nome della città di cui si vogliono sapere
	 * 		  	  Se non si specifica questo parametro, verrà usato il valore di default.
	 * 
	 * @return La rotta restituisce un JSONObject contente il JSON con le previsioni.
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@GetMapping("/forecast") 
	public JSONArray forecast(@RequestParam(name="citta", defaultValue = "Ponsacco") String par) throws MalformedURLException, IOException
	{
		Services serv = new Services();
		JSONgest ge = new JSONgest();
		
		JSONObject obj = new JSONObject();
		JSONArray ja = new JSONArray();
		JSONObject app = new JSONObject();
		
		//String cittaInserita = par;
		
		System.out.println(par);
		
		obj=serv.forecastID(par);
		
		app.put("city", par);
		
		ja = ge.forecastFilter(obj);
		ja.add(app);
		
		ge.arrayPrinter(ja);

		return ja;
	}
	
	
	/**
	 * Test per lettura file. non fa niente di utile, probabilmente verrà cancellato a breve.
	 * @param par
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@GetMapping("/testfile") 
	public void testfile(@RequestParam(name="nome_file", defaultValue = "test.txt") String par) throws MalformedURLException, IOException
	{
		Services serv = new Services();
		JSONObject obj = new JSONObject();
		GestioneFile gestF = new GestioneFile();
		gestF.leggiFileTest(par+".json");
	}
	
	
	/**
	 * Questo metodo salva su un file .json il JSONObject restituito dalla chiamata all'API
	 * 
	 * @param par Questo parametro contiene il nome della città di cui si vogliono stampare le previsioni.
	 * 
	 * @return Se l'esecuzione va a buon fine, il metodo restituisce la stringa "salvato".
	 * @throws IOException
	 */
	@GetMapping("/save")
	public String save(@RequestParam(name="citta", defaultValue = "Ponsacco") String par) throws IOException
	{
		try 
		{	
		JSONObject oggetto = null;	
		GestioneFile gest = new GestioneFile();
		Services serv = new Services();
		
		oggetto =serv.forecastID(par);
		System.out.println(oggetto);
		
		String s = par+".json";
		
		gest.salvaFile(s, oggetto);
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return "salvato";
	}
	
	/**
	 * Questo metodo fa una chiamata all'API;
	 * salva su un file locale solo le informazioni necessarie per calcolare le statistiche.
	 * 
	 * @param city Questo parametro contiene il nome della città 
	 * @param giorno Questo parametro contiene il giorno in cui viene effettuata la chiamata (solo per comodità per la gestione)
	 * @return Restituisce il nome del file salvato se è andato tutto a buon fine
	 * @throws IOException
	 * @throws ParseException
	 */
	@GetMapping("/saveex")
	public String saveX(@RequestParam(name="citta", defaultValue = "Ponsacco") String city,String giorno) throws IOException, ParseException
	{
		String nomeFile ="";
		try 
		{	
		JSONObject oggetto = null;	
		GestioneFile gest = new GestioneFile();
		Services serv = new Services();
		
		oggetto =serv.forecastID(city);
		System.out.println(oggetto);
		
		nomeFile = city+giorno;
		System.out.println("il file si chiamerà: " +nomeFile);
		
		gest.salvaEssenziale(nomeFile, oggetto, city);
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return "salvato "+nomeFile+".JSON";
	}
	
	/**
	 * Questa rotta mostra delle statistiche basate su delle previsioni salvate con /saveex;
	 * Vengono visualizzati temperatura media, massima, minima varianza nel periodo dei 5 giorni forniti da OpenWeather.
	 * 
	 * @param nomeFile Basta indicare il nome della città precedentemente salvata.
	 * @return Restituisce un jsonObject;
	 */
	@GetMapping("/stat")
	public JSONObject statiscsGenerator(@RequestParam(name="nome_file", defaultValue = "Pontedera") String nomeFile)
	{
		JSONObject jo = null;
		StatGen sg = new StatGen();
		jo= sg.statisticator(nomeFile+".json");
		System.out.println("oggetto generato: "+jo);
		return jo;
	}
	
	/**
	 * Questa rotta costruisce un database con i valori della precisione delle previsioni per i giorni disponibili.
	 * Salva questi dati su un database locale (un file .txt)
	 * 
	 * @param nomeFile Nome del file contente il database
	 * @throws IOException 
	 */
	@GetMapping("/builddatabase")
	public void databaseGenerator(@RequestParam(name="nome_file", defaultValue = "DATABASE") String nome) throws IOException
	{
		
		Vector<String> nomiFile = new Vector<String>();
		CassaAttrezzi ca = new CassaAttrezzi();
		
		nomiFile = ca.magnificiSette(nomiFile); //carico vettore;
		
		JSONObject jo = null;
		JSONArray ja = new JSONArray();
		JSONArray precision = new JSONArray();
		
		Vector<Double> database = new Vector<Double>();
		double d = 0.0;
		
		StatGen sg = new StatGen();
		GestioneFile gest = new GestioneFile();
		
		for(int i = 0; i<nomiFile.size();i++)
		{
			sg.precisionLoader(nomiFile.get(i), precision);
			//System.out.println("REST PRIMO FOR");//NOTA QUI HO TOLTO +".json"
		}
		//dopo questo for su precision ho tutte le precisioni per singolo giorno lette dai file.
		
		for(int i=0; i<4; i++)
		{
			sg.precisionFilter(precision, i, database);
			//System.out.println("REST SECONDO FOR");
		}
		//a questo punto su database ho 4 valori (media delle precisioni delle città);
		gest.salvaFile(nome, database);
	}
	
	@PostMapping("/filter")
	public JSONArray precisionForecast(@RequestBody Corpo body) throws MalformedURLException
	{
		Services serv = new Services();
		FunzioniFilter filter = new FunzioniFilter();
		
		JSONObject obj = new JSONObject();
		JSONArray list = new JSONArray();
		JSONArray arr = new JSONArray();
		
		Vector<String> date = new Vector<String>();
		Vector<Double> precisioni = new Vector<Double>();
		
		double precisione = body.getPrecisione(); //salvo i valori del body;
		String nome = body.getNome();
		
		int index = 0; //indice comune dei vettori.
		String data = ""; //data puntata da index;
		
		obj = serv.forecastID(nome);
		list = (JSONArray) obj.get("list");
		//da list prendo le date. chiamo funzione su filter
		
		date = filter.dateLoader(list);
		
		System.out.println("DATE: "+date);
		
		precisioni = filter.databaseReader("database.txt");
		System.out.println("Precisioni: "+precisioni);
		
		index = filter.trovaIndice(precisioni, precisione);//nota: index va da 0 a 4, dimensione array = 5;
		System.out.println("index:" +index);
		
		data = date.get(index); //salvo data puntata da index;
		
		//funzione che legge contenuto di list; confronta dt_txt con vettore di date (devo usare jsonToDay) e
		//salva l'oggetto se la data 
		//letta è nell'array
		
		arr = filter.filteredComparer(date, list, index);
		//System.out.println("SIZE: "+arr.size());
		JSONArray filtered = new JSONArray();
		filtered = filter.filteredLoader(arr);
		
		for(int i=0; i<filtered.size();i++)
		{
			System.out.println("STAMPO filtered: "+filtered.get(i));
		}
		
		
		//salvo su array campo list del json di forecast.
		//uso jsonToDate
		//fro-> compare data array con data all'indice del filtro. => la metto nell'array che restituisco solo se if è vero.
		
		return filtered;
	}
	
	
	/**
	 * Questo è un metodo usato per testare funzioni specifiche prima di modificare le rotte.
	 * Lo lascio che non si sa mai.
	 * 
	 */
	@GetMapping("/t")
	public void tester()
	{
		JSONObject prova = new JSONObject();
		
		
	}
		
		
		
		
	
	
		
		
		
		
		
}
	

