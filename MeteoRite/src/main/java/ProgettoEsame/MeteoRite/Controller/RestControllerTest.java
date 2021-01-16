package ProgettoEsame.MeteoRite.Controller;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Filters.StatGen;
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
	public JSONObject paramTest(@RequestParam(name="citta", defaultValue = "Ponsacco") String par) throws MalformedURLException, IOException
	{
		Services serv = new Services();
		JSONObject obj = new JSONObject();
		
		String cittaInserita = par;
		
		System.out.println(par);
		
		obj=serv.forecastID(par);
		System.out.println(obj);
		return obj;
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
	//AGGIUNGERE PARAMETRO PER SPECIFICARE NOME FILE.txt DA SALVARE
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
		
		gest.salvaFile("testSalvataggio", oggetto);
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
	 * @param nomeFile Nome della città 
	 * @throws IOException 
	 */
	@GetMapping("/builddatabase")
	public void databaseGenerator(@RequestParam(name="nome_file", defaultValue = "precision") String nome) throws IOException
	{
		
		Vector<String> nomiFile = new Vector<String>();
		CassaAttrezzi ca = new CassaAttrezzi();
		
		nomiFile = ca.magnificiSette(nomiFile); //carico vettore;
		
		JSONObject jo = null;
		JSONArray ja = new JSONArray();
		JSONArray precision = new JSONArray();
		
		Vector<Double> database = new Vector<Double>();
		
		StatGen sg = new StatGen();
		GestioneFile gest = new GestioneFile();
		
		for(int i = 0; i<nomiFile.size();i++)
		{
			sg.precisionLoader(nomiFile.get(i), precision); //NOTA QUI HO TOLTO +".json"
		}
		//dopo questo for su precision ho tutte le precisioni per singolo giorno lette dai file.
		
		for(int i=0; i<4; i++)
		{
			sg.precisionFilter(precision, i, database);
		}
		//a questo punto su database ho 4 valori (media delle precisioni delle città);
		
		//scrivo su file e stampo la precisione che ho calcolato.
		for(int i=0; i<database.size();i++)
		{
			System.out.println("database "+i+") "+database.get(i));
			gest.salvaFile(nome,database.get(i));
		}
		
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
	

