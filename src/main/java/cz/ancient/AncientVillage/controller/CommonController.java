package cz.ancient.AncientVillage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.ancient.AncientVillage.db.entity.Empire;
import cz.ancient.AncientVillage.db.repository.EmpireRepository;
import cz.ancient.AncientVillage.model.User;

/**
 * CommonController
 *
 * @author Jan Duchac
 */
@RestController
@RequestMapping("/common")
public class CommonController {

//    @PostMapping("/logged_user")
//    public User loggedUser(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return new User("");
//    }

//    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private EmpireRepository empireRepository;

    @GetMapping("/logged_user")
    public User greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        empireRepository.findAll();
        
        return new User("aaa", name, "B");
    }

    @GetMapping("/emire_list")
    public List<Empire> getEmpireList() {
        return empireRepository.findAll();
    }
}
