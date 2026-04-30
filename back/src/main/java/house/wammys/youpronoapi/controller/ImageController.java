package house.wammys.youpronoapi.controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
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
        // Extract everything after /api/images/ — works regardless of context path
        String requestUri = request.getRequestURI();
        String marker = "/api/images/";
        int idx = requestUri.indexOf(marker);
        String rawPath = idx >= 0 ? requestUri.substring(idx + marker.length()) : "";

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
        // FileSystemResource manages stream lifecycle; Spring closes it after writing the response
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(new FileSystemResource(imagePath));
    }
}
