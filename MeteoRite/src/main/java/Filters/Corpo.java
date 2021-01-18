package Filters;
/**
 * Classe inutile usata solo per fare requestBody
 * @author Davide
 *
 */
public class Corpo 
{
	private String nome = "Pandoiano";
	private double precisione;
	
	Corpo(String nome, double precisione)
	{
		this.nome=nome;
		this.precisione=precisione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPrecisione() {
		return precisione;
	}

	public void setPrecisione(double precisione) {
		this.precisione = precisione;
	}
	
	

}
