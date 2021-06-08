package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.dataset;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

@Service
/**
 * Basic class which is base for data generation
 * every random event should be derrived from random
 *
 */
public class DataCreator {


    private final File file = ResourceUtils.getFile("classpath:samples/data.csv");
    private final Map<String, List<String>> dataMap = new HashMap<>();
    private final Random random = new Random(0L);


    public <T> T getBetween(T... ts) {
        return ts[random.nextInt(ts.length)];
    }

    public int getRandom(int from, int to) {
        return random.nextInt(to - from) + from;
    }

    private DataCreator() throws FileNotFoundException {
        init();
    }

    private void init() {
        try (BufferedReader csvReader = new BufferedReader(new FileReader(file))) {
            String row = csvReader.readLine(); // skip header line

            String[] keys = row.split(",");
            for (String elm : keys)
                dataMap.put(elm, new ArrayList<>());
            while ((row = csvReader.readLine()) != null) {

                List<String> data = splitRow(row);
                for (int i = 0; i < data.size(); i++) {
                    dataMap.get(keys[i]).add(data.get(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> splitRow(String row) {
        List<String> result = new ArrayList<>();
        StringBuilder elm = new StringBuilder();
        boolean isInQuotes = false;
        for (char c : row.toCharArray()) {
            if (c == ',' && !isInQuotes) {
                result.add(elm.toString());
                elm = new StringBuilder();
            } else if (c == '"') {
                isInQuotes = !isInQuotes;
            } else {
                elm.append(c);
            }
        }
        result.add(elm.toString());

        return result;
    }

    public String getData(DataTypes type) {
        List<String> selected = this.dataMap.get(type.getName());
        return selected.get(random.nextInt(selected.size()));
    }

}
