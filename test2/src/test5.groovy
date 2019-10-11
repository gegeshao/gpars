/**
 * Created by root on 10/11/19.
 */
def examiningClosure(closure){
    closure.call()
}

//examiningClosure({println"ccc"})
examiningClosure(){
    println"In first Closure"
    println"class is "+getClass().name
    println"this is "+this+" ,super:"+this.getClass().superclass.name
    println"owner is "+owner+" ,super:"+owner.getClass().superclass.name
    println"delegate is "+delegate+" ,super:"+delegate.getClass().superclass.name+"\r\n"
    examiningClosure() {
        println "In closure within first Closure"
        println "class is " + getClass().name
        println "this is " + this + " ,super:" + this.getClass().superclass.name
        println "owner is " + owner + " ,super:" + owner.getClass().superclass.name
        println "delegate is " + delegate + " ,super:" + delegate.getClass().superclass.name+"\r\n"
    }
    examiningClosure() {
        println "In closure within first Closure"
        println "class is " + getClass().name
        println "this is " + this + " ,super:" + this.getClass().superclass.name
        println "owner is " + owner + " ,super:" + owner.getClass().superclass.name
        println "delegate is " + delegate + " ,super:" + delegate.getClass().superclass.name+"\r\n"
    }
}

examiningClosure() {
    println "In second Closure"
    println "class is " + getClass().name
    println "this is " + this + " ,super:" + this.getClass().superclass.name
    println "owner is " + owner + " ,super" + owner.getClass().superclass.name
    println "delegate is " + delegate + " ,super" + delegate.getClass().superclass.name+"\r\n"
    examiningClosure() {
        println "In closure within second Closure"
        println "class is " + getClass().name
        println "this is " + this + " ,super:" + this.getClass().superclass.name
        println "owner is " + owner + " ,super:" + owner.getClass().superclass.name
        println "delegate is " + delegate + " ,super:" + delegate.getClass().superclass.name+"\r\n"
    }
}

