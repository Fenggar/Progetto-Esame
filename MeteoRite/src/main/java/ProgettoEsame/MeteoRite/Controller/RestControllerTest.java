package ProgettoEsame.MeteoRite.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ProgettoEsame.MeteoRite.Model.Prova;

@RestController
public class RestControllerTest {
	//per il momento stampa un oggetto di prova e cambia il nome della città con quello che viene usato nella chiamata. utile insomma.
	@GetMapping("/prova")
	public Prova metodoProva(@RequestParam(name="citta", defaultValue="Pandoiano") String parametro)
	{
		return new Prova(parametro);
	}
	@PostMapping("/test") //converte il json che metto su postman in un oggetti di Prova
	public Prova provaPost(@RequestBody Prova body)
	{
		return body;
	}

}
