package de.hdbw.webshop.model.artwork;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class CustomMultipartFile implements MultipartFile {

    private final byte[] fileContent;

    private String fileName;

    private String contentType;

    private File file;

    private String destPath = System.getProperty("java.io.tmpdir");

    private String imageBase64;

    private FileOutputStream fileOutputStream;

    public CustomMultipartFile(byte[] fileData, String name, String contentType) {
        this.fileContent = fileData;
        this.fileName = name;
        this.contentType = contentType;
        file = new File(destPath + fileName);
        imageBase64 = Base64.getEncoder().encodeToString(fileContent);
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        fileOutputStream = new FileOutputStream(dest);
        fileOutputStream.write(fileContent);
    }

    public void clearOutStreams() throws IOException {
        if (null != fileOutputStream) {
            fileOutputStream.flush();
            fileOutputStream.close();
            file.deleteOnExit();
        }
    }
}