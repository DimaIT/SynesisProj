package model.services;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;


public class ResponseUpdater extends UntypedActor {

    private final ActorRef out;

    public ResponseUpdater(ActorRef out) {
        this.out = out;
        ResponseUpdaterService.addMenuUpdater(out);
    }

    public ResponseUpdater(ActorRef out, boolean table) {
        this.out = out;
        ResponseUpdaterService.addTableUpdater(out);
    }

    public void onReceive(Object message) throws Exception {
        out.tell(message, self());
    }
}
