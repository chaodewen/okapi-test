package applications.calculator;

import okapi.annotation.GET;
import okapi.check.MethodChecker;
import okapi.client.InvokeFuture;
import okapi.client.Response;
import okapi.client.Service;
import okapi.client.ServiceClient;

/**
 * Created by Cc on 2017/5/4.
 */

public class Calculator extends Service {
    public static void main(String[] args) {
        MethodChecker.checkMethod();
    }
    @GET("/calculator/addition/<int:a>/<int:b>")
    public int add(int a, int b) {
        return a + b;
    }
    @GET("/calculator/subtraction")
    public int subtract() {
        if(this.Args.containsKey("a") && this.Args.containsKey("b")) {
            int a = Integer.valueOf(this.Args.get("a"));
            int b = Integer.valueOf(this.Args.get("b"));
            return a - b;
        }
        return Integer.MIN_VALUE;
    }
    @GET("/calculator/multiplication/<int:a>/<int:b>")
    public Response multiply(int a, int b) {
        return new Response(a * b);
    }
    @GET("/calculator/complexCal/<int:a>/<int:b>")
    public Response complexCal(int a, int b) {
        InvokeFuture addFuture = ServiceClient.invokeAPI("/calculator/addition/" + a + "/" + b);
        InvokeFuture mulFuture = ServiceClient.invokeAPI("/calculator/subtraction?a=" + a + "&b=" + b);
        int addition = addFuture.get().getBodyByInt();
        int multiplication = mulFuture.get().getBodyByInt();
        return new Response(addition + multiplication);
    }
    @GET("/calculator/print")
    public Response print() {
        System.out.println("This is Calculator.");
        return new Response("Calculator is ok.");
    }
}