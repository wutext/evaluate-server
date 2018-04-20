package com.litsoft.evaluateserver.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.litsoft.evaluateserver.entity.Batch;
import com.litsoft.evaluateserver.entity.DepartUtil;
import com.litsoft.evaluateserver.entity.Department;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.basic.BasicAttribute;
import com.litsoft.evaluateserver.entity.sysVo.RoleVo;
import com.litsoft.evaluateserver.entity.vo.DepartmentVo;
import com.litsoft.evaluateserver.service.BatchService;
import com.litsoft.evaluateserver.service.DepartmentService;
import com.litsoft.evaluateserver.service.DepartmentUtilService;
import com.litsoft.evaluateserver.service.PageQueryService;
import com.litsoft.evaluateserver.util.LayUiData;
import com.litsoft.evaluateserver.util.PageInfo;
import com.litsoft.evaluateserver.util.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @Autowired
    private PageQueryService pageQueryService;

    @RequestMapping("/batchView")
    public String findBatchList(Model model, String batchNumber) {

        model.addAttribute("batchNumber", batchNumber);
        return "/view/front/batch-list";
    }

    @ResponseBody
    /*@RequiresPermissions("sys:menu")*/
    @RequestMapping("/batchList")
    public LayUiData adminList(@RequestParam Map<String, Object> params) {
        QueryParam param = new QueryParam(params);
        Page<Batch> pageBatch = pageQueryService.findBatchPageSearch(param);
        List<Batch> batchList = new ArrayList<>();
        pageBatch.getContent().forEach(batch -> {
            Batch batchBean = new Batch(batch.getId(), batch.getBatchNumber());
            batchList.add(batchBean);
        });
        PageInfo<Batch> pageInfo = new PageInfo((int) pageBatch.getTotalElements(), param.getPage(),
            param.getLimit(), batchList);
        return LayUiData.data(pageInfo.getTotalSize(), pageInfo.getPageList());
    }


    @RequestMapping("/addBatchView")
    public String batchAdd() {

        return "/view/front/batch-add";
    }

    @ResponseBody
    @RequestMapping("/addBatchDo")
    public String addBatchDo(@RequestBody Batch batch) {

        Batch backBatch = batchService.save(batch);
        return !ObjectUtils.isEmpty(backBatch)? "success" : "fail";
    }

    @RequestMapping("/editBatchView")
    public String editBatchDo(Model model, @RequestParam Integer id) {

        Batch batch = batchService.findOne(id);
        model.addAttribute("batch", batch);
        return "/view/front/batch-add";
    }

    @ResponseBody
    @RequestMapping("deleteBatchByIds")
    public String deleteSingleBatch(@RequestParam List<Integer> ids) {
        return batchService.deleteBatchByIds(ids)? "success" : "fail";
    }

    @ResponseBody
    @RequestMapping("deleteSingleBatch")
    public String deleteSingleBatch(@RequestBody Batch batch) {
        return batchService.deleteBatch(batch.getId())? "success" : "fail";
    }
}
