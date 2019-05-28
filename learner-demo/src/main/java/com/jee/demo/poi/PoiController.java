package com.jee.demo.poi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/15
 * @Version:v1.0
 */
@RestController
@RequestMapping(value = "/poi")
public class PoiController {

    //导出excel表
    @RequestMapping("/export")
    public void exportExcel(HttpServletResponse response) throws Exception {

        // 初始化HttpServletResponse对象
        //HttpServletResponse response = ServletActionContext.getResponse();

        // 定义表的标题
        String title = "测试列表";

        //定义表的列名
        String[] rowsName = new String[] { "员工编号", "姓名" };

        //定义表的内容
        List<Object[]> dataList = new ArrayList<Object[]>();
        List<Person> list = list();
        Object[] objs = null;
        for (int i = 0; i < list.size(); i++) {
            Person per = list.get(i);
            objs = new Object[rowsName.length];
            objs[0] = per.getId();
            objs[1] = per.getName();
            dataList.add(objs);
        }

        ExportUtils.exportExcel("员工表",title, rowsName, dataList, response);
    }

    //导入excel表中的数据
    /*public String importExcel() throws Exception {

        // 初始化HttpServletRequest对象
        HttpServletRequest request = ServletActionContext.getRequest();

        // 获得文件名
        String filename = getMyfileFileName();

        // 上传文件到服务器中
        filename = FileUpload2.upload(filename, myfile);

        Person per = new Person();// 新建一个per对象
        Dept dept = new Dept();// 新建一个dept对象

        // 获取服务器中文件的路径
        String path = request.getSession().getServletContext().getRealPath("")
                + "/upload/" + filename;

        try {
            InputStream is = new FileInputStream(path);//将路径转为输入流对象 输入流对象可以取出来读取
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);//将输入流对象存到工作簿对象里面

            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }

                // 循环行Row
                for (int rowNum = 3; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow == null) {
                        continue;
                    }

                    // 循环列Cell
                    // "姓名","密码","性别","爱好","简介","部门did"};
                    per.setPname(getValue(hssfRow.getCell(1)));
                    per.setPsex(getValue(hssfRow.getCell(2)));
                    per.setSkilled(getValue(hssfRow.getCell(3)));
                    per.setDegree(getValue(hssfRow.getCell(4)));
                    String value = getValue(hssfRow.getCell(5));
                    SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd");
                    Date parse = da.parse(value);
                    per.setJobtime(parse);
                    per.setResume(getValue(hssfRow.getCell(6)));
                    per.setFilepath(getValue(hssfRow.getCell(7)));
                    //这里很重要，通过部门列表然后与excel中的部门字段进行对比，匹配后获取对应的did
                    String dname = getValue(hssfRow.getCell(8));//获取excel中的部门字段
                    list=ds.list();//得到数据库中的部门列表
                    for (Dept dd : list) {//增强for循环
                        if (dd.getDname().equals(dname)) {//如果两者匹配
                            dept.setDid(dd.getDid());//则得到对应的did，并设置dept对象的did
                            per.setDept(dept);//再把dept对象设置到user对象中
                        }
                    }

                    ps.add(per);//写入到数据中
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return "tolist";//返回列表显示
    }*/

    /**
     * 得到Excel表中的值
     *
     * @param
     * @return Excel中每一个格子中的值
     */
    @SuppressWarnings("static-access")
   /* private static String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }

    }*/

    public List<Person> list(){
        List<Person> list = new ArrayList<>(5);
        for (int i=0; i<5; i++){
            Person person = new Person();
            person.setId(Long.valueOf(i));
            person.setName("user" + i);
            list.add(person);
        }
        return list;
    }
}

