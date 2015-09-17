package model;

import junit.framework.TestCase;

import static org.fest.assertions.Assertions.assertThat;

public class CellTest extends TestCase {

    public void testValue() throws Exception {
        Cell tstCell = new Cell(null, "val1", "val2", "val3");
        String rslt = tstCell.value();
        assertThat(rslt.equals("val1, val2, val3"));
    }
}