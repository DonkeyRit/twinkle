package com.github.donkeyrit.twinkle.panels.content;

import com.github.donkeyrit.twinkle.panels.content.listeners.ScrollPageListener;
import com.github.donkeyrit.twinkle.panels.content.listeners.NextBackListener;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;
import com.github.donkeyrit.twinkle.DataBase;

import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class ContentPanel extends JPanel
{ 
	private final CarQueryFilter filter;
	
	public CarQueryFilter getFilter() {
		return filter;
	}

	public ContentPanel(
		JPanel panel, 
		CarRepository carRepository, 
		RentRepository rentRepository, 
		DataBase database)
	{
		this(panel, carRepository, rentRepository, database, new CarQueryFilter());
	}

	public ContentPanel(
		JPanel panel, 
		CarRepository carRepository, 
		RentRepository rentRepository, 
		DataBase database, 
		CarQueryFilter filter)
	{
		setLayout(null); 
		this.filter = filter;
		
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

				JPanel content = new ContentPanel(panel, carRepository, rentRepository, database); 
				content.setBounds(250,100,605,550); 
				panel.add(content); 
				panel.revalidate(); 
				panel.repaint(); 
			}
		});
		add(reload);

		java.util.List<Car> filteredCars = carRepository.getList(filter).toList();
		int j = 0;
		
		for (Car car : filteredCars) {
			JPanel temp = new CarPanel(carRepository, rentRepository, database, panel, car.getId()); 
			temp.setBorder(new LineBorder(new Color(0,163,163), 4)); 
			temp.setBounds(20,40 + j * 120,565,100); 
			add(temp); 
		}
		
		/* int num = (int)(Math.ceil(numString / 4f)); 
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
		add(buttonBox);  */
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.setColor(new Color(237,237,237)); 
		g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),30,25); 
	}
}

