package Objects;

import java.util.HashMap;
import java.util.Set;

public class Group {
    private final String name;
    private int size;
    private final HashMap<Line, Integer> lines = new HashMap<>();

    public Group(String name){
        this.name = name;
    }

    public void addLine(Line newLine){
        if(lines.containsKey(newLine)){
            return;
        }

        size++;
        lines.put(newLine, 1);
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Line line : lines.keySet()) {
            str.append(line).append("\n");
        }
        return str.toString();
    }
}
