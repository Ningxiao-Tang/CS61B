public class LinkedListDeque<T> implements Deque<T>{
    // "desk" double-ended queue
    private class Node {
        public T item;
        public Node prev, next;

        public Node(T i, Node p, Node n){
            item = i;
            prev = p;
            next = n;
        }
    }
    private Node sentinel;
    private int size;
    public LinkedListDeque(){
        //creates an empty linked-list deque
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    /* last = sentinel.prev */
    @Override
    public void addFirst(T item){
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size +=1;
    }
    @Override
    public void addLast(T item){
        sentinel.prev = new Node(item, sentinel.prev,sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }
    @Override
    public boolean isEmpty(){
        if (sentinel.next == sentinel)
            return true;
        return false;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        Node p = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.println(p.item);
            p = p.next;
        }
    }
    @Override
    public T removeFirst(){
        if(sentinel.next != null ){
            T item = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return item;
        }
        return null;
    }
    @Override
    public T removeLast(){
        if (sentinel.next != null){
            T item = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            return item;
        }
        return null;
    }
    @Override
    public T get(int index){
        Node p = sentinel;
        if (index > size)
            return null;
        for (int i = 0; i < index; i++) {
                p = p.next;
        }
        return p.item;
    }


    private T getRc(Node p, int i) {
        if (i == 0) return p.item;
        return getRc(p.next, --i);
    }
    public T getRecursive(int index){
        // Node p =
        if (index > size) return null;
        return getRc(sentinel, index);
    }

}
