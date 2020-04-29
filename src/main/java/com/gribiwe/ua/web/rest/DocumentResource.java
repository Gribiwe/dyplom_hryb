package com.gribiwe.ua.web.rest;

import com.gribiwe.ua.domain.Department;
import com.gribiwe.ua.domain.Employee;
import com.gribiwe.ua.domain.User;
import com.gribiwe.ua.security.SecurityUtils;
import com.gribiwe.ua.service.DepartmentService;
import com.gribiwe.ua.service.DocumentService;
import com.gribiwe.ua.service.EmployeeService;
import com.gribiwe.ua.service.UserService;
import com.gribiwe.ua.service.dto.DepartmentDTO;
import com.gribiwe.ua.service.dto.EmployeeDTO;
import com.gribiwe.ua.web.rest.errors.BadRequestAlertException;
import com.gribiwe.ua.service.dto.DocumentDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing {@link com.gribiwe.ua.domain.Document}.
 */
@RestController
@RequestMapping("/api")
public class DocumentResource {

    private final Logger log = LoggerFactory.getLogger(DocumentResource.class);

    private static final String ENTITY_NAME = "document";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;
    private final EmployeeService employeeService;
    private final DocumentService documentService;
    private final DepartmentService departmentService;

    public DocumentResource(DepartmentService departmentService, EmployeeService employeeService, UserService userService, DocumentService documentService) {
        this.departmentService = departmentService;
        this.userService = userService;
        this.employeeService = employeeService;
        this.documentService = documentService;
    }

    /**
     * {@code POST  /documents} : Create a new document.
     *
     * @param documentDTO the documentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentDTO, or with status {@code 400 (Bad Request)} if the document has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documents")
    public ResponseEntity<DocumentDTO> createDocument(@RequestBody DocumentDTO documentDTO) throws URISyntaxException {
        log.debug("REST request to save Document : {}", documentDTO);
        if (documentDTO.getId() != null) {
            throw new BadRequestAlertException("A new document cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            String s = currentUserLogin.get();
            Optional<User> userWithAuthoritiesByLogin = userService.getUserWithAuthoritiesByLogin(s);
            if (userWithAuthoritiesByLogin.isPresent()) {
                User user = userWithAuthoritiesByLogin.get();
                documentDTO.setAuthor(user.getId());
            }
        }
        DocumentDTO result = documentService.save(documentDTO);
        return ResponseEntity.created(new URI("/api/documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documents} : Updates an existing document.
     *
     * @param documentDTO the documentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentDTO,
     * or with status {@code 400 (Bad Request)} if the documentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documents")
    public ResponseEntity<DocumentDTO> updateDocument(@RequestBody DocumentDTO documentDTO) throws URISyntaxException {
        log.debug("REST request to update Document : {}", documentDTO);
        if (documentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentDTO result = documentService.save(documentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /documents} : get all the documents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documents in body.
     */
    @GetMapping("/documents")
    public ResponseEntity<List<DocumentDTO>> getAllDocuments(Pageable pageable) {
        log.debug("REST request to get a page of Documents");
        List<DocumentDTO> page = documentService.findAll(pageable)
            .filter(document -> {
                if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
                    return true;
                }

                Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
                if (currentUserLogin.isPresent()) {
                    String s = currentUserLogin.get();
                    Optional<User> userWithAuthoritiesByLogin = userService.getUserWithAuthoritiesByLogin(s);
                    if (userWithAuthoritiesByLogin.isPresent()) {
                        User user = userWithAuthoritiesByLogin.get();
                        return isDocumentInSameCompany(document.getAuthor(), user);
                    }
                }
                return false;
            }).toList();
        return ResponseEntity.ok().body(page);
    }

    private boolean isDocumentInSameCompany(Long documentAuthor, User user) {
        Long userId = user.getId();
        if (documentAuthor.equals(userId)) return true;

        if (SecurityUtils.isCurrentUserInRole("ROLE_MANAGER")) {
            Long userDepartmentId = employeeService.findByJhiId(userId).getDepartmentId();
            Long documentDepartmentId = employeeService.findByJhiId(documentAuthor).getDepartmentId();
            boolean inTheSameDepartment = userDepartmentId.equals(documentDepartmentId);
            if (inTheSameDepartment) return true;

            if (SecurityUtils.isCurrentUserInRole("ROLE_COMPANYMANAGER")) {
                Optional<DepartmentDTO> userDepartment = departmentService.findOne(userDepartmentId);
                Optional<DepartmentDTO> documentDepartment = departmentService.findOne(documentDepartmentId);

                if (userDepartment.isPresent() && documentDepartment.isPresent()) {
                    return userDepartment.get().getCompanyId().equals(documentDepartment.get().getCompanyId()) ;
                }
            }
        }
        return false;
    }

    /**
     * {@code GET  /documents/:id} : get the "id" document.
     *
     * @param id the id of the documentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documents/{id}")
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable Long id) {
        log.debug("REST request to get Document : {}", id);
        Optional<DocumentDTO> documentDTO = documentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentDTO);
    }

    /**
     * {@code DELETE  /documents/:id} : delete the "id" document.
     *
     * @param id the id of the documentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        log.debug("REST request to delete Document : {}", id);
        documentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/documents/upload")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String s = UUID.randomUUID().toString() + "." + extension;
        File targetFile = new File("files/" + s);
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(file.getBytes());

        return ResponseEntity.ok().body(String.format("{\"UUID\": \"%s\"}", s));
    }

    @GetMapping(path = "/documents/download/{fileName}")
    public ResponseEntity download(@PathVariable String fileName) throws IOException {

        File file = new File("files/" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

    @GetMapping(path = "/documents/avatar/{login}")
    public ResponseEntity avatar(@PathVariable String login) throws IOException {

        File file = new File("avatar/" + login);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);


    }
}
