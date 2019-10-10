/**
 * Created by root on 10/10/19.
 */

/*def d=new DDD()
Closure cc={callback call-> d.test(1,call)};
cc.call(new callback() {
    @Override
    public void handle(int c) {
        System.out.print("i ="+c);
    }
});*/
def Io={int e,int f,int p-> e+f+p}
def dd ={ ->def c =3
          def e  = Io(1,2,c)
          def f  = Io(1,c,e)
          def io = Io(c,e,f)
               io}
println("$dd")
