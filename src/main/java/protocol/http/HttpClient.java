package protocol.http;

import framework.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 模拟发送请求
 * @author 曾鹏
 */
public class HttpClient {
    public String send(String hostname, Integer port, Invocation invocation) {

        try {
            URL url=new URL("http",hostname,port,"/");
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);


            //发送数据
            OutputStream outputStream=httpURLConnection.getOutputStream();
            //进行包装
            ObjectOutputStream oos=new ObjectOutputStream(outputStream);
            oos.writeObject(invocation);
            oos.flush();
            oos.close();

            //获得返回结果
            InputStream inputStream = httpURLConnection.getInputStream();
            String result = IOUtils.toString(inputStream);
            System.out.println("result:::"+result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
