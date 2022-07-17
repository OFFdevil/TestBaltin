import Objects.DSU;
import Objects.Group;
import Objects.Line;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    // алгоритм
    // 1) фильтруем строки и создаем структуру line для каждой строки
    // 2) создаем СНМ, чтобы можно было пересекать множества
    // 3) создаем из множеств снм-а группы
    // 4) сортируем по размеру и выводим

    static private class Config{
        private final static String FILEPATH = System.getProperty("user.dir") + "/src/main/resources/lng.txt";
    }

    private static final ArrayList<HashMap<String, Line>> elementsInColumns = new ArrayList<>();
    private static final ArrayList<Line> lines = new ArrayList<>();
    private static final HashMap<Integer, Group> groups = new HashMap<>(3000000);
    private static final ArrayList<Integer> countElementInColumn = new ArrayList<>();
    private static final ArrayList<Group> groupsAnswer = new ArrayList<>();
    private static DSU dsu;

    private static int countGroup = 0;


    static public void main(String[] args){
        long start = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(Config.FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(!checkLine(line)){
                    continue;
                }
                newLine(line.replace("\"", "").split(";", 100));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // создаем массив из хеш-таблиц
        createHashTables();
        // добавляем все строки в dsu
        addLinesInDSU();
        // формируем группы
        createGroups();
        // сортируем группы
        sortedGroups();


        printAnswer();

        System.out.println(((System.currentTimeMillis() - start) / 1000f) + " seconds");
    }

    // Проверяем на корректность строку
    static public boolean checkLine(String line){
        char[] charArray = line.toCharArray();

        int countQuote = 0;
        for(int i = 0; i < charArray.length; i++){
            if(charArray[i] == '\"'){
                countQuote++;
                if(2 < countQuote){
                    return false;
                }
                continue;
            }

            if(charArray[i] == ';'){
                if(countQuote != 2){
                    return false;
                }
                countQuote = 0;
            }
        }

        return true;
    }

    static public void newLine(String[] strings){
        while(countElementInColumn.size() < strings.length){
            countElementInColumn.add(0);
        }

        countElementInColumn.replaceAll(integer -> integer + 1);

        int name = lines.size();
        lines.add(new Line(name, strings));
    }

    static public void createHashTables(){
        for(Integer number : countElementInColumn){
            elementsInColumns.add(new HashMap<String, Line>(number * 2));
        }
    }

    static public void addLinesInDSU(){
        dsu = new DSU(lines.size());

        for(Line line : lines){
            int i = 0;
            for(String str : line.getElements()){
                if(!str.equals("")) {
                    if (elementsInColumns.get(i).containsKey(str)) {
                        dsu.union_sets(elementsInColumns.get(i).get(str).getName(), line.getName());
                    } else {
                        elementsInColumns.get(i).put(str, line);
                    }
                }

                i++;
            }
        }
    }

    static public void createGroups(){
        for(Line line : lines){
            int numberGroup = dsu.getSet(line.getName());

            if(!groups.containsKey(numberGroup)){
                groups.put(numberGroup, new Group((((Integer) numberGroup)).toString()));
            }

            groups.get(numberGroup).addLine(line);
        }

        // считаем количество групп, в которых не один элемент
        for(Group group : groups.values()){
            if(1 < group.getSize()){
                countGroup++;
            }
        }
    }

    static public void sortedGroups(){
        List<Group> sortedGroup = new ArrayList<>();

        sortedGroup.addAll(groups.values());

        Collections.sort(sortedGroup, new Comparator<Group>(){
            public int compare(Group o1, Group o2) {
                int size1 = o1.getSize();
                int size2 = o2.getSize();
                if(size1 == size2){
                    return 0;
                }
                if(size1 < size2){
                    return 1;
                }
                return -1;
            }
        });

        groupsAnswer.addAll(sortedGroup);
    }

    private static void printAnswer(){
        System.out.println("Количество групп: " + countGroup);
        int numberGroup = 1;
        for(Group group : groupsAnswer) {
            if(group.getSize() < 2){
                continue;
            }
            System.out.println("Группа " + numberGroup + "\n");
            System.out.println(group);
            numberGroup++;
        }
    }
}
