package knu.ridi.charactalk.chat.supporter;

import com.google.cloud.speech.v2.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpeechToTextConverter {

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

    public String convert(final ByteArrayOutputStream buffer) {
        if (buffer == null) {
            throw new IllegalStateException("⚠️ STT 세션이 초기화되지 않았습니다.");
        }

        byte[] audioData = buffer.toByteArray();

        final RecognizeRequest request = RecognizeRequest.newBuilder()
            .setConfig(recognitionConfig)
            .setRecognizer(recognizerName)
            .setContent(ByteString.copyFrom(audioData))
            .build();

        final RecognizeResponse response = speechClient.recognize(request);

        return response.getResultsList().stream()
            .filter(result -> !result.getAlternativesList().isEmpty())
            .map(result -> result.getAlternatives(0).getTranscript())
            .collect(Collectors.joining());
    }
}