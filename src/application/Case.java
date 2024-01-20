package application;
import javafx.scene.image.*;
public class Case extends ImageView {

	private static Image sauveteur = new Image(Case.class.getResource("image_last.png").toExternalForm());
	private static Image serpent = new Image(Case.class.getResource("serpent_ia2.png").toExternalForm());

	private int statut;
	
	public Case(){
		this.statut = 0;
	}
	
	public void set(int j){
		this.setImage(j == 1 ? sauveteur : serpent);
		this.statut = j;
	}
	
	
	public int getStatut(){
		return statut;
	}
	
	
}
