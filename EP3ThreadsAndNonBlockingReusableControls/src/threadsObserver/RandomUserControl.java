package threadsObserver;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import randomperson.RandomUser;
import randomperson.RandomUserGenerator;

public class RandomUserControl extends Observable {

    public void fetchRandomUser() {
        FetchThread t = new FetchThread();
        t.start();
    }

    class FetchThread extends Thread {
        //heavy time-consuming work is done here
        public void run() {
            RandomUser user = null;
            try {
                user = RandomUserGenerator.getRandomUser();
            } catch (InterruptedException ex) {
                Logger.getLogger(RandomUserControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            setChanged();
            notifyObservers(user);
        }
    }
}
