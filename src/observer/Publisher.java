package observer;

import observer.enums.NotificationCode;

public interface Publisher {
    void addSubscriber(Subscriber sub);
    void removeSubscriber(Subscriber sub);
    void notifySubscribers(Notification notification);
}
