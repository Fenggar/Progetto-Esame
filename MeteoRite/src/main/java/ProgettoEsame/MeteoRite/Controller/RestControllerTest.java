package ProgettoEsame.MeteoRite.Controller;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ProgettoEsame.MeteoRite.Model.GestioneFile;
import ProgettoEsame.MeteoRite.Model.Services;

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
		
		//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    //Date date = new Date();  
	    //System.out.println(formatter.format(date));  
		
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
	

