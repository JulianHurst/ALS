package datamodel;

public class Atom {
	Weight weight;
	String value;
	Serie serie;
	
	public Atom(String value, Serie serie){
		this.value = value;
		this.serie = serie;
	}
	
	public Atom(String value, Weight weight, Serie serie){
		this.value = value;
		this.weight = weight;
		this.serie = serie;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public Weight getWeight() {
		return weight;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public int getSerieValue() {
		if(serie.equals(Serie.A))
			return -1;
		return 1;
	}
	
	public int getWeightValue() {
		switch(weight) {
			case NEGATIVE :
				return -1;
			case NEUTRAL :
				return 0;
			case POSITIVE :
				return 1;
			default :
				return 0;
		}
	}

}
