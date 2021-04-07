package nl.tudelft.oopp.group54.util;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TextFormatterFactoryTest {

  @Test
  void createFormatter() {
    TextFormatter<?> f1 = TextFormatterFactory.createFormatter(TextFormatterType.JOIN_ID);
    TextFormatter<?> f2 = TextFormatterFactory.createFormatter(TextFormatterType.NAME);
    TextFormatter<?> f3 = TextFormatterFactory.createFormatter(TextFormatterType.TIME);
    TextFormatter<?> f4 = TextFormatterFactory.createFormatter(null);

    //assertNotNull(TextFormatterFactory.createPattern(TextFormatterType.JOIN_ID));
    //assertNotNull(TextFormatterFactory.createPattern(TextFormatterType.NAME));
    //assertNotNull(TextFormatterFactory.createPattern(TextFormatterType.TIME));
  }
}