package excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Desc * @param
 * @return
 * @date:
 * @Author
 */
public class SimpleDemo {
    public static void writeDemo() {
        List<ZhuZi> zhuZis = new ArrayList<>();
        ZhuZi zhuZi = new ZhuZi(1L, "竹子", "男", "熊猫", new Date());
        zhuZis.add(zhuZi);

        // 可以写绝对路径，没有绝对路径默认放在当前目录下
        String fileName = "竹子数据-" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, ZhuZi.class).sheet("竹子数据").doWrite(zhuZis);
    }

    public static void main(String[] args) {
        writeDemo();
    }

}
