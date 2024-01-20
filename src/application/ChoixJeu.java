package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.effect.DropShadow;


public class ChoixJeu extends Application{
    
    private Scene mainScene;
    private Button Puissance4;
    private Button Puissance5;
    private Button Facile;
    private Button Impossible;
    private Button Valider;
    private int choixJeu=-1;
    private int choixDifficulte = -1;
    
    @Override
    public void start(Stage primaryStage) {
      
        Label titre = new Label("CHOISISSEZ VOTRE JEU FAVORI :");
        titre.setStyle("-fx-text-fill: #000000; -fx-font-size: 24;");

        Puissance4 = new Button("Puissance4");
        Puissance5 = new Button("Puissance5");
        Facile = new Button("Facile");
        Impossible = new Button("Impossible");
        Valider = new Button("Valider");
        
        Image imageFond = new Image(ChoixJeu.class.getResource("ileserpents2.png").toExternalForm());

        ImageView imageFondVue = new ImageView(imageFond);

        
        Facile.setOnAction(e->{
        	choixDifficulte = 0;
        	DropShadow carreRouge = new DropShadow();
        	carreRouge.setOffsetX(0);
        	carreRouge.setOffsetY(0);
        	carreRouge.setColor(Color.RED);
        	carreRouge.setWidth(10);
        	carreRouge.setHeight(10);

        	Facile.setEffect(carreRouge);
        	Impossible.setEffect(null);
        });
        
        Impossible.setOnAction(e->{
        	choixDifficulte=1;
        	DropShadow carreRouge = new DropShadow();
        	carreRouge.setOffsetX(0);
        	carreRouge.setOffsetY(0);
        	carreRouge.setColor(Color.RED);
        	carreRouge.setWidth(10);
        	carreRouge.setHeight(10);
        	Impossible.setEffect(carreRouge);
        	Facile.setEffect(null);

        });
        
        Puissance4.setOnAction(e->{
        	choixJeu=0;
        	DropShadow carreRouge = new DropShadow();
        	carreRouge.setOffsetX(0);
        	carreRouge.setOffsetY(0);
        	carreRouge.setColor(Color.RED);
        	carreRouge.setWidth(10);
        	carreRouge.setHeight(10);

        	Puissance4.setEffect(carreRouge);
        	Puissance5.setEffect(null);
        });
        
        Puissance5.setOnAction(e->{
        	choixJeu=1;
        	DropShadow carreRouge = new DropShadow();
        	carreRouge.setOffsetX(0);
        	carreRouge.setOffsetY(0);
        	carreRouge.setColor(Color.RED);
        	carreRouge.setWidth(10);
        	carreRouge.setHeight(10);
        	Puissance5.setEffect(carreRouge);
        	Puissance4.setEffect(null);
        });
      
        HBox bouttonBox = new HBox();
        bouttonBox.getChildren().addAll(Puissance4,Puissance5);
        bouttonBox.setAlignment(Pos.CENTER);
        bouttonBox.setSpacing(10);
       
        HBox difficulteBox = new HBox();
        difficulteBox.getChildren().addAll(Facile,Impossible);
        difficulteBox.setAlignment(Pos.CENTER);
        difficulteBox.setSpacing(10);
        

        Valider.setOnAction(e->{
               	if(choixJeu==0 && choixDifficulte == 0) {
               		Connect4 main = new Connect4();
               		Connect4AvecIAFacile(main);
               		main.start(new Stage());
               	}
               	if(choixJeu==1 && choixDifficulte ==0) {
               		Connect5 main2 = new Connect5();
               		Connect5AvecIAFacile(main2);
               		main2.start(new Stage());
               	}
               	if(choixJeu==0 && choixDifficulte == 1) {
               		Connect4 main3 = new Connect4();
               		main3.start(new Stage());
               	}
               	if(choixJeu==1 && choixDifficulte == 1) {
               		Connect5 main4 = new Connect5();
               		main4.start(new Stage());
               	}
               
               
               });

        // Créer un layout pour l'interface utilisateur
        VBox boxPrincipale = new VBox();
        boxPrincipale.getChildren().addAll(titre, bouttonBox);
        boxPrincipale.getChildren().add(difficulteBox);
        boxPrincipale.getChildren().add(Valider);
        boxPrincipale.setAlignment(Pos.CENTER);
        boxPrincipale.setSpacing(20);
        boxPrincipale.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        StackPane root = new StackPane();
        root.getChildren().addAll(imageFondVue, boxPrincipale);

        
        // Créer la scène principale et l'ajouter au stage
        mainScene = new Scene(root, 730, 400);
        mainScene.setFill(Color.YELLOW);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Sélection Mode de Jeu");
        primaryStage.show();
    }
    
    private void Connect4AvecIAFacile(Connect4 jeufacile) {
        jeufacile.setProfondeurMaximum(2);
    }
    
    private void Connect5AvecIAFacile(Connect5 jeufacile) {
    	jeufacile.setProfondeurMax(3);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
