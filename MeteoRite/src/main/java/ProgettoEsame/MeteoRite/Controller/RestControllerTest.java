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
	
	/*Esempio RequestBody
	@PostMapping("/test") //converte il json che metto su postman in un oggetti di Prova
	public Prova provaPost(@RequestBody Prova body)
	{
		return body;
	}
	*/
	
	//test per utilizzare le date. non funziona. stranamente.
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
	public void testfile(@RequestParam(name="citta", defaultValue = "Ponsacco") String par) throws MalformedURLException, IOException
	{
		Services serv = new Services();
		JSONObject obj = new JSONObject();
		GestioneFile gestF = new GestioneFile();
		gestF.leggiFileTest("test.txt");
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
	 * Questo metodo dovrebbe salvare su file solo alcuni parametri specific del JSONObject restituito dalla chiamata 
	 * ma ancora non è finito.
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@GetMapping("/saveex")
	public String saveX() throws IOException, ParseException
	{
		try 
		{	
		JSONObject oggetto = null;	
		GestioneFile gest = new GestioneFile();
		Services serv = new Services();
		
		oggetto =serv.forecastID("Pontedera");
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
	
	
	@GetMapping("/t")
	public void tester()
	{
		JSONObject prova = new JSONObject();
		
			
			int matrice[][] = new int[5][8];
			for(int j = 0; j<5; j++)
			{
				System.out.println("riga?:" +j);
				for(int k = 0; k<8; k++)
				{
					matrice[j][k] = k;
					System.out.println("cella:" +j+")" +matrice[j][k]);
				}
			}
		
	}
		
		
		
		
	
	
		
		
		
		
		
}
	

