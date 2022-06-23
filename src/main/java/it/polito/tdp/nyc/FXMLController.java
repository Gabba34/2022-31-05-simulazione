/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.nyc.model.Borough;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.Neighbor;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="cmbQuartiere"
    private ComboBox<Borough> cmbQuartiere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML // fx:id="clQuartiere"
    private TableColumn<Neighbor, String > clQuartiere; // Value injected by FXMLLoader
 
    @FXML // fx:id="clDistanza"
    private TableColumn<Neighbor, Double> clDistanza; // Value injected by FXMLLoader
    
    @FXML // fx:id="tblQuartieri"
    private TableView<Neighbor> tblQuartieri; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	cmbQuartiere.getItems().clear();
    	tblQuartieri.getItems().clear();
    	String provider = cmbProvider.getValue();
    	if(provider!=null) {
    		List<Borough> boroughs = new ArrayList<>(model.getBoroughs(provider));
    		Collections.sort(boroughs, new Comparator<Borough>() {
    			@Override
    			public int compare(Borough o1, Borough o2) {
    				return o1.getBoroName().compareTo(o2.getBoroName());
    			}
    		});
    		cmbQuartiere.getItems().addAll(boroughs);
    		txtResult.setText(model.createGraph());
    	} else {
    		txtResult.setText("Seleziona un provider!");
    		return;
    	}
    }

    @FXML
    void doQuartieriAdiacenti(ActionEvent event) {
    	Borough borough = cmbQuartiere.getValue();
    	if(borough!=null) {
    		txtResult.clear();
    		List<Neighbor> neighbors = model.getNeighbor(borough);
    		tblQuartieri.setItems(FXCollections.observableArrayList(neighbors));
    	} else {
    		txtResult.setText("Seleziona un quartiere!");
    		return;
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	Borough b = cmbQuartiere.getValue();
    	if(b==null) {
    		txtResult.setText("Seleziona un quartiere!");
    		return;
    	}
    	int N=0;
    	try {
			N=Integer.parseInt(txtMemoria.getText());
		} catch (NumberFormatException e) {
			txtResult.appendText("Errore: inserire un numero valido\n");
    		return;
    	}
    	model.simulation(b, N);
    	txtResult.appendText("Durata simulazione: "+model.getDuration()+" minuti\n");
    	txtResult.appendText("Impegni dei tecnici: "+model.getRevisioned()+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbQuartiere != null : "fx:id=\"cmbQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clDistanza != null : "fx:id=\"clDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clQuartiere != null : "fx:id=\"clQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        clQuartiere.setCellValueFactory(new PropertyValueFactory<Neighbor, String>("name"));
		clDistanza.setCellValueFactory(new PropertyValueFactory<Neighbor, Double>("distance"));
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<String> providers = new ArrayList<>(model.getProviders());
    	Collections.sort(providers);
    	cmbProvider.getItems().addAll(providers);
    }

}
