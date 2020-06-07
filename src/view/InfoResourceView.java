package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gui.MainFrame;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

public class InfoResourceView extends JPanel implements Subscriber{

	private InformationResource infoResource;
	private JTabbedPane jTabPane;
	private JTabbedPane jTabPaneRelated;
	private ArrayList<EntityView> entityViews = new ArrayList<>();
	private EntityView selectedEntity;

	public InfoResourceView(InformationResource infoResource) {
		this.infoResource = infoResource;
		this.infoResource.addSubscriber(this);

		setLayout(new BorderLayout());
		jTabPane = new JTabbedPane();
		jTabPaneRelated = new JTabbedPane();

		add(jTabPane,BorderLayout.NORTH);
		add(jTabPaneRelated, BorderLayout.SOUTH);
	}

	@Override
	public void update(Notification notification) {
		if (notification.getCode() == NotificationCode.ADD_TAB) {
			Entity e = (Entity) notification.getData();
			EntityView ew = new EntityView(e);
			entityViews.add(ew);
			selectedEntity = ew;
			renderTabs();
			renderRelatedTabs();
		}

	}

	public void renderTabs() {
		jTabPane.removeAll();
		for (int i = 0; i <entityViews.size(); i++) {
			jTabPane.addTab(entityViews.get(i).getLabel().getText(),entityViews.get(i));
			jTabPane.setTabComponentAt(jTabPane.getTabCount()-1,entityViews.get(i).getLabel());
		}
		repaint();
		MainFrame.getInstance().revalidate();
	}

	public EntityView getSelectedEntity() {
		if (selectedEntity == null)
			selectedEntity = (EntityView)jTabPane.getSelectedComponent();
		return selectedEntity;
	}

	public void renderRelatedTabs() {
		jTabPaneRelated.removeAll();
		for (int i = 0; i < getSelectedEntity().getEntity().getRelated().size(); i++) {
			EntityView ev = new EntityView(getSelectedEntity().getEntity().getRelated().get(i));
			jTabPaneRelated.addTab(ev.getLabel().getText(),ev);
			jTabPaneRelated.setTabComponentAt(jTabPaneRelated.getTabCount()-1,ev.getLabel());
		}
		MainFrame.getInstance().repaint();
		MainFrame.getInstance().revalidate();
	}

	public String getInfoName() {
		return infoResource.getName();
	}
}
