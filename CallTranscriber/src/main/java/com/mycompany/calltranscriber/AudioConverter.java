package com.mycompany.calltranscriber;

import java.io.File;
import java.io.IOException;

public class AudioConverter {
    public static File convertToWav(File inputFile) throws IOException, InterruptedException {
        String inputPath = inputFile.getAbsolutePath();
        if (inputPath.toLowerCase().endsWith(".wav")) {
            return inputFile; // لا حاجة للتحويل
        }

        String outputPath = inputPath.substring(0, inputPath.lastIndexOf('.')) + "_converted.wav";
        File outputFile = new File(outputPath);

        ProcessBuilder pb = new ProcessBuilder(
            "ffmpeg", "-y", "-i", inputPath, "-ar", "16000", "-ac", "1", outputFile.getAbsolutePath());
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("cant convert to wav");
        }

        return outputFile;
    }
}
