package excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Desc * @param
 * @return
 * @date:
 * @Author
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZhuZi {
    private Long id;
    private String name;
    private String sex;
    private String hobby;
    private Date birthday;
}
