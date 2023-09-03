package com.github.donkeyrit.twinkle.panels.content;

import com.github.donkeyrit.twinkle.events.contracts.ContentEventsListener;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.bll.services.contracts.CarService;
import com.github.donkeyrit.twinkle.controls.MarkComboBoxModel;

import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import com.google.inject.Inject;

public class SideBarFilterPanel extends JPanel {
	
	private final List<JCheckBox> bodyTypeCheckBoxes;
	private final JComboBox<MarkOfCar> markComboBox;
	private final JComboBox<String> modelComboBox;

	private final CarService carService;

	@Inject
	public SideBarFilterPanel(ContentEventsListener contentEventsListener, CarService carService) {
		setLayout(null);
		this.carService = carService;

		JLabel mainLabel = new JLabel("Применить фильтр");
		Font font = new Font("Arial", Font.BOLD, 13);
		mainLabel.setFont(font);
		mainLabel.setBounds(40, 10, 140, 20);
		add(mainLabel);

		this.markComboBox = createMarkComboBox();
		this.modelComboBox = createModelComboBox();
		this.markComboBox.addActionListener(e -> {
			MarkOfCar markSelected = (MarkOfCar) this.markComboBox.getSelectedItem();
			Optional<List<String>> modelList = this.carService.getExistingModelsByMark(markSelected);

			modelList.ifPresent(l -> {
				this.modelComboBox.removeAllItems();
				modelComboBox.addItem("All models");
				for (int i = 0; i < l.size(); i++) {
					modelComboBox.addItem(l.get(i));
				}
			});
		});
		add(markComboBox);
		add(modelComboBox);

		JLabel label = new JLabel("Choose price:");
		label.setBounds(60, 110, 100, 30);
		add(label);

		int d = this.carService.getMaxPrice();
		JSlider price = new JSlider(JSlider.HORIZONTAL, 0, 10 * d, 0);
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
		applyFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				CarQueryFilter filter = new CarQueryFilter();
				filter.setSelectedMark(markComboBox.getItemAt(markComboBox.getSelectedIndex()));
				filter.setSelectedModel(modelComboBox.getItemAt(modelComboBox.getSelectedIndex()));
				filter.setSelectedPrice(price.getValue());
				filter.setSelectedBodyTypes(bodyTypeCheckBoxes
					.stream()
					.filter(cb -> cb.isSelected())
					.map(cb -> cb.getText())
					.toList());
				filter.setPaging(new Paging(1, 4));
				contentEventsListener.onContentPageRequest(filter);
			}
		});
		add(applyFilter);
	}

	private JComboBox<MarkOfCar> createMarkComboBox() {
		List<MarkOfCar> marks = this.carService.getExistingMarks();

		MarkComboBoxModel markComboBoxModel = new MarkComboBoxModel(marks, "All marks");
		JComboBox<MarkOfCar> markComboBox = new JComboBox<MarkOfCar>(markComboBoxModel);
		markComboBox.setBounds(10, 40, 180, 20);

		return markComboBox;
	}

	private JComboBox<String> createModelComboBox() {
		JComboBox<String> modelComboBox = new JComboBox<>(new String[] { "All models" });
		modelComboBox.setBounds(10, 70, 180, 20);
		return modelComboBox;
	}

	private List<JCheckBox> createBodyTypeCheckBoxes() {
		Box box = Box.createVerticalBox();
		List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

		List<JCheckBox> bodyTypeCheckBoxes = this.carService.getCarBodyTypes().map(bodyType -> {
			JCheckBox checkBox = new JCheckBox(bodyType.getType());
			checkBoxes.add(checkBox);
			box.add(checkBox);
			return checkBox;
		}).collect(Collectors.toList());

		JScrollPane scrollPane = new JScrollPane(box);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(new TitledBorder("Типы кузова"));
		scrollPane.setBounds(10, 225, 180, 270);
		add(scrollPane);

		return bodyTypeCheckBoxes;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
	}
}
