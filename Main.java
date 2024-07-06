package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Main extends Application {
//Button
	static Button read = new Button("Read The File");
	static Button show = new Button("Show File Date");
	static Button back = new Button("Back Home");
	static Button cont = new Button("Continue");
	static VBox vbox = new VBox();
	static TextArea textArea = new TextArea();
	static Stage stage;
	static int numberOfCities = 0;
	static int fromCity = 0;
	static int toCity = 0;
	static int dis = 0;
	static String startFrom = "";
	static String path;
	static String destination;
	static String[][] next;
	static String[] city;
	static String input = "";
	static int[][] tableView;
	static ComboBox<String> startCity = new ComboBox<String>();
	static ComboBox<String> endCity = new ComboBox<String>();
	static int INF = Integer.MAX_VALUE;

	@Override
	public void start(Stage primaryStage) throws IOException {
		BorderPane bpane = new BorderPane();
		bpane.setCenter(getmainPage());
		Scene sen = new Scene(bpane, 500, 500);
		primaryStage.setScene(sen);
		primaryStage.show();
		primaryStage.setMaximized(true);
		// textArea.setVisible(false);
		try {
			read.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*", "*.txt"));
				File chosenFile = fileChooser.showOpenDialog(primaryStage);

				if (chosenFile != null) {
					try (Scanner scanner = new Scanner(chosenFile)) {
						String fileContent = readFileContent(chosenFile);
						textArea.setText(fileContent);
						String number = scanner.nextLine();
						int numberOfCities = Integer.parseInt(number); // number of cities

						int numOfLine = 0;
						 city = new String[numberOfCities];

						String cityEnd = "";

						 input = "";

						while (scanner.hasNext()) {
							numOfLine++;
							if (numOfLine == 1) {
								String s = scanner.nextLine();
								String[] str = s.split(", ");
								cityEnd = str[1]; // str[1] = end
								city[0] = str[0];
								city[numberOfCities - 1] = str[1];
								continue;
							}

							String s = scanner.nextLine(); // line

							input += s + "\n";
							System.out.println(input);

							String[] str = s.split(", "); // start city in each line

							city[numOfLine - 2] = str[0]; // add cities to the array

							startCity.getItems().addAll(str[0]); // add cities to comboBox
							endCity.getItems().addAll(str[0]); // add cities to comboBox
						} // end of while loop

						startCity.getItems().addAll(cityEnd); // add endCity to comboBox
						endCity.getItems().addAll(cityEnd); // add endCity to comboBox

						startCity.setValue(startCity.getItems().get(0));
						endCity.setValue(endCity.getItems().get(numberOfCities - 1));
//
//						startCity.setPrefWidth(200);
//						startCity.setPrefHeight(50);
//
//						endCity.setPrefWidth(200);
//						endCity.setPrefHeight(50);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			show.setOnAction(e -> {
				show.setVisible(false);
				read.setVisible(false);
				vbox.setVisible(true);

				textArea.setVisible(true);

			});
			back.setOnAction(e -> {
				primaryStage.setScene(sen);
				primaryStage.show();
				primaryStage.setMaximized(true);
				textArea.setVisible(false);
				vbox.setVisible(false);
				read.setVisible(true);
				show.setVisible(true);
			});
			cont.setOnAction(e -> {
				Scene secondScene = new Scene(getInfo());
				primaryStage.setScene(secondScene);
				primaryStage.show();
				primaryStage.setFullScreen(true);

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// main page
	private static VBox getmainPage() {
		// title and image
		VBox panes = new VBox();
		VBox hbx = new VBox();
		Label txt = new Label(
				"Optimal Strategy for Minimum cost from city1 to city1 using Dynamic \r\n" + "Programming ");
		txt.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, FontPosture.ITALIC, 50));
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.5f, 0.5f, 0.5f));
		txt.setEffect(ds);
		txt.setCache(true);
		txt.setTextFill(Color.LEMONCHIFFON);
		hbx.setPadding(new Insets(20, 20, 170, 20));
		hbx.setAlignment(Pos.CENTER);
		hbx.getChildren().add(txt);
		BackgroundImage b2 = new BackgroundImage(
				new Image("C:\\Users\\LENOVO\\Desktop\\Data Structer\\Algoo1\\src\\MTMwMTI2MjYyMTYwMTAzOTAy.jpg"),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		Background bb2 = new Background(b2);
		panes.setBackground(bb2);

		// button
		FlowPane flow = new FlowPane();
		show = new Button("Show Data");
		read = new Button("Read File");
		// Styling
		show.setStyle("-fx-background-color:#FFE4C4; " + "-fx-text-fill: black; " + "-fx-border-color: black; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-padding: 8px 16px;");
		show.setPrefHeight(50);
		show.setPrefWidth(150);
		read.setStyle("-fx-background-color:#FFE4C4; " + "-fx-text-fill: black; " + "-fx-border-color: black; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-padding: 8px 16px;");
		read.setPrefHeight(50);
		read.setPrefWidth(150);

		back.setStyle("-fx-background-color:#FFE4C4; " + "-fx-text-fill: black; " + "-fx-border-color: black; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-padding: 8px 16px;");
		back.setPrefHeight(50);
		back.setPrefWidth(150);
		cont.setStyle("-fx-background-color:#FFE4C4; " + "-fx-text-fill: black; " + "-fx-border-color: black; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-padding: 8px 16px;");
		cont.setPrefHeight(50);
		cont.setPrefWidth(150);
		textArea.setStyle("-fx-background-color:#FFE4C4; " + "-fx-text-fill: black; " + "-fx-border-color: black; ");
		// show.setMaxHeight(50);

		flow.setAlignment(Pos.CENTER);
		flow.setPadding(new Insets(5));
		flow.setHgap(10);
		flow.setVgap(10);
		vbox.getChildren().addAll(back, cont);
		vbox.setSpacing(10);
		vbox.setVisible(false);
		textArea.setVisible(false);
		flow.getChildren().addAll(read, show, textArea, vbox);
		panes.getChildren().addAll(hbx, flow);
		return panes;

	}

	private static String readFileContent(File file) {
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private static VBox getInfo() {
		VBox pane = new VBox();
		TextField bestCost = new TextField();
		TextField pathText = new TextField();
		// First Step
		HBox sel = new HBox();
		Label start = new Label(" Strat From : ");
		Label end = new Label(" To : ");
		Button minCost = new Button("Show Minimum Cost");
		Font font = Font.font("Lucida Sans Unicode", FontWeight.BOLD, 30);
		start.setTextFill(Color.BLACK);
		start.setFont(font);
		end.setTextFill(Color.BLACK);
		end.setFont(font);
		minCost.setPrefHeight(50);
		minCost.setPrefWidth(150);
		minCost.setStyle("-fx-background-color:#AFEEEE; " + "-fx-text-fill: black; " + "-fx-border-color: black; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-padding: 8px 16px;");
		sel.getChildren().add( minCost);
		sel.setSpacing(10);
		sel.setAlignment(Pos.CENTER);
		minCost.setOnAction(e -> {
			if (toCity >= fromCity) {
				System.out.println(startCity.getValue());
				System.out.println(endCity.getValue());
				findMinAlgo(startCity, endCity, input);
				bestCost.setText(Integer.toString(tableView[0][numberOfCities - 1]));
			} else
				bestCost.setText("Sorry, There is no direct way.");
		});

		// Second Step
		HBox mCost = new HBox();
		Label min = new Label(" Minimum Cost : ");
		min.setFont(font);
		bestCost.setEditable(false);
		bestCost.setPromptText("Best Cost");
		bestCost.setPrefColumnCount(6);
		bestCost.setPrefWidth(150);
		bestCost.setPrefHeight(50);
		Button bPath = new Button("Show Best Path");
		bPath.setPrefHeight(50);
		bPath.setPrefWidth(150);
		bPath.setStyle("-fx-background-color:#AFEEEE; " + "-fx-text-fill: black; " + "-fx-border-color: black; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-padding: 8px 16px;");
		bPath.setOnAction(e -> {
			if (toCity >= fromCity && tableView[0][numberOfCities - 1] != Integer.MAX_VALUE)
				pathText.setText(printPath(next, startFrom, next[0][numberOfCities - 1]) + destination);
			else
				pathText.setText("Sorry, There is no direct way.");
		});

		mCost.getChildren().addAll(min, bestCost, bPath);
		mCost.setAlignment(Pos.CENTER);
		mCost.setSpacing(10);

		// Step3
		HBox path = new HBox();
		Label bestPath = new Label(" Best Path : ");
		bestPath.setFont(font);
		pathText.setEditable(false);
		pathText.setPromptText("Best Path");
		pathText.setPrefColumnCount(6);
		pathText.setPrefWidth(200);
		pathText.setPrefHeight(50);
		Button DPtable = new Button("Show The DP table");
		DPtable.setPrefHeight(50);
		DPtable.setPrefWidth(150);
		DPtable.setStyle("-fx-background-color:#AFEEEE; " + "-fx-text-fill: black; " + "-fx-border-color: black; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-padding: 8px 16px;");
		path.getChildren().addAll(bestPath, pathText, DPtable);
		path.setAlignment(Pos.CENTER);
		path.setSpacing(10);
		DPtable.setOnAction(e -> {
		    TextArea taTable = new TextArea();
		    taTable.setEditable(false);
		    taTable.setPrefHeight(600);
		    taTable.setPrefWidth(600);
		    taTable.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
		    taTable.setText("");
		    
		    if (toCity >= fromCity) {
		        // Call the buildOutputTable method and pass the required parameters
		        StringBuilder output = buildOutputTable(startFrom, dis, toCity, city, tableView, numberOfCities);
		        
		        taTable.setStyle("-fx-font-family: 'Courier New', monospaced;");
		        taTable.appendText(output.toString());
		    } else {
		        taTable.appendText("Sorry, The road is in one way and you can't back.");
		    }

		    VBox vbTable = new VBox(taTable);
		    vbTable.setSpacing(50);
		    vbTable.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

		    Scene sceneTable = new Scene(vbTable, 800, 600);
		    stage = new Stage();
		    stage.setScene(sceneTable);
		    stage.setFullScreen(true);
		    stage.show();
		});



		// Step4
		HBox all = new HBox();
		Label allPath = new Label(" The alternatives result : ");
		allPath.setFont(font);
		Button paths = new Button("Show Result");
		paths.setPrefHeight(50);
		paths.setPrefWidth(150);
		paths.setStyle("-fx-background-color:#AFEEEE; " + "-fx-text-fill: black; " + "-fx-border-color: black; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-padding: 8px 16px;");
		all.getChildren().addAll(allPath, paths);
		all.setAlignment(Pos.CENTER);
	

		// Last Step
		HBox textArea = new HBox();
		TextArea alternatives = new TextArea();
		alternatives.setEditable(false);
		alternatives.setPromptText("All Result");
		alternatives.setPrefColumnCount(6);
		alternatives.setPrefWidth(500);
		alternatives.setPrefHeight(500);
		alternatives
				.setStyle("-fx-background-color:#AFEEEE; " + "-fx-text-fill: black; " + "-fx-border-color: black; ");
		textArea.getChildren().add(alternatives);
		textArea.setAlignment(Pos.CENTER);
		paths.setOnAction(e -> {
			if (toCity >= fromCity && tableView[0][numberOfCities - 1] != Integer.MAX_VALUE)
				
				alternatives.setText(printShortestPathsToDestination(tableView, city, startFrom, destination));
			
			else
				alternatives.setText("Sorry, There is no direct way.");
		});
		BackgroundImage b2 = new BackgroundImage(
				new Image("C:\\Users\\LENOVO\\Desktop\\Data Structer\\Algoo1\\src\\MTMwMTI2MjYyMTYwMTAzOTAy.jpg"),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		Background bb2 = new Background(b2);
		pane.setBackground(bb2);
		pane.setSpacing(5);
		pane.getChildren().addAll(sel, mCost, path, all, textArea);
		return pane;
	}

	public static void findMinAlgo(ComboBox<String> start, ComboBox<String> end, String input) {
	    startFrom = start.getValue();
	    destination = end.getValue();

	    fromCity = start.getSelectionModel().getSelectedIndex();
	    toCity = end.getSelectionModel().getSelectedIndex();

	    dis = fromCity;

	    numberOfCities = toCity - fromCity + 1;

	    initializeTables(numberOfCities);

	    fillTable(input);

	    applayDynamic();

	    adjustIndices();

	    handleUnreachableCities();
	}

	private static void initializeTables(int numOfCities) {
	    tableView = new int[numOfCities][numOfCities];
	    next = new String[numOfCities][numOfCities];

	    for (int i = 0; i < numOfCities; i++) {
	        for (int j = 0; j < numOfCities; j++) {
	            if (i == j)
	                tableView[i][j] = 0;
	            else
	                tableView[i][j] = Integer.MAX_VALUE;
	            next[i][j] = "X"; // that there is no next city in the shortest path yet 
	        }
	    }
	}
	
//The method takes a string parameter input, which represents the input containing city connections and costs.
	private static void fillTable(String input) {
	    String[] line = input.split("\n");

	    for (int i = 0; i < numberOfCities - 1; i++) {
	        String[] parts = line[i + fromCity].split(", (?=\\[)");//splits each line of the input based on the comma followed by a space and an opening square bracket.

	        int city1 = i; //representing the index of the first city.

	        for (int j = 1; j < parts.length; j++) {
	            String[] cityAndCosts = parts[j].replaceAll("[\\[\\]]", "").split(",");
	            String item = cityAndCosts[0].trim();

	            int city2 = 0;

	            for (int k = fromCity; k <= toCity; k++) {
	                if (startCity.getItems().get(k).equals(item)) {
	                    city2 = k - fromCity;
	                    break;
	                }
	            }
	            int petrolCost = Integer.parseInt(cityAndCosts[1].trim());
	            int hotelCost = Integer.parseInt(cityAndCosts[2].trim());

	            tableView[city1][city2] = petrolCost + hotelCost;
	        }
	    }

	    for (int i = 0; i < numberOfCities; i++) {
	        for (int j = 0; j < numberOfCities; j++) {
	            if (j < i) // cost was maximum value for cells where the destination city index is less than the source city index, ensuring that these cells are not considered in the shortest path calculation.
	            	tableView[i][j] = Integer.MAX_VALUE;
	            if (i == j)
	            	tableView[i][j] = 0;
	        }
	    }
	}

	private static void applayDynamic() {
	    for (int i = 0; i < numberOfCities; i++) {
	        for (int j = 0; j < numberOfCities; j++) {
	            for (int k = 0; k < numberOfCities; k++) {
	                if (tableView[j][i] == Integer.MAX_VALUE || tableView[i][k] == Integer.MAX_VALUE) // if there is no direct connection between city j and i, or between city i and k, which is represented by Integer.MAX_VALUE in the tableView array. If either condition is true, the loop proceeds to the next iteration
	                    continue;

	                if (tableView[j][k] > tableView[j][i] + tableView[i][k]) {
	                	tableView[j][k] = tableView[j][i] + tableView[i][k];
	                    next[j][k] = city[i + dis]; //updated with the next city in the shortest path
	                }
	            }
	        }
	    }
	}
	
//	private static void applyDynamicProgramming() {
//	    for (int i = toCity - 2; i >= fromCity; i--) {
//	        for (int j = 0; j < numberOfCities; j++) {
//	            int minCost = Integer.MAX_VALUE;
//	            String nextCity = "";
//
//	            for (int k = fromCity; k <= toCity; k++) {
//	                int cost = tableView[i][k - fromCity] + tableView[i + 1][j];
//	                if (cost < minCost) {
//	                    minCost = cost;
//	                    nextCity = city[k];
//	                }
//	            }
//
//	            tableView[i][j] = minCost;
//	            next[i][j] = nextCity;
//	        }
//	    }
//	}

	private static void adjustIndices() {
	    for (int i = 1; i < numberOfCities; i++) {
	        if (next[0][i] == "X")
	            next[0][i] = startFrom;
	        else
	            break;
	    }

	    fromCity = fromCity - dis;
	    toCity = toCity - dis;
	}
	// this method iterates over the cities represented by the columns of the tableView array.
	private static void handleUnreachableCities() {
	    for (int i = 0; i < next.length; i++) {
	        if (tableView[0][i] == 0 || tableView[0][i] == Integer.MAX_VALUE)
	            next[0][i] = "X";
	    }
	}


		public static String printPath(String[][] arr, String src, String cities) {
			int size = toCity;  //represents the total number of cities
			String path = "";
			int diff = 0;
			while (!cities.equals(src)) {
				for (int j = size; j >= fromCity; j--) { // the cities from the last one (size) to the starting city (fromCity)
					if (j == fromCity)
						break;

					if (city[j + dis].equals(cities)) {

						diff = size - j; //the number of cities between the current city (identified in the loop) and the destination city,
						path = city[j + dis] + " -> " + path; // Start -> B -> E -> I -> J
						cities = arr[0][toCity - diff]; // I
					}
				}
//				cities = src; // we had reached the starting city
			}
			return src + " -----> " + path;
		}

		public static String printShortestPathsToDestination(int[][] table, String[] city, String source,
				String destination) {
			String result = "";

			int shortestDistance = table[0][numberOfCities - 1];

			// If there is a direct connection from the source to the destination
			if (shortestDistance != Integer.MAX_VALUE) {
				List<Path> shortestPaths = new ArrayList<>();
				List<String> currentPath = new ArrayList<>();
				currentPath.add(city[fromCity]);
				printAllShortestPathsHelper(table, city, fromCity, toCity, currentPath, shortestPaths);
				result += "Shortest paths from " + source + " to " + destination + ":\n";
				if (shortestPaths.isEmpty()) {
					result += "No paths found.\n";
				} else {
					int count = 0;
					for (int i = 1; i < shortestPaths.size(); i++) {
						Path path = shortestPaths.get(i);
						result += "Path: " + path.getPath() + "\n";
						result += "Cost: " + path.getCost() + "\n";
						count++;
						if (count == 2) {
							break;
						}
					}
				}
				return result;
			}
			return "No direct connection from " + source + " to " + destination;
		}

		private static void printAllShortestPathsHelper(int[][] table, String[] city, int currentIndex,
		        int destinationIndex, List<String> currentPath, List<Path> shortestPaths) {
		    if (currentIndex == destinationIndex) {
		        // Add the current path to the list of shortest paths
		        shortestPaths.add(new Path(String.join(" -> ", currentPath), calculatePathCost(table, currentPath)));
		    } else {
		        for (int i = 0; i < table.length; i++) { // Adjust loop boundary to table length
		            if (table[currentIndex][i] != Integer.MAX_VALUE && i != currentIndex) {
		                currentPath.add(city[i]);
		                printAllShortestPathsHelper(table, city, i, destinationIndex, currentPath, shortestPaths);
		                currentPath.remove(currentPath.size() - 1);
		            }
		        }
		    }
		}


		private static int calculatePathCost(int[][] table, List<String> path) {
			int cost = 0;
			for (int i = 0; i < path.size() - 1; i++) {
				int city1Index = getIndex(path.get(i));
				int city2Index = getIndex(path.get(i + 1));
				cost += table[city1Index][city2Index];
			}
			return cost;
		}
		private static int getIndex(String cityName) {
			// Assuming the city array contains unique city names
			for (int i = 0; i < city.length; i++) {
				if (city[i].equals(cityName)) {
					return i;
				}
			}
			return -1;
		}

		private static StringBuilder buildOutputTable(String startFrom, int dis, int toCity, String[] city, int[][] tableView, int numberOfCities) {
		    StringBuilder outputBuilder = new StringBuilder();

		    // Adjust 'next' array and prepare column headers
		    for (int i = 1; i < toCity; i++) {
		        if (next[0][i].equals("X")) {
		            next[0][i] = startFrom;
		        }
		    }
		    outputBuilder.append("          ");
		    for (int i = dis; i <= toCity + dis; i++) {
		        outputBuilder.append(String.format("%-10s", city[i]));
		    }
		    outputBuilder.append("\n");

		    // Construct the table content
		    for (int i = 0; i < numberOfCities; i++) { // print the paths
		        outputBuilder.append(String.format("%-10s", city[i + dis]));
		        for (int j = 0; j < numberOfCities; j++) {
		            if (tableView[i][j] == Integer.MAX_VALUE || j < i) {
		                outputBuilder.append(String.format("%-10s", ""));
		            } else {
		                outputBuilder.append(String.format("%-10s", tableView[i][j]));
		            }
		            if (j == numberOfCities - 1) {
		                outputBuilder.append("\n");
		                outputBuilder.append("\n");
		            }
		        }
		    }

		    return outputBuilder;
		}


	public static void main(String[] args) {
		launch(args);
	}
}
