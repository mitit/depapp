package group.depapp;

import group.depapp.service.SynchronizeService;
import group.depapp.service.XMLService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import static java.lang.System.exit;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DepappApplication implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(DepappApplication.class);

    @Autowired
    private SynchronizeService synchronizeService;

    @Autowired
    private XMLService xmlService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DepappApplication.class);

        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        xmlService.create();
        synchronizeService.synchronize();

        if (args.length > 0) {
            String command = args[0];
            switch (command) {
                case "create":
                    xmlService.create();
                    break;
                case "sync":
                    synchronizeService.synchronize();
                    break;
                default:
                    break;
            }
            log.info("APP STARTED");
        } else {
            System.out.println("Не введена команда");
            log.info("COMMAND NOT FOUND");
        }

        exit(0);
    }
}