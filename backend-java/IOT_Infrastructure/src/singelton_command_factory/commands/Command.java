package singelton_command_factory.commands;

public interface Command<T> {
    int execute(T data);
}