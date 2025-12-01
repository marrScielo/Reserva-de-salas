package notification;

public class ConsoleNotifier implements Notifier {
    @Override
    public void notify(String toUserId, String message) {
        System.out.println("[NOTIF -> " + toUserId + "]: " + message);
    }
}
