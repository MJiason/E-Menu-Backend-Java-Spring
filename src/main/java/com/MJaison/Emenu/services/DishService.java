package com.MJaison.Emenu.services;

import com.MJaison.Emenu.dtos.Dish;
import com.MJaison.Emenu.repository.AwsS3Storage;
import com.MJaison.Emenu.repository.DishesRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    @Autowired
    private AwsS3Storage awsS3Storage;

    @Autowired
    private DishesRepository dishesRepository;

    public void saveDish(Dish dish, MultipartFile image) throws Exception {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + image.getName());
        image.transferTo(convFile);
        awsS3Storage.save(image.getOriginalFilename(), convFile);
        URL url = awsS3Storage.getUrl(image.getOriginalFilename());
        dish.setPhoto(url);
        dishesRepository.save(dish);
    }

    public void deleteDish(long id) throws NotFoundException {
        Optional<Dish> dish = dishesRepository.findById(id);
        if(!dish.isPresent()){
            throw new NotFoundException("Not found dish with that Id");
        }
        dishesRepository.deleteById(id);
    }

    public Dish getDishById(long id) throws NotFoundException{
        Optional<Dish> dish = dishesRepository.findById(id);
        if (!dish.isPresent()){
            throw new NotFoundException("Not found dish with that Id");
        }
        return dish.get();
    }

    public void updateDish(Dish dish) throws NotFoundException {
        Optional<Dish> tmpDish = dishesRepository.findById(dish.getId());
        if (!tmpDish.isPresent()){
            throw new NotFoundException("Not found dish for update");
        }
        dishesRepository.save(dish);
    }

    public List<Dish> getAll(){
        return dishesRepository.findAll();
    }

}
