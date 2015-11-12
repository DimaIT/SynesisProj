package controllers;

import junit.framework.TestCase;
import model.Field;
import org.mockito.Mockito;
import services.FieldService;

import java.util.Collections;

/**
 * @author D.Tolpekin
 */
public class ApplicationTest extends TestCase {

    public void testHaveFields() throws Exception {
        FieldService mock = Mockito.mock(FieldService.class);
        Mockito.when(mock.getActualFields()).thenReturn(Collections.singletonList(new Field()));

        Application app = new Application(mock, null);
        assertTrue(app.haveFields());
    }
}