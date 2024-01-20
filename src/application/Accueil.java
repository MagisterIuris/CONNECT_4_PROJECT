package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Accueil extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger l'image de fond
            Image imageFond = new Image(Accueil.class.getResource("avion_chute.png").toExternalForm());
            ImageView imageFondVue = new ImageView(imageFond);
            // Créer un texte explicatif
            Label explication = new Label("Mr Garcia voulant se rendre en Australie pour observer les kangourous, se retrouve dans un sacré pétrin, lorsque le moteur de l'avion explose et se fait éjecter. Heureusement, il pense à tout et a un parachute sur lui. Mais il se dirige tout droit"
            		+ " sur une île peuplée de serpents féroces et très entraînées. Pour le sauver c'est simple, il suffit d'alligner 4 jetons sur le Puissance4 ou bien 5 jetons sur le Puissance5 "
            		+ " mais contre des serpents imbattables à ce jeu."
            		+ "Vous pouvez vous entrainer sur le niveau facile mais cela ne le sauvera pas. Donc entraînez-vous et tentez de sauver Mr Garcia."
            		+ "\n                                        Si tout est clair, appuyez sur compris !");
            explication.setTextFill(Color.RED);
            explication.setWrapText(true);
            explication.setMaxWidth(650);
            explication.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
            // Créer un bouton "Compris"
            Button button = new Button("Compris");
            button.setOnAction(e -> {
            	ChoixJeu main = new ChoixJeu();
           		main.start(new Stage());
            });
            // Ajouter les éléments à la scène
            VBox vbox = new VBox(20, explication, button);
            vbox.setTranslateY(-50);
            vbox.setAlignment(Pos.CENTER);
            StackPane root = new StackPane(imageFondVue, vbox);
            Scene scene = new Scene(root, 700, 470);
            primaryStage.setTitle("Accueil et explication de l'histoire");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
