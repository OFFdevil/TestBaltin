package Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Line {
    private final int name;
    private final ArrayList<String> elements = new ArrayList<>();

    public Line(int name, String[] elements){
        this.name = name;
        this.elements.addAll(List.of(elements));
    }

    public int getName() {
        return name;
    }

    public ArrayList<String> getElements() {
        return elements;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < elements.size(); i++){
            if(i != 0){
                stringBuilder.append(";");
            }
            stringBuilder.append("\"");
            stringBuilder.append(elements.get(i));
            stringBuilder.append("\"");
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return elements.equals(line.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }
}
