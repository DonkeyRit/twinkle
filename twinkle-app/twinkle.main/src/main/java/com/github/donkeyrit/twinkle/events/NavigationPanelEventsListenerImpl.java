package com.github.donkeyrit.twinkle.events;

import com.github.donkeyrit.twinkle.events.contracts.NavigationPanelEventsListener;
import com.github.donkeyrit.twinkle.panels.ioc.factories.ContentPanelFactory;
import com.github.donkeyrit.twinkle.panels.settings.AdminSideActionMenuPanel;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import com.github.donkeyrit.twinkle.panels.ioc.factories.CarPanelFactory;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;
import com.github.donkeyrit.twinkle.panels.content.CarPanel;

import com.google.inject.Provider;
import com.google.inject.Inject;
import java.util.Optional;
import javax.swing.JPanel;

public class NavigationPanelEventsListenerImpl implements NavigationPanelEventsListener {

	private final Provider<ContentCompositePanel> contentCompositePanelProvider;
	private ContentCompositePanel contentCompositePanel;
	private final Provider<AdminSideActionMenuPanel> sideActionMenuPanelProvider;

	private final ContentPanelFactory contentPanelFactory;
	private final CarPanelFactory carPanelFactory;

	@Inject
	public NavigationPanelEventsListenerImpl(Provider<ContentCompositePanel> contentCompositePanelProvider,
			Provider<AdminSideActionMenuPanel> sideActionMenuPanelProvider, ContentPanelFactory contentPanelFactory,
			CarPanelFactory carPanelFactory) {
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
	public void onNextContentPageRequest(boolean direction) {
		Optional<JPanel> contentPanelContainer = getContentCompositePanel().getContentPanel();
		if (contentPanelContainer.isPresent() && contentPanelContainer.get() instanceof ContentPanel) {

			ContentPanel contentPanel = (ContentPanel) contentPanelContainer.get();
			CarQueryFilter previousFilter = contentPanel.getFilter();
			Paging paging = previousFilter.getPaging();
			previousFilter.setPaging(direction ? paging.next() : paging.previous());
			ContentPanel newContentPanel = this.contentPanelFactory.create(previousFilter);
			getContentCompositePanel().setContentPanel(newContentPanel);
		}
	}

	@Override
	public void onNextContentPageRequest(int pageNumber) {

		Optional<JPanel> contentPanelContainer = getContentCompositePanel().getContentPanel();
		if (contentPanelContainer.isPresent() && contentPanelContainer.get() instanceof ContentPanel) {

			ContentPanel contentPanel = (ContentPanel) contentPanelContainer.get();
			CarQueryFilter previousFilter = contentPanel.getFilter();
			Paging paging = previousFilter.getPaging();
			previousFilter.setPaging(new Paging(pageNumber, paging.getPageSize()));
			ContentPanel newContentPanel = this.contentPanelFactory.create(previousFilter);
			getContentCompositePanel().setContentPanel(newContentPanel);
		}
	}

	@Override
	public CarPanel onCarPanelCreateRequest(Car car) {
		return this.carPanelFactory.create(car);
	}

	private ContentCompositePanel getContentCompositePanel() {
		if (this.contentCompositePanel == null) {
			this.contentCompositePanel = contentCompositePanelProvider.get();
		}

		return this.contentCompositePanel;
	}
}
