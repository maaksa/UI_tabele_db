package resource.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import observer.Notification;
import observer.Publisher;
import observer.Subscriber;
import observer.enums.NotificationCode;


public class InformationResource extends DefaultMutableTreeNode implements Publisher{

	private String name;
	private ArrayList<Entity> entities = new ArrayList<>();
	private ArrayList<Entity> openEntities = new ArrayList<>();
	private List<Subscriber> subscribers;

    public InformationResource(String name) {
    	this.name = name;
    }

    public void add(MutableTreeNode child) {
        if (child != null && child instanceof Entity){
            Entity entity = (Entity) child;
            entities.add(entity);
            super.add(child);
        }
    }

    public void generateOpened(Entity e) {
    	if (openEntities.contains(e)) return;
    	openEntities.add(e);
    	notifySubscribers(new Notification(NotificationCode.ADD_TAB, e));
    }

	@Override
	public String toString() {
		return name;
	}

	public void generateEntities() {
		for (Entity entity : entities) {
			this.notifySubscribers(new Notification(NotificationCode.ADD_TAB, entity));
		}
	}


    public void addSubscriber(Subscriber sub) {
        if(sub == null)
            return;
        if(this.subscribers ==null)
            this.subscribers = new ArrayList<>();
        if(this.subscribers.contains(sub))
            return;
        this.subscribers.add(sub);
    }

    public void removeSubscriber(Subscriber sub) {
        if(sub == null || this.subscribers == null || !this.subscribers.contains(sub))
            return;
        this.subscribers.remove(sub);
    }

    public void notifySubscribers(Notification notification) {
        if(notification == null || this.subscribers == null || this.subscribers.isEmpty())
            return;

        for(Subscriber listener : subscribers){
            listener.update(notification);
        }
    }

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

    public Entity getEntity(String name) {
        for(Entity entity: entities) {
            if(entity.toString().equals(name)){
                return entity;
            }
        }
        return null;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Subscriber> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<Subscriber> subscribers) {
		this.subscribers = subscribers;
	}


}