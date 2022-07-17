package Objects;

public class DSU {
    private final int[] set;

    public DSU(int size){
        set = new int[size];
        for(int i = 0; i < size; i++){
            set[i] = i;
        }
    }

    public int getSet(int v){
        return find_set(v);
    }

    public int find_set(int v){
        return v == set[v] ? v : (set[v] = find_set(set[v]));
    }


    public void union_sets(int a, int b) {
        a = find_set(a);
        b = find_set(b);
        if (a != b) {
            set[b] = a;
        }
    }
}
