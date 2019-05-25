package myList;

public class Item <Type> {
    private Type value;
    private Item<Type> next;
    private Item<Type> back;

    Item(Type value) {
        this.value = value;
    }

    public Type getValue() {
        return this.value;
    }

    void setBack(Item<Type> item) {
        this.back = item;
    }

    public Item<Type> getNext() {
        return next;
    }

    public void setNext(Item<Type> next) {
        this.next = next;
    }

    public Item<Type> getBack() {
        return back;
    }
}
