package com.github.donkeyrit.twinkle.panels.nestedpanels;

import com.github.donkeyrit.twinkle.dal.models.filters.Paging;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Component;

public class PageNavigatorPanel extends JPanel {
	private int choosenPage;
	private int maxItems;
	private int itemsPerPage;

	public PageNavigatorPanel(int choosenPage, int itemsPerPage, int maxItems) {
		this.choosenPage = choosenPage;
		this.maxItems = maxItems;
		this.itemsPerPage = itemsPerPage;

		setLayout(new FlowLayout(FlowLayout.LEFT));
		setupPanel();
	}

	private void setupPanel() {
		int totalPages = (int) Math.ceil((double) maxItems / itemsPerPage);

		for (int i = 1; i <= totalPages; i++) {
			JButton pageButton = new JButton(String.valueOf(i));
			if (i == choosenPage) {
				pageButton.setEnabled(false);
			} else {
				// pageButton.addActionListener(new ActionListener() {
				// 	@Override
				// 	public void actionPerformed(ActionEvent e) {
				// 		choosenPage = Integer.parseInt(e.getActionCommand());
				// 		refreshButtons();
				// 	}
				// });
			}

			add(pageButton);
		}
	}

	private void refreshButtons() {
		Component[] components = getComponents();
		for (Component c : components) {
			if (c instanceof JButton) {
				JButton button = (JButton) c;
				int pageNumber = Integer.parseInt(button.getText());
				button.setEnabled(pageNumber != choosenPage);
			}
		}
	}

	private void showPageNumbers(Paging paging) {
		// TODO: Move to separate panel

		// int currentPageNumber = paging.getPageNumber();
		// int firstPageNumber = ((int) (currentPageNumber / 5)) * 5 + 1;
		// int endPageNumber = firstPageNumber + 4;

		// Box buttonBox = Box.createHorizontalBox();
		// buttonBox.setBounds(205, 520, 400, 30);

		// if (firstPageNumber >= 5) {
		// 	JButton backBut = new JButton(
		// 			AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/back.png"));
		// 	backBut.addActionListener(e -> contentEventsListener.onNextContentPageRequest());

		// 	buttonBox.add(backBut);
		// }

		// Font buttonFont = new Font("Arial", Font.ITALIC, 10);
		// ArrayList<JButton> buttonsList = new ArrayList<JButton>();
		// for (int i = firstPageNumber; i < endPageNumber; i++) {
		// 	JButton temp = new JButton(i + "");

		// 	temp.setFont(buttonFont);
		// 	temp.addActionListener(e -> {
		// 		JButton pressButton = (JButton) e.getSource();
		// 		int pageNumber = Integer.parseInt(pressButton.getText());
		// 		contentEventsListener.onNextContentPageRequest(pageNumber);
		// 	});

		// 	buttonBox.add(temp);
		// 	buttonsList.add(temp);
		// }

		// add(buttonBox);
	}

}
