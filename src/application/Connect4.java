package application;


import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class Connect4 extends Application {
	
	private static int precedent;
	private static int nbTour;
	private static boolean gameover=false;
	private int pronfondeurMaximum=10;
	private int locationProchainMouvement=-1;
	private static List<Integer> listelocations = new ArrayList<Integer>();
	private Case[][] cases;
	
	
	//Fonction utiliser pour modifier la profondeur de notre algorithme minmax pour avoir deux types difficulté facile ou difficile
	
	public void setProfondeurMaximum(int profondeurMax) {
		pronfondeurMaximum=profondeurMax;
	}
	

	//Fonction qui permet de savoir si un joueur remporte la partie ou non ou si il y a match nul
	
	public int resultatJeu(Case[][] grille){
		int scoreIA = 0, scoreHumain = 0;
	    for (int i = 5; i >= 0; --i) {
	        for (int j = 0; j <= 6; ++j) {
	            if (grille[j][i].getStatut() == 0) {
	                continue;
	            }
	            // Vérification des cellules à droite
	            if(j <= 3) {
	                for (int k = 0; k < 4; ++k) {
	                    int statut = grille[j + k][i].getStatut();
	                    if (statut == 2) {
	                        scoreIA++;
	                    } else if (statut == 1) {
	                        scoreHumain++;
	                    } else {
	                        break;
	                    }
	                }
	                if(scoreIA == 4) {
	                    return 1;
	                } else if (scoreHumain == 4) {
	                    return 2;
	                }
	                scoreIA = 0;
	                scoreHumain = 0;
	            }
	            // Vérification des cellules vers le haut
	            if(i >= 3) {
	                for(int k = 0; k < 4; ++k) {
	                    int statut = grille[j][i - k].getStatut();
	                    if(statut != 1 && statut != 0) {
	                        scoreIA++;
	                    } else if (statut == 1) {
	                        scoreHumain++;
	                    } else {
	                        break;
	                    }
	                }
	                if(scoreIA == 4) {
	                    return 1;
	                } else if(scoreHumain == 4) {
	                    return 2;
	                }
	                scoreIA = 0;
	                scoreHumain = 0;
	            }
	            // Vérification en diagonale en haut à droite
	            if(j <= 3 && i >= 3) {
	                for(int k = 0; k < 4; ++k) {
	                    int statut = grille[j + k][i - k].getStatut();
	                    if(statut != 1 && statut != 0) {
	                        scoreIA++;
	                    } else if (statut == 1) {
	                        scoreHumain++;
	                    } else {
	                        break;
	                    }
	                }
	                if(scoreIA == 4) {
	                    return 1;
	                } else if (scoreHumain == 4) {
	                    return 2;
	                }
	                scoreIA = 0;
	                scoreHumain = 0;
	            }
	            // Vérification en diagonale en haut à gauche
	            if(j >= 3 && i >= 3) {
	                for (int k = 0; k < 4; ++k) {
	                    int statut = grille[j - k][i - k].getStatut();
	                    if (statut != 1 && statut != 0) {
	                        scoreIA++;
	                    } else if (statut == 1) {
	                        scoreHumain++;
	                    } else {
	                        break;
	                    }
	                }
	                if (scoreIA == 4) {
	                    return 1;
	                } else if (scoreHumain == 4) {
	                    return 2;
	                }
	                scoreIA = 0;
	                scoreHumain = 0;
	            }
	        }
	    }
	  for(int j=0;j<7;++j){
	      //La partie n'est toujours pas finie
	      if(grille[j][0].getStatut()==0)return -1;
	  }
	  //Match nul ! 
	  return 0;
	}
	
	//Fonction pour savoir si un mouvement est possible ou non
	
	 public boolean mouvementLegal(int colonne, Case[][] grille){
	        return grille[colonne][0].getStatut()==0;
	    }

	 
	 
	 public int calculerLeScore(int scoreDeIA, int mouvementSupplementaire){   
			int scoreMouvement = 4 - mouvementSupplementaire;
			if(scoreDeIA==0) {
				return 0;
			}
			else if(scoreDeIA==1) {
				return 1*scoreMouvement;
			}
			else if(scoreDeIA==2) {
				return 10*scoreMouvement;
			}
			else if(scoreDeIA==3) {
				return 100*scoreMouvement;
			}
			else return 1000;
		}

		
	 //Fonction pour pouvoir placer un jeton sur la grille
	 
	 public boolean placerMouvement(int colonne, int joueur, Case[][] grille){ 
	        if(!mouvementLegal(colonne,grille)) {
	        	return false;
	        	}
	        for(int i=5;i>=0;--i){
	            if(grille[colonne][i].getStatut() == 0) {
	                grille[colonne][i].set(joueur);
	                return true;
	            }
	        }
	        return false;
	    }
	    
	 //Fonction pour pouvoir annuler un mouvement
	 
	 public void annulerMouvement(int colonne, Case[][] grille){
	        for(int i=0;i<=5;++i){
	            if(grille[colonne][i].getStatut() != 0) {
	                grille[colonne][i].set(0); 
	                break;
	            }
	        }        
	    }
	 
	//Fonction exécutant l'algorithme minmax
	 
	public int minmax(int profondeur, int tour, int alpha, int beta, Case[][] grille){
		
		 // Vérifier si l'élagage alpha-bêta est possible
		  if(beta<=alpha){
			  if(tour == 2) return Integer.MAX_VALUE; 
			  else return Integer.MIN_VALUE; 
			  }
		  
		  int resultatPartie = resultatJeu(grille);
		  
		  if(resultatPartie==1)return Integer.MAX_VALUE/2;
		  
		  else if(resultatPartie==2)return Integer.MIN_VALUE/2;
		  
		  else if(resultatPartie==0)return 0; 
		  
		  if(profondeur==pronfondeurMaximum) {
			  return evaluerGrille(grille);
		  }
		  
		  int scoreMax=Integer.MIN_VALUE, scoreMin = Integer.MAX_VALUE;
		          
		  for(int j=0;j<=6;++j){
		      
		      int scoreCourant = 0;
		      
		      if(!mouvementLegal(j,grille)) continue; 
		      
		      if(tour==2){
		              placerMouvement(j, 2,grille);
		              scoreCourant = minmax(profondeur+1, 1, alpha, beta,grille);
		              if(profondeur==0){
		                  listelocations.add(scoreCourant);
		                  if(scoreCourant > scoreMax)locationProchainMouvement = j; 
		                  if(scoreCourant == Integer.MAX_VALUE/2){
		                	  annulerMouvement(j,grille);
		                	  break;
		                	  }
		              }
		              
		              scoreMax = Math.max(scoreCourant, scoreMax);
		              
		              alpha = Math.max(scoreCourant, alpha);  
		      } 
		      else if(tour==1){
		    	  
		              placerMouvement(j, 1,grille);
		              scoreCourant = minmax(profondeur+1, 2, alpha, beta,grille);
		              scoreMin = Math.min(scoreCourant, scoreMin);
		              beta = Math.min(scoreCourant, beta); 
		      }  
		      
		      annulerMouvement(j,grille); 
		      if(scoreCourant == Integer.MAX_VALUE || scoreCourant == Integer.MIN_VALUE) {
		    	  break; 
		      }
		  }  
		  return tour==2?scoreMax:scoreMin;
		
	}
	
	//permet d'evaluer le score d'une grille pour voir si celle ci est favorable ou non 
	
		public int evaluerGrille(Case[][] grille){
		      
		 
		  int k=0;
		  int mouvementsSupplementaires=0;
		  int scoreIA=1;
		  int vide = 0;
		  int score=0;
		  for(int i=5;i>=0;--i){
		      for(int j=0;j<=6;++j){
		          if(grille[j][i].getStatut()==0 || grille[j][i].getStatut()==1) {
		        	  continue; 
		          }
		          if(j<=3){ 
		              for(k=1;k<4;++k){
		                  if(grille[j+k][i].getStatut()!=1 && grille[j+k][i].getStatut()!=0) {
		                	  scoreIA++;
		                  }
		                  else if(grille[j+k][i].getStatut()==1){
		                	  scoreIA=0;
		                	  vide = 0;
		                	  break;}
		                  else vide++;
		              }
		               
		              mouvementsSupplementaires = 0; 
		              if(vide>0) 
		                  for(int c=1;c<4;++c){
		                      int colonne = j+c;
		                      for(int m=i; m<= 5;m++){
		                       if(grille[colonne][m].getStatut()==0) {
		                    	   mouvementsSupplementaires++;
		                       }
		                          else {
		                        	  break;
		                          }
		                      } 
		                  } 
		              
		              if(mouvementsSupplementaires!=0) {
		            	  score += calculerLeScore(scoreIA, mouvementsSupplementaires);
		              }
		              scoreIA=1;   
		              vide = 0;
		          } 
		          
		          if(i>=3){
		              for(k=1;k<4;++k){
		                  if(grille[j][i-k].getStatut()!=1 && grille[j][i-k].getStatut()!=0) {
		                	  scoreIA++;
		                  }
		                  else {
		                	  if(grille[j][i-k].getStatut()==1){
		                		  scoreIA=0;
		                		  break;
		                		  } 
		                  }
		              } 
		              mouvementsSupplementaires = 0; 
		              if(scoreIA>0){
		                  int colonne = j;
		                  for(int m=i-k+1; m<=i-1;m++){
		                   if(grille[colonne][m].getStatut()==0) {
		                	   mouvementsSupplementaires++;
		                   }
		                      else {
		                    	  break;
		                      }
		                  }  
		              }
		              if(mouvementsSupplementaires!=0) {
		            	  score += calculerLeScore(scoreIA, mouvementsSupplementaires);
		              }
		              scoreIA=1;  
		              vide = 0;
		          }
		           
		          if(j>=3){
		              for(k=1;k<4;++k){
		                  if(grille[j-k][i].getStatut()!=1 && grille[j-k][i].getStatut()!=0)scoreIA++;
		                  else if(grille[j-k][i].getStatut()==1){scoreIA=0; vide=0;break;}
		                  else vide++;
		              }
		              mouvementsSupplementaires=0;
		              if(vide>0) 
		                  for(int c=1;c<4;++c){
		                      int colonne = j- c;
		                      for(int m=i; m<= 5;m++){
		                       if(grille[colonne][m].getStatut()==0)mouvementsSupplementaires++;
		                          else break;
		                      } 
		                  } 
		              
		              if(mouvementsSupplementaires!=0) {
		            	  score += calculerLeScore(scoreIA, mouvementsSupplementaires);
		              }
		              scoreIA=1; 
		              vide = 0;
		          }
		           
		          if(j<=3 && i>=3){
		              for(k=1;k<4;++k){
		                  if(grille[j+k][i-k].getStatut()!=1 && grille[j+k][i-k].getStatut()!=0) {
		                	  scoreIA++;
		                  }
		                  else if(grille[j+k][i-k].getStatut()==1){scoreIA=0;vide=0;break;}
		                  else vide++;                        
		              }
		              mouvementsSupplementaires=0;
		              if(vide>0){
		                  for(int c=1;c<4;++c){
		                      int colonne = j+c, ligne = i-c;
		                      for(int m=ligne;m<=5;++m){
		                          if(grille[colonne][m].getStatut()==0)mouvementsSupplementaires++;
		                          else if(grille[colonne][m].getStatut()!=1 && grille[colonne][m].getStatut()!=0 );
		                          else break;
		                      }
		                  } 
		                  if(mouvementsSupplementaires!=0) score += calculerLeScore(scoreIA, mouvementsSupplementaires);
		                  scoreIA=1;
		                  vide = 0;
		              }
		          }
		           
		          if(i>=3 && j>=3){
		              for(k=1;k<4;++k){
		                  if(grille[j-k][i-k].getStatut()!=1 && grille[j-k][i-k].getStatut()!=0) {
		                	  scoreIA++;
		                  }
		                  else if(grille[j-k][i-k].getStatut()==1){scoreIA=0;vide=0;break;}
		                  else vide++;                        
		              }
		              mouvementsSupplementaires=0;
		              if(vide>0){
		                  for(int c=1;c<4;++c){
		                      int colonne = j-c, ligne = i-c;
		                      for(int m=ligne;m<=5;++m){
		                          if(grille[colonne][m].getStatut()==0) {
		                        	  mouvementsSupplementaires++;
		                          }
		                          else if(grille[colonne][m].getStatut()!=1 && grille[colonne][m].getStatut()!=0);
		                          else break;
		                      }
		                  } 
		                  if(mouvementsSupplementaires!=0) score += calculerLeScore(scoreIA, mouvementsSupplementaires);
		                  scoreIA=1;
		                  vide = 0;
		              }
		          } 
		      }
		  }
		  return score;
		} 
	
	//Fonction qui permet d'obtenir la case ou joue notre IA
	public int getMouvementIA(Case[][] grille){
	  locationProchainMouvement = -1;
	  minmax(0, 2, Integer.MIN_VALUE, Integer.MAX_VALUE,grille);
	  return locationProchainMouvement;
	}
	
	
	public int scoreMax() {
		int res=0;
		for(Integer l : listelocations) {
			if(res<l) {
				res=l;
			}
		}
		return res;
	}
	
	public String message(int c) {
		int var = scoreMax();
		String f ="";
		if(c==1) {
			String res = "Le sauvetage est sur la bonne voie !";
			return res;
		}
		if(var<100) {
			String res2 = "Tu es sur le bon chemin continue !";
			return res2;
		}
		if(var<500) {
			String res3 ="Aïe ! il vient de se faire mordre !";
			return res3;
		}
		if(var<10000) {
			String res4 = "Il commence à être gravement blessé !";
			return res4;
		}
		if(var>=10000) {
			String res5 = "C'est fini, il est bientôt mort !";
			return res5;
		}
		return f;
	}


	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			
			//le plateau vertical de jeu:
			
			//colonnes et lignes :
			int C=7;
			int L=6;
			
			//couleur de fond:
			Color couleurFond=Color.SKYBLUE;
			
			//root contient la scene
			Group root = new Group();
			
			//700 pixels de largeur 700 de hauteur
			Scene scene= new Scene(root,700,600+100);
			
			//couleur du font = black
			scene.setFill(couleurFond);
			
			//utile layouts:
			When w = Bindings.when((scene.widthProperty().divide(scene.heightProperty()).greaterThan(7.0/6.0)));
			
			//grille :
			Rectangle r = new Rectangle(0,0,700,600);
			LinearGradient lg = new LinearGradient(0,0,1,1,true,CycleMethod.NO_CYCLE,new Stop(0,Color.SKYBLUE),new Stop(0.5,Color.rgb(0, 0, 100)),new Stop(1,Color.SKYBLUE));
			r.setFill(lg);
			root.getChildren().addAll(r);
			
			r.heightProperty().bind(w.then(scene.heightProperty().subtract(100)).otherwise(r.widthProperty().multiply(6.0/7.0)));
			r.widthProperty().bind(w.then(r.heightProperty().multiply(7.0/6.0)).otherwise(scene.widthProperty()));
			
			for(int i=0;i<L;i++) {
				for(int j=0;j<C;j++) {
					Circle c = new Circle(5+45+100*j,5+45+100*i,45);
					c.setFill(couleurFond);
					c.radiusProperty().bind(r.heightProperty().divide(12).subtract(5));
					c.centerXProperty().bind(r.widthProperty().divide(7).multiply(j+0.5));
					c.centerYProperty().bind(r.heightProperty().divide(6).multiply(i+0.5));
					root.getChildren().add(c);
				}
			}
			
			
			//Creation des cases :
			cases = new Case[7][6];
			for(int i = 0 ; i < L ; i++){
				for (int j = 0 ; j < C ; j++){
					cases[j][i] = new Case();
					cases[j][i].layoutXProperty().bind(r.widthProperty().divide(7).multiply(j));
					cases[j][i].layoutYProperty().bind(r.heightProperty().divide(6).multiply(i));
					cases[j][i].fitHeightProperty().bind(r.heightProperty().divide(6));
					cases[j][i].fitWidthProperty().bind(r.widthProperty().divide(7));
					root.getChildren().add(cases[j][i]);
				}
			}
			 
			
			//textes:
			Label tour = new Label("Tour 1");
			tour.setTextFill(Color.DARKGREEN);
			tour.setFont(Font.font("Comic Sans MS", scene.getHeight() / 20));
			tour.setLayoutX(100);
			tour.layoutYProperty().bind(r.heightProperty());
			
			Button rejouer = new Button("Rejouer");
			rejouer.setOnAction(e->{
               		Connect4 main = new Connect4();
               		main.start(new Stage());
               	});
			rejouer.setLayoutX(550);
			rejouer.setLayoutY(660);
			
			Label joueur = new Label("Au sauveteur de jouer");
			joueur.setTextFill(Color.RED);
			joueur.setFont(Font.font("Comic Sans MS", scene.getHeight() / 20));
			joueur.setLayoutX(300);
			joueur.layoutYProperty().bind(r.heightProperty());
			
			Label victoire = new Label("");
			victoire.setTextFill(Color.MAGENTA);
			victoire.setFont(Font.font("Comic Sans MS", scene.getHeight() / 20));
			victoire.setLayoutX(300);
			victoire.layoutYProperty().bind(r.heightProperty());
			victoire.setVisible(false);
			
			Label score = new Label("");
			score.setTextFill(Color.DARKGREEN);
			score.setFont(Font.font("Comic Sans MS", scene.getHeight() / 20));
			score.setLayoutX(10);
			score.setLayoutY(640);
			
			
			scene.heightProperty().addListener(e->{
				tour.setFont(Font.font("Comic Sans MS", scene.getHeight() / 20));
				joueur.setFont(Font.font("Comic Sans MS", scene.getHeight() / 20));
				victoire.setFont(Font.font("Comic Sans MS", scene.getHeight() / 20));
			});
			
			
			
			root.getChildren().addAll(tour, joueur, victoire,score);
			
			
			//cadres de sélections:
			Rectangle[] rects = new Rectangle[C];
			
			for(int i = 0 ; i < C ; i++){
				rects[i] = new Rectangle(0, 0, 10, 10);
				rects[i].layoutXProperty().bind(r.widthProperty().divide(C).multiply(i));
				rects[i].heightProperty().bind(r.heightProperty());
				rects[i].widthProperty().bind(r.widthProperty().divide(C));
				rects[i].setFill(Color.TRANSPARENT);
				rects[i].setStroke(Color.GREENYELLOW);
				rects[i].setStrokeType(StrokeType.INSIDE);
				rects[i].setStrokeWidth(12);
				rects[i].setVisible(false);
				root.getChildren().addAll(rects[i]);
			}
			
			
			//selections:
			Rectangle r2 = new Rectangle(0,0,10,10);
			r2.heightProperty().bind(r.heightProperty());
			r2.widthProperty().bind(r.widthProperty());
			r2.setFill(Color.TRANSPARENT);
			root.getChildren().addAll(r2);
			
			precedent = -1;
			r2.setOnMouseMoved(e -> {
				
					int val = (int)(e.getX() / (r.getWidth() / C));
					if(val != precedent){
						rects[val].setVisible(true);
						if(precedent > -1)
							rects[precedent].setVisible(false);
					}
					precedent= val;
				
			});
			
			nbTour = 1;
			
			//clique:
			r2.setOnMouseClicked(e -> {
				
				score.setText(message(nbTour));
				listelocations.clear();
				int colonne = (int)(e.getX() / (r.getWidth() / C));
				
				//placement du jeton:
				if(cases[colonne][0].getStatut() == 0 && !victoire.isVisible()){
					
					int rang = L-1;
					while(cases[colonne][rang].getStatut() != 0){
						rang--;
					}
					cases[colonne][rang].set(1);
					
					
					//couleur en cours:
					int couleur = (1);
					
					//Cas de victoire sauveteur
					if(resultatJeu(cases) != 0 && resultatJeu(cases)!=-1){
						joueur.setVisible(false);
						victoire.setVisible(true);
						victoire.setTextFill(couleur == 1 ? Color.RED : Color.YELLOW);
						victoire.setText("VICTOIRE " + (couleur == 1 ? "Sauveteur" : "Serpent"));
						score.setText("Hallelujah ! tu l'as sauvé !");
						root.getChildren().add(rejouer);
						gameover=true;
						nbTour--;
					}
					
					
					nbTour++;
					
					//Cas de l'égalité
					if(resultatJeu(cases)==0){
						joueur.setVisible(false);
						victoire.setVisible(true);
						victoire.setText("EGALITE !");
						root.getChildren().add(rejouer);
						nbTour--;
					}

					tour.setText("Tour " + nbTour);
					joueur.setText("A " + (nbTour%2 == 1 ? "Sauveteur" : "Serpent") + " de jouer");
	
				
				}
				
				
				
				if(gameover!=true) {
				
				
			//tour IA :
					Case[][] nouveau = new Case[7][6];
					for(int k=0;k<6;k++) {
						for(int l=0;l<7;l++) {
							nouveau[l][k] = new Case();
							nouveau[l][k].set(cases[l][k].getStatut());
						}
					}
					int minimax = getMouvementIA(nouveau);
					if(cases[minimax][0].getStatut() == 0 && !victoire.isVisible()){
			
					
					int rangIA = L-1;
					while(cases[minimax][rangIA].getStatut() != 0){
						rangIA--;
					}
					cases[minimax][rangIA].set(2);
				
					
					//Cas victoire serpents
					if(resultatJeu(cases) != 0 && resultatJeu(cases)!=-1){
						joueur.setVisible(false);
						victoire.setVisible(true);
						score.setText("Dead ! Paix à son âme !");
						victoire.setTextFill(Color.YELLOW);
						victoire.setText("VICTOIRE " + ("Serpents"));
						root.getChildren().add(rejouer);
						nbTour--;
					}
					
					nbTour++;
						
					if(resultatJeu(cases)==0){
						joueur.setVisible(false);
						victoire.setVisible(true);
						victoire.setText("EGALITE ! Retentez ");
						root.getChildren().add(rejouer);
						nbTour--;
					}
					
					tour.setText("Tour " + nbTour);
					joueur.setText("Au " + ("sauveteur de jouer"));
								
				
				}
				
				}
				
			});
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Puissance 4 for INSA");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}