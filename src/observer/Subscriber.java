package observer;

import observer.enums.NotificationCode;

public interface Subscriber {
    void update(Notification notification);
}
