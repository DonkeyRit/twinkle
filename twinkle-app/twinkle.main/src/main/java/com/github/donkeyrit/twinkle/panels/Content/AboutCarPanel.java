package com.github.donkeyrit.twinkle.panels.content;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.swing.border.*;
import javax.swing.text.*;

import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

public class AboutCarPanel extends JPanel 
{
	private int imagesNum;
	private Date modelYear;
	private Double cost;
	private String modelName;
	private String markName;
	private String nameCountry;
	private String info;
	private String bodyTypeName;

	private CarQueryFilter carQueryFilter;

	public AboutCarPanel(
		CarRepository carRepository, 
		RentRepository rentRepository, 
		DataBase database, 
		JPanel panel, 
		Car car,
		CarQueryFilter carQueryFilter) 
	{
		this.imagesNum = car.getImageId();
		this.modelYear = car.getModelYear();
		this.cost = car.getCost();
		this.modelName = car.getModelOfCar().getModelName();
		this.markName = car.getModelOfCar().getMark().getName();
		this.nameCountry = car.getModelOfCar().getMark().getCountry().getCountryName();
		this.info = car.getInfo();
		this.bodyTypeName = car.getModelOfCar().getBodyType().getType();
		this.carQueryFilter = carQueryFilter;

		setLayout(null);

		JTextArea textArea = new JTextArea(info);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(300, 290, 285, 190);
		add(scrollPane);

		ResultSet statusSet = database.select(
				"SELECT * FROM rent WHERE id_car = " + imagesNum + " ORDER BY end_date, plan_date DESC LIMIT 1");
		String statusStr = "Свободно";
		try {
			while (statusSet.next()) {
				Date rentDate = statusSet.getDate("end_date");
				if (rentDate == null) {
					statusStr = "Busy";
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		Font font = new Font("Arial", Font.BOLD, 13);
		Font alterfont = new Font("Arial", Font.ITALIC, 13);

		String[] massArgs = new String[] { "Model:", this.modelName, "Mark:", this.markName, "Year:",
				this.modelYear.toString(), "Type:", this.bodyTypeName, "Cost per day:", this.cost + "", "Status:",
				statusStr };
		for (int i = 0; i < 12; i++) {
			JLabel temp = new JLabel(massArgs[i]);
			if (i % 2 == 0) {
				temp.setBounds(30, 300 + (i / 2) * 30, 80, 20);
				temp.setFont(alterfont);
			} else {
				temp.setBounds(150, 300 + (i / 2) * 30, 120, 20);
				temp.setFont(font);
			}

			add(temp);
		}

		JButton reloadButton = new JButton(
				AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/reload.png"));
		reloadButton.setBounds(550, 0, 16, 16);
		reloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Component[] mas = panel.getComponents();
				AboutCarPanel temp = null;
				for (int i = 0; i < mas.length; i++) {
					if (mas[i].getClass().toString().indexOf("AboutCarPanel") != -1) {
						temp = (AboutCarPanel) mas[i];
					}
				}
				AboutCarPanel newPanel = new AboutCarPanel(carRepository, rentRepository, database, panel, car, carQueryFilter);
				newPanel.setBounds(250, 100, 605, 550);
				panel.remove(temp);
				panel.add(newPanel);
				panel.revalidate();
				panel.repaint();
			}
		});
		add(reloadButton);

		JButton returnButton = new JButton(
				AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/return.png"));
		returnButton.setBounds(570, 0, 16, 16);
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Component[] mas = panel.getComponents();
				JPanel temp = null;
				for (int i = 0; i < mas.length; i++) {
					if (mas[i].getClass().toString().indexOf("AboutCarPanel") != -1) {
						temp = (JPanel) mas[i];
					}
				}

				panel.remove(temp);
				JPanel contentPanel = new ContentPanel(panel, carRepository, rentRepository, database, carQueryFilter);
				contentPanel.setBounds(250, 100, 605, 550);
				panel.add(contentPanel);
				panel.revalidate();
				panel.repaint();
			}
		});
		add(returnButton);

