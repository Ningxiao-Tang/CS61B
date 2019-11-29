public class ArrayDeque<T> implements Deque<T> {
    private T[] items;

    private int size, first, last;
    private int REFACTOR = 2;
    public ArrayDeque(){
        items = (T[]) new Object[256];
        first = 0;
        last = 1;
        size = 0;
    }
    private void resize (int capacity){
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0,a, 0, size);
        items = a;
    }
    @Override
    public void addFirst(T x){
        items[first] = x;
        first--;
        size++;
        if(first == -1)
            first = items.length;
        if(size == items.length)
            resize(size * REFACTOR);
    }
    @Override
    public void addLast(T x){
        items[last] = x;
        last ++;
        size ++;
        if(last == items.length)
            last = 0;
        if(size == items.length)
            resize(size * REFACTOR);
    }
    @Override
    public boolean isEmpty(){
        return last - first == 1;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        for (int i = first; i < last; i++) {
            System.out.println(items[i]);
        }
    }
    @Override
    public T removeFirst(){
        first ++;
        size --;
        return items[first-1];
    }
    @Override
    public T removeLast(){
        last--;
        size--;
        return items[last++];
    }
    @Override
    public T get(int i){
        int x = first + i;
        if(x < size)
            return items[x];
        else return null;
    }
}
