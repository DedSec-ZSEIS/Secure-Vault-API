package net.ckmk.api;

import net.ckmk.api.handlers.CommandHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		Thread cmds = new Thread(new Runnable() {
			@Override
			public void run() {
				CommandHandler h = new CommandHandler();
				Scanner scan = new Scanner(System.in);
				String currCmd;

				while (true){
					System.out.print("> \n");
					currCmd = scan.next();
					h.handleCommand(currCmd);
				}
			}
		});
		cmds.start();
	}

}
