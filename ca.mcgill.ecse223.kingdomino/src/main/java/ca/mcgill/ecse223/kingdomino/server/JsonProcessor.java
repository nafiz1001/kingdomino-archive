package ca.mcgill.ecse223.kingdomino.server;

import java.util.Map;

public interface JsonProcessor {
    public Map<String, Object> process(Map<String, Object> json);
}
