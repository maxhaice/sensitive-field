package com.hub.sensitivefield.config.oAuth2Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Test {
    List list;
    Test(){
        list = new ArrayList();
        someVoid(list);
    }
    void someVoid(List l){
        l.add(0);
        l=null;
    }
    public static void main(String[] args) {
        String[] str = new String[] { "1", "2", "3" };

        List list = Arrays.asList(str);
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Object object = (Object) iterator.next();
            iterator.remove();
        }
        System.out.println(list.size());
    }
}
