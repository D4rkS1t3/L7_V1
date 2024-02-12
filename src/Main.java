import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Object> a=new LinkedList<>();
        int[] tab=new int[]{2,3,5};
        int[] reverseTab=new int[tab.length];
        System.out.println(Arrays.toString(tab));
        Stack<Integer> stos =new Stack<Integer>();
        for (int i = 0; i < tab.length; i++) {
            stos.push(tab[i]);
        }
        for (int i = 0; i < tab.length; i++) {
            reverseTab[i]=stos.pop();
        }
        for (int i:reverseTab) {
            System.out.println(i);
        }


        Set<String> zbior=new TreeSet<>();
        zbior.add("bartek");
        zbior.add("bartek");
        zbior.add("bartek");
        zbior.add("zosia");
        System.out.println(zbior);

        Map<Integer, String> mapa=new TreeMap<>();
        mapa.put(3, "elektrotechnika");
        mapa.put(4,"bazy danych");
        mapa.put(5,"java");
        System.out.println(mapa);
        System.out.println(mapa.get(4));

        Queue<Integer> kolej=new LinkedList<>();
        kolej.add(1);
        kolej.add(2);
        System.out.println(kolej);
        kolej.remove();
        System.out.println(kolej);
}}