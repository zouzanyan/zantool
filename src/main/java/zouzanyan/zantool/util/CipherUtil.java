package zouzanyan.zantool.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.io.CipherOutputStream;
import org.bouncycastle.crypto.params.KeyParameter;

import zouzanyan.zantool.service.ProgressCallback;

import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;

public class CipherUtil {
//	public static void main(String[] args) {
//        try {
//            String inputFile = "test.mp4";
//            String encryptedFile = "test_encrypted.txt";
//            String decryptedFile = "test_decrypted.txt";
//            String password = "mySecretKey12345";
//
//            // Encrypt the file
//            encryptFile(inputFile, encryptedFile, password);
//
//            // Decrypt the file
//            decryptFile(encryptedFile, decryptedFile, password);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void encryptFile(String inputFile, String outputFile, String password, ProgressCallback progressCallback) throws Exception {
    	File file = new File(inputFile);
    	String filename = file.getName();
        byte[] keyBytes = password.getBytes();

        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new AESEngine());
        CipherParameters params = new KeyParameter(keyBytes);
        cipher.init(true, params);

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(inputFile));
             OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile + File.separator+ filename + ".zouzanyan"));
             CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {
        	
            long processedBytes = 0;
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, bytesRead);
                processedBytes += bytesRead;
                progressCallback.progressUpdate(processedBytes);
//                double progress = (double) processedBytes / totalBytes * 100;
//                System.out.printf("Encryption progress: %.2f%%\n", progress);
            }
        }
    }

    public static void decryptFile(String inputFile, String outputFile, String password, ProgressCallback progressCallback) throws Exception {
    	File file = new File(inputFile);
    	String filename = file.getName();
        byte[] keyBytes = password.getBytes();

        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new AESEngine());
        CipherParameters params = new KeyParameter(keyBytes);
        cipher.init(false, params);

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(inputFile));
             OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile + File.separator + filename.substring(0,filename.length() - 10)));
             CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher)) {
            long processedBytes = 0;
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) >= 0) {
                outputStream.write(buffer, 0, bytesRead);
                processedBytes += bytesRead;
                progressCallback.progressUpdate(processedBytes);
            }
        }
    }
}
