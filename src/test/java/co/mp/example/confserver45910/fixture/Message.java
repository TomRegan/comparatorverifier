package co.mp.example.confserver45910.fixture;

import java.util.Objects;

public final class Message {

    private final String key;

    public Message(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Message that = (Message) obj;
        return Objects.equals(this.key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "Message[" +
            "key=" + key + ']';
    }
}
