package com.jpmc.midascore;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest(
    classes = {MidasCoreApplication.class, TestKafkaConfiguration.class},
    properties = {
        "spring.config.activate.on-profile=test",
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.kafka.bootstrap-servers=localhost:9092",
        "spring.kafka.consumer.bootstrap-servers=localhost:9092",
        "spring.kafka.producer.bootstrap-servers=localhost:9092",
        "spring.kafka.consumer.auto-offset-reset=earliest"

    }
)
@ActiveProfiles("test")
@DirtiesContext
@EmbeddedKafka(partitions = 1,
topics = {"test-topic"},
brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"}

)
/*@ContextConfiguration(classes = {MidasCoreApplication.class, TestKafkaConfiguration.class})
@TestPropertySource(locations ={
    "classpath:application-test.properties",
    "classpath:application.properties"
},
properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop"
}
)*/
public class TaskFourTests {
    static final Logger logger = LoggerFactory.getLogger(TaskFourTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    public void setup() {
        // Ensure the topic is created
        kafkaTemplate.setDefaultTopic("test-topic");
    }
    

    @Test
    void task_four_verifier() throws InterruptedException {
        try{
        // Send a test message
        kafkaTemplate.sendDefault("test-key", "test-message").get(5, TimeUnit.SECONDS);
        }
        catch (ExecutionException | TimeoutException e){
            e.printStackTrace(); // Log or handle the exception

        }
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/alskdjfh.fhdjsk");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));


        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to find out what wilbur's balance is after all transactions are processed");
        logger.info("kill this test once you find the answer");
        while (true) {
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            logger.info("...");
        }

      
    }
}
