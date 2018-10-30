package standalone;


//import org.apache.log4j.Logger;
//import org.slf4j.LoggerFactory;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam
 */
public class MyLogger {
   private static Logger logger = LoggerFactory.getLogger(MyLogger.class);

    public static void main(String[] args) {
        getMessage();
    }

    private static void getMessage() {
        // 记录下各种级别的信息,这些信息放在哪儿,以哪种方式存放,在log4j.properties文件中配置.
            logger.debug("This is debug message.");
            logger.info("This is a info message.");
            logger.warn("This is a warn message.");
            logger.error("This is a error message.....");
    }

}
