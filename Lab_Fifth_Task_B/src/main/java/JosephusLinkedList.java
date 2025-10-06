import java.util.LinkedList;
import java.util.ListIterator;

public class JosephusLinkedList {
    public static int solve(int n) {
        LinkedList<Integer> people = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            people.add(i);
        }

        ListIterator<Integer> it = people.listIterator();
        while (people.size() > 1) {
            if (!it.hasNext()) it = people.listIterator();
            it.next();
            if (!it.hasNext()) it = people.listIterator();
            it.next();
            it.remove();
        }

        return people.get(0);
    }
}
