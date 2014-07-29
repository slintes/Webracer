package net.slintes.webracer.web.impl.server.commands.server;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * base class for server side websocket commands
 */
public class AbstractServerCommand implements ServerCommand {

    private final String command;
    private Map<String, Object> data;

    public AbstractServerCommand(String command) {
        this.command = command;
    }

    protected void setData(Map<String, Object> data){
        this.data = data;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getJson(){
        JSONObject jsonCommand = new JSONObject();
        jsonCommand.put("command", command);

        if(data != null){
            JSONObject jsonData = new JSONObject();
            data.forEach(jsonData::put);
            jsonCommand.put("data", jsonData);
        }

        return jsonCommand.toJSONString();
    }

    protected Map<String, Object> getEmptyMap(){
        return new HashMap<>();
    }
}
