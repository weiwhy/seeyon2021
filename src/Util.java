import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
    public static String getText(String name) {
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(name), "UTF-8");
            BufferedReader reader=new BufferedReader(isr);
            String tmp;
            StringBuffer stringBuffer=new StringBuffer();
            while ((tmp=reader.readLine())!=null) {
                stringBuffer.append(tmp);
            }
            reader.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            System.err.println(e.getMessage()+"");
            return "";
        }
    }
    
    public static byte[] fileToBytes(File file) {
        byte[] buffer = null;

        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            buffer = bos.toByteArray();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } finally{
                try {
                    if(null!=fis){
                        fis.close();
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        return buffer;
    }
}
