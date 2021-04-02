package nl.tudelft.oopp.group54.entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapLoggers {
    private Map<Integer, List<String>> map;

    private static MapLoggers instance;

    /**
     * Empty constructor.
     */
    private MapLoggers() {
        map = new HashMap<>();
    }

    /**
     * Returns the a singleton instance of the class.
     * @return a static instance
     */
    public static MapLoggers getInstance() {
        if (instance == null) {
            instance = new MapLoggers();
        }

        return instance;
    }

    /**
     * Adds a log to the list of other logs for the specific lecture.
     * @param id ID of the lecture for which the log is going to be written at
     * @param msg the content of the log message
     */
    public void logWarning(Integer id, String msg) {
        if (id == null) {
            return;
        }

        map.computeIfAbsent(id, k -> new ArrayList<>());

        List<String> temp = map.get(id);
        temp.add(msg);
        map.put(id, temp);

        if (map.get(id).size() > 10) {
            try {
                writeToFile(id);
                setMapValue(id, new ArrayList<>());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes to a file.
     * @param lectureId the lecture id
     * @throws IOException exception when writing to file
     */
    public void writeToFile(Integer lectureId) throws IOException {
        if (lectureId == null) {
            return;
        }

        FileWriter fw = new FileWriter("server/Logs/" + lectureId + ".log", true);
        BufferedWriter bw = new BufferedWriter(fw);

        String toWrite = "";

        Iterator<String> it = map.get(lectureId).iterator();
        while (it.hasNext()) {
            bw.write(it.next());
            bw.newLine();
        }

        bw.close();
    }

    /**
     * Updates the value for a specific lecture.
     * @param lectureId the lecture id
     * @param newList the new value
     */
    public void setMapValue(Integer lectureId, List<String> newList) {
        if (lectureId == null) {
            return;
        }

        this.map.put(lectureId, newList);
    }
}
