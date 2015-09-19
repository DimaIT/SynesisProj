package model.services;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.HashSet;
import java.util.Set;

public class ResponseUpdaterService extends UntypedActor {

    private static Set<ActorRef> menuUpdaters = new HashSet<>();

    public static void updateAll(long tmp) {
        for (ActorRef updater : menuUpdaters) {
            if (updater.isTerminated()) menuUpdaters.remove(updater);
            updater.tell(String.valueOf(tmp), ActorRef.noSender());
        }
    }

    public static void updateAll() {
        updateAll(ResponseService.countRecords());
    }

    private final ActorRef out;

    public static Props props(ActorRef out) {
        return Props.create(ResponseUpdaterService.class, out);
    }

    public ResponseUpdaterService(ActorRef out) {
        out.tell(String.valueOf(ResponseService.recordsCount), ActorRef.noSender());
        this.out = out;
        menuUpdaters.add(out);
    }

    public void onReceive(Object message) throws Exception {
        out.tell(message.toString(), self());
    }
}
