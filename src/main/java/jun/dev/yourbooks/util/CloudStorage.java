package jun.dev.yourbooks.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import jun.dev.yourbooks.exception.FileException;
import jun.dev.yourbooks.exception.GCPFileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class CloudStorage {

    @Value("${gcp.config.file}")
    private String gcpFileConfig;
    @Value("${gcp.project.id}")
    private String gcpProjectId;
    @Value("${gcp.bucket.id}")
    private String gcpBucketId;
    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;

    public String uploadFile(MultipartFile file){
        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new NullPointerException("File name is null");
        try{
            String contentType = file.getContentType();
            byte[] fileData = file.getBytes();
            return uploadToStorage(fileName, fileData, contentType);
        } catch (IOException e) {
            throw new GCPFileUploadException("An error occurred while storing data to GCS");
        }
    }

    private String uploadToStorage(String fileName, byte[] fileData, String contentType) throws IOException {
        InputStream inputStream = new ClassPathResource(gcpFileConfig).getInputStream();
        StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
        Storage storage = options.getService();
        Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());
        String imageName = imageName(fileName);
        Blob blob = bucket.create(gcpDirectoryName+ "/" +imageName,fileData,contentType);
        if (blob != null) {
            return storage.getOptions().getHost() + "/" +
                    blob.getBucket() + "/" +
                    blob.getName();
        }
        throw new GCPFileUploadException("An error occurred while storing data to GCS");
    }
    public boolean isImageFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Set<String> extensions = Set.of("jpg", "jpeg", "img", "png", "svg");
        return extensions.contains(extension);
    }
    public void checkBook(MultipartFile file) throws FileException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"pdf".equals(extension))
            throw new FileException("Book's extension is not correct!");
    }
    public String imageName (String fileName){
        String randomId = UUID.randomUUID().toString();
        return randomId + "." + FilenameUtils.getBaseName(fileName);
    }

    public void deleteImage(String imageName)  {
        Storage storage = StorageOptions.newBuilder().setProjectId(gcpProjectId).build().getService();
        Blob blob = storage.get(gcpBucketId, imageName);
        if (blob == null) {
            throw new RuntimeException("No object found with name "+imageName);
        }
        Storage.BlobSourceOption precondition = Storage.BlobSourceOption.generationMatch(blob.getGeneration());
        storage.delete(gcpBucketId, imageName, precondition);
    }
    public String downloadFile(String fileName) {
        Storage storage = StorageOptions.newBuilder().setProjectId(gcpProjectId).build().getService();
        Blob blob = storage.get(gcpBucketId, fileName);

        URL signedUrl = null;
        try {
            signedUrl = storage.signUrl(BlobInfo.newBuilder(gcpBucketId, fileName).build(),
                    1, TimeUnit.DAYS, Storage.SignUrlOption.signWith(ServiceAccountCredentials.fromStream(
                            new FileInputStream(gcpFileConfig))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return signedUrl.toString();

    }


}
