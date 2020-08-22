package singelton_command_factory.commands;

import java.io.IOException;
import org.json.JSONObject;

import singelton_command_factory.commands.connection.HTTPConnection;

public class EUR implements Command<Object>{
    HTTPConnection connection;
    
    public EUR() throws IOException {
        connection = new HTTPConnection("http://localhost:8080/data_tomcat_server/end_users");
    }    
    
    @Override
    public int execute(Object data) {
        return connection.send((JSONObject)data);         
    }
}