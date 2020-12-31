package ProgettoEsame.MeteoRite.Model;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

public class GestioneFile 
{
	
	public void salvaFile(String nome_file, JSONObject jo) throws IOException 
	{
		try {
		FileWriter file = new FileWriter(nome_file+ ".json");
	
//rivedere questo toString, forse cambia il formato da json a txt		
		//cambiato toJSONString in toString, non so la differenza ma pare uguale.
            file.write(jo.toString());
            file.flush();
	}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
		
		
		/*try {
			PrintWriter file_output = new PrintWriter(new BufferedWriter(new FileWriter(nome_file)));
			
				file_output.println(jo);
			
			file_output.close();
			System.out.println("File salvato!");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		}
		
		*/
		
	



