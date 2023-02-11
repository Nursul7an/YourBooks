package jun.dev.yourbooks.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jun.dev.yourbooks.exception.FileException;
import jun.dev.yourbooks.exception.GCPFileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Set;
import java.util.UUID;

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
        String randomId = UUID.randomUUID().toString();
        Blob blob = bucket.create(gcpDirectoryName+ "/" +
                randomId + "." + FilenameUtils.getBaseName(fileName),fileData,contentType);
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

}
