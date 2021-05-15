package cz.ancient.AncientVillage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * AncientVillageController
 *
 * @author Jan Duchac [jan.duchac@intelis.cz]
 */
@RestController
public class AncientVillageController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }
}
