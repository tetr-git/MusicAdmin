package domain_logic.producer;

import java.io.Serializable;

public class Producer implements Uploader, Serializable {

    static final long serialVersionUID = 1L;
    private String name;

    public Producer(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Uploader:" + name + " ";
        }
}
