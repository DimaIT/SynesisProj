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

/**
 * Web socket service
 * updates header response count & response table
 */
public class ResponseUpdaterService extends UntypedActor {

    /**
     * Actor's refs
     */
    private static Set<ActorRef> menuUpdaters = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static Set<ActorRef> tableUpdaters = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * Updates headers
     * @param tmp new value
     */
    private static void updateMenu(long tmp) {
        for (ActorRef updater : menuUpdaters) {
            if (updater.isTerminated()) menuUpdaters.remove(updater);
            updater.tell(String.valueOf(tmp), ActorRef.noSender());
        }
    }

    /**
     * Updates tables with new records or deletes them by id
     * @param node json node
     */
    private static void updateTable(ObjectNode node) {
        for (ActorRef updater : tableUpdaters) {
            if (updater.isTerminated()) tableUpdaters.remove(updater);
            updater.tell(node, ActorRef.noSender());
        }
    }

    /**
     * updates with new record
     */
    public static void updateAll(Record record) {
        updateMenu(ResponseService.countRecords());
        updateTable(create(record));
    }

    /**
     * updates with deleted record
     * @param id of record
     */
    public static void updateAll(long id) {
        updateMenu(ResponseService.countRecords());
        updateTable(create(id));
    }

    /**
     * creates json node for row deletion
     * @param id of record
     * @return node
     */
    public static ObjectNode create(long id) {
        ObjectNode event = Json.newObject();
        event.put("action", "delete");
        event.put("id", id);

        return event;
    }

    /**
     * creates node with new record
     * @return node
     */
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

    //header
    public static Props menuProps(ActorRef out) {
        return Props.create(ResponseUpdaterService.class, out);
    }

    //table
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
