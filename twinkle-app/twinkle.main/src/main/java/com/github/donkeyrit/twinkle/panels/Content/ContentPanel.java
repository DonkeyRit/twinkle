package com.github.donkeyrit.twinkle.panels.content;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.border.*;
import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.panels.content.listeners.NextBackListener;
import com.github.donkeyrit.twinkle.panels.content.listeners.ScrollPageListener;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

public class ContentPanel extends JPanel
{ 
	public int numOfPage = 1; 
	public int startBut = 1; 
	public String conditionPanel = ""; 
	
	public ContentPanel(
		JPanel panel, 
		CarRepository carRepository, 
		RentRepository rentRepository, 
		DataBase database, 
		String condition, 
		int ... args)
	{
		setLayout(null); 
		conditionPanel = condition; 
		
		JLabel contentMainLabel = new JLabel("List of cars:"); 
		Font font = new Font("Arial", Font.BOLD, 13); 
		contentMainLabel.setFont(font); 
		contentMainLabel.setBounds(250,10,100,20); 
		add(contentMainLabel); 
		
		JButton reload = new JButton(); 
		reload.setBounds(568, 10,16,16); 
		ImageIcon iconExit = AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/reload.png"); 
		reload.setIcon(iconExit); 
		reload.setHorizontalTextPosition(SwingConstants.LEFT);
		reload.addActionListener(new ActionListener(){ 
			@Override
			public void actionPerformed(ActionEvent e) {
				Component[] mas = panel.getComponents(); 
				JPanel temp = null; 
				for(int i = 0; i < mas.length; i++){
					if(mas[i].getClass().toString().indexOf("ContentPanel") != -1){
						temp = (JPanel) mas[i];
					}
				}
				
				panel.remove(temp); 
				JPanel content = new ContentPanel(panel, carRepository, rentRepository, database, ""); 
				content.setBounds(250,100,605,550); 
				panel.add(content); 
				panel.revalidate(); 
				panel.repaint(); 
			}
		});
		add(reload);
		
		if(args.length != 0){ 
			numOfPage = args[0]; 
		}
		
		String conditionQuery = ""; 
		if(!condition.equals("")){ 
			conditionQuery = "WHERE " + condition;
		}
		
		String query = "SELECT id FROM\n" +
"                (SELECT id,model_year,info,cost,model_name,id_body_type,mark_name,country_name FROM\n" +
"                (SELECT id,model_year,image,info,cost,model_name,id_body_type,mark_name,id_country FROM \n" +
"                (SELECT id,model_year,image,info,cost,model_name,id_mark,id_body_type FROM car \n" +
"                INNER JOIN model ON car.id_model = model.id_model) as join1\n" +
"                INNER JOIN mark ON join1.id_mark = mark.id_mark) as join2\n" +
"                INNER JOIN country ON join2.id_country = country.id_country) as join3\n" +
"                INNER JOIN body_type ON join3.id_body_type = body_type.id_body_type " + conditionQuery + " ORDER BY model_year DESC";

		ResultSet carsSet = database.select(query); 
		ArrayList<Integer> carsList = new ArrayList<Integer>(); 
		int numString = 0; 
		try{
			while(carsSet.next()){
				carsList.add(carsSet.getInt("id")); 
			}
			carsSet.last(); 
			numString = carsSet.getRow(); 
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		int a  = numString - (numOfPage - 1) * 4; 
		int size = 0; 
		if(a > 4){ 
			size = 4; 
		}else{ 
			size = a; 
		}
		
		int start = (numOfPage - 1) * 4; 
		int end = (numOfPage - 1) * 4 + size; 
		
		for(int i = start,j = 0; i < end; i++,j++){
			JPanel temp = new CarPanel(carRepository, rentRepository, database, panel, carsList.get(i)); 
			temp.setBorder(new LineBorder(new Color(0,163,163), 4)); 
			temp.setBounds(20,40 + j * 120,565,100); 
			add(temp); 
		}
		
		int num = (int)(Math.ceil(numString / 4f)); 
		if(args.length != 0 && args.length > 1){
			startBut = args[1]; 
		}

		int m = startBut + 5;
		if(m > num){
			m = num + 1;
		}
		
		Box buttonBox = Box.createHorizontalBox(); 
		buttonBox.setBounds(205, 520, 400, 30); 
		
		if(startBut > 5){ 
			JButton backBut = new JButton(AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/back.png")); 
			backBut.addActionListener(new NextBackListener(panel, carRepository, rentRepository, database)); 
			
			
			buttonBox.add(backBut); 
		}
		
		Font buttonFont = new Font("Arial", Font.ITALIC, 10); 
		ArrayList<JButton> buttonsList = new ArrayList<JButton>(); 
		for(int i = startBut; i < m; i++){
			JButton temp = new JButton(i + ""); 
			
			temp.setFont(buttonFont); 
			temp.addActionListener(new ScrollPageListener(panel, carRepository, rentRepository, database)); 
			
			buttonBox.add(temp); 
			buttonsList.add(temp); 
		}
		
		if(m != (num + 1)){ 
			JButton nextBut = new JButton(AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/next.png")); 
			nextBut.addActionListener(new NextBackListener(panel, carRepository, rentRepository, database)); 
			
			
			buttonBox.add(nextBut); 
		}  
		add(buttonBox); 
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.setColor(new Color(237,237,237)); 
		g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),30,25); 
	}
}

