package com.github.donkeyrit.twinkle.events;

import com.github.donkeyrit.twinkle.panels.ioc.factories.ContentPanelFactory;
import com.github.donkeyrit.twinkle.events.contracts.ContentEventsListener;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ContentEventsListenerImpl implements ContentEventsListener {

	private final Provider<ContentCompositePanel> contentCompositePanelProvider;
	private ContentCompositePanel contentCompositePanel;
	private final ContentPanelFactory contentPanelFactory;
	
	@Inject
	public ContentEventsListenerImpl(
		Provider<ContentCompositePanel> contentCompositePanelProvider,
		ContentPanelFactory contentPanelFactory
	) {
		this.contentCompositePanelProvider = contentCompositePanelProvider;
		this.contentPanelFactory = contentPanelFactory;
	}

	@Override
	public void onSettingsPageRequest() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'onSettingsPageRequest'");
	}

	@Override
	public void onHomePageRequest() {
		ContentPanel contentPanel = this.contentPanelFactory.create(new CarQueryFilter());
		getContentCompositePanel().setContentPanel(contentPanel);		
	}

	@Override
	public void onContentPageRequest(CarQueryFilter queryFilter) {
		ContentPanel contentPanel = this.contentPanelFactory.create(queryFilter);
		getContentCompositePanel().setContentPanel(contentPanel);		
	}

	@Override
	public void onNextContentPageRequest() {
		// JButton selectedBut = (JButton) e.getSource();
		// ContentPanel outerPanel = (ContentPanel) selectedBut.getParent().getParent();

		// String nameBut = selectedBut.getIcon().toString();
		// int indexName = nameBut.lastIndexOf("/");

		// panel.remove(outerPanel);

		// JPanel addPanel = null;
		// CarQueryFilter carQueryFilter = outerPanel.getFilter();
		// if (nameBut.substring(indexName + 1, indexName + 5).equals("next")) {
		// 	outerPanel.getFilter().setPaging(outerPanel.getFilter().getPaging().next());
		// } else {
		// 	outerPanel.getFilter().setPaging(outerPanel.getFilter().getPaging().previous());
		// }
		// addPanel = new ContentPanel(this.panel, this.carRepository, this.rentRepository, this.dataBase, carQueryFilter);
		// panel.add(addPanel);
		// addPanel.setBounds(250, 100, 605, 550);
		// panel.revalidate();
		// panel.repaint();
	}

	@Override
	public void onNextContentPageRequest(int pageNumber) {
		// JButton pressButton = (JButton) e.getSource();
		// String textButton = pressButton.getText(); 
		// int numPage = Integer.parseInt(textButton); 
		
		// Component[] mas = panel.getComponents(); 
		// ContentPanel temp = null; 
		// for(int i = 0; i < mas.length; i++){
		// 	if(mas[i].getClass().toString().indexOf("ContentPanel") != -1){
		// 		temp = (ContentPanel) mas[i];
		// 	}
		// }
		
		// if(temp == null)
		// {
		// 	return;
		// }

		// panel.remove(temp); 
		// CarQueryFilter carQueryFilter = temp.getFilter();
		// carQueryFilter.setPaging(new Paging(numPage, 4));
		// JPanel content = new ContentPanel(this.panel, this.carRepository, this.rentRepository, this.dataBase, carQueryFilter); 
		// content.setBounds(250,100,605,550); 
		// panel.add(content); 
		// panel.revalidate(); 
		// panel.repaint(); 
	}


	private ContentCompositePanel getContentCompositePanel(){
		if(this.contentCompositePanel == null)
		{
			this.contentCompositePanel = contentCompositePanelProvider.get();
		}

		return this.contentCompositePanel;
	}
}
