public class Main {
    public static void main(String[] args) {
        int n = 100000;

        long startArrayListTime = System.nanoTime();
        int resultArrayList = JosephusArrayList.solve(n);
        long endArrayListTime = System.nanoTime();
        System.out.println("Последний оставшийся (ArrayList): " + resultArrayList);
        System.out.println((endArrayListTime - startArrayListTime) / 1000000);

        long startLinkedListTime = System.nanoTime();
        int resultLinkedList = JosephusLinkedList.solve(n);
        long endLinkedListTime = System.nanoTime();
        System.out.println("Последний оставшийся (LinkedList): " + resultLinkedList);
        System.out.println((endLinkedListTime - startLinkedListTime) / 1000000);

    }
}
