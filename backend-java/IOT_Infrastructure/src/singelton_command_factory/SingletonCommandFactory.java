package singelton_command_factory;

import singelton_command_factory.commands.Command;

public class SingletonCommandFactory {
    private FactoryPattern<Command<Object>,String,Void> factory = new FactoryPattern<>();

    private SingletonCommandFactory() {}
    
    private static class SingletonInstance {
        private static final SingletonCommandFactory instance = new SingletonCommandFactory();
    }
    
    public static SingletonCommandFactory getInstance() {
        return SingletonInstance.instance;
    }
    
    public void add(String key, Command<Object> method) {
    	factory.add(key, (Void) -> {return method;});
    }
   
    public Command<Object> create(String key) throws IllegalArgumentException{
    	return factory.create(key, null);
    }
}

