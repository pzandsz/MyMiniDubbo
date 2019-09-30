package provider.imp;

import jdk.nashorn.internal.parser.JSONParser;
import provider.api.HelloService;

public class HelloServiceImp implements HelloService {

    @Override
    public String sayHello(String message) {
        return "hello,"+message;
    }

    @Override
    public Object fromJson2Object(String jsonStr) {
        return jsonStr;
    }
}
