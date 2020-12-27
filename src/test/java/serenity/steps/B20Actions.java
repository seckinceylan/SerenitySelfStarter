package serenity.steps;
import net.thucydides.core.annotations.Step;
public class B20Actions {
    String actor ;

    @Step("#actor This is where I prepare all stuff before taking action")
    public void preparedSomething(){
        System.out.println(actor+ " preparing cool stuff");
    }
    @Step("#actor Taking some action here")
    public void takeAnAction(){
        System.out.println(actor+ " taking some action");
    }
    @Step("#actor Eventually expecting a result")
    public void expectSomeResult(){
        System.out.println(actor+ " expecting some result");
    }
}