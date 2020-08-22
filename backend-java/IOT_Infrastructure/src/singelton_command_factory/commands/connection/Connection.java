package singelton_command_factory.commands.connection;

public interface Connection<T> {
    public int send(T data);
}