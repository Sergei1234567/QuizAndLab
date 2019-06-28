package ua.com.quizandndlab.lab43;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EntityInputStream implements EntityInput {
    private DataInput in;

    public EntityInputStream(InputStream in) {
        this.in = new DataInputStream(in);
    }

    @Override
    public Person readPerson() throws IOException {
        int age = in.readInt();

        String name = "";
        boolean hasName = in.readBoolean();
        if (hasName) {
            name = in.readUTF();
        }

        return new Person(name, age);
    }

    @Override
    public Point readPoint() throws IOException {
        int value = in.readByte();
        return new Point(value >>> 4, value & 0x0F);
    }
}
