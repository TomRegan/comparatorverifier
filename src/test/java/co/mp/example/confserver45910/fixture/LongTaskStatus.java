package co.mp.example.confserver45910.fixture;

import java.util.StringJoiner;

public class LongTaskStatus {
    private final Message name;

    public LongTaskStatus(Message name) {
        this.name = name;
    }

    Message getName() {
        return name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LongTaskStatus.class.getSimpleName() + "[", "]")
                .add("name=" + name)
                .toString();
    }
}
