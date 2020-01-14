package framework;

import lombok.*;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 调用对象信息,通过这些信息可以将一个接口实现类复原
 * @author 曾鹏
 */
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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


    @Override
    public String toString() {
        return "Invocation{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
