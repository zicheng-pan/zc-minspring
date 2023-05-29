import com.minispring.ClassPathXmlApplicationContext;
import com.minispring.testbean.TestObj;

public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        TestObj testobj = (TestObj) classPathXmlApplicationContext.getBean("testobj");
        testobj.sayHello();
    }
}
