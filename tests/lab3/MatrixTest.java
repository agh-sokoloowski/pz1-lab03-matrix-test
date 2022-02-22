package lab3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    @Test
    void asArray() {
        Matrix m = new Matrix(new double[][]{{1, 2, 3, 4}, {5, 6}, {7, 8}, {9}});
        double[][] a = new double[][]{{1, 2, 3, 4}, {5, 6, 0, 0}, {7, 8, 0, 0}, {9, 0, 0, 0}};
        assertArrayEquals(a, m.asArray());
    }

    @Test
    void get() {
        Matrix m = new Matrix(new double[][]{{1, 2, 3, 4}, {5, 6}, {7, 8}, {9}});
        assertEquals(5f, m.get(1, 0));
        assertEquals(8f, m.get(2, 1));

        Exception e = assertThrows(RuntimeException.class, () -> m.get(5, 5));
        assertEquals("Cannot get given field", e.getMessage());
    }

    @Test
    void set() {
        Matrix m = new Matrix(new double[][]{{1, 2, 3, 4}, {5, 6}, {7, 8}, {9}});
        assertEquals(8f, m.get(2, 1));
        m.set(2, 1, 10f);
        assertNotEquals(8f, m.get(2, 1));
        assertEquals(10f, m.get(2, 1));

        Exception e = assertThrows(RuntimeException.class, () -> m.set(5, 5, 1f));
        assertEquals("Cannot set given field", e.getMessage());
    }

    @Test
    void testToString() {
        Matrix m = new Matrix(new double[][]{{1, 2, 3, 4}, {5, 6}, {7, 8}, {9}});
        assertEquals("[[ 1.0 2.0 3.0 4.0 ][ 5.0 6.0 0.0 0.0 ][ 7.0 8.0 0.0 0.0 ][ 9.0 0.0 0.0 0.0 ]]", m.toString());
    }

    @Test
    void reshape() {
        Matrix m = new Matrix(2, 3);
        assertArrayEquals(new int[]{2, 3}, m.shape());
        m.reshape(1, 6);
        assertArrayEquals(new int[]{1, 6}, m.shape());

        Exception e = assertThrows(RuntimeException.class, () -> m.reshape(2, 4));
        assertTrue(e.getMessage().contains("matrix can't be reshaped to"));
    }

    @Test
    void shape() {
        Matrix m = new Matrix(2, 3);
        assertArrayEquals(new int[]{2, 3}, m.shape());
    }

    @Test
    void testAdd() {
        Matrix m1 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        Matrix m2 = new Matrix(new double[][]{{4, 5, 6}, {7, 8, 9}});
        Matrix m3 = new Matrix(new double[][]{{1}});
        Matrix res = new Matrix(new double[][]{{5, 7, 9}, {11, 13, 15}});
        assertEquals(res.toString(), m1.add(m2).toString());
        assertEquals(m2.toString(), m1.add(3f).toString());

        Exception e = assertThrows(RuntimeException.class, () -> m1.add(m3));
        assertTrue(e.getMessage().contains("matrix can't be added to"));
    }

    @Test
    void testSub() {
        Matrix m1 = new Matrix(new double[][]{{4, 5, 6}, {7, 8, 9}});
        Matrix m2 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        Matrix m3 = new Matrix(new double[][]{{1}});
        Matrix res = new Matrix(new double[][]{{3, 3, 3}, {3, 3, 3}});
        assertEquals(res.toString(), m1.sub(m2).toString());
        assertEquals(m2.toString(), m1.sub(3f).toString());

        Exception e = assertThrows(RuntimeException.class, () -> m1.sub(m3));
        assertTrue(e.getMessage().contains("matrix can't be subtracted from"));
    }

    @Test
    void testMul() {
        Matrix m1 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        Matrix m2 = new Matrix(new double[][]{{4, 5, 6}, {7, 8, 9}});
        Matrix m3 = new Matrix(new double[][]{{1}});
        Matrix res1 = new Matrix(new double[][]{{4, 10, 18}, {28, 40, 54}});
        Matrix res2 = new Matrix(new double[][]{{2, 4, 6}, {8, 10, 12}});
        assertEquals(res1.toString(), m1.mul(m2).toString());
        assertEquals(res2.toString(), m1.mul(2f).toString());

        Exception e = assertThrows(RuntimeException.class, () -> m1.mul(m3));
        assertTrue(e.getMessage().contains("matrix can't be multiplied by"));
    }

    @Test
    void testDiv() {
        Matrix m1 = new Matrix(new double[][]{{2, 4, 6}, {8, 10, 12}});
        Matrix m2 = new Matrix(new double[][]{{1}});
        Matrix res1 = new Matrix(new double[][]{{1, 1, 1}, {1, 1, 1}});
        Matrix res2 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        assertEquals(res1.toString(), m1.div(m1).toString());
        assertEquals(res2.toString(), m1.div(2f).toString());

        Exception e = assertThrows(RuntimeException.class, () -> m1.div(m2));
        assertTrue(e.getMessage().contains("matrix can't be divided by"));
    }

    @Test
    void testDot() {
        Matrix m1 = new Matrix(new double[][]{{-1, 1}, {2, 1}, {-1, 0}});
        Matrix m2 = new Matrix(new double[][]{{0}, {1}});
        Matrix res = new Matrix(new double[][]{{1}, {1}, {0}});
        assertEquals(res.toString(), m1.dot(m2).toString());

        Exception e = assertThrows(RuntimeException.class, () -> m2.dot(m1));
        assertTrue(e.getMessage().contains("matrix can't be multiplied by"));
    }

    @Test
    void testFrobenius() {
        Matrix m = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        assertEquals(Math.sqrt(1 + 2 * 2 + 3 * 3 + 4 * 4 + 5 * 5 + 6 * 6), m.frobenius());
    }

    @Test
    void testRandom() {
        Matrix m = Matrix.random(2, 3);
        assertArrayEquals(new int[]{2, 3}, m.shape());
    }

    @Test
    void testEye() {
        Matrix m = Matrix.eye(5);
        for (int row = 0; row < m.shape()[0]; row++) {
            for (int col = 0; col < m.shape()[1]; col++) {
                double expected = 0;
                if (row == col) {
                    expected = 1;
                }

                assertEquals(expected, m.get(row, col));
            }
        }
    }
}