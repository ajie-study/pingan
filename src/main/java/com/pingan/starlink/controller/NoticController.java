package com.pingan.starlink.controller;

import com.pingan.starlink.model.Notic;
import com.pingan.starlink.service.NoticDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/eirene/notic")
@CrossOrigin
public class NoticController {

    @Autowired
    private NoticDBService noticService;

    @RequestMapping(value = "/notics", method = RequestMethod.GET)
    @ResponseBody
    public List<Notic> getAll() {
        return noticService.getAll();
    }

    @RequestMapping(value = "/notics", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap save(@RequestBody Notic notic) throws Exception {
        ModelMap result = new ModelMap();
        notic = noticService.createNotic(notic);
        result.put("notic", notic);
        result.put("msg", "新增成功");

        return result;
    }

    @RequestMapping(value = "/notics/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public Notic getNotic(@PathVariable String uuid) throws Exception {
        Notic notic = noticService.getByUUID(uuid);
        return notic;
    }

    @RequestMapping(value = "/notics", method = RequestMethod.PUT)
    @ResponseBody
    public ModelMap getNotic(@RequestBody Notic notic) throws Exception {
        ModelMap result = new ModelMap();
        notic = noticService.updateNotic(notic);
        result.put("notic", notic);
        result.put("msg", "更新成功");

        return result;
    }

    @RequestMapping(value = "/notics/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelMap delete(@PathVariable String uuid) {
        ModelMap result = new ModelMap();
        int res = noticService.deleteByUUID(uuid);
        result.put("msg", "删除成功!");
        result.put("result", res);
        return result;
    }

    @RequestMapping(value = "/notics_latest", method = RequestMethod.GET)
    @ResponseBody
    public List<Notic> findLatest(@RequestParam(value = "num", required = false, defaultValue = "5") Integer num) throws Exception {
        return noticService.findLatest(num);
    }


}
