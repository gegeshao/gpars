/**
 * Created by root on 10/9/19.
 */

import groovy.lang.Closure;
import groovyx.gpars.dataflow.LazyDataflowVariable;
import org.codehaus.groovy.runtime.MethodClosure;

import java.util.concurrent.Callable;

/**
 * @ClassName TEST
 * @description TODO
 * @Author root
 * @Date 10/9/19 11:04 AM
 * @Version 1.0
 **/
public class TEST {
    public static void main(String[] args){

     /*   Callable<String> callable = new Callable<String>(){
            @Override
            public String call() throws Exception {
                if(callResult != null)
                    return callResult.toString();
                else
                    return null;
            }
        };*/
       DDD d =new DDD();

       MethodClosure crwap = new MethodClosure(d,"handle");
       Closure c =crwap.curry(3);
       c.call(2);


     /*   MethodClosure initMc = new MethodClosure(callable, "call");
        p = new LazyDataflowVariable(initMc);*/
    }
}
