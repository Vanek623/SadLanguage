package myList;

public class CustomList<Type> {

    Item<Type> last;
    private Item<Type> first;
    int size;

    public void add(Type value) {           //add new value
        if (size == 0) {
            first = last = new Item<>(value);
        } else if (size == 1) {
            first = new Item<>(last.getValue());
            last = new Item<>(value);
            last.setBack(first);
            first.setNext(last);
        } else {
            last.setNext(new Item<>(value));
            last.getNext().setBack(last);
            last = last.getNext();
        }
        size++;
    }

    public Type getValue(int i) {          //get value by index
        Item<Type> item;
        if (i < size) {
            if (i < size / 2) {         //from begin
                item = first;
                for (int j = 0; j < size; j++) {
                    if (j == i)
                        return (Type) item.getValue();
                    else
                        item = item.getNext();
                }
            } else {        //from end
                item = last;
                for (int j = size - 1; j >= 0; j--) {
                    if (j == i)
                        return (Type) item.getValue();
                    else
                        item = item.getBack();
                }
            }
        }
        return null;
    }

    public boolean deleteByIndex(int i) {      //delete object by index
        Item<Type> item;
        if (i == 0) {
            item = first.getNext();
            first.getNext().setBack(null);
            first.setNext(null);
            first = item;
            size--;
        } else if (i == size - 1) {
            item = last.getBack();
            last.getBack().setNext(null);
            last.setBack(null);
            last = item;
            size--;
        } else if (i < size / 2) {      //from begin
            item = first;
            for (int j = 0; j < size; j++) {
                if (j == i) {
                    item.getBack().setNext(item.getNext());
                    item.getNext().setBack(item.getBack());
                    size--;
                } else
                    item = item.getNext();
            }
        } else {            //from end
            item = last;
            for (int j = size - 1; j >= 0; j--) {
                if (j == i) {
                    item.getBack().setNext(item.getNext());
                    item.getNext().setBack(item.getBack());
                    size--;
                } else
                    item = item.getBack();
            }
        }

        return false;
    }

    public boolean delete(Type value){      //delete element by himself
        Item<Type> item;
        if(size != 0) {
            item = last;
            for (int i = 0; i < size; i++) {
                if (item.getValue().equals(value)) {
                    item.getBack().setNext(item.getNext());
                    item.getNext().setBack(item.getBack());
                    size--;
                    return true;
                }

                item = item.getBack();
            }
        }

        return false;
    }

    public void print() {
        System.out.println();
        Item item = first;
        for (int i = 0; i < size; i++) {
            System.out.print(item.getValue() + ", ");
            item = item.getNext();
        }

        System.out.println();
    }

    public Item<Type> getFirst(){
        return first;
    }

    public int size(){
        return size;
    }
}
