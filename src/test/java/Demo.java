import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试类
 *
 * @author zhangxinqiang
 * @date 21/03/2018
 */
public class Demo {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring/applicationContext.xml");
    }
}
