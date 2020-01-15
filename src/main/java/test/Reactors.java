package test;

/**
 * Reactor 好莱坞原则
 * 多Reactor模式
 * Netty中使用的Reactor模式，引入了多Reactor,也就是一个主Reactor负责监控所有的连接请求，
 * 多个子Reactor负责监控并处理I/O请求,减轻了主Reactor的压力太大而造成的延迟
 *
 * 在netty中,一个MainReactor对应1个线程处理，subReactor对应多个线程，默认是当前CPU核数量的两倍
 *
 * @Author: zengpeng
 * @Date: 2020/1/1417:15
 */
public class Reactors {

    public static void main(String[] args) {
    }
}
