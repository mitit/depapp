package group.depapp;


import group.depapp.util.Synchronizer;
import group.depapp.util.XMLHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import static java.lang.System.exit;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
@EntityScan("group.depapp")
public class DepappApplication implements CommandLineRunner {

    @Autowired
    Synchronizer synchronizer;
    @Autowired
    XMLHandler xmlHandler;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DepappApplication.class);

        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (args.length > 0) {
            String command = args[0];
            switch (command) {
                case "create":
                    xmlHandler.create();
                    break;
                case "sync":
                    synchronizer.synchronize();
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("Не введена команда");
        }

        exit(0);
    }
}