package com.key4dream.sun;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.key4dream.sun.dao.MaterialMapper;
import com.key4dream.sun.entity.Material;

public class MaterialDAOTest extends BaseTest {

    @Autowired
    private MaterialMapper mapper;

    @Test
    public void testInsert() {
        Material material = new Material();
        material.setMid(111L);
        material.setContent("test");
        //        mapper.add(material);
    }

    @Test
    public void testFind() {
        List<Material> list = mapper.findAll();
        if (list != null) {
            for (Material m : list) {
                System.out.println(m);
            }
        }
    }

}
