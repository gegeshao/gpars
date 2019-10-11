/**
 * Created by root on 10/11/19.
 */

class lcd{
    def name
    def list= []
    def dream(ge) {


/*println"my name is lcd,and i am $ge"*/


        list.add ge
    }
    def lcd(String cc){
        this.name=cc
    }
    def leftShift(person) { list.add person }

    String toString(){
        "$list"
    }
}

def person = new lcd("111")


 person.dream("handsome")

    person.invokeMethod("dream","handsome")

    MetaMethod m =person.metaClass.getMetaMethod("dream" ,"handsome")
    m.invoke(person,"handsome")

    person << 'gege'
    println person //  public static String valueOf(Object obj) { return (obj == null) ? "null" : obj.toString(); }

