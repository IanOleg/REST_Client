package com.restclient;

import com.restclient.configuration.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        communication.getAllUsers();
        communication.addUser();
        communication.updateUser();
        communication.deleteUser();
        System.out.println(communication.getResponse());
    }
}
