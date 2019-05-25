package mySet;

import myList.CustomList;
import myList.Item;

import java.util.ArrayList;

public class CustomHashSet<Type> {
    private int n = 4;
    private float k_load = 1f;

    private int max_size;
    private int size;
    private ArrayList<CustomList<Node<Type>>> list;

    public CustomHashSet() {
        list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(new CustomList<>());
        }

        size = 0;
        max_size = (int) (n * k_load);
        //System.out.println("Max size is " + max_size);
    }

    public boolean add(Type value) {
        int hash = generateHash(value);
        if(size == max_size) resize();
        if (!contains(value)) {
            list.get(hash & (n - 1)).add(new Node<>(hash, value));
            size++;
            return true;
        }

        return false;
    }

    private void resize(){
        //print();
        n = n << 1;
        //System.out.println("Increase size to " + n);
        ArrayList<CustomList<Node<Type>>> newList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            newList.add(new CustomList<>());
        }
        for (int i = 0; i < list.size(); i++) {
            CustomList<Node<Type>> tmp = list.get(i);
            Item<Node<Type>> item = tmp.getFirst();

            for (int j = 0; j < tmp.size(); j++) {
                Node<Type> node = item.getValue();
                int hash = generateHash(node.getValue());

                node.setHash(hash);
                newList.get(hash & n -1).add(node);
                item = item.getNext();
            }
        }

        max_size = (int)(n * k_load);
        list = newList;
        //System.out.println("Max size is " + max_size);
    }

    public void print() {
        for (int i = 0; i < list.size(); i++) {
            CustomList<Node<Type>> tmp = list.get(i);

            System.out.print("\n" + i + ".");
            Item item = tmp.getFirst();
            for (int j = 0; j < tmp.size(); j++) {
                System.out.print(((Node) item.getValue()).getValue() + ", ");
                item = item.getNext();
            }
        }
        System.out.println();
    }

    public boolean delete(Type value) {
        int hash = generateHash(value);
        CustomList<Node<Type>> tmp = list.get(hash & (n - 1));
        return tmp.delete(new Node<>(hash, value));
    }

    public int generateHash(Type value) {
        if (value instanceof String)
            return ((int) ((String) value).charAt(0));
        else if (value instanceof Integer)
            return (Integer) value % 10;
        else
            System.out.println("Ошибка создания хэша!");
        return 0;
    }

    public boolean contains(Type value) {
        int hash = generateHash(value);
        CustomList tmp = list.get(hash & (n - 1));
        for (int i = 0; i < tmp.size(); i++) {
            Item item = tmp.getFirst();
            if (((Node) item.getValue()).getHash() == hash && ((Node) item.getValue()).getValue().equals(value))
                return true;
        }
        return false;
    }
}
