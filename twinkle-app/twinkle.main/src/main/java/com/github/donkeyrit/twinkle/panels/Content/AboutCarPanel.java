package com.github.donkeyrit.twinkle.panels.content;

import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;
import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;
import com.github.donkeyrit.twinkle.dal.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.ClientRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.InjuryRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.ResultingInjuryRepository;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.models.Rent;
import com.github.donkeyrit.twinkle.dal.models.Client;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.dal.models.Injury;
import com.github.donkeyrit.twinkle.dal.models.ResultingInjury;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;
import javax.swing.border.*;
import javax.swing.text.*;

public class AboutCarPanel extends JPanel 
{
	private int imagesNum;
	private LocalDate modelYear;
	private Double cost;
	private String modelName;
	private String markName;
	private String nameCountry;
	private String info;
	private String bodyTypeName;

	private CarQuerySpecification carQueryFilter;

	public AboutCarPanel(
		CarRepository carRepository,
		RentRepository rentRepository,
		ClientRepository clientRepository,
		UserRepository userRepository,
		InjuryRepository injuryRepository,
		ResultingInjuryRepository resultingInjuryRepository,
		JPanel panel,
		Car car,
		CarQuerySpecification carQueryFilter)
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

		String statusStr = "Свободно";
		Optional<Rent> lastRentForCar = rentRepository.getLastByCarId(imagesNum);
		if (lastRentForCar.isPresent() && lastRentForCar.get().getEndDate() == null) {
			statusStr = "Busy";
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
				AboutCarPanel newPanel = new AboutCarPanel(carRepository, rentRepository, clientRepository,
						userRepository, injuryRepository, resultingInjuryRepository, panel, car, carQueryFilter);
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
				//JPanel contentPanel = new ContentPanel(panel, carRepository, rentRepository, database, carQueryFilter);
				//contentPanel.setBounds(250, 100, 605, 550);
				//panel.add(contentPanel);
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
										int idClient = 0;
										Optional<User> currentUser = userRepository.get(new UserInfoSpecifciation(
												UserInformation.getLogin(), UserInformation.getPassword()));
										if (currentUser.isPresent()) {
											Optional<Client> currentClient = clientRepository
													.getByUserId(currentUser.get().getId());
											if (currentClient.isPresent()) {
												idClient = currentClient.get().getId();
											}
										}

										if (idClient == 0) {
											planPriceLabel.setText("Please, fill data");
										} else {

											boolean isHaveRenta = false;
											Optional<Rent> lastRentForClient = rentRepository
													.getLastByClientId(idClient);
											if (lastRentForClient.isPresent()
													&& lastRentForClient.get().getEndDate() == null) {
												isHaveRenta = true;
											}

											if (isHaveRenta) {
												planPriceLabel.setText("You cannot take more than one car at a time");
											} else {
												Rent newRent = new Rent();
												newRent.setIdClient(idClient);
												newRent.setIdCar(imagesNum);
												newRent.setStartDate(LocalDate.of(
														yList.get(0), yList.get(1), yList.get(2)));
												newRent.setPlanDate(LocalDate.of(
														yList.get(3), yList.get(4), yList.get(5)));

												rentRepository.save(newRent);

												String insertRenta = "rent for client " + idClient + ", car "
														+ imagesNum;

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

			boolean isTrue = false;
			Optional<Rent> lastRentForThisCar = rentRepository.getLastByCarId(imagesNum);
			if (lastRentForThisCar.isPresent()) {
				Client renterClient = clientRepository.findById(lastRentForThisCar.get().getIdClient());
				if (renterClient != null) {
					User renterUser = userRepository.findById(renterClient.getUserId());
					if (renterUser != null
							&& UserInformation.getLogin().equals(renterUser.getLogin())
							&& UserInformation.getPassword().equals(renterUser.getPassword())) {
						isTrue = true;
					}
				}
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
						ArrayList<String> injuryNames = injuryRepository.findAll()
								.map(Injury::getInjuryName)
								.collect(Collectors.toCollection(ArrayList::new));

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

										int idRentaNum = 0;
										Optional<Rent> rentToClose = rentRepository
												.getLastByCarId(imagesNum);
										if (rentToClose.isPresent()) {
											Rent rent = rentToClose.get();
											rent.setEndDate(LocalDate.of(currYear, currMont + 1, currDay));
											rentRepository.update(rent);
											idRentaNum = rent.getId();
										}

										int idInjuryNum = 0;
										Optional<Injury> matchingInjury = injuryRepository
												.getByName(injuryForCar);
										if (matchingInjury.isPresent()) {
											idInjuryNum = matchingInjury.get().getId();
										}

										ResultingInjury resultingInjury = new ResultingInjury();
										resultingInjury.setIdRent(idRentaNum);
										resultingInjury.setIdInjury(idInjuryNum);
										resultingInjuryRepository.save(resultingInjury);

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

								LocalDate startRentaDate = null;
								LocalDate dataRentaPlan = null;
								Optional<Rent> rentForCostCalc = rentRepository.getLastByCarId(imagesNum);
								if (rentForCostCalc.isPresent()) {
									startRentaDate = rentForCostCalc.get().getStartDate();
									dataRentaPlan = rentForCostCalc.get().getPlanDate();
								}

								LocalDate currentGetData = LocalDate.of(currYear, currMont + 1, currDay);
								startRentaDate = LocalDate.of(currYear, currMont + 1, currDay);

								JLabel labelCostRenta = new JLabel("5000");
								labelCostRenta.setBounds(480, 460, 120, 30);

								if (currentGetData.isAfter(startRentaDate)) {
									labelCostRenta.setText("Sum = 0");
								} else {
									double costForTheRent = 0f;

									int days = (int) ChronoUnit.DAYS.between(currentGetData, startRentaDate);
									costForTheRent = (days + 1f) * cost;

									if (dataRentaPlan != null && currentGetData.isAfter(dataRentaPlan)) {
										int overDay = (int) ChronoUnit.DAYS.between(dataRentaPlan, currentGetData);
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