		JButton actionWithCarButton = new JButton("ACTION");
		actionWithCarButton.setBounds(300, 500, 150, 20);
		if (statusStr.equals("Free")) {
			actionWithCarButton.setText("Get rent");
			actionWithCarButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton tempBut = (JButton) e.getSource();
					tempBut.setVisible(false);
					JPanel tempPanel = (JPanel) tempBut.getParent();

					Component[] mas = tempPanel.getComponents();
					JScrollPane temp = null;
					for (int i = 0; i < mas.length; i++) {
						if (mas[i].getClass().toString().indexOf("JScrollPane") != -1) {
							temp = (JScrollPane) mas[i];
						}
					}

					tempPanel.remove(temp);

					tempPanel.revalidate();
					tempPanel.repaint();

					ArrayList<JTextField> fields = new ArrayList<>();
					MaskFormatter formatter = null;

					Box yearsStart = Box.createHorizontalBox();
					for (int i = 0; i < 3; i++) {
						String form = "##";
						if (i == 0) {
							form = "####";
						}
						try {
							formatter = new MaskFormatter(form);
							formatter.setPlaceholderCharacter('0');
						} catch (Exception exec) {
							exec.printStackTrace();
						}
						JFormattedTextField ssnField = new JFormattedTextField(formatter);
						ssnField.setHorizontalAlignment(JTextField.CENTER);
						formatter.setPlaceholderCharacter('0');
						fields.add(ssnField);
						yearsStart.add(ssnField);
					}
					yearsStart.setBounds(350, 320, 200, 30);
					add(yearsStart);

					Box yearsPlan = Box.createHorizontalBox();
					for (int i = 0; i < 3; i++) {
						String form = "##";
						if (i == 0) {
							form = "####";
						}
						try {
							formatter = new MaskFormatter(form);
							formatter.setPlaceholderCharacter('0');
						} catch (Exception exec) {
							exec.printStackTrace();
						}
						JFormattedTextField tempField = new JFormattedTextField(formatter);

						tempField.setHorizontalAlignment(JTextField.CENTER);
						fields.add(tempField);
						yearsPlan.add(tempField);
					}
					yearsPlan.setBounds(350, 400, 200, 30);
					add(yearsPlan);

					JLabel startYearsLabel = new JLabel("Enter start rent date(гг.мм.дд)");
					startYearsLabel.setBounds(330, 290, 300, 30);
					add(startYearsLabel);

					JLabel planYearsLabel = new JLabel("Enter end rent date(г.м.д)");
					planYearsLabel.setBounds(330, 370, 300, 30);
					add(planYearsLabel);

					JLabel planPriceLabel = new JLabel("Approximately cost: ");
					planPriceLabel.setBounds(330, 440, 300, 30);
					add(planPriceLabel);

					JButton countPrice = new JButton("Count");
					Border borderButton = fields.get(0).getBorder();
					countPrice.setBounds(340, 480, 120, 30);
					countPrice.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
							calendar.setTime(new Date());
							int currYear = calendar.get(Calendar.YEAR);
							int currMont = calendar.get(Calendar.MONTH);
							int currDay = calendar.get(Calendar.DATE);

							ArrayList<Integer> yList = new ArrayList<>();
							int count = 0;
							for (int i = 0; i < fields.size(); i++) {
								if (!fields.get(i).getText().equals("0000")) {
									if (!fields.get(i).getText().equals("00")) {
										count++;
										fields.get(i).setBorder(borderButton);
									} else {
										fields.get(i).setBorder(new LineBorder(Color.RED, 4));
									}
								} else {
									fields.get(i).setBorder(new LineBorder(Color.RED, 4));
								}
							}
							if (count == fields.size()) {
								int tempNum = Integer.parseInt(fields.get(0).getText());
								if (tempNum < currYear || tempNum > currYear + 2) {
									fields.get(0).setBorder(new LineBorder(Color.RED, 4));
								} else {
									fields.get(0).setBorder(borderButton);
									yList.add(tempNum);
								}

								tempNum = Integer.parseInt(fields.get(1).getText());
								if (tempNum < 0 || tempNum > 12) {
									fields.get(1).setBorder(new LineBorder(Color.RED, 4));
								} else {
									if (Integer.parseInt(fields.get(0).getText()) == currYear && tempNum < currMont) {
										fields.get(1).setBorder(new LineBorder(Color.RED, 4));
									} else {
										fields.get(1).setBorder(borderButton);
										yList.add(tempNum);
									}
								}

								tempNum = Integer.parseInt(fields.get(2).getText());
								if (tempNum < 0 || tempNum > 28) {
									fields.get(2).setBorder(new LineBorder(Color.RED, 4));
								} else {
									if (Integer.parseInt(fields.get(0).getText()) == currYear
											&& Integer.parseInt(fields.get(1).getText()) == currMont
											&& tempNum < currDay) {
										fields.get(2).setBorder(new LineBorder(Color.RED, 4));
									} else {
										fields.get(2).setBorder(borderButton);
										yList.add(tempNum);
									}
								}

								tempNum = Integer.parseInt(fields.get(3).getText());
								if (tempNum < currYear || tempNum > currYear + 4) {
									fields.get(3).setBorder(new LineBorder(Color.RED, 4));
								} else {
									if (tempNum >= Integer.parseInt(fields.get(0).getText())) {
										fields.get(3).setBorder(borderButton);
										yList.add(tempNum);
									} else {
										fields.get(3).setBorder(new LineBorder(Color.RED, 4));
									}
								}

								tempNum = Integer.parseInt(fields.get(4).getText());
								if (tempNum < 0 || tempNum > 12) {
									fields.get(4).setBorder(new LineBorder(Color.RED, 4));
								} else {
									if (Integer.parseInt(fields.get(3).getText()) == Integer
											.parseInt(fields.get(0).getText())) {
										if (tempNum >= Integer.parseInt(fields.get(1).getText())) {
											fields.get(4).setBorder(borderButton);
											yList.add(tempNum);
										} else {
											fields.get(4).setBorder(new LineBorder(Color.RED, 4));
										}
									} else {
										if (Integer.parseInt(fields.get(3).getText()) > Integer
												.parseInt(fields.get(0).getText())) {
											fields.get(4).setBorder(borderButton);
											yList.add(tempNum);
										} else {
											fields.get(4).setBorder(new LineBorder(Color.RED, 4));
										}
									}
								}

								tempNum = Integer.parseInt(fields.get(5).getText());
								if (tempNum < 0 || tempNum > 28) {
									fields.get(5).setBorder(new LineBorder(Color.RED, 4));
								} else {
									if (Integer.parseInt(fields.get(3).getText()) == Integer
											.parseInt(fields.get(0).getText())) {
										if (Integer.parseInt(fields.get(4).getText()) == Integer
												.parseInt(fields.get(1).getText())) {
											if (tempNum > Integer.parseInt(fields.get(2).getText())) {
												fields.get(5).setBorder(borderButton);
												yList.add(tempNum);
											} else {
												fields.get(5).setBorder(new LineBorder(Color.RED, 4));
											}
										} else {
											if (Integer.parseInt(fields.get(4).getText()) > Integer
													.parseInt(fields.get(1).getText())) {
												fields.get(5).setBorder(borderButton);
												yList.add(tempNum);
											} else {
												fields.get(5).setBorder(new LineBorder(Color.RED, 4));
											}
										}
									} else {
										if (Integer.parseInt(fields.get(3).getText()) > Integer
												.parseInt(fields.get(0).getText())) {
											fields.get(5).setBorder(borderButton);
											yList.add(tempNum);
										} else {
											fields.get(5).setBorder(new LineBorder(Color.RED, 4));
										}
									}
								}
							}

							if (yList.size() == 6) {
								Date startDateGetCar = new Date(yList.get(0), yList.get(1), yList.get(2));
								Date planDateReturnCar = new Date(yList.get(3), yList.get(4), yList.get(5));

								long difference = planDateReturnCar.getTime() - startDateGetCar.getTime();
								int days = (int) (difference / (24 * 60 * 60 * 1000));
								planPriceLabel
										.setText("Approximately cost: " + (days + 1) * cost + " на " + (days + 1));

								JButton buttonGetCar = new JButton("Get rent.");
								buttonGetCar.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										String checkQuery = "SELECT id_client FROM clients INNER JOINusersON client.id_user = user.id_user WHERE login = "
												+ "'" + UserInformation.getLogin() + "'";
										ResultSet checkClientSet = database.select(checkQuery);
										int idClient = 0;
										try {
											while (checkClientSet.next()) {
												idClient = checkClientSet.getInt("id_client");
											}
										} catch (SQLException ex) {
											ex.printStackTrace();
										}

										if (idClient == 0) {
											planPriceLabel.setText("Please, fill data");
										} else {

											String queryToDB = "SELECT * FROM rent where id_client = (SELECT id_clients FROM client WHERE id_user = (SELECT id_user FROM users WHERE login = '"
													+ UserInformation.getLogin()
													+ "')) ORDER BY end_date, plan_date DESC LIMIT 1";
											ResultSet checkRentaSet = database.select(queryToDB);
											System.out.println(queryToDB);
											boolean isHaveRenta = false;
											try {
												while (checkRentaSet.next()) {
													Date rentDate = checkRentaSet.getDate("end_date");
													if (rentDate == null) {
														isHaveRenta = true;
														;
													}
												}
											} catch (SQLException ex) {
												ex.printStackTrace();
											}

											if (isHaveRenta) {
												planPriceLabel.setText("You cannot take more than one car at a time");
											} else {
												String insertRenta = "INSERT INTO rent(id_client,id_car,start_date,plan_date) VALUES ("
														+ idClient + "," + imagesNum;

												String startDataIn = "'" + yList.get(0) + "-" + yList.get(1) + "-"
														+ yList.get(2) + "'";
												String planDataIn = "'" + yList.get(3) + "-" + yList.get(4) + "-"
														+ yList.get(5) + "'";

												insertRenta += "," + startDataIn + "," + planDataIn + ")";

												database.insert(insertRenta);

												JButton selecBut = (JButton) e.getSource();
												JPanel selecPane = (JPanel) selecBut.getParent();

												Component[] compons = selecPane.getComponents();
												for (int i = 0; i < compons.length; i++) {
													if (compons[i].getClass().toString().indexOf("JButton") > -1) {
														JButton button = (JButton) compons[i];
														if (button.getText().indexOf("Get rent") > -1
																|| button.getText().indexOf("Return") > -1) {
															selecPane.remove(button);
														}
													}
												}

												selecPane.remove(startYearsLabel);

												selecPane.remove(planPriceLabel);
												selecPane.remove(planYearsLabel);
												selecPane.remove(countPrice);
												selecPane.remove(yearsPlan);
												selecPane.remove(yearsStart);
												selecPane.revalidate();
												selecPane.repaint();

												JTextArea textArea = new JTextArea(info);
												textArea.setLineWrap(true);
												JScrollPane scrollPane = new JScrollPane(textArea);
												scrollPane.setVerticalScrollBarPolicy(
														ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
												scrollPane.setHorizontalScrollBarPolicy(
														ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
												scrollPane.setBounds(300, 290, 285, 190);
												add(scrollPane);
												System.out.println(insertRenta);
											}
										}
									}
								});
								buttonGetCar.setBounds(375, 510, 150, 30);
								add(buttonGetCar);
							} else {
								Component[] compons = tempPanel.getComponents();
								for (int i = 0; i < compons.length; i++) {
									if (compons[i].getClass().toString().indexOf("JButton") > -1) {
										JButton button = (JButton) compons[i];
										if (button.getText().indexOf("Get rent") > -1) {
											tempPanel.remove(button);
										}
									}
									planPriceLabel.setText("Incorrect date: ");
								}
							}
							revalidate();
							repaint();
						}
					});
					add(countPrice);

					JButton back = new JButton("Return");
					back.setBounds(460, 480, 100, 30);
					back.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JButton selecBut = (JButton) e.getSource();
							JPanel selecPane = (JPanel) selecBut.getParent();

							Component[] compons = selecPane.getComponents();
							for (int i = 0; i < compons.length; i++) {
								if (compons[i].getClass().toString().indexOf("JButton") > -1) {
									JButton button = (JButton) compons[i];
									if (button.getText().indexOf("Get rent") > -1) {
										selecPane.remove(button);
									}
								}
							}

							selecPane.remove(startYearsLabel);
							selecPane.remove(back);
							selecPane.remove(planPriceLabel);
							selecPane.remove(planYearsLabel);
							selecPane.remove(countPrice);
							selecPane.remove(yearsPlan);
							selecPane.remove(yearsStart);
							selecPane.revalidate();
							selecPane.repaint();

							tempBut.setVisible(true);

							JTextArea textArea = new JTextArea(info);
							textArea.setLineWrap(true);
							JScrollPane scrollPane = new JScrollPane(textArea);
							scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
							scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
							scrollPane.setBounds(300, 290, 285, 190);
							add(scrollPane);
						}
					});
					add(back);
				}
			});
			add(actionWithCarButton);
		} else {

			String queryToDatabase = "SELECT * FROM users WHERE id_user = (SELECT id_user FROM clients WHERE id_client = (SELECT id_client FROm rent WHERE id_car = "
					+ imagesNum + " ORDER BY end_date,plan_date DESC LIMIT 1))";
			ResultSet checkUserSet = database.select(queryToDatabase);
			boolean isTrue = false;
			try {
				while (checkUserSet.next()) {
					if (UserInformation.getLogin().equals(checkUserSet.getString("login"))
							&& UserInformation.getPassword().equals(checkUserSet.getString("password"))) {
						isTrue = true;
					}
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			if (isTrue) {
				actionWithCarButton.setText("Return car");
				actionWithCarButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JButton tempBut = (JButton) e.getSource();
						JPanel tempPanel = (JPanel) tempBut.getParent();
						tempPanel.remove(scrollPane);
						tempPanel.remove(tempBut);

						Box box = Box.createVerticalBox();
						String queryReturnCar = "SELECT * FROM injury";
						ResultSet queryReturnCarSer = database.select(queryReturnCar);
						ArrayList<String> injuryNames = new ArrayList<>();
						try {
							while (queryReturnCarSer.next()) {
								injuryNames.add(queryReturnCarSer.getString("injury_name"));
							}
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
						for (int i = 0; i < injuryNames.size(); i++) {
							JCheckBox temp = new JCheckBox(injuryNames.get(i));
							checkBoxes.add(temp);
							box.add(temp);
						}
						box.setBorder(new TitledBorder("Types of damage"));
						box.setBounds(330, 290, 250, 150);
						tempPanel.add(box);

						JButton countButton = new JButton("Count");
						countButton.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								JButton selectedButton = (JButton) e.getSource();
								JPanel selecButPanel = (JPanel) selectedButton.getParent();

								Component[] listMas = selecButPanel.getComponents();
								for (int i = 0; i < listMas.length; i++) {
									if (listMas[i].getClass().toString().indexOf("JLabel") > -1) {
										JLabel label = (JLabel) listMas[i];
										if (label.getText().indexOf("Sum") > -1) {
											selecButPanel.remove(label);
										}
									}
								}

								JButton newReturnButton = new JButton("Return");
								newReturnButton.setBounds(330, 500, 120, 30);
								newReturnButton.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										String injuryForCar = "";
										for (int i = 0; i < checkBoxes.size(); i++) {
											if (checkBoxes.get(i).isSelected()) {
												injuryForCar = checkBoxes.get(i).getText();
											}
										}
										Calendar calendar = Calendar.getInstance(TimeZone.getDefault(),
												Locale.getDefault());
										calendar.setTime(new Date());
										int currYear = calendar.get(Calendar.YEAR);
										int currMont = calendar.get(Calendar.MONTH);
										int currDay = calendar.get(Calendar.DATE);

										String dataStr = currYear + "-" + currMont + "-" + currDay;
										String updateQuery = "UPDATE rent SET end_date = '" + dataStr
												+ "' WHERE id_car = " + imagesNum
												+ " AND id_client = (SELECT id_client FROM client INNER JOIN users ON client.id_user = user.id_user WHERE login = '"
												+ UserInformation.getLogin() + "');";
										database.update(updateQuery);

										String idRentaStr = "SELECT id_rent FROM rent WHERE id_car = " + imagesNum
												+ " AND id_client = (SELECT id_client FROM client INNER JOINusersON client.id_user = user.id_user WHERE login = '"
												+ UserInformation.getLogin() + "') AND end_date = '" + dataStr + "'";
										ResultSet rentaSet = database.select(idRentaStr);
										int idRentaNum = 0;
										try {
											while (rentaSet.next()) {
												idRentaNum = rentaSet.getInt("id_rent");
											}
										} catch (SQLException ex) {
											ex.printStackTrace();
										}

										String idInjuryStr = "SELECT id_injury FROM injury WHERE injury_name = '"
												+ injuryForCar + "'";
										ResultSet injurySet = database.select(idInjuryStr);
										int idInjuryNum = 0;
										try {
											while (injurySet.next()) {
												idInjuryNum = injurySet.getInt("id_injury");
											}
										} catch (SQLException ex) {
											ex.printStackTrace();
										}

										database.insert("INSERT INTO  resultinginjury(id_rent,id_injury) VALUES("
												+ idRentaNum + "," + idInjuryNum + ")");

										remove(box);
										remove(newReturnButton);
										remove(countButton);
										Component[] listMas = selecButPanel.getComponents();
										for (int i = 0; i < listMas.length; i++) {
											if (listMas[i].getClass().toString().indexOf("JLabel") > -1) {
												JLabel label = (JLabel) listMas[i];
												if (label.getText().indexOf("Sum") > -1) {
													remove(label);
												}
											}
										}
										JTextArea textArea = new JTextArea(info);
										textArea.setLineWrap(true);
										JScrollPane scrollPane = new JScrollPane(textArea);
										scrollPane.setVerticalScrollBarPolicy(
												ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
										scrollPane.setHorizontalScrollBarPolicy(
												ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
										scrollPane.setBounds(300, 290, 285, 190);
										add(scrollPane);
										revalidate();
										repaint();
									}
								});
								tempPanel.add(newReturnButton);

								Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
								calendar.setTime(new Date());
								int currYear = calendar.get(Calendar.YEAR);
								int currMont = calendar.get(Calendar.MONTH);
								int currDay = calendar.get(Calendar.DATE);

								String queryToDb = "SELECT login,id_car,join1.id_user,start_date,plan_date,end_date FROM\n"
										+ "(SELECT id_car,id_user,rent.id_client,start_date,plan_date,end_date FROM rent INNER JOIN client ON rent.id_client = client.id_client) as join1\n"
										+ "INNER JOIN users ON join1.id_user = user.id_user WHERE login = '"
										+ UserInformation.getLogin() + "' AND id_car = " + imagesNum
										+ " ORDER BY end_date,plan_date DESC LIMIT 1;";

								ResultSet queryToDbSet = database.select(queryToDb);
								Date startRentaDate = null;
								Date dataRentaPlan = null;
								try {
									while (queryToDbSet.next()) {
										startRentaDate = queryToDbSet.getDate("start_date");
										dataRentaPlan = queryToDbSet.getDate("plan_date");
									}
								} catch (SQLException ex) {
									ex.printStackTrace();
								}

								Date currentGetData = new Date(currYear, currMont, currDay);
								startRentaDate = new Date(currYear, currMont + 1, currDay);

								JLabel labelCostRenta = new JLabel("5000");
								labelCostRenta.setBounds(480, 460, 120, 30);

								if (currentGetData.after(startRentaDate)) {
									labelCostRenta.setText("Sum = 0");
								} else {
									double costForTheRent = 0f;

									long difference = startRentaDate.getTime() - currentGetData.getTime();
									int days = (int) (difference / (24 * 60 * 60 * 1000));
									costForTheRent = (days + 1f) * cost;

									if (currentGetData.after(dataRentaPlan)) {
										long diff = currentGetData.getTime() - dataRentaPlan.getTime();
										int overDay = (int) (difference / (24 * 60 * 60 * 1000));
										costForTheRent += (cost * overDay) * 0.2;
									}

									for (int i = 0; i < checkBoxes.size(); i++) {
										if (checkBoxes.get(i).isSelected()) {
											costForTheRent += (i + 1) * 50000;
										}
									}

									labelCostRenta.setText("Sum " + costForTheRent + "");
								}

								tempPanel.add(labelCostRenta);

								tempPanel.revalidate();
								tempPanel.repaint();
							}

						});
						countButton.setBounds(330, 460, 120, 30);
						tempPanel.add(countButton);

						tempPanel.revalidate();
						tempPanel.repaint();
					}
				});
				add(actionWithCarButton);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
		Image image = AssetsRetriever.retrieveAssetImageFromResources("assets/cars/" + imagesNum + ".png");
		g.drawImage(image, 30, 10, this);
		g.setColor(new Color(255, 255, 255));
		g.fillRect(20, 290, 260, 190);

	}
}
