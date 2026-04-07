package ru.aston.hometask.listener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "user-events")
@TestPropertySource(properties = {
        "spring.kafka.consumer.group-id=test-group",
        "spring.kafka.consumer.auto-offset-reset=earliest",
        "eureka.client.enabled=false",
        "spring.cloud.config.import-check.enabled=false"
})
public class UserEventListenerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockitoBean
    private JavaMailSender mailSender;

    @Test
    void when_createEventReceived_then_emailIsSent() throws Exception {
        String payload = """
                {"email":"john@mail.com","event":"CREATE"}
                """;

        kafkaTemplate.send("user-events", payload).get();

        ArgumentCaptor<SimpleMailMessage> captor =
                ArgumentCaptor.forClass(SimpleMailMessage.class);

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() ->
                verify(mailSender, times(1)).send(captor.capture())
        );

        SimpleMailMessage sent = captor.getValue();
        assertThat(sent.getTo()).containsExactly("john@mail.com");
        assertThat(sent.getSubject()).isEqualTo("Добро пожаловать!");
    }

    @Test
    void whenDeleteEventReceived_thenDeleteEmailIsSent() throws Exception {
        String payload = """
                {"email":"john@mail.com","event":"DELETE"}
                """;

        kafkaTemplate.send("user-events", payload).get();

        ArgumentCaptor<SimpleMailMessage> captor =
                ArgumentCaptor.forClass(SimpleMailMessage.class);

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() ->
                verify(mailSender, times(1)).send(captor.capture())
        );

        SimpleMailMessage sent = captor.getValue();
        assertThat(sent.getTo()).containsExactly("john@mail.com");
        assertThat(sent.getSubject()).isEqualTo("Удаление аккаунта!");
    }
}
