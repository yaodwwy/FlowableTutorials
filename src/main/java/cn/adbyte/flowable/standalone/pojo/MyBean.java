package cn.adbyte.flowable.standalone.pojo;


import org.flowable.engine.runtime.Execution;

import java.io.Serializable;

public class MyBean implements Serializable {

    private String name = "angus";

    public String getName() {
        return name;
    }
    
    public void print(Execution exe) {
        System.out.println("执行print方法，执行流id：" + exe.getId());
    }
}
