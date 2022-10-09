package observer;

public interface Observable {
    public void attachObserver( Observer observer );
    public void detachObserver( Observer observer );
    public void notifyObservers();
}
