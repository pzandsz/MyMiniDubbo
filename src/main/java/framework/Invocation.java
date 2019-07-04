package framework;

import java.io.Serializable;

/**
 * 调用对象信息,通过这些信息可以将一个接口实现类复原
 * @author 曾鹏
 */
public class Invocation implements Serializable {

    /**
     * 接口名称
     */
    private String interfaceName;
    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数类型列表
     */
    private Class[] paramTypes;

    /**
     * 参数列表
     */
    private Object[] params;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Invocation(String interfaceName, String methodName, Class[] paramTypes, Object[] params) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }
}
