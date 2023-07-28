package com.github.donkeyrit.twinkle.panels.sidebar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;
import com.github.donkeyrit.twinkle.panels.sidebar.listeners.ModelComboBoxUpdateActionListener;
import com.github.donkeyrit.twinkle.panels.sidebar.models.MarkComboBoxModel;

public class SideBarFilterPanel extends JPanel
{ 
	private CarBodyTypeRepository carBodyTypeRepository;
	private ModelOfCarRepository modelOfCarRepository;
	private MarkOfCarRepository markOfCarRepository;

	private final List<JCheckBox> bodyTypeCheckBoxes;
	private final JComboBox<MarkOfCar> markComboBox;
	private final JComboBox<String> modelComboBox;
	
	public SideBarFilterPanel(
		ModelOfCarRepository modelOfCarRepository,
		MarkOfCarRepository markOfCarRepository,
		CarBodyTypeRepository carBodyTypeRepository,
		CarRepository carRepository,
		RentRepository rentRepository,
		DataBase database, 
		JPanel panel)
	{
		setLayout(null); 
		this.carBodyTypeRepository = carBodyTypeRepository;
		this.modelOfCarRepository = modelOfCarRepository;
		this.markOfCarRepository = markOfCarRepository;
		
		JLabel mainLabel = new JLabel("Применить фильтр"); 
		Font font = new Font("Arial", Font.BOLD, 13); 
		mainLabel.setFont(font); 
		mainLabel.setBounds(40, 10, 140, 20); 
		add(mainLabel);
		
		this.markComboBox = createMarkComboBox();
		this.modelComboBox = createModelComboBox();
		ModelComboBoxUpdateActionListener modelActionListener = new ModelComboBoxUpdateActionListener(
			this.markComboBox, 
			this.modelComboBox, 
			this.modelOfCarRepository);
		this.markComboBox.addActionListener(modelActionListener);
		add(markComboBox); 
		add(modelComboBox);

		ResultSet priceSet = database.select("SELECT MAX(cost) as price FROM car"); 
		int d = 1; 
		try{
			while(priceSet.next()){
				double m2 = priceSet.getDouble("price"); 
				int max = (int) m2; 
				 d = (int) (max / 10000); 
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}

		JLabel label = new JLabel("Choose price:"); 
		label.setBounds(60,110,100,30);
		add(label); 
		
		JSlider price = new JSlider(JSlider.HORIZONTAL, 0, 10 * d,0); 
		price.setMajorTickSpacing(((10 * d) / 4)); 
		price.setMinorTickSpacing(((10 * d) / 8)); 
		price.setPaintTicks(true); 
		price.setPaintLabels(true); 
		price.setSnapToTicks(true); 
		price.setBounds(10, 150, 180, 45);
		add(price); 
		
		this.bodyTypeCheckBoxes = createBodyTypeCheckBoxes();
		
		JButton applyFilter = new JButton("Apply"); 
		applyFilter.setBounds(50, 520, 100, 20);
		applyFilter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) { 
				String resultCondition = ""; 
				
				String selectedMark = markComboBox.getSelectedItem().toString(); 
				if(!selectedMark.equals("All marks")){
					resultCondition += "mark_name = " + "'" + selectedMark + "'";
				}else{
					resultCondition += "!";
				}
				
				resultCondition += ":"; 
				
				String selectedModel = modelComboBox.getSelectedItem().toString(); 
				if(!selectedModel.equals("All models")){
					resultCondition += "model_name = " + "'" + selectedModel + "'";
				}else{
					resultCondition += "!";
				}
				
				resultCondition += ":"; 
				
				int selectedPrice = price.getValue(); 
				if(selectedPrice != 0){
					resultCondition += "cost < " + selectedPrice * 1000;
				}else{
					resultCondition += "!";
				}
				resultCondition += ":"; 
				
				String selectedCheckBoxes = ""; 
				ArrayList<String> selectedCB = new ArrayList<String>();
				for(int i = 0; i< bodyTypeCheckBoxes.size(); i++){
					boolean isTrue = bodyTypeCheckBoxes.get(i).isSelected(); 
					if(isTrue){
						selectedCB.add(bodyTypeCheckBoxes.get(i).getText());
					} 
				}
				
				if(selectedCB.size() == 0){
					resultCondition += "!";
				}else{
					selectedCheckBoxes += "body_type_name IN (";
					for(int i = 0; i < selectedCB.size(); i++){
						selectedCheckBoxes += "'" + selectedCB.get(i) + "'";
						if(i != selectedCB.size() - 1){
							selectedCheckBoxes += ",";
						}
					}
					selectedCheckBoxes += ")";
				}
				resultCondition += selectedCheckBoxes;
				
				String res  = resultCondition.replaceAll("!", ""); 
				String[] masive = res.split(":"); 
				String resStr = ""; 
				for(int i = 0; i < masive.length; i++){ 
					if(masive[i].equals("")){
						continue; 
					}else{
						resStr += masive[i]; 
						if(i != masive.length - 1){ 
							resStr += " AND "; 
						}
					}
				}
				
				Component[] mas = panel.getComponents(); 
				JPanel temp = null; 
				for(int i = 0; i < mas.length; i++){
					if(mas[i].getClass().toString().indexOf("ContentPanel") != -1 || mas[i].getClass().toString().indexOf("AboutCarPanel") != -1 ){
						temp = (JPanel) mas[i];
					}
				}

				panel.remove(temp); 
				JPanel content = new ContentPanel(panel, carRepository, rentRepository, database, resStr); 
				content.setBounds(250,100,605,550); 
				panel.add(content); 
				panel.revalidate(); 
				panel.repaint(); 
			}		
		});
		add(applyFilter);
	}

	private JComboBox<MarkOfCar> createMarkComboBox()
	{
		List<MarkOfCar> marks = markOfCarRepository
			.getList()
			.collect(Collectors.toList());

		MarkComboBoxModel markComboBoxModel = new MarkComboBoxModel(marks, "All marks");
		JComboBox<MarkOfCar> markComboBox = new JComboBox<MarkOfCar>(markComboBoxModel);
		markComboBox.setBounds(10, 40, 180, 20); 

		return markComboBox;
	}

	private JComboBox<String> createModelComboBox()
	{
		JComboBox<String> modelComboBox = new JComboBox<>(new String[]{"All models"});
		modelComboBox.setBounds(10, 70, 180, 20); 
		return modelComboBox;
	}

	private List<JCheckBox> createBodyTypeCheckBoxes() 
	{
		Box box = Box.createVerticalBox();
		List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

		List<JCheckBox> bodyTypeCheckBoxes = this.carBodyTypeRepository
			.getList()
			.map(bodyType -> {
				JCheckBox checkBox = new JCheckBox(bodyType.getType()); 
				checkBoxes.add(checkBox); 
				box.add(checkBox);
				return checkBox;
			})
			.collect(Collectors.toList());

		JScrollPane scrollPane = new JScrollPane(box); 
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); 
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(new TitledBorder("Типы кузова")); 
		scrollPane.setBounds(10, 225, 180, 270);
		add(scrollPane);

		return bodyTypeCheckBoxes;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(237,237,237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(),30,25);
	}
}
