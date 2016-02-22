package com.key4dream.sun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.key4dream.sun.bo.QbRequest;
import com.key4dream.sun.bo.QbResponse;
import com.key4dream.sun.bo.SResponse;
import com.key4dream.sun.dao.MaterialMapper;
import com.key4dream.sun.entity.Material;

@Controller
@RequestMapping(value = "/qb")
public class QBController {

    @Autowired
    private MaterialMapper mMapper;

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    @ResponseBody
    public SResponse<List<QbResponse>> get(@RequestBody QbRequest request) {
        SResponse<List<QbResponse>> response = new SResponse<List<QbResponse>>();
        List<QbResponse> datas = new ArrayList<QbResponse>();
        Long currentId = request.getCurrentId();
        if (currentId == null || currentId <= 0) {
            currentId = mMapper.findMaxId();
        }
        Integer limit = request.getLimit();

        if (limit == null || limit == 0) {
            limit = 10;
        }

        List<Material> materilas = mMapper.find(currentId, limit);

        if (!CollectionUtils.isEmpty(materilas)) {
            for (Material m : materilas) {
                QbResponse res = new QbResponse();
                res.setArticleId(m.getId());
                res.setContent(m.getContent());
                datas.add(res);
            }
        }
        response.setData(datas);

        return response;
    }

}
