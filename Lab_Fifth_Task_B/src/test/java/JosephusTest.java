import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class JosephusTest {
    @Test
    void testLinkedListSmallN() {
        assertEquals(3, JosephusLinkedList.solve(5));
    }

    @Test
    void testLinkedListMediumN() {
        assertEquals(5, JosephusLinkedList.solve(10));
    }

    @Test
    void testLinkedListOne() {
        assertEquals(1, JosephusLinkedList.solve(1));
    }

    @Test
    void testArrayListSmallN() {
        assertEquals(3, JosephusArrayList.solve(5));
    }

    @Test
    void testArrayListMediumN() {
        assertEquals(5, JosephusArrayList.solve(10));
    }

    @Test
    void testArrayListOne() {
        assertEquals(1, JosephusArrayList.solve(1));
    }
}