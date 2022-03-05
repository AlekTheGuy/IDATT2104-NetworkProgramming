package no.ntnu.codeRunner.controllers;

import no.ntnu.codeRunner.models.Code;
import no.ntnu.codeRunner.services.DockerCompile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
public class DockerCompileController {

    @Autowired
    DockerCompile dockerCompiler;

    @PostMapping("/compile")
    public String postCode(@RequestBody Code inputCode) throws IOException, InterruptedException {
        String result = dockerCompiler.compileCode(inputCode.getCode());
        return result;
    }
    
}
