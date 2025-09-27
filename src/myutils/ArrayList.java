package myutils;

public class ArrayList<T> {
    private T[] data;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        this.data = (T[]) new Object[10];
        this.capacity = 10;
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        this.data = (T[]) new Object[10];
        this.capacity = capacity;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        capacity *= 2;
        T[] temp = (T[]) new Object[capacity];
        System.arraycopy(data, 0, temp, 0, capacity / 2);
        data = temp;
    }

    private void shiftRight(int index) {
        for (int i = size; i >= index; i--) {
            data[i] = data[i - 1];
        }
    }

    private void shiftLeft(int index) {
        for (int i = index; i < size; i++) {
            data[i] = data[i + 1];
        }
    }

    public void add(int index, T e) {
        if(index > size){
            throw new IndexOutOfBoundsException();
        } else {
            if(size == capacity){
                resize();
            }

            shiftRight(index);
            data[index] = e;
            size++;
        }
    }

    public void add(T e) {
        if (size == capacity) { resize(); }

        data[size] = e;
        size++;
    }

    public void remove(T e){
        int index = indexOf(e);
        if(index != -1){
            data[index] = null;
            shiftLeft(index);
            size--;
        }
    }

    public void remove(int index) {
        data[index] = null;
        shiftLeft(index);
        size--;
    }

    public void set(int index, T e) { data[index] = e; }

    public T get(int index) { return data[index]; }

    public boolean contains(T e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return true;
            }
        }

        return false;
    }

    public int indexOf(T e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }

        return -1;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] temp = (T[]) new Object[size];
        System.arraycopy(data, 0, temp, 0, size);
        return temp;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}