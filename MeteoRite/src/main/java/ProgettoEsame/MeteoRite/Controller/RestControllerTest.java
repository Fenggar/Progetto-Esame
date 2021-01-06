package ProgettoEsame.MeteoRite.Controller;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ProgettoEsame.MeteoRite.Model.GestioneFile;
import ProgettoEsame.MeteoRite.Model.Services;

@RestController
public class RestControllerTest {
	//Esempio RequestParam
	/*@GetMapping("/prova")
	public Prova metodoProva(@RequestParam(name="citta", defaultValue="Pandoiano") String parametro)
	{
		return new Prova(parametro);
	}
	
	*Esempio RequestBody
	@PostMapping("/test") //converte il json che metto su postman in un oggetti di Prova
	public Prova provaPost(@RequestBody Prova body)
	{
		return body;
	}
	*/
	
	/*
	 * ESEMPIO chiamata, restituisce solo meteo di Pollenza.
	 * 
	 * @GetMapping("/url") 
	public JSONObject urlTest(@RequestParam(name="citta", defaultValue = "Ponsacco") String par) throws MalformedURLException, IOException
	{
		//APICall testNuovo = new APICall("Pollenza");
		Services serv = new Services();
		JSONObject obj = new JSONObject();
		obj=serv.call();
		System.out.println(obj);
		return obj;
		//stampa 2 volte. cout funziona; return pure. 
		//se funziona qua, funzionerà anche sul file. se spera.
	}
	
	 */
	
	@GetMapping("/datatest") 
	public void data(@RequestParam(name="citta", defaultValue = "Ponsacco") String par) throws MalformedURLException, IOException, ParseException
	{
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    Date date = new Date();
	    String s = "2021-01-05";
	    Date d = new SimpleDateFormat("yyyy-MM-dd").parse(s);
	    System.out.println(date);
	    System.out.println(d);
	    
	    if(d.compareTo(date)==0 )
	    	System.out.println("funziona");
	    else
	    	System.out.println("oh no");
	    
	}
	
	
	
	/*
	 * Questo metodo fornisce le previsioni per una città passata come parametro.
	 * Se non si inserisce un parametro, visualizza meteo della città di default.
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
	
	
	
	
	//ho cancellato per sbaglio il test filtro ma non funzionava quindi fa niente
	
	/*
	 * Semplice test che legge un file nella cartella del progetto.
	 */
	@GetMapping("/testfile") 
	public void testfile(@RequestParam(name="citta", defaultValue = "Ponsacco") String par) throws MalformedURLException, IOException
	{
		Services serv = new Services();
		JSONObject obj = new JSONObject();
		GestioneFile gestF = new GestioneFile();
		gestF.leggiFileTest("test.txt");
	}
	
	
	@GetMapping("/save")
	public String save() throws IOException
	{
		try 
		{	
		JSONObject oggetto = null;	
		GestioneFile gest = new GestioneFile();
		Services serv = new Services();
		
		
		//oggetto= (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object
		//System.out.println("JSONObject scaricato: "+ this.jo);
		
		oggetto =serv.call();
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
	
	@GetMapping("/saveex")
	public String saveX() throws IOException, ParseException
	{
		try 
		{	
		JSONObject oggetto = null;	
		GestioneFile gest = new GestioneFile();
		Services serv = new Services();
		
		
		//oggetto= (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object
		//System.out.println("JSONObject scaricato: "+ this.jo);
		
		oggetto =serv.call();
		System.out.println(oggetto);
		
		gest.salvaEssenziale("testSpecificoLista", oggetto);
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
		
		
		
		
	
	
		
		
		
		
		
}
	

