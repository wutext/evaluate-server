package com.litsoft.evaluateserver.api;


import com.litsoft.evaluateserver.entity.UserScore;
import com.litsoft.evaluateserver.entity.sysVo.ScoreView;
import com.litsoft.evaluateserver.entity.vo.UserScoreVo;
import com.litsoft.evaluateserver.service.PageQueryService;
import com.litsoft.evaluateserver.service.UserScoreService;
import com.litsoft.evaluateserver.util.LayUiData;
import com.litsoft.evaluateserver.util.PageInfo;
import com.litsoft.evaluateserver.util.QueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/visit")
public class VisitorController {
    @Autowired
    private UserScoreService userScoreService;

    //项目进度占比
    @Value("${userScore.progress-completion-proportion}")
    private String progressCompletionProportion;

    //工作量占比
    @Value("${userScore.workload-proportion}")
    private String workloadProportion;

    //工作质量占比
    @Value("${userScore.work-quality-proportion}")
    private String workQualityProportion;

    //工作效率占比
    @Value("${userScore.work-efficiency-proportion}")
    private String workEfficiencyProportion;

    //工作态度占比
    @Value("${userScore.working-attitude-proportion}")
    private String workingAttitudeProportion;

    //出勤率占比
    @Value("${userScore.attendance-proportion}")
    private String attendanceProportion;

    //进度偏差占比
    @Value("${userScore.progress-deviation-proportion}")
    private String progressDeviationProportion;

    //工作配合情况占比
    @Value("${userScore.work-cooperate-proportion}")
    private String workCooperateProportion;

    @Autowired
    private PageQueryService pageQueryService;

    //员工考核页面
    @RequestMapping("/research")
    public String visit(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        model.addAttribute("userName", name);
        model.addAttribute("type", role);
        return "/view/research/research";
    }

    //考核数据保存
    @RequestMapping("/save")
    public String save(UserScore userScore, HttpServletRequest request, UserScoreVo userScoreVo) {
        //进度完成情况
        String progressCompletionScoreString = userScoreVo.getProgressCompletionScoreString();
        Integer progressCompletionScore = getScore(progressCompletionScoreString, progressCompletionProportion);
        userScore.setProgressCompletionScore(progressCompletionScore);
        //工作量
        String workloadScoreString = userScoreVo.getWorkloadScoreString();
        Integer workloadScore = getScore(workloadScoreString, workloadProportion);
        userScore.setWorkloadScore(workloadScore);
        //工作质量
        String workQualityScoreString = userScoreVo.getWorkQualityScoreString();
        Integer workQualityScore = getScore(workQualityScoreString, workQualityProportion);
        userScore.setWorkQualityScore(workQualityScore);
        //工作效率
        String workEfficiencyScoreString = userScoreVo.getWorkEfficiencyScoreString();
        Integer workEfficiencyScore = getScore(workEfficiencyScoreString, workEfficiencyProportion);
        userScore.setWorkEfficiencyScore(workEfficiencyScore);
        //工作态度
        String workingAttitudeScoreString = userScoreVo.getWorkingAttitudeScoreString();
        Integer workingAttitudeScore = getScore(workingAttitudeScoreString, workingAttitudeProportion);
        userScore.setWorkingAttitudeScore(workingAttitudeScore);
        //出勤率
        String attendanceScoreString = userScoreVo.getAttendanceScoreString();
        Integer attendanceScore = getScore(attendanceScoreString, attendanceProportion);
        userScore.setAttendanceScore(attendanceScore);
        //进度偏差
        String progressDeviationScoreString = userScoreVo.getProgressDeviationScoreString();
        Integer progressDeviationScore = getScore(progressDeviationScoreString, progressDeviationProportion);
        userScore.setProgressDeviationScore(progressDeviationScore);
        //配合工作情况
        String workCooperateScoreString = userScoreVo.getWorkCooperateScoreString();
        Integer workCooperateScore = getScore(workCooperateScoreString, workCooperateProportion);
        userScore.setWorkCooperateScore(workCooperateScore);

        //总计得分
        Integer totalScore = progressCompletionScore + workloadScore + workQualityScore + workEfficiencyScore
            + workingAttitudeScore + attendanceScore + progressDeviationScore + workCooperateScore;
        userScore.setTotal(totalScore);
        String create = request.getParameter("create");
        if (StringUtils.isNotEmpty(create)) {
            try {
                userScore.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").parse(create));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        userScoreService.save(userScore);
        return "/view/research/success";
    }

    //根据分数值和占比，计算得分
    private Integer getScore(String score, String ratio) {
        Integer result = 0;
        if (StringUtils.isNotEmpty(score) && StringUtils.isNotEmpty(ratio)) {
            result = Integer.parseInt(score) * Integer.parseInt(ratio) / 100;
        }
        return result;
    }

    //员工得分页面
    @RequestMapping("/userScoreView")
    public String getUserScoreView(Model model, String time, String username) {
        model.addAttribute("username", username);
        model.addAttribute("time", time);
        return "/view/front/userScore-list";
    }


    //员工得分数据
    @ResponseBody
    @RequestMapping("/userScoreList")
    public LayUiData getUserScoreList(@RequestParam Map<String, Object> params) {
        QueryParam param = new QueryParam(params);
        List<ScoreView> list = userScoreService.getUserScore(param);
        PageInfo<UserScore> pageInfo = new PageInfo((int) list.size(), param.getPage(),
            param.getLimit(), list);
        return LayUiData.data(pageInfo.getTotalSize(), pageInfo.getPageList());
    }


    //员工得分详情
    @RequestMapping("/userScoreDetail")
    public String getUserScoreDetail(String userName, String time, Model model) {
        if (StringUtils.isEmpty(userName)) {
            return "用户名不能为空";
        }
        if (StringUtils.isEmpty(time)) {
            return "查询时间不能为空";
        }
        List<UserScore> list = userScoreService.getUserScoreDetail(userName, time);
        model.addAttribute("list", list);
        return "/view/front/userScore-detail";
    }


}
