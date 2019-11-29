public class OffByN implements CharacterComparator {
    /*
    The OffByN constructor should return an object whose equalChars method
    returns true for characters that are off by N.

    OffByN offBy5 = new OffByN(5);
    offBy5.equalChars('a', 'f');  // true
    offBy5.equalChars('f', 'a');  // true
    offBy5.equalChars('f', 'h');  // false
     */
    private int off;

    public OffByN(int n){
        off = n;
    }

    @Override
    public boolean equalChars(char x, char y){
        return x-y==off || y-x==off;
    }
}
