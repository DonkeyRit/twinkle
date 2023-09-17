package com.github.donkeyrit.twinkle.panels.nestedpanels;

import com.github.donkeyrit.twinkle.events.contracts.NavigationPanelEventsListener;
import java.util.stream.IntStream;
import javax.swing.*;
import java.awt.*;

public class PageNavigatorPanel extends JPanel {

    private int chosenPage;
    private int maxPages;

    private Color bgColor = new Color(18, 18, 18);
    private Color fgColor = Color.WHITE;
    private Color activeColor = new Color(255, 80, 0);

	private NavigationPanelEventsListener navigationPanelEventsListener;

    public PageNavigatorPanel(
		NavigationPanelEventsListener navigationPanelEventsListener, 
		int chosenPage, 
		int itemsPerPage, 
		int maxItems
	) {
		this.navigationPanelEventsListener = navigationPanelEventsListener;
        this.chosenPage = chosenPage;
        this.maxPages = (int) Math.ceil((double) maxItems / itemsPerPage);

        initializeComponents();
    }

    private void initializeComponents() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(bgColor);

        JButton prevButton = createNavButton("<<");
        prevButton.addActionListener(e -> navigationPanelEventsListener.onNextContentPageRequest());

        JButton nextButton = createNavButton(">>");
        nextButton.addActionListener(e -> navigationPanelEventsListener.onNextContentPageRequest());

        this.add(prevButton);

        // Calculate start and end for pagination links, to handle cases where maxPages is too large
        int startPage = Math.max(1, chosenPage - 5);
        int endPage = Math.min(maxPages, chosenPage + 5);

        IntStream.range(startPage, endPage + 1).forEach(pageNum -> {
            JButton pageButton = createNavButton(Integer.toString(pageNum));
            if (pageNum == chosenPage) {
                pageButton.setForeground(activeColor);
                pageButton.setFont(pageButton.getFont().deriveFont(Font.BOLD));
            }
            pageButton.addActionListener(e -> navigationPanelEventsListener.onNextContentPageRequest(pageNum));
            this.add(pageButton);
        });

        this.add(nextButton);
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }
}
