package provider.imp;

import provider.api.HelloService;

public class HelloServiceImp implements HelloService {

    @Override
    public String sayHello(String message) {
        return "hello,"+message;
    }
}
