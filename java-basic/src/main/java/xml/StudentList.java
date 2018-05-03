package xml;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午11:09 2018/5/3
 * https://www.cnblogs.com/liuk/p/5829389.html
 */

@XmlRootElement(name="list")
public class StudentList {

  List<Student> students;    //所有学生信息的集合

  @XmlElement(name = "student")
  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

}
