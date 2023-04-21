package com.contractManagement.controller;

import com.contractManagement.common.ResponseMessage;
import com.contractManagement.common.Result;
import com.contractManagement.common.UploadFileResponse;
import com.contractManagement.entity.Contract;
import com.contractManagement.entity.FileDB;
import com.contractManagement.service.ContractService;
import com.contractManagement.service.impl.FileStorageService;
import com.contractManagement.service.impl.FileStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 上传 下载文件
 */
@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileStorageServiceImpl storageService;
    private final FileStorageService fileStorageService;
    private final ContractService contractService;
//    private final Path fileStorageLocation;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.store(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Contract contract = contractService.findById(id);
        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(contract.getScanningCopy().getBytes());
/*        FileDB fileDB = storageService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());*/
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadFiles")
    public Result uploadFiles(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/downloadFile/").path(fileName).toUriString();
        String fileDownloadUri = "http://htupload.ziubao.com/file/downloadFile/" + fileName;
        UploadFileResponse response = new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        return Result.success("上传成功", response);
    }

/*    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file->uploadFiles(file))
                .collect(Collectors.toList());
    }*/

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * 预览pdf
     *
     * @param id
     */
    @RequestMapping("preview/{fileName:.+}")
    @ResponseBody
    public void preview(/*@PathVariable("id") Long id,*/@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        Contract contract = contractService.findById(id);
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        File file = resource.getFile();
//        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//        Resource resource = new UrlResource(filePath.toUri());
//        System.out.println("************** file.exists()：  " + file.exists());
        if (file.exists()) {
            byte[] data = null;
            FileInputStream input = null;
            try {
                input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            } catch (Exception e) {
                System.out.println("pdf文件处理异常：" + e);
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 预览pdf
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    @GetMapping(value = "previewPDF/{fileName:.+}"/*, produces = MediaType.APPLICATION_PDF_VALUE*/)
    @ResponseBody
    public ResponseEntity<Resource> previewPDF(@PathVariable String fileName) throws IOException {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        if (resource.exists() || resource.isReadable()) {
//            byte[] pdf = null;
                HttpHeaders headers = new HttpHeaders();
//            String fileName = "example.pdf";
            String contentDisposition = String.format("inline; filename=\"%s\"", resource.getFile()
                    .getName());
            headers.setContentDispositionFormData(fileName, fileName);
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set(HttpHeaders.CONTENT_DISPOSITION,contentDisposition);
            return ResponseEntity.ok().headers(headers).body(resource);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 预览pdf
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    @GetMapping("showPdf/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> showPdf(@PathVariable String fileName) throws IOException {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
//        String pdfFilesPath=resource2.getURI().getPath();
//        Resource resource = new UrlResource(Paths.get(pdfFilesPath).resolve(filename).toUri());
        if (resource.exists() || resource.isReadable()) {
            String contentDisposition = String.format("inline; filename=\"%s\"", resource.getFile()
                    .getName());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        }
        return ResponseEntity.notFound()
                .build();
    }

/*    @GetMapping(value = "/previewPDF/{fileName:.+}")
    public ResponseEntity<InputStreamResource> previewPDF2(@PathVariable String fileName) throws FileNotFoundException {
        String filePath = "http://htupload.ziubao.com/file/downloadFile/";
//        String fileName = "fileName.pdf";
        File file = new File(filePath + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }*/
}
