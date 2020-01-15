package xml.util;

import bean.Person;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import sensetive.SensetiveWordFilter;

/**
 * Xml转换工具
 * @Author: zengpeng
 * @Date: 2019/10/915:57
 */
public class XmlTransferUtil {

    public static void main(String[] args) {
        Person bean=new Person("呼叫小爱",19);
        //XStream xstream = new XStream();//需要XPP3库
        //XStream xstream = new XStream(new DomDriver());//不需要XPP3库

        //不需要XPP3库开始使用Java6
        XStream xstream = new XStream(new StaxDriver());

        //为类名节点重命名
        xstream.alias("人",Person.class);

        //XML序列化
        String xml = xstream.toXML(bean);


        System.out.println(xml);


        //XML反序列化
        bean=(Person)xstream.fromXML(xml);
        String name = bean.getName();

        SensetiveWordFilter filter=new SensetiveWordFilter();
        System.out.println(filter.isContaintSensitiveWord(name));
    }
}
