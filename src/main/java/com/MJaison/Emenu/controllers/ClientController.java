package com.MJaison.Emenu.controllers;

import com.MJaison.Emenu.dtos.Dish;
import com.MJaison.Emenu.repository.AwsS3Storage;
import com.MJaison.Emenu.repository.DishesRepository;
import com.MJaison.Emenu.services.DishService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.AccessControlException;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private DishesRepository dishesRepository;

    @Autowired
    private AwsS3Storage s3Storage;

    @Autowired
    private DishService dishService;


    @GetMapping("/dish/{id}")
    public ResponseEntity<Dish> getDish(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(dishesRepository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping("/image")
    public ResponseEntity<String> putImage(@RequestParam(name = "image") MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getName());
        file.transferTo(convFile);
        s3Storage.save(file.getOriginalFilename(), convFile);
        return new ResponseEntity(file.getOriginalFilename(), HttpStatus.OK);
    }

    @GetMapping("/url/{fileName}")
    public ResponseEntity<URL> testMethod(@PathVariable(name = "fileName") String fileName) {
        return new ResponseEntity<>(s3Storage.getUrl(fileName), HttpStatus.OK);
    }

    @PostMapping("/dish/new")
    public ResponseEntity saveDish(@RequestPart("dish") Dish dish, @RequestPart(name = "image") MultipartFile image) {
        try {
            dishService.saveDish(dish, image);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/dish/delete/{id}")
    public ResponseEntity<String> deleteDish(@PathVariable(name = "id") long id) {
        try {
            dishService.deleteDish(id);
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/dish/get/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable(name = "id") long id) {
        Dish dish;
        try {
            dish = dishService.getDishById(id);
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(dish, HttpStatus.OK);
    }

    @GetMapping("/dish/all")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Dish>> getAllCourses() {
        List<Dish> list = dishService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @PostMapping("/dish/update")
    public ResponseEntity update(@RequestBody Dish dish) {
        try {
            dishService.updateDish(dish);
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
