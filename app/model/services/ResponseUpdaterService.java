package model.services;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Cell;
import model.Record;
import play.i18n.Messages;
import play.libs.Json;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseUpdaterService extends UntypedActor {

    private static Set<ActorRef> menuUpdaters = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static Set<ActorRef> tableUpdaters = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private static void updateMenu(long tmp) {
        for (ActorRef updater : menuUpdaters) {
            if (updater.isTerminated()) menuUpdaters.remove(updater);
            updater.tell(String.valueOf(tmp), ActorRef.noSender());
        }
    }

    private static void updateTable(ObjectNode node) {
        for (ActorRef updater : tableUpdaters) {
            if (updater.isTerminated()) tableUpdaters.remove(updater);
            updater.tell(node, ActorRef.noSender());
        }
    }

    public static void updateAll(Record record) {
        updateMenu(ResponseService.countRecords());
        updateTable(create(record));
    }

    public static void updateAll(long id) {
        updateMenu(ResponseService.countRecords());
        updateTable(create(id));
    }

    public static ObjectNode create(long id) {
        ObjectNode event = Json.newObject();
        event.put("action", "delete");
        event.put("id", id);

        return event;
    }

    public static ObjectNode create(Record record) {
        ObjectNode event = Json.newObject();
        event.put("action", "insert");
        event.put("id", record.getId());

        for (Cell cell : record.getCells()) {
            event.put(cell.getField().getLabel().replace(' ', '_'), cell.getValue() == null
                    ? Messages.get("not.available")
                    : cell.getValue());
        }

        return event;
    }


    private final ActorRef out;

    public static Props menuProps(ActorRef out) {
        return Props.create(ResponseUpdaterService.class, out);
    }

    public static Props tableProps(ActorRef out) {
        return Props.create(ResponseUpdaterService.class, out, true);
    }

    public ResponseUpdaterService(ActorRef out) {
        out.tell(String.valueOf(ResponseService.countRecords()), ActorRef.noSender());
        this.out = out;
        menuUpdaters.add(out);
    }

    public ResponseUpdaterService(ActorRef out, boolean table) {
        this.out = out;
        tableUpdaters.add(out);
    }

    public void onReceive(Object message) throws Exception {
        out.tell(message, self());
    }

}
