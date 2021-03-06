package com.tmf.Controller;

import com.tmf.Models.Patch;
import com.tmf.Repositories.PatchRepository;
import io.swagger.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/patchwork")
public class PatchController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PatchController.class);
    private final PatchRepository patchRepository;

    public PatchController(PatchRepository patchRepository) {
        this.patchRepository = patchRepository;
    }

    @CrossOrigin(origins = "https://patchwork-friends.herokuapp.com")
    @PostMapping("patch")
    @ResponseStatus(HttpStatus.CREATED)
    public Patch postPatch(@RequestBody Patch patch){
        return patchRepository.save(patch);
    }

    @CrossOrigin(origins = "https://patchwork-friends.herokuapp.com")
    @GetMapping("patches")
    public List<Patch> getPatches() {
        return patchRepository.findAll();
    }

    @CrossOrigin(origins = "https://patchwork-friends.herokuapp.com")
    @GetMapping("patch/{id}")
    public ResponseEntity<Patch> getPatch(@PathVariable String id) {
        Patch patch = patchRepository.findOne(id);

        if (patch == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(patch);
    }

    @CrossOrigin(origins = "https://patchwork-friends.herokuapp.com")
    @GetMapping("patches/{ids}")
    public List<Patch> getPatches(@PathVariable String ids){
        List<String> listIds = asList(ids.split(","));
        return patchRepository.findAll(listIds);
    }

    @CrossOrigin(origins = "https://patchwork-friends.herokuapp.com")
    @GetMapping("patches/count")
    public Long getCount() {
        return patchRepository.count();
    }

    @CrossOrigin(origins = "https://patchwork-friends.herokuapp.com")
    @DeleteMapping("patch/{id}")
    public Long deletePatch(@PathVariable String id){
        return patchRepository.delete(id);
    }

    @CrossOrigin(origins = "https://patchwork-friends.herokuapp.com")
    @DeleteMapping("patches/{ids}")
    public Long deletePatches(@PathVariable String ids){
        List<String> listIds = asList(ids.split(","));
        return patchRepository.delete(listIds);
    }

    @CrossOrigin(origins = "https://patchwork-friends.herokuapp.com")
    @DeleteMapping("patches")
    public Long deletePatches() {
        return patchRepository.deleteAll();
    }

    @CrossOrigin(origins = "https://patchwork-friends.herokuapp.com")
    @PutMapping("patch/{id}")
    public Patch putPatch(@PathVariable String id, @RequestBody Patch patch) {
        return patchRepository.update(id, patch);
    }

    @CrossOrigin(origins = "https://patchwork-friends.herokuapp.com")
    @PutMapping("patches")
    public Long putPatch(@RequestBody List<Patch> patches) {
        return patchRepository.update(patches);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e){
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
