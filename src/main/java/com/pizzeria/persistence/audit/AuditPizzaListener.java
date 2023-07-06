package com.pizzeria.persistence.audit;

import com.pizzeria.persistence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;


public class AuditPizzaListener {
    private PizzaEntity currentValue;

    @PostLoad
    public  void PostLoad(PizzaEntity entity) {
        System.out.println("POST LOAD");
        this.currentValue = SerializationUtils.clone(entity);
    }

    @PostPersist
    @PostUpdate
    public  void onPostPersist(PizzaEntity entity) {
        System.out.println("POST PERSIS OR UPDATE");
        System.out.println("OLD VALUE "+ this.currentValue /*.toString()*/);
        System.out.println("NEW VALUE "+ entity.toString());
    }

    @PreRemove
    public void onPreDelete(PizzaEntity entity) {
        System.out.println(entity.toString());
    }
}
