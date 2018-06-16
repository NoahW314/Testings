
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class DataLogger {
    private Writer writer;
    private StringBuffer lineBuffer;

    public DataLogger (String fileName) {
        String directoryPath    = "F:\\";
        String filePath         = directoryPath+fileName + ".txt";

        new File(directoryPath).mkdir();        // Make sure that the directory exists

        try {
            writer = new FileWriter(filePath);
            lineBuffer = new StringBuffer(128);
        } catch (IOException e) {
        	System.out.println(e);
        }
    }

    private void flushLineBuffer(){

        try {
            lineBuffer.append('\n');
            writer.write(lineBuffer.toString());
            lineBuffer.setLength(0);
        }
        catch (IOException e){
        }
    }

    public void closeDataLogger() {
        try {
            writer.close();
        }
        catch (IOException e) {
        }
    }

    public void addField(String s) {
        if (lineBuffer.length()>0) {
            lineBuffer.append(',');
        }
        lineBuffer.append(s);
    }

    public void addField(char c) {
        if (lineBuffer.length()>0) {
            lineBuffer.append(',');
        }
        lineBuffer.append(c);
    }

    public void addField(boolean b) {
        addField(b ? '1' : '0');
    }

    public void addField(byte b) {
        addField(Byte.toString(b));
    }

    public void addField(short s) {
        addField(Short.toString(s));
    }

    public void addField(long l) {
        addField(Long.toString(l));
    }

    public void addField(float f) {
        addField(Float.toString(f));
    }

    public void addField(double d) {
        addField(Double.toString(d));
    }

    public void newLine() {
        flushLineBuffer();
    }

    @Override
    protected void finalize() throws Throwable {
        closeDataLogger();
        super.finalize();
    }
}
