package ProgettoEsame.MeteoRite.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ProgettoEsame.MeteoRite.Model.Prova;

@RestController
public class RestControllerTest {
	@GetMapping("/prova")
	public Prova metodoProva(@RequestParam(name="parametro", defaultValue="Pandoiano") String parametro)
	{
		return new Prova("test che non funziona");
	}

}
