package bowling.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FrameState {
    private final int number;
    private final List<String> results;

    private FrameState(int number) {
        this.number = number;
        this.results = new ArrayList<>();
    }

    public static FrameState from(int number) {
        return new FrameState(number);
    }

    public int getNumber() {
        return number;
    }

    public String store(String result) {
        results.add(result);
        return results.stream().collect(Collectors.joining("|"));
    }

    @Override
    public String toString() {
        return "FrameState{" +
                "number=" + number +
                ", results=" + results +
                '}';
    }

    public String getPrevResult() {
        return results.get(results.size() - 2);
    }
}
