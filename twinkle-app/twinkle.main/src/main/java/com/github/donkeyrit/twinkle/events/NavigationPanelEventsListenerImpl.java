package com.github.donkeyrit.twinkle.events;

import com.github.donkeyrit.twinkle.panels.ioc.factories.ContentPanelFactory;
import com.github.donkeyrit.twinkle.panels.settings.AdminSideActionMenuPanel;
import com.github.donkeyrit.twinkle.panels.ioc.factories.CarPanelFactory;
import com.github.donkeyrit.twinkle.events.contracts.NavigationPanelEventsListener;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.panels.content.CarPanel;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class NavigationPanelEventsListenerImpl implements NavigationPanelEventsListener {

	private final Provider<ContentCompositePanel> contentCompositePanelProvider;
	private ContentCompositePanel contentCompositePanel;
	private final Provider<AdminSideActionMenuPanel> sideActionMenuPanelProvider;

	private final ContentPanelFactory contentPanelFactory;
	private final CarPanelFactory carPanelFactory;
	
	@Inject
	public NavigationPanelEventsListenerImpl(
		Provider<ContentCompositePanel> contentCompositePanelProvider,
		Provider<AdminSideActionMenuPanel> sideActionMenuPanelProvider,
		ContentPanelFactory contentPanelFactory,
		CarPanelFactory carPanelFactory
	) {
		this.contentCompositePanelProvider = contentCompositePanelProvider;
		this.sideActionMenuPanelProvider = sideActionMenuPanelProvider;
		this.contentPanelFactory = contentPanelFactory;
		this.carPanelFactory = carPanelFactory;
	}

	@Override
	public void onSettingsPageRequest() {
		AdminSideActionMenuPanel sideActionMenuPanel = sideActionMenuPanelProvider.get();
		getContentCompositePanel().setSidebarPanel(sideActionMenuPanel);
		getContentCompositePanel().setContentPanel(null);
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


	@Override
	public CarPanel onCarPanelCreateRequest(Car car) {
		return this.carPanelFactory.create(car);
	}

	private ContentCompositePanel getContentCompositePanel(){
		if(this.contentCompositePanel == null)
		{
			this.contentCompositePanel = contentCompositePanelProvider.get();
		}

		return this.contentCompositePanel;
	}
}
