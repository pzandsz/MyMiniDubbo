package protocol.http;

import framework.Invocation;
import org.apache.commons.io.IOUtils;
import provider.LocalRegister;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HttpServerHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp){

        try {
            //处理请求，返回结果
            ServletInputStream inputStream = req.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(inputStream);

            Invocation invocation = (Invocation) ois.readObject();

            /**
             * 从本地注册map中找到接口名对应的实现类
             * 寻找注册中心的类,通过反射执行方法
             */
            Class impClass= LocalRegister.get(invocation.getInterfaceName());
            Method method=impClass.getMethod
                    (invocation.getMethodName(),invocation.getParamTypes());

            String result= (String) method.invoke
                    (impClass.newInstance(),invocation.getParams());
            System.out.println(result);

            //返回结果
            IOUtils.write(result,resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
