/**
 * Created by root on 10/9/19.
 */

import groovy.lang.Closure;
import org.codehaus.groovy.runtime.MethodClosure;

/**
 * @ClassName DDD
 * @description TODO
 * @Author root
 * @Date 10/9/19 10:46 AM
 * @Version 1.0
 **/
public class DDD {


    public void test(int i,callback call){

       /* MethodClosure cwrap =new MethodClosure(DDD.this,"handle");
        Closure cc = cwrap.curry(3);
        cc.call();*/

        call.handle(2);
    }
    DDD(){}
    public void handle(int i,int j){

        System.out.println("j="+j);
        System.out.println("3+j="+(3+j));

    }
}
