package domain_logic;

import observer.Observer;

public interface MediaFileRepositoryInterface {
    void register (Observer observer);
    void deregister (Observer observer);
    void notifyObservers();

    //void notifyObservers(String string);
}
