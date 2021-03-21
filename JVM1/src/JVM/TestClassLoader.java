package JVM;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestClassLoader extends ClassLoader{
    public static void main(String[] args) throws Exception{
        Object hello = new TestClassLoader().findClass("Hello").newInstance();
        Method method = hello.getClass().getDeclaredMethod("hello");
        method.invoke(hello);
    }

    public byte[] getBytes(){
        String filePath = "src/JVM/Hello.xlass";
        File f = new File(filePath);
        Path path = Paths.get(f.getAbsolutePath());

        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e){
            e.printStackTrace();
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (255 - data[i]);
        }
        return data;
    }

    @Override
    protected Class<?> findClass(String name) throws  ClassNotFoundException{
        return defineClass(name, getBytes(), 0, getBytes().length);
    }
}
