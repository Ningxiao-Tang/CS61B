public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        LinkedListDeque<Character> deque = new LinkedListDeque<>();

        for(int i = 0; i < word.length(); i++){
            char ch = word.charAt(i);
            //System.out.println(ch);
            deque.addLast(ch);
        }
        return deque;
    }

    public boolean isPalindrome(String word){
        Deque<Character> d = wordToDeque(word);
        Deque<Character> d1 = wordToDeque(word);
        int size = d.size(), i = 0;
        while(i <size){
            if(d.removeFirst().equals(d1.removeLast())){
                i++;
            }
            else return false;
        }
        return  true;
    }
}
