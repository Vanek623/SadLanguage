package mySet;

public class Node<Type> {
    private int hash;
    private Type value;

    Node(int hash, Type value){
        this.hash = hash;
        this.value = value;
    }

    public Type getValue() {
        return value;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }
}
