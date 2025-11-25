package net.adam85w.openapi.openapidiff.web;

import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import org.openapitools.openapidiff.core.output.MarkdownRender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
@RequestMapping("/")
class OpenApiDiffController {

    private final OpenApiDiffService service;

    OpenApiDiffController(OpenApiDiffService service) {
        this.service = service;
    }

    @GetMapping
    String index(Model model) {
        model.addAttribute("process", false);
        return "index";
    }

    @PostMapping
    String process(Model model, @RequestParam(name = "current_version") MultipartFile currentVersion, @RequestParam(name = "new_version") MultipartFile newVersion) {
        try {
            var changedOpenApi = service.diff(currentVersion, newVersion);
            model.addAttribute("process", true);
            model.addAttribute("error", false);
            model.addAttribute("isDifferent", changedOpenApi.isDifferent());
            model.addAttribute("isCompatible", changedOpenApi.isCompatible());
            model.addAttribute("output", renderConsoleOutput(changedOpenApi));
            model.addAttribute("markdown", renderMarkdownOutput(changedOpenApi));
        } catch (InvalidOpenAPIException e) {
            model.addAttribute("process", true);
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "index";
    }

    private String renderConsoleOutput(ChangedOpenApi changedOpenApi) {
        var consoleRender = new ConsoleRender();
        var consoleOutput = new ByteArrayOutputStream();
        consoleRender.render(changedOpenApi, new OutputStreamWriter(consoleOutput));
        return consoleOutput.toString();
    }

    private String renderMarkdownOutput(ChangedOpenApi changedOpenApi) {
        var markdownRender = new MarkdownRender();
        var markdownOutput = new ByteArrayOutputStream();
        markdownRender.render(changedOpenApi, new OutputStreamWriter(markdownOutput));
        return markdownOutput.toString();
    }
}
