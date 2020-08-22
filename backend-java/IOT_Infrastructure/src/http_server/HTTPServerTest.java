package http_server;

import java.io.IOException;
import java.util.concurrent.Executors;

import singelton_command_factory.SingletonCommandFactory;
import singelton_command_factory.commands.CR;
import singelton_command_factory.commands.EUR;
import singelton_command_factory.commands.IOTUpdate;
import singelton_command_factory.commands.PR;

public class HTTPServerTest {
	public static void main(String[] args) throws IOException {
		SingletonCommandFactory factory = SingletonCommandFactory.getInstance();		
		factory.add("CR", new CR());
		factory.add("PR", new PR());
		factory.add("EUR", new EUR());
		factory.add("IOTUpdate", new IOTUpdate());
		
		HttpServerIOT server = new HttpServerIOT("localhost", 7777, Executors.newFixedThreadPool(10));
		server.startServer();
	}
}

