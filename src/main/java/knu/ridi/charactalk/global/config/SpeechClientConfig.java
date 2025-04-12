package knu.ridi.charactalk.global.config;


import com.google.cloud.speech.v2.SpeechClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpeechClientConfig {

    @Bean
    public SpeechClient speechClient() throws Exception {
        return SpeechClient.create();
    }
}
