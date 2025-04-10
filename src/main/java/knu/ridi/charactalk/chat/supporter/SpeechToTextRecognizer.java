package knu.ridi.charactalk.chat.supporter;

import com.google.cloud.speech.v2.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpeechToTextRecognizer {

    @Value("${gcp.speech.recognizer}")
    private String recognizerName;

    private final SpeechClient speechClient;

    private final RecognitionConfig recognitionConfig = RecognitionConfig.newBuilder()
        .setExplicitDecodingConfig(ExplicitDecodingConfig.newBuilder()
            .setEncoding(ExplicitDecodingConfig.AudioEncoding.LINEAR16)
            .setSampleRateHertz(16000)
            .setAudioChannelCount(1)
            .build())
        .build();

    public void recognize(
        final ByteArrayOutputStream buffer,
        final Consumer<String> onTranscript
    ) {
        if (buffer == null) {
            return ;
        }

        byte[] audioData = buffer.toByteArray();

        final RecognizeRequest request = RecognizeRequest.newBuilder()
            .setConfig(recognitionConfig)
            .setRecognizer(recognizerName)
            .setContent(ByteString.copyFrom(audioData))
            .build();

        log.debug("🎤 오디오 인식 시작");

        final RecognizeResponse response = speechClient.recognize(request);

        for (final SpeechRecognitionResult result : response.getResultsList()) {
            if (!result.getAlternativesList().isEmpty()) {
                final String transcript = result.getAlternatives(0).getTranscript();
                onTranscript.accept(transcript);
            }
        }

        log.debug("🎤 오디오 인식 종료");
    }
}