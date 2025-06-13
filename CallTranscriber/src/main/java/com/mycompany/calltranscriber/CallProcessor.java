package com.mycompany.calltranscriber;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CallProcessor {
    public static void main(String[] args) {
        try (
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/asterisk", "asterisk", "123");
            Statement stmt = conn.createStatement();
        ) {
            System.out.println("Starting...");

            while (true) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM calllogs WHERE IsCallTransferred = FALSE");

                List<Integer> processedCallIds = new ArrayList<>();

                while (rs.next()) {
                    int callId = rs.getInt("CallID");
                    String path = rs.getString("Path_WAV_File");

                    System.out.println("🎧 Processing  CallID: " + callId);

                   
                    File originalFile = new File(path);
                    File wavFile = AudioConverter.convertToWav(originalFile);

                    if (!wavFile.exists()) {
                        System.out.println("❌ File not found: " + path);
                        continue;
                    }

                    String transcript = AssemblyTranscriber.transcribe(wavFile);
                    System.out.println("📝 Arabic Transcript: " + transcript);

                    if (transcript != null) {
                        
                        PreparedStatement insertTranscript = conn.prepareStatement(
                            "INSERT INTO transactionlogs (CallID, Text) VALUES (?, ?)");
                        insertTranscript.setInt(1, callId);
                        insertTranscript.setString(2, transcript);
                        insertTranscript.executeUpdate();

                        
                        PreparedStatement updateFlag = conn.prepareStatement(
                            "UPDATE calllogs SET IsCallTransferred = TRUE WHERE CallID = ?");
                        updateFlag.setInt(1, callId);
                        updateFlag.executeUpdate();

                        processedCallIds.add(callId);
                    } else {
                        System.out.println(" Failed to process CallID: " + callId);
                    }
                }

                if (processedCallIds.isEmpty()) {
                    System.out.println("⏳ There are no calllogs.");
                } else {
                    System.out.println("Processing completed: " + processedCallIds);
                }

                
                Thread.sleep(5000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
