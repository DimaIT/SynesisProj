package util;

import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author D.Tolpekin
 */
public class ReflectTest extends TestCase {
    public static class TestClass {
        private String name;
        private int count;

        public TestClass(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public TestClass() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestClass testClass = (TestClass) o;

            if (count != testClass.count) return false;
            return !(name != null ? !name.equals(testClass.name) : testClass.name != null);

        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + count;
            return result;
        }
    }

    public void testCloneEntity() throws Exception {
        TestClass obj = new TestClass("1", 1);
        assertEquals(obj, Reflect.cloneEntity(obj));
    }

    public void testCreateObjectByClass() throws Exception {
        assertNotNull(Reflect.createObjectByClass(TestClass.class));
    }

    public void testSimplifiedInvoke() throws Exception {
        Method setter = Reflect.getSetter(TestClass.class, nameField());
        TestClass obj = new TestClass("hello", 10);
        String target = "world";
        Reflect.simplifiedInvoke(setter, obj, target);

        assertEquals(obj.getName(), target);
    }

    public void testSimplifiedGetField() throws Exception {
        Field field = Reflect.simplifiedGetField(TestClass.class, "name");
        assertNotNull(field);
        assertEquals(field.getName(), "name");
    }

    public void testGetGetter() throws Exception {
        Method getter = Reflect.getGetter(TestClass.class, nameField());
        assertNotNull(getter);
        assertEquals(getter.getName(), "getName");
    }

    public void testGetSetter() throws Exception {
        Method getter = Reflect.getSetter(TestClass.class, nameField());
        assertNotNull(getter);
        assertEquals(getter.getName(), "setName");
    }

    public static Field nameField() {
        return Reflect.simplifiedGetField(TestClass.class, "name");
    }
}