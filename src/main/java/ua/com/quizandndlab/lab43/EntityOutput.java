package ua.com.quizandndlab.lab43;

import java.awt.*;
import java.io.IOException;

public interface EntityOutput {
    public void writePerson(Person person) throws IOException;
    public void writePoint(Point point) throws IOException;
}
