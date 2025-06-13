package com.mycompany.calltranscriber;

import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.nio.file.Files;
import org.json.JSONObject;

public class AssemblyTranscriber {
    private static final String API_KEY = "ff013e2900324574850dea0bfaf50cd6";  

    public static String transcribe(File audioFile) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // 1️⃣ رفع الملف
        HttpRequest uploadRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.assemblyai.com/v2/upload"))
                .header("Authorization", API_KEY)
                .POST(HttpRequest.BodyPublishers.ofFile(audioFile.toPath()))
                .build();

        HttpResponse<String> uploadResponse = client.send(uploadRequest, HttpResponse.BodyHandlers.ofString());

        String uploadUrl = new JSONObject(uploadResponse.body()).getString("upload_url");

        // 2️⃣ إرسال طلب الترانسكرايب
        JSONObject requestBody = new JSONObject();
        requestBody.put("audio_url", uploadUrl);
        requestBody.put("language_code", "ar");          // ✅ اللغة العربية
        requestBody.put("speech_model", "nano");         // ✅ استخدام النموذج المناسب

        HttpRequest transcribeRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.assemblyai.com/v2/transcript"))
                .header("Authorization", API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> transcribeResponse = client.send(transcribeRequest, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonResponse = new JSONObject(transcribeResponse.body());

        if (!jsonResponse.has("id")) {
            System.out.println("no id" + jsonResponse);
            return null;
        }

        String id = jsonResponse.getString("id");

        // 3️⃣ الانتظار حتى يجهز الترانسكرايب
        String status = "";
        JSONObject pollingResponse;

        do {
            Thread.sleep(3000);  // انتظر 3 ثوانٍ بين كل طلب
            HttpRequest pollingRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.assemblyai.com/v2/transcript/" + id))
                    .header("Authorization", API_KEY)
                    .build();

            HttpResponse<String> pollingResult = client.send(pollingRequest, HttpResponse.BodyHandlers.ofString());
            pollingResponse = new JSONObject(pollingResult.body());
            status = pollingResponse.getString("status");

        } while (status.equals("queued") || status.equals("processing"));

        if (status.equals("completed")) {
            return pollingResponse.getString("text");
        } else {
            System.out.println("failed: " + pollingResponse);
            return null;
        }
    }
}
