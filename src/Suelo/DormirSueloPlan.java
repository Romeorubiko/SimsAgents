package Suelo;
import ontologia.acciones.*;
import java.util.*;
import jadex.runtime.*;
import jadex.adapter.fipa.*;


/**
 * Created by eldgb on 25-Oct-17.
 */
public class DormirSueloPlan extends Plan {

    public void body(){
        IMessageEvent message = waitForMessageEvent("dormir_suelo");
        DormirSuelo content = (DormirSuelo)message.getContent();
    }
}
