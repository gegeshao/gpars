import org.codehaus.groovy.runtime.MethodClosure

/**
 * Created by root on 10/9/19.
 */
class cwrap1 {
    int limit

    cwrap1(int limit){
        this.limit=limit
    }

    void show(int i ,int j){
         println("j= "+j)
         println("3+j="+(3+j))
    }

}

/*
cwrap1 d = new cwrap1(2)

MethodClosure C = new MethodClosure(d,"show")
//Closure cc = C.curry(3)
cc.call(1,2)*/
