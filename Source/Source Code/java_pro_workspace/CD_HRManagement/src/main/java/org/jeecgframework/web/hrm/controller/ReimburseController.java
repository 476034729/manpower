package org.jeecgframework.web.hrm.controller;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.hrm.bean.ExpenseProject;
import org.jeecgframework.web.hrm.bean.ProjectCode;
import org.jeecgframework.web.hrm.service.ReimburseService;
import org.jeecgframework.web.hrm.service.WorkHourService;
import org.jeecgframework.web.hrm.service.impl.ApplyCheckServiceImpl;
import org.jeecgframework.web.hrm.service.impl.ReimExcelService;
import org.jeecgframework.web.hrm.utils.DateUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Name;
import javax.persistence.Id;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @authortangzhen
 * @createDate2019-07-17
 */
@Controller
@RequestMapping("/reimburseController")
public class ReimburseController {
    private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(ReimburseController.class);

    @Autowired
    private ReimExcelService excelService;
    @Autowired
    private ReimburseService reimburseService;
    @Autowired
    private WorkHourService workHourService;
    @Autowired
    private SystemService systemService;

    /*
    添加报销
     */
    @RequestMapping(params = "addReimburse",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson addReimburse(@RequestBody Map<String,Object> map){
        AjaxJson json=new AjaxJson();
        try{
            reimburseService.addReimburse(map);
//            reimburseService.addReimDetails(tsReimDetailsList);
        }catch (Exception e){
            logger.error("add reimburse is error ",e);
            json.setSuccess(false);
            json.setMsg("操作失败");
        }
        return json;
    }

    /**
     * 编辑报销
     * @param map
     * @return
     */
    @RequestMapping(params = "updateReimburse",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson updateReimburse(@RequestBody Map<String,Object> map){
        AjaxJson json=new AjaxJson();
       try{
            reimburseService.updateReimburse(map);
//            reimburseService.updateReimDetails(tsReimDetailsList);
        }catch (Exception e){
            logger.error("update reimburse is error ",e);
            json.setSuccess(false);
            json.setMsg("操作失败");
        }
        return json;
    }

    /**
     * 查看报销详情
     * @return
     */
     @RequestMapping(params = "getReimburseDetails",method = RequestMethod.GET)
     public ModelAndView getReimburseDetails(String reimburseId){
         ModelAndView m=new ModelAndView("hrm/reimburse/reimburselistupdate");
         try {
             TSReimburse tsReimburse= reimburseService.findReimburse(reimburseId);
             List<TSReimDetails> tsReimDetails=reimburseService.findReimDetails(reimburseId);
             TSUser user=workHourService.getUserById(tsReimburse.getUserId());
             List<Map<String,Object>> list=reimburseService.findAllowance();
             List<ExpenseProject> list2=reimburseService.getProjectList();
             Map<String,Object> country=reimburseService.findCountryByName(tsReimburse.getCountryName().trim());
             ExpenseProject p1=reimburseService.findByProjectName(tsReimburse.getProjectNmae().trim());
             ExpenseProject p2=reimburseService.findByProjectName(tsReimburse.getExpendName().trim());
             String startTime="";
             String endTime="";
             if(tsReimburse.getStartTime()!=null){
                  startTime=DateUtil.formatDateTime2(tsReimburse.getStartTime().getTime());
             }
             if(tsReimburse.getEndTime()!=null){
                  endTime=DateUtil.formatDateTime2(tsReimburse.getEndTime().getTime());
             }
             String currentDate=DateUtil.formatDate(tsReimburse.getApplyDate().getTime());

             List<TSReimDetailsVO> reimDetailsVOList=new ArrayList<TSReimDetailsVO>();
             TSReimDetailsVO t=null;
             for(TSReimDetails s:tsReimDetails){
                t=new TSReimDetailsVO();
                t.setCostFee(s.getCostFee());
                t.setCreateDate(DateUtil.formatDate(s.getCreateDate().getTime()));
                t.setDetails(s.getDetails());
                t.setExpenseType(s.getExpenseType());
                t.setReimburseId(s.getReimburseId());
                reimDetailsVOList.add(t);
             }
             m.addObject("startTime",startTime);
             m.addObject("endTime",endTime);
             m.addObject("currentDate",currentDate);
             m.addObject("user", user);
             m.addObject("reimburseId", reimburseId);
             m.addObject("tsReimburse", tsReimburse);
             m.addObject("tsReimDetails", reimDetailsVOList);
             m.addObject("countryList", list);
             m.addObject("projectList", list2);
             m.addObject("country", country);
             m.addObject("projectCode1", p1.getCode());
             m.addObject("projectCode2", p2.getCode());
         }catch (Exception e){
             logger.error("find reimburse details is error ",e);
         }
         return m;
     }


    /**
     * 查询报销集合
     * @return
     */
    @RequestMapping(params = "getReimburseList",method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String,Object>> getReimburseList(String userId, int page, int rows, Date startDate, Date endDate){
        List<Map<String,Object>> list=null;
        try {
            list=reimburseService.getReimburseList(userId,page,rows,startDate,endDate);
        }catch (Exception e){
            logger.error("get reimburse list is error ",e);
        }
        return list;
    }

    /**
     * 导出pdf
     * @return
     */
    @RequestMapping(params = "exportReimbursePDF",method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportReimbursePDF(String reimburseId){
        TSReimburse tsReimburse= reimburseService.findReimburse(reimburseId);
        List<TSReimDetails> tsReimDetails=reimburseService.findReimDetails(reimburseId);
        return null;
    }

    /*
    查询国家
     */
    @RequestMapping(params = "getCountryList")
    @ResponseBody
    public Object getCountryList(){
        return JSON.toJSON(reimburseService.findAllowance());
    }

    @RequestMapping(params = "gotoReim",method = RequestMethod.GET)
    public ModelAndView test(){

        ModelAndView m=new ModelAndView("hrm/reimburse/reimburselist");

        return m;
    }

    @RequestMapping(params = "addReim",method = RequestMethod.GET)
    public ModelAndView addReim(HttpSession session){
        ModelAndView m=new ModelAndView("hrm/reimburse/reimburselistadd");
//        int isResponsPerson=0;
        try {
            List<Map<String,Object>> list=reimburseService.findAllowance();
            List<ExpenseProject> list2=reimburseService.getProjectList();
           /* Client client = ClientManager.getInstance().getClient(session.getId());
            TSUser user =client.getUser();
            List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
            List<TSUser> userList=reimburseService.findAllUser();
            for (TSRoleUser roleUser : rUsers){
                if ("部门负责人".equals(roleUser.getTSRole().getRoleName())){
                    isResponsPerson = 1;
                    break;
                }
                if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                    isResponsPerson = 1;
                    break;
                }
                if ("ceo".equals(roleUser.getTSRole().getRoleName())){
                    isResponsPerson = 1;
                    break;
                }
                if ("管理员".equals(roleUser.getTSRole().getRoleName())){
                    isResponsPerson = 1;
                    break;
                }
                if ("行政".equals(roleUser.getTSRole().getRoleName())){
                    isResponsPerson = 1;
                    break;
                }
            }*/
            m.addObject("countryList", list);
            m.addObject("projectList", list2);
//            m.addObject("isResponsPerson", isResponsPerson);
//            m.addObject("userList", userList);

        }catch (Exception e){
            logger.error("go to add reim is error ",e);
        }

        return m;
    }

    @RequestMapping(params = "exportPDF",method = RequestMethod.GET)
     public void createPdf(HttpServletResponse response,String reimburseId) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String newName = "reimburse.pdf";  //导出pdf的名称

        try {
            TSReimburse tsReimburse = reimburseService.findReimburse(reimburseId);
            List<TSReimDetails> tsReimDetails = reimburseService.findReimDetails(reimburseId);
            TSUser user = workHourService.getUserById(tsReimburse.getUserId());
//        Document document = new Document(PageSize.A4.rotate());//横向
            Document document = new Document(PageSize.A4);//纵向
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + newName);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont);
            Font font2 = new Font(baseFont,18);
            Paragraph paragraph = new Paragraph(" 报销申请单 ", font2);
            document.add(paragraph);


            float[] columnWidths = {1f, 3f, 1f, 3f};
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(5f);
            table.setSpacingAfter(5f);
            table.setWidths(columnWidths);
            PdfPCell[] cells1 = new PdfPCell[columnWidths.length];
            cells1[1] = new PdfPCell(new Paragraph("新钶电子（上海）有限公司 ", font));
            cells1[3] = new PdfPCell(new Paragraph("费用报销单 ", font));
            cells1[0] = new PdfPCell(new Paragraph());
            cells1[2] = new PdfPCell(new Paragraph());


            PdfPCell[] cells2 = new PdfPCell[columnWidths.length];
            PdfPCell[] cells3 = new PdfPCell[columnWidths.length];
            PdfPCell[] cells4 = new PdfPCell[columnWidths.length];
            PdfPCell[] cells5 = new PdfPCell[columnWidths.length];
            PdfPCell[] cells6 = new PdfPCell[columnWidths.length];
            PdfPCell[] cells7 = new PdfPCell[columnWidths.length];

            cells2[0] = new PdfPCell(new Paragraph("姓名", font));
            cells2[1] = new PdfPCell(new Paragraph(user.getUserName(), font));
            cells2[2] = new PdfPCell(new Paragraph("工号", font));
            cells2[3] = new PdfPCell(new Paragraph(user.getJobNum()));
            cells3[0] = new PdfPCell(new Paragraph("部门", font));
            cells3[1] = new PdfPCell(new Paragraph(user.getDepartment(), font));
            cells3[2] = new PdfPCell(new Paragraph("日期", font));
            cells3[3] = new PdfPCell(new Paragraph(DateUtil.formatDate(tsReimburse.getApplyDate().getTime())));
            cells4[0] = new PdfPCell(new Paragraph("出差开始时间", font));
            cells4[1] = new PdfPCell(new Paragraph(DateUtil.formatDateTime2(tsReimburse.getStartTime().getTime())));
            cells4[2] = new PdfPCell(new Paragraph("出差结束时间", font));
            cells4[3] = new PdfPCell(new Paragraph(DateUtil.formatDateTime2(tsReimburse.getEndTime().getTime())));
            cells5[0] = new PdfPCell(new Paragraph("实际产生项目", font));
            cells5[1] = new PdfPCell(new Paragraph(tsReimburse.getProjectNmae(), font));
            cells5[2] = new PdfPCell(new Paragraph("报销支出项目", font));
            cells5[3] = new PdfPCell(new Paragraph(tsReimburse.getExpendName(), font));
            cells6[0] = new PdfPCell(new Paragraph("成本中心号", font));
            cells6[1] = new PdfPCell(new Paragraph(tsReimburse.getCostCenterCode()));
            cells6[2] = new PdfPCell(new Paragraph("出差地点", font));
            cells6[3] = new PdfPCell(new Paragraph(tsReimburse.getCountryName(), font));
            cells7[0] = new PdfPCell(new Paragraph("补贴金额", font));
            cells7[1] = new PdfPCell(new Paragraph(String.valueOf(tsReimburse.getAllowanceFee().intValue())));
            cells7[2] = new PdfPCell(new Paragraph("货币类型", font));
            cells7[3] = new PdfPCell(new Paragraph(tsReimburse.getCurrency(), font));
            for (int i = 0; i < columnWidths.length; i++) {
                cells2[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                cells2[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells3[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                cells3[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                cells4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells5[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                cells5[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells6[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                cells6[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells7[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                cells7[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
            }
            table.addCell(cells1[0]);
            table.addCell(cells1[1]);
            table.addCell(cells1[2]);
            table.addCell(cells1[3]);
            table.addCell(cells2[0]);
            table.addCell(cells2[1]);
            table.addCell(cells2[2]);
            table.addCell(cells2[3]);
            table.addCell(cells3[0]);
            table.addCell(cells3[1]);
            table.addCell(cells3[2]);
            table.addCell(cells3[3]);
            table.addCell(cells4[0]);
            table.addCell(cells4[1]);
            table.addCell(cells4[2]);
            table.addCell(cells4[3]);
            table.addCell(cells5[0]);
            table.addCell(cells5[1]);
            table.addCell(cells5[2]);
            table.addCell(cells5[3]);
            table.addCell(cells6[0]);
            table.addCell(cells6[1]);
            table.addCell(cells6[2]);
            table.addCell(cells6[3]);
            table.addCell(cells7[0]);
            table.addCell(cells7[1]);
            table.addCell(cells7[2]);
            table.addCell(cells7[3]);
            document.add(table);

            String[] head = {"日期", "费用类型", "简述", "金额"};
            float[] columnWidths2 = {2f, 1f, 6f, 1f};
            PdfPTable table2 = new PdfPTable(head.length);
            table2.setWidthPercentage(100);
            table2.setSpacingBefore(5f);
            table2.setSpacingAfter(5f);
            List<PdfPRow> listRow2 = table2.getRows();
            table2.setWidths(columnWidths2);

            PdfPCell[] cellshead = new PdfPCell[head.length];
            for (int i = 0; i < head.length; i++) {
                cellshead[i] = new PdfPCell(new Paragraph(head[i], font));
                cellshead[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                cellshead[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
            }
            PdfPRow rowhead = new PdfPRow(cellshead);
            listRow2.add(rowhead);

            PdfPCell[] cells = null;
            double totalAmount = 0;
            for (TSReimDetails r : tsReimDetails) {
                cells = new PdfPCell[head.length];

                cells[0] = new PdfPCell(new Paragraph(r.getCreateDate().toString()));
                cells[1] = new PdfPCell(new Paragraph(typeIntToString(r.getExpenseType()), font));
                cells[2] = new PdfPCell(new Paragraph(r.getDetails(), font));
                cells[3] = new PdfPCell(new Paragraph(r.getCostFee().toString()));

                PdfPRow row = new PdfPRow(cells);
                listRow2.add(row);
                totalAmount = totalAmount + r.getCostFee().doubleValue();
            }
            totalAmount = totalAmount + tsReimburse.getAllowanceFee().doubleValue();

            document.add(table2);
            Paragraph p1 = new Paragraph("总金额 : " + String.valueOf(totalAmount), font2);
            p1.setAlignment(Element.ALIGN_RIGHT);
            document.add(p1);
            document.close();
        } catch (Exception e) {
            logger.error("Error on createPdf ", e);
        }
    }
    public String  typeIntToString(int type){
        String str="";
        if(type==1){
            str="交通";
        }else if(type==2){
            str="酒店";
        }else if(type==3){
            str="通讯";
        }else if(type==4){
            str="补贴";
        }else {
            str="其它";
        }
        return str;
    }
    @RequestMapping(params = "closeAdd",method = RequestMethod.GET)
    public ModelAndView closeAdd(){
        ModelAndView m=new ModelAndView("hrm/reimburse/reimburselist");
        return m;
    }

    @RequestMapping(params = "exportEXL",method = RequestMethod.GET)
    public void exportEXL(HttpServletResponse response,String reimburseId){
        TSReimburse tsReimburse = reimburseService.findReimburse(reimburseId);
        List<TSReimDetails> tsReimDetails = reimburseService.findReimDetails(reimburseId);
        TSUser user = workHourService.getUserById(tsReimburse.getUserId());
        String projectCode1=reimburseService.findByProjectName(tsReimburse.getProjectNmae().trim()).getCode();
        String projectCode2=reimburseService.findByProjectName(tsReimburse.getExpendName().trim()).getCode();
//        Document document = new Document(PageSize.A4.rotate());//横向
        Document document = new Document(PageSize.A4);//纵向
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=expense.xls" );
        InputStream inputStream=null;
        OutputStream outputStream=null;
        try {
            inputStream=excelService.createExcelFile(user, tsReimburse, tsReimDetails,projectCode1,projectCode2);
            HSSFWorkbook book = new HSSFWorkbook(inputStream);
            outputStream=response.getOutputStream();
            book.write(outputStream);
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            logger.error("export reimburse execl is error ",e);
        }
    }
    @RequestMapping(params = "deleteById",method = RequestMethod.GET)
    public ModelAndView deleteById(String reimburseId){
        ModelAndView m=new ModelAndView("hrm/reimburse/reimburselist");
           try {
               reimburseService.deleteById(reimburseId);
           }catch (Exception e){
               logger.error("delete reimburse is error {}" ,e);
           }
           return m;
    }

    @RequestMapping(params = "findProjectCode",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson findProjectCode(@RequestBody Map<String,String> map){
        String projectName=map.get("projectName");
        AjaxJson ajaxJson=new AjaxJson();
        ExpenseProject p=reimburseService.findByProjectName(projectName);
        ajaxJson.setObj(p);
        return ajaxJson;
    }

    /**
     * 增加权限操作
     * @param userId
     * @param page
     * @param rows
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(params = "getReimburseList2",method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String,Object>> getReimburseList2(String userId, int page, int rows, Date startDate, Date endDate,String userName){
        List<Map<String,Object>> list=null;
        int isResponsPerson=0;
        try {
            List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", userId);
            for (TSRoleUser roleUser : rUsers){
                if ("部门负责人".equals(roleUser.getTSRole().getRoleName())){
                    isResponsPerson = 1;
                    break;
                }
                if ("部门经理".equals(roleUser.getTSRole().getRoleName())){
                    isResponsPerson = 1;
                    break;
                }
                if ("ceo".equals(roleUser.getTSRole().getRoleName())){
                    isResponsPerson = 1;
                    break;
                }
                if ("管理员".equals(roleUser.getTSRole().getRoleName())){
                    isResponsPerson = 1;
                    break;
                }
                if ("行政".equals(roleUser.getTSRole().getRoleName())){
                    isResponsPerson = 1;
                    break;
                }
            }
            if(isResponsPerson==1){
                list=reimburseService.getReimburseListByAll(page,rows,userName,startDate,endDate);
            }else{
                list=reimburseService.getReimburseList(userId,page,rows,startDate,endDate);
            }
        }catch (Exception e){
            logger.error("get reimburse list is error ",e);
        }
        return list;
    }
}
