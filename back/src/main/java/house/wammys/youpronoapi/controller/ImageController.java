package house.wammys.youpronoapi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final Path basePath;

    public ImageController(@Value("${images.storage-path:./images}") String storagePath) {
        this.basePath = Paths.get(storagePath).toAbsolutePath().normalize();
    }

    @GetMapping("/**")
    public ResponseEntity<?> getImage(HttpServletRequest request) throws IOException {
        // Extract everything after /api/images/
        String prefix = request.getContextPath() + "/api/images/";
        String rawPath = request.getRequestURI().substring(prefix.length());

        // If it is an absolute external URL (stored by the import services), redirect to it
        if (rawPath.startsWith("http://") || rawPath.startsWith("https://")) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(rawPath))
                    .build();
        }

        // Otherwise serve the local file
        Path imagePath = basePath.resolve(rawPath).normalize();
        // Prevent path traversal
        if (!imagePath.startsWith(basePath)) {
            return ResponseEntity.badRequest().build();
        }
        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }
        String contentType = Files.probeContentType(imagePath);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        InputStream stream = Files.newInputStream(imagePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(new InputStreamResource(stream));
    }
}
