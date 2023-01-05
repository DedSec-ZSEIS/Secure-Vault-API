package net.ckmk.api;

import net.ckmk.api.handlers.CommandHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		Thread commands = new Thread(new Runnable() {
			@Override
			public void run() {
				CommandHandler h = new CommandHandler();
				Scanner scan = new Scanner(System.in);
				String currCmd;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				while (true){
					currCmd = scan.next();
					h.handleCommand(currCmd);
				}
			}
		});
		commands.start();
	}

}
