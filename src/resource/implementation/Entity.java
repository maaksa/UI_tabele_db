package resource.implementation;

import observer.Notification;
import observer.Publisher;
import observer.Subscriber;
import observer.enums.NotificationCode;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

// public class Entity extends DBNodeComposite

public class Entity extends DefaultMutableTreeNode implements Publisher {

	private String name;
	private InformationResource parent;
	private List<Attribute> attributes = new ArrayList<>();
	private ArrayList<AttributeConstraint> attrConstraints = new ArrayList<>();
	private List<Subscriber> subscribers;
	private ArrayList<Entity> related = new ArrayList<>();
	private static int numnum = 0;
	public Entity(String name, InformationResource parent) {
		super();
		this.name = name;
		this.parent = parent;
	}
	@Override
	public String toString() {
		return name;
	}

	public void add(MutableTreeNode child) {
        if (child != null && child instanceof Attribute){
        	Attribute entity = (Attribute) child;
            this.attributes.add(entity);
            super.add(child);
        }
    }

	public void addRelated(Entity e) {
		if (!related.contains(e)) related.add(e);
	}

	@Override
	public int getChildCount() {
		return super.getChildCount();
	}

	@Override
	public TreeNode getChildAt(int index) {
		return super.getChildAt(index);
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

	public String getName() {
		return name;
	}
	public ArrayList<Entity> getRelated() {
		return related;
	}
}